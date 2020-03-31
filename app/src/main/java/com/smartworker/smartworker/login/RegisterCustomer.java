package com.smartworker.smartworker.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.smartworker.smartworker.Utile;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterCustomer extends AppCompatActivity {

    private static final int SELECT_PHOTO = 7777;

    EditText FIRST_NAME,LAST_NAME,PHONE_NUMBER,PASSWORD,CONFIREM_PASSWORD;
    Button SIGN_UP;
    DbOperation_Users db_user;
    boolean log=true;
    String location;

    ImageView IMAGE_SELECT;
    CircleImageView IMAGE_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        FIRST_NAME = (EditText)findViewById(R.id.first_name);
        LAST_NAME = (EditText)findViewById(R.id.last_name);
        PHONE_NUMBER = (EditText)findViewById(R.id.phone_number);
        PASSWORD = (EditText)findViewById(R.id.input_password);
        CONFIREM_PASSWORD = (EditText)findViewById(R.id.Confirm_password);
        SIGN_UP = (Button) findViewById(R.id.sign_up);
        IMAGE_SELECT = (ImageView)findViewById(R.id.select_image);
        IMAGE_USER = (CircleImageView)findViewById(R.id.customer_image);


        location = "Saudi Arabia,Riyadh,45/55";     //will tack from google map

        db_user = new DbOperation_Users(this);



        SIGN_UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    if(FIRST_NAME.getText().toString().isEmpty()){
                        FIRST_NAME.setFocusable(true);
                        FIRST_NAME.setBackgroundResource(R.drawable.background_error);
                        log=false;
                    }else {
                        FIRST_NAME.setBackgroundResource(R.drawable.background_toinput);
                        log=true;
                    }
                    if(LAST_NAME.getText().toString().isEmpty()){
                        LAST_NAME.setFocusable(true);
                        LAST_NAME.setBackgroundResource(R.drawable.background_error);
                        log=false;
                    }else {
                        LAST_NAME.setBackgroundResource(R.drawable.background_toinput);
                        log=true;
                    }
                    if(PHONE_NUMBER.getText().toString().isEmpty()){
                        PHONE_NUMBER.setFocusable(true);
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                        log=false;
                    }else {
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_toinput);
                        log=true;
                    }
                    if(PASSWORD.getText().toString().isEmpty() || CONFIREM_PASSWORD.getText().toString().isEmpty()||!PASSWORD.getText().toString().equalsIgnoreCase(CONFIREM_PASSWORD.getText().toString())){
                        PASSWORD.setFocusable(true);
                        PASSWORD.setBackgroundResource(R.drawable.background_error);
                        CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_error);
                        log=false;
                    }else {
                        PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                        CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                        log=true;
                    }
                    if(db_user.isRigester(PHONE_NUMBER.getText().toString())){
                        PHONE_NUMBER.setFocusable(true);
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                        log=false;
                        Toast.makeText(getApplicationContext(),"The Phone Number is Register",Toast.LENGTH_SHORT).show();
                    }

                    if(!log) return;
                }


                User user = new User();
                user.setFARST_NAME(FIRST_NAME.getText().toString());
                user.setLAST_NAME(LAST_NAME.getText().toString());
                user.setPHONE_NUMBER(PHONE_NUMBER.getText().toString());
                user.setPASSWORD(PASSWORD.getText().toString());
                user.setMEMBER_SHIP(0);
                user.setIMAGE(convertBitmapToByte());
                user.setMAP(location);
                db_user.insert(user);
                Toast.makeText(getApplicationContext(),"INSETR",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.putExtra("user_id",db_user.getUser_id(PHONE_NUMBER.getText().toString()));
                startActivity(in);
            }
        });

        FIRST_NAME.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(FIRST_NAME.getText().length() < 3 || FIRST_NAME.getText().length() > 16){
                    FIRST_NAME.setBackgroundResource(R.drawable.background_error);
                    log=false;
                }else {
                    FIRST_NAME.setBackgroundResource(R.drawable.background_toinput);
                    log=true;
                }
            }
        });
        LAST_NAME.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(LAST_NAME.getText().length() < 3 || LAST_NAME.getText().length() > 16){
                    LAST_NAME.setBackgroundResource(R.drawable.background_error);
                    log=false;
                }else {
                    LAST_NAME.setBackgroundResource(R.drawable.background_toinput);
                    log=true;
                }
            }
        });
        PHONE_NUMBER.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( PHONE_NUMBER.getText().length() == 14){
                    PHONE_NUMBER.setBackgroundResource(R.drawable.background_toinput);
                    log=true;
                }else {
                    PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                    log=false;
                }
            }
        });
        PASSWORD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(PASSWORD.getText().length() < 8 || PASSWORD.getText().length() > 16){
                    PASSWORD.setBackgroundResource(R.drawable.background_error);
                    log=false;
                }
            }
        });
        CONFIREM_PASSWORD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(PASSWORD.getText().toString().equalsIgnoreCase(CONFIREM_PASSWORD.getText().toString()) && (PASSWORD.getText().length() >= 8 || PASSWORD.getText().length() <= 16)){
                    CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                    PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                    CONFIREM_PASSWORD.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirmpassword, 0, R.drawable.ic_check_true, 0);
                    log=true;
                }else {
                    CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_error);
                    CONFIREM_PASSWORD.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirmpassword, 0, R.drawable.ic_check_false, 0);
                    log=false;
                }
            }
        });


        IMAGE_SELECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in,SELECT_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null){
            Uri pick_image = data.getData();
            IMAGE_USER.setImageURI(pick_image);
        }
    }

    public byte[] convertBitmapToByte(){
        Bitmap bitmap = ((BitmapDrawable)IMAGE_USER.getDrawable()).getBitmap();
        return Utile.getbyte(bitmap);
    }
}
