package com.smartworker.smartworker.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartworker.smartworker.R;
import com.smartworker.smartworker.account.Profile;
import com.smartworker.smartworker.db.DbOperation_Users;

public class CheckPassword extends AppCompatActivity {

    String PhoneNumber, NewPassword, ConfirmPassword;
    private EditText mPhoneNumber;
    private EditText mNewPassword;
    private EditText mConfirmPassword;
    private Button mSave;
    DbOperation_Users db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        mPhoneNumber = findViewById(R.id.phone_number);
        mNewPassword = findViewById(R.id.newPassword);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        mSave = findViewById(R.id.save);
        PhoneNumber = mPhoneNumber.getText().toString();
        NewPassword = mNewPassword.getText().toString();
        ConfirmPassword = mConfirmPassword.getText().toString();
        db=new DbOperation_Users(this);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhoneNumber.getText().toString() == null |mPhoneNumber.getText().toString().equals("")) {
                    mPhoneNumber.setError("Enter Phone Number");
                    mPhoneNumber.requestFocus();
                }
                else if (mNewPassword.getText().toString()==null|mNewPassword.getText().toString().equals("")) {
                    mNewPassword.setError("Enter New Password");
                    mNewPassword.requestFocus();

                }
                else if ( mConfirmPassword.getText().toString().equals("")|  mConfirmPassword.getText().toString()==null) {
                    mConfirmPassword.setError("Enter Confirm  Password");
                    mConfirmPassword.requestFocus();

                }
                else if (!ConfirmPassword.equals(NewPassword)) {
                    mNewPassword.setError("New Password and confirm Not Matching ");
                    mNewPassword.requestFocus();

                }
                else {
                    int id =db.getUser_id(mPhoneNumber.getText().toString());
                    User user=db.getUser_Info(id);
                    int user_id=user.getID();
                    int member_id=user.getMEMBER_SHIP();
                    User updateUser =new User();
                    updateUser.setPASSWORD(mNewPassword.getText().toString());
                    updateUser.setID(user_id);
                    updateUser.setCity_id(user.getCity_id());
                    updateUser.setCity_name(user.getCity_name());
                    updateUser.setLongitude(user.getLongitude());
                    updateUser.setLatitude(user.getLatitude());
                    updateUser.setFARST_NAME(user.getFARST_NAME());
                    updateUser.setLAST_NAME(user.getLAST_NAME());
                    updateUser.setIMAGE(user.getIMAGE());
                    updateUser.setPHONE_NUMBER(user.getPHONE_NUMBER());
                    updateUser.setMEMBER_SHIP(user.getMEMBER_SHIP());
                    if (member_id == 0) {
                        boolean updated = db.RestoredPassward(updateUser);
                        if (updated) {
                            Toast.makeText(getApplicationContext(), "Password Restored", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(), Login.class);
                            in.putExtra("user_id", user_id);
                            startActivity(in);

                        } else {
                            Toast.makeText(getApplicationContext(), "Password not  Restored", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Check From Your Data", Toast.LENGTH_SHORT).show();

                        }


                    }

                    else {
                        updateUser.setJOP_ID(user.getJOP_ID());
                        boolean updated = db.RestoredPassward(updateUser);
                        if (updated) {
                            Intent in = new Intent(getApplicationContext(), Login.class);
                            in.putExtra("user_id", user_id);
                            startActivity(in);
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), "Password not  Restored", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Check From Your Data", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });




    }
}
