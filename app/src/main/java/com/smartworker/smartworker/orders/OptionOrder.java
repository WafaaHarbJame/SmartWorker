package com.smartworker.smartworker.orders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smartworker.smartworker.BaseActivity;
import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.account.Profile;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.Utile;

import java.util.Calendar;

public class OptionOrder extends BaseActivity {

    private static final int SELECT_PHOTO = 7777;

    int User_id, Worker_id, state;
    int cases = 1;

    EditText Description;

    RadioGroup Group;
    TextView Tv_Time, Tv_Date;
    boolean show;
    ImageView Add_Image;
    Button Add_time, Add_Date, Add_Order, Update_Order, btn_back, btn_profile;

    DbOperation_Users db_user;
    DbOperation_Jops db_jop;
    DbOperation_Orders db_order;
    String catogory;
    int jop_id;
    String selectedUri;
    int order_id;
    boolean ShowProfile;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_option_order);

        User_id = getIntent().getIntExtra("user_id", 0);
        Worker_id = getIntent().getIntExtra("worker_id", 0);
        state = getIntent().getIntExtra("state", -1);
        catogory = getIntent().getStringExtra("job_name");
        jop_id = getIntent().getIntExtra("jop_id", 0);
        ShowProfile=getIntent().getBooleanExtra("ShowProfile",false);
//        Toast.makeText(OptionOrder.this, "OptionOrder " + jop_id, Toast.LENGTH_SHORT).show();


        
        Description = (EditText) findViewById(R.id.description);

        Group = (RadioGroup) findViewById(R.id.group);
        Tv_Time = (TextView) findViewById(R.id.tv_time);
        Tv_Date = (TextView) findViewById(R.id.tv_date);
        Add_Image = (ImageView) findViewById(R.id.add_image);
        Add_time = (Button) findViewById(R.id.time_piker);
        Add_Date = (Button) findViewById(R.id.calender);
        Add_Order = (Button) findViewById(R.id.btn_add);
        Update_Order = (Button) findViewById(R.id.btn_update);
        btn_back = (Button) findViewById(R.id.back);
        btn_profile = (Button) findViewById(R.id.profile);


        db_user = new DbOperation_Users(this);
        db_jop = new DbOperation_Jops(this);
        db_order = new DbOperation_Orders(this);

        if (state == 1) {
            Update_Order.setVisibility(View.VISIBLE);
            Add_Order.setVisibility(View.GONE);
             order_id= getIntent().getIntExtra("order_id", 0);
            Order order=db_order.getOrder(order_id);
            Description.setText(order.getDescription());
            Tv_Time.setText(order.getTime_add());
            Tv_Date.setText(order.getDate_add());
            order_id=order.getId();

        } else {
            Update_Order.setVisibility(View.GONE);
            Add_Order.setVisibility(View.VISIBLE);
//        }
            }




        Update_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order=db_order.getOrder(order_id);
                if (Description.length() < 20) {
                    Toast.makeText(getApplicationContext(), "You must describe your needs", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tv_Time.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must choose time of work", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tv_Date.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must choose date of work", Toast.LENGTH_SHORT).show();
                    return;
                }

                order.setUser_id(User_id);
                order.setWorker_id(order.worker_id);
                order.setCatagoris(order.getCatagoris());
                order.setCases(order.getCases());
                order.setTime_add(Tv_Time.getText().toString());
                order.setDate_add(Tv_Date.getText().toString());
                order.setDescription(Description.getText().toString());
                order.setState(1);
                order.setAcc(0);
                if(order.getImageUri()!=null){
                    order.setImageUri(order.getImageUri());

                }
                if (selectedUri == null) {
//                    Add_Image.setImageResource(R.drawable.ic_image_defalt);
//                    order.setImage(convertBitmapToByte());
                    order.setImageUri(null);
                } else {
//                    order.setImage(convertBitmapToByte());
                    order.setImageUri(selectedUri);
                }
                boolean added = db_order.Updateorder(order,order_id);
                if(added){
                    Toast.makeText(getApplicationContext(),"Order Updated",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),ShowOrder.class);
                    in.putExtra("order_id",order_id);
                    in.putExtra("user_id",User_id);
                    startActivity(in);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Order NOT Updated",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogViewTime();
            }
        });
        Add_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewDate();
            }
        });

        Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton U = (RadioButton) findViewById(checkedId);
                if (U.getText().toString().equalsIgnoreCase("Urgent")) {
                    cases = 1;
                } else {
                    cases = 2;
                }

            }
        });

        Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                in.setType("image/*");
                startActivityForResult(in, SELECT_PHOTO);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(User_id==Worker_id){
            show=true;
        }
        else {
            show=false;

        }


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Profile.class);
               // Toast.makeText(OptionOrder.this, "Worker_id"+Worker_id, Toast.LENGTH_SHORT).show();
                in.putExtra("user_id", Worker_id);
                in.putExtra("user_id_check", User_id);
                in.putExtra("show", show);
                in.putExtra("FROMadapter",true);
                startActivity(in);
            }
        });

        Add_Order.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (Description.length() < 20) {
                    Toast.makeText(getApplicationContext(), "You must describe your needs", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tv_Time.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must choose time of work", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tv_Date.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must choose date of work", Toast.LENGTH_SHORT).show();
                    return;
                }
                Order order = new Order();
                order.setUser_id(User_id);
                order.setWorker_id(Worker_id);
                order.setCatagoris(jop_id);
                order.setCases(cases);
                order.setTime_add(Tv_Time.getText().toString());
                order.setDate_add(Tv_Date.getText().toString());
                order.setDescription(Description.getText().toString());
                order.setState(1);
                order.setAcc(0);
                if (selectedUri == null) {
                    order.setImageUri(order.getImageUri());
//                    Add_Image.setImageResource(R.drawable.ic_image_defalt);
//                    order.setImage(convertBitmapToByte());
                    order.setImageUri(null);
                } else {
//                    order.setImage(convertBitmapToByte());
                    order.setImageUri(selectedUri);

                }
                boolean add = db_order.insert(order);
                if (add) {
                    Toast.makeText(getApplicationContext(), "Order added Successfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(), ShowOrder.class);
                    in.putExtra("order_id", db_order.getOrderIdLast());
                    in.putExtra("user_id", User_id);
                    in.putExtra("goMain", true);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Order Not added ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pick_image = data.getData();
            Add_Image.setImageURI(pick_image);
            selectedUri = pick_image.toString();
        }
    }

    public byte[] convertBitmapToByte() {
        Bitmap bitmap = ((BitmapDrawable) Add_Image.getDrawable()).getBitmap();
        return Utile.getbyte(bitmap);
    }

    public void dialogViewTime() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (hourOfDay > 12) {
                    Tv_Time.setText(hourOfDay - 12 + ":" + minute + " PM");

                } else {
                    Tv_Time.setText(hourOfDay + ":" + minute + " AM");
                }


            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void dialogViewDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Tv_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public int getState() {
        return state;
    }
}
