package com.smartworker.smartworker.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.Utile;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.orders.Orders;

public class Login extends AppCompatActivity {

    EditText phone_number,password;
    TextView forget,rigester;
    Button sign_in;
    ImageView show_password;
    DbOperation_Users db;
    DbOperation_Jops db_j;
    boolean log=false;
    boolean show = false;
    byte [] jop_images = new byte[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget_password);
        rigester = findViewById(R.id.rigester);
        show_password = findViewById(R.id.showpassword);
        sign_in = findViewById(R.id.sign_in);


        db_j = new DbOperation_Jops(this);

        if(db_j.getSize() < 12){    // later will create activity to add jops

            String[] albums = getResources().getStringArray(R.array.fields);

            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.i1);
            Bitmap bitmap1 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap1);
            db_j.insert_jop(albums[0],jop_images);
            iv.setImageResource(R.drawable.i2);
            Bitmap bitmap2 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap2);
            db_j.insert_jop(albums[1],jop_images);
            iv.setImageResource(R.drawable.i3);
            Bitmap bitmap3 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap3);
            db_j.insert_jop(albums[2],jop_images);
            iv.setImageResource(R.drawable.i4);
            Bitmap bitmap4 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap4);
            db_j.insert_jop(albums[3],jop_images);
            iv.setImageResource(R.drawable.i5);
            Bitmap bitmap5 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap5);
            db_j.insert_jop(albums[4],jop_images);
            iv.setImageResource(R.drawable.i6);
            Bitmap bitmap6 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap6);
            db_j.insert_jop(albums[5],jop_images);
            iv.setImageResource(R.drawable.i7);
            Bitmap bitmap7 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap7);
            db_j.insert_jop(albums[6],jop_images);
            iv.setImageResource(R.drawable.i8);
            Bitmap bitmap8 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap8);
            db_j.insert_jop(albums[7],jop_images);
            iv.setImageResource(R.drawable.i9);
            Bitmap bitmap9 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap9);
            db_j.insert_jop(albums[8],jop_images);
            iv.setImageResource(R.drawable.i10);
            Bitmap bitmap10 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap10);
            db_j.insert_jop(albums[9],jop_images);
            iv.setImageResource(R.drawable.i11);
            Bitmap bitmap11 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap11);
            db_j.insert_jop(albums[10],jop_images);
            iv.setImageResource(R.drawable.i12);
            Bitmap bitmap12 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            jop_images = Utile.getbyte(bitmap12);
            db_j.insert_jop(albums[11],jop_images);
        }



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DbOperation_Users(getApplicationContext());
                log = db.getUser(phone_number.getText().toString(),password.getText().toString());
                if(log){
                    Intent in ;
                    if(db.getMember(db.getUser_id(phone_number.getText().toString())) == 1){
                        in = new Intent(getApplicationContext(), Orders.class);
                    }else {
                        in = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    in.putExtra("user_id",db.getUser_id(phone_number.getText().toString()));
                    startActivity(in);
                }else {
                    Toast.makeText(getApplicationContext(),"Check Your Phone Number or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    show_password.setImageResource(R.drawable.ic_hied_password);
                    password.setTransformationMethod(null);
                    show = false;
                }else {
                    show_password.setImageResource(R.drawable.ic_show_password);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    show = true;
                }
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),CheckPassword.class);
                startActivity(in);
            }
        });
        rigester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),LoginCategories.class);
                startActivity(in);
            }
        });


    }
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you wont to Exit ?").setCancelable(false)
                .setTitle("Worning")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
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
