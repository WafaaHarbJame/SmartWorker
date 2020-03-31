package com.smartworker.smartworker.account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.smartworker.smartworker.R;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.User;
import com.smartworker.smartworker.Utile;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    int id ;
    private static final int SELECT_PHOTO = 7777;

    EditText FIRST_NAME,LAST_NAME,PHONE_NUMBER,Time_ST,Time_ED;
    RelativeLayout lay_filed;
    Button SAVE,back;
    DbOperation_Users db_user;
    DbOperation_Jops db_jops;

    String location;
    Spinner fileds,member;
    int jop_id;
    int member_id;
    ImageView IMAGE_SELECT;
    CircleImageView IMAGE_USER;
    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        id = getIntent().getIntExtra("user_id",0);

        FIRST_NAME = (EditText)findViewById(R.id.ed_first_name);
        LAST_NAME = (EditText)findViewById(R.id.ed_last_name);
        PHONE_NUMBER = (EditText)findViewById(R.id.ed_phone_number);
        Time_ST = (EditText)findViewById(R.id.ed_time_start);
        Time_ED = (EditText)findViewById(R.id.ed_time_last);
        SAVE = (Button) findViewById(R.id.ed_save);
        back = (Button) findViewById(R.id.back);
        fileds = (Spinner)findViewById(R.id.ed_filed);
        member = (Spinner)findViewById(R.id.ed_member);
        IMAGE_SELECT = (ImageView)findViewById(R.id.ed_select_image);
        IMAGE_USER = (CircleImageView)findViewById(R.id.ed_customer_image);
        lay_filed = (RelativeLayout)findViewById(R.id.layout_filed);
        db_user = new DbOperation_Users(this);
        db_jops = new DbOperation_Jops(this);


        location = "Saudi Arabia,Riyadh,45/55";     //will tack from google map

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,db_jops.getALLJops_names());
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad.notifyDataSetChanged();
        fileds.setAdapter(ad);

        User user = new User();
        user = db_user.getUser_Info(id);
        member_id = db_user.getMember(id);
        FIRST_NAME.setText(user.getFARST_NAME());
        LAST_NAME.setText(user.getLAST_NAME());
        PHONE_NUMBER.setText(user.getPHONE_NUMBER());
        PHONE_NUMBER.setEnabled(false);
        IMAGE_USER.setImageBitmap(Utile.getImage(user.getIMAGE()));
        jop_id = user.getJOP_ID();
        if(member_id == 0){
            lay_filed.setVisibility(View.INVISIBLE);
            Time_ST.setVisibility(View.INVISIBLE);
            Time_ED.setVisibility(View.INVISIBLE);
            member.setSelection(0);
        }else {
            member.setSelection(1);
            fileds.setSelection(jop_id-1);
            Time_ST.setText("00:00");
            Time_ED.setText("00:00");
        }

        member.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    member_id = 0;
                    lay_filed.setVisibility(View.INVISIBLE);
                    Time_ST.setVisibility(View.INVISIBLE);
                    Time_ED.setVisibility(View.INVISIBLE);
                }else {
                    member_id = 1;
                    lay_filed.setVisibility(View.VISIBLE);
                    Time_ST.setVisibility(View.VISIBLE);
                    Time_ED.setVisibility(View.VISIBLE);
                    fileds.setSelection(jop_id -1);
                    Time_ST.setText("00:00");
                    Time_ED.setText("00:00");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fileds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jop_id = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User();
                u.setID(id);
                u.setIMAGE(convertBitmapToByte());
                u.setFARST_NAME(FIRST_NAME.getText().toString());
                u.setLAST_NAME(LAST_NAME.getText().toString());
                u.setPHONE_NUMBER(PHONE_NUMBER.getText().toString());
                u.setMAP(location);
                u.setMEMBER_SHIP(member_id);
                if(member_id == 0){
                    boolean updated = db_user.Update(u);
                    if(updated){
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(),Profile.class);
                        in.putExtra("user_id",id);
                        startActivity(in);

                    }else {
                        Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Check From Your Data",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    u.setJOP_ID(jop_id);
                    boolean updated = db_user.Update(u);
                    if(updated){
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(),Profile.class);
                        in.putExtra("user_id",id);
                        startActivity(in);
                    }else {
                        Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Check From Your Data",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
