package com.smartworker.smartworker.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.Utile;
import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.orders.Orders;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Login;
import com.smartworker.smartworker.login.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Profile extends AppCompatActivity {

    int user_id ;
    int member;
    boolean show = false;
    DbOperation_Users db_user;
    DbOperation_Jops db_jop;
    User user = new User();
    int int_membership;
    TextView first_name,lastname,phone,membership,location,orders_unm,jop_name;
    LinearLayout jop;
    ImageView image_profile;
    DbOperation_Orders db_orders;
    int Number_of_order;

    Button btn_back,btn_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_id = getIntent().getIntExtra("user_id",0);
        Log.e("user_idProfile","Profile"+user_id);
        show = getIntent().getBooleanExtra("show",false);
        first_name = (TextView)findViewById(R.id.tv_first_name);
        lastname = (TextView)findViewById(R.id.tv_last_name);
        phone = (TextView)findViewById(R.id.tv_phone_number);
        location = (TextView)findViewById(R.id.tv_location);
        orders_unm = (TextView)findViewById(R.id.tv_orders);
        jop_name = (TextView)findViewById(R.id.tv_jop_name);
        membership = (TextView)findViewById(R.id.tv_membership);
        jop = (LinearLayout)findViewById(R.id.jop);
        image_profile=(ImageView)findViewById(R.id.profile_image);
        db_user = new DbOperation_Users(this);
        db_orders = new DbOperation_Orders(this);
        int_membership = db_user.getMember(user_id);

        btn_back=(Button)findViewById(R.id.back);
        btn_setting=(Button)findViewById(R.id.setting);

        db_user = new DbOperation_Users(this);
        db_jop = new DbOperation_Jops(this);

        user = db_user.getUser_Info(user_id);
        member = db_user.getMember(user_id);

        int_membership = db_user.getMember(user_id);
        if(db_orders.getALLOrdersCOUNT(user_id,int_membership)>0){
            Number_of_order=db_orders.getALLOrdersCOUNT(user_id,int_membership);
            Toast.makeText(this, "Number_of_order"+Number_of_order, Toast.LENGTH_SHORT).show();


        }

        if(user.getMEMBER_SHIP() == 0){
            jop.setVisibility(View.GONE);
            membership.setText("Customer");
        }else {
            membership.setText("Worker");
            jop_name.setText(db_jop.getJopNames(user.getJOP_ID()));
        }
        if(show){
            btn_setting.setVisibility(View.GONE);
        }
        Log.e("user_id profile","user_id profile"+user_id);

        first_name.setText(user.getFARST_NAME());
        lastname.setText(user.getLAST_NAME());
        phone.setText(user.getPHONE_NUMBER());
        if(user.getIMAGE()!=null){
            image_profile.setImageBitmap(Utile.getImage(user.getIMAGE()));

        }
        location.setText(user.getMAP());
        Log.e("city_id", "city_idDB" + user.getCity_id());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    onBackPressed();
                }else {
                    onBackPressedMy();
                }
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        orders_unm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), Orders.class);
                iin.putExtra("user_id",user_id);
                Log.e("orders_unm","Profile"+user_id);

                startActivity(iin);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.setting_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.edit:
                        Intent in = new Intent(getApplicationContext(), EditProfile.class);
                        in.putExtra("user_id",user_id);
                        startActivity(in);
                        return true;
                    case R.id.my_massage:

                        return true;
                    case R.id.my_orders:
                        Intent iin = new Intent(getApplicationContext(), Orders.class);
                        iin.putExtra("user_id",user_id);
                        startActivity(iin);
                        return true;
                    case R.id.remove:
                        RemoveAccount();
                        return true;

                    case R.id.sign_out:
                        SignOut();
                        return false;

                    default:
                        return false;
                }

            }
        });
        popup.show();
    }


    public void onBackPressedMy() {
        int member = db_user.getMember(user_id);
        if(member == 0){
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            in.putExtra("user_id",user_id);
            startActivity(in);
        }else {
            Intent in = new Intent(getApplicationContext(), Orders.class);
            in.putExtra("user_id",user_id);
            startActivity(in);
        }
    }
    public void SignOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setMessage("Are you sure you wont to Sign out ?").setCancelable(false)
                .setTitle("Worning")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"You Sign Out",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), Login.class);
                        startActivity(in);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void RemoveAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setMessage("Are you sure you wont to Remove Account ?").setCancelable(false)
                .setTitle("Worning")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db_user.delete(user_id);
                        Toast.makeText(getApplicationContext(),"Account Deleted",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), Login.class);
                        startActivity(in);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


