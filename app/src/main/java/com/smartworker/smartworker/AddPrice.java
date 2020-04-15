package com.smartworker.smartworker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.orders.Order;
import com.smartworker.smartworker.orders.ShowOrder;

import java.util.Calendar;

public class AddPrice extends BaseActivity {


    int order_id,user_id;

    EditText price,command;
    Button btn_time,btn_date,btn_add,back;
    TextView tv_time,tv_date;

    DbOperation_Orders db_order;
    DbOperation_Users db_user;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        order_id = getIntent().getIntExtra("order_id",-1);
        user_id = getIntent().getIntExtra("user_id",-1);

        price = (EditText)findViewById(R.id.price);
        command = (EditText)findViewById(R.id.command);
        btn_time = (Button)findViewById(R.id.time_piker);
        btn_date = (Button)findViewById(R.id.calender);
        btn_add = (Button)findViewById(R.id.btn_add);
        back = (Button)findViewById(R.id.back);
        tv_time = (TextView)findViewById(R.id.time_accept);
        tv_date = (TextView)findViewById(R.id.date_accept);


        db_order = new DbOperation_Orders(this);
        db_user = new DbOperation_Users(this);


        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogViewTime();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewDate();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(price.getText().toString()) == 0){
                    Toast.makeText(getApplicationContext(),"You must choose your price",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tv_time.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"You must choose time of work",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tv_date.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"You must choose date of work",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(command.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"You must add command",Toast.LENGTH_SHORT).show();
                    return;
                }

                Order order = new Order();
                order.setPrice(Integer.parseInt(price.getText().toString()));
                order.setCommand(command.getText().toString());
                order.setTime_accept(tv_time.getText().toString());
                order.setDate_accept(tv_date.getText().toString());
                order.setState(1);
                order.setAcc(1);
                boolean added = db_order.Update(order,order_id);
                if(added){
                    Toast.makeText(getApplicationContext(),"Order Updated",Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getApplicationContext(),ShowOrder.class);
                    in.putExtra("order_id",db_order.getOrderIdLast());
                    in.putExtra("user_id",user_id);
                    startActivity(in);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Order NOT Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public void dialogViewTime(){

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if(hourOfDay > 12){
                            tv_time.setText(hourOfDay-12 + ":" + minute+" PM");

                        }else {
                            tv_time.setText(hourOfDay + ":" + minute+" AM");
                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    public void dialogViewDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
