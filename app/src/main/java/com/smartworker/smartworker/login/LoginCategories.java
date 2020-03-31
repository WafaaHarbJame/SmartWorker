package com.smartworker.smartworker.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.smartworker.smartworker.R;

public class LoginCategories extends AppCompatActivity {

    LinearLayout customer,worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_categories);

        customer = (LinearLayout)findViewById(R.id.Customer);
        worker = (LinearLayout)findViewById(R.id.Worker);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),RegisterCustomer.class);
                startActivity(in);
            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),RegisterWorker.class);
                startActivity(in);
            }
        });



    }
}
