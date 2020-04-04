package com.smartworker.smartworker.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smartworker.smartworker.AppConstants;
import com.smartworker.smartworker.Cities;
import com.smartworker.smartworker.MyWorkerLocationActivity;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.Utile;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.orders.Orders;

import org.json.JSONObject;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterWorker extends AppCompatActivity {

    private static final int SELECT_PHOTO = 7777;
    private static final int LOCATION_CODE = 100;

    EditText FIRST_NAME, LAST_NAME, PHONE_NUMBER, PASSWORD, CONFIREM_PASSWORD;
    Button SIGN_UP;
    DbOperation_Users db_user;
    DbOperation_Jops db_jops;
    boolean log = true;
    Spinner fileds;
    int jop_id;
    String location;
    ImageView IMAGE_SELECT;
    CircleImageView IMAGE_USER;
    Double longitude_d;
    Double latitude_d;
    int city_id = 1;
    String city_name;
    List<String> city_list = new ArrayList<>();
    List<Cities> citiesList = new ArrayList<>();
    List<Cities> citiesListArray = new ArrayList<>();
    private TextView mMyLocation;
    private Spinner mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_worker);

        FIRST_NAME = (EditText) findViewById(R.id.first_name);
        LAST_NAME = (EditText) findViewById(R.id.last_name);
        PHONE_NUMBER = (EditText) findViewById(R.id.phone_number);
        PASSWORD = (EditText) findViewById(R.id.input_password);
        CONFIREM_PASSWORD = (EditText) findViewById(R.id.Confirm_password);
        SIGN_UP = (Button) findViewById(R.id.sign_up);
        fileds = (Spinner) findViewById(R.id.filed);
        IMAGE_SELECT = (ImageView) findViewById(R.id.select_image);
        IMAGE_USER = (CircleImageView) findViewById(R.id.customer_image);


        location = "Saudi Arabia,Riyadh,45/55";     //will tack from google map

        db_user = new DbOperation_Users(this);
        db_jops = new DbOperation_Jops(this);

        mMyLocation = findViewById(R.id.my_location);
        mCity = findViewById(R.id.city);
        // String[] cities = getResources().getStringArray(R.array.cities);


//
//        for (int i = 0; i <cities.length ; i++) {
//            city_list.add(cities[i]);
//            Collections.sort(city_list);
//
//
//        }
        city_list.clear();
        citiesList = db_jops.getALLCities();
        for (int i = 0; i < citiesList.size(); i++) {
            int id = citiesList.get(i).getId();
            String name = citiesList.get(i).getName();
            Double LAT = citiesList.get(i).getLatitude();
            Double LNG = citiesList.get(i).getLongitude();
            citiesListArray.add(new Cities(id, name, LAT, LNG));
            city_list.add(name);
            Log.e("wafaa", "" + citiesList.get(i).getLatitude());


        }


        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, db_jops.getALLJops_names());
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad.notifyDataSetChanged();
        fileds.setAdapter(ad);


        //cities
        //  city_list= db_jops.getALLCities_names();


        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city_list);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.notifyDataSetChanged();
        mCity.setAdapter(cityAdapter);


        mMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterWorker.this, MyWorkerLocationActivity.class);
                startActivityForResult(intent, LOCATION_CODE);

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


        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city_id = citiesListArray.get(position).getId();
                city_name = citiesListArray.get(position).getName();
                double lat = citiesListArray.get(position).getLatitude();
                Toast.makeText(RegisterWorker.this, "lat" + lat, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SIGN_UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    if (FIRST_NAME.getText().toString().isEmpty()) {
                        FIRST_NAME.setFocusable(true);
                        FIRST_NAME.setBackgroundResource(R.drawable.background_error);
                        log = false;
                    } else {
                        FIRST_NAME.setBackgroundResource(R.drawable.background_toinput);
                        log = true;
                    }
                    if (LAST_NAME.getText().toString().isEmpty()) {
                        LAST_NAME.setFocusable(true);
                        LAST_NAME.setBackgroundResource(R.drawable.background_error);
                        log = false;
                    } else {
                        LAST_NAME.setBackgroundResource(R.drawable.background_toinput);
                        log = true;
                    }


                    if (mMyLocation.getText().toString().isEmpty()) {
                        mMyLocation.setFocusable(true);
                        mMyLocation.setBackgroundResource(R.drawable.background_error);
                        log = false;
                    } else {
                        mMyLocation.setBackgroundResource(R.drawable.background_toinput);
                        log = true;
                    }
                    if (PHONE_NUMBER.getText().toString().isEmpty()) {
                        PHONE_NUMBER.setFocusable(true);
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                        log = false;
                    } else {
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_toinput);
                        log = true;
                    }
                    if (PASSWORD.getText().toString().isEmpty() || CONFIREM_PASSWORD.getText().toString().isEmpty() || !PASSWORD.getText().toString().equalsIgnoreCase(CONFIREM_PASSWORD.getText().toString())) {
                        PASSWORD.setFocusable(true);
                        PASSWORD.setBackgroundResource(R.drawable.background_error);
                        CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_error);
                        log = false;
                    } else {
                        PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                        CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                        log = true;
                    }
                    if (db_user.isRigester(PHONE_NUMBER.getText().toString())) {
                        PHONE_NUMBER.setFocusable(true);
                        PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                        log = false;
                        Toast.makeText(getApplicationContext(), "The Phone Number is Register", Toast.LENGTH_SHORT).show();
                    }
                    if (!log) return;
                }


                User user = new User();
                user.setFARST_NAME(FIRST_NAME.getText().toString());
                user.setLAST_NAME(LAST_NAME.getText().toString());
                user.setPHONE_NUMBER(PHONE_NUMBER.getText().toString());
                user.setPASSWORD(PASSWORD.getText().toString());
                user.setLatitude(latitude_d);
                user.setLongitude(longitude_d);
                user.setMEMBER_SHIP(1);
                user.setJOP_ID(jop_id);
                System.out.println("Log city id " + city_id);
                user.setCity_id(city_id);
                user.setCity_name(city_name);
                user.setIMAGE(convertBitmapToByte());
                user.setMAP(location);
                boolean isSuccess = db_user.insert(user);

                Toast.makeText(getApplicationContext(), "isSuccess " + isSuccess, Toast.LENGTH_SHORT).show();

                Intent in = new Intent(getApplicationContext(), Orders.class);
                in.putExtra("user_id", db_user.getUser_id(PHONE_NUMBER.getText().toString()));
                startActivity(in);
            }
        });

        FIRST_NAME.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (FIRST_NAME.getText().length() < 3 || FIRST_NAME.getText().length() > 16) {
                    FIRST_NAME.setBackgroundResource(R.drawable.background_error);
                    log = false;
                } else {
                    FIRST_NAME.setBackgroundResource(R.drawable.background_toinput);
                    log = true;
                }
            }
        });
        LAST_NAME.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (LAST_NAME.getText().length() < 3 || LAST_NAME.getText().length() > 16) {
                    LAST_NAME.setBackgroundResource(R.drawable.background_error);
                    log = false;
                } else {
                    LAST_NAME.setBackgroundResource(R.drawable.background_toinput);
                    log = true;
                }
            }
        });
        PHONE_NUMBER.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PHONE_NUMBER.getText().length() == 14) {
                    PHONE_NUMBER.setBackgroundResource(R.drawable.background_toinput);
                    log = true;
                } else {
                    PHONE_NUMBER.setBackgroundResource(R.drawable.background_error);
                    log = false;
                }
            }
        });
        PASSWORD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PASSWORD.getText().length() < 8 || PASSWORD.getText().length() > 16) {
                    PASSWORD.setBackgroundResource(R.drawable.background_error);
                    log = false;
                }
            }
        });
        CONFIREM_PASSWORD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PASSWORD.getText().toString().equalsIgnoreCase(CONFIREM_PASSWORD.getText().toString()) && (PASSWORD.getText().length() >= 8 || PASSWORD.getText().length() <= 16)) {
                    CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                    PASSWORD.setBackgroundResource(R.drawable.background_toinput);
                    CONFIREM_PASSWORD.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirmpassword, 0, R.drawable.ic_check_true, 0);
                    log = true;
                } else {
                    CONFIREM_PASSWORD.setBackgroundResource(R.drawable.background_error);
                    CONFIREM_PASSWORD.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_confirmpassword, 0, R.drawable.ic_check_false, 0);
                    log = false;
                }
            }
        });


        IMAGE_SELECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, SELECT_PHOTO);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pick_image = data.getData();
            IMAGE_USER.setImageURI(pick_image);
        }


        if (requestCode == LOCATION_CODE && resultCode == RESULT_OK) {
            //Write your code if there's no result
            longitude_d = data.getExtras().getDouble("latitude_d");
            latitude_d = data.getExtras().getDouble("latitude");
            location = data.getStringExtra(AppConstants.location);

            Bundle extras = data.getExtras();
            latitude_d = extras.getDouble("latitude");
            longitude_d = extras.getDouble("longitude");

            Toast.makeText(this, "onActivityResult" + latitude_d, Toast.LENGTH_SHORT).show();
            location = location;
            mMyLocation.setText(location);


        }
    }

    public byte[] convertBitmapToByte() {
        Bitmap bitmap = ((BitmapDrawable) IMAGE_USER.getDrawable()).getBitmap();
        return Utile.getbyte(bitmap);
    }
}
