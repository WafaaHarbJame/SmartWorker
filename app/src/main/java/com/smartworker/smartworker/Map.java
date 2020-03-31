package com.smartworker.smartworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.User;
import com.smartworker.smartworker.orders.OptionOrder;

public class Map extends AppCompatActivity {

    int user_id,worker_id;
    String name;
    LinearLayout worker;
    TextView jop_name;
    Button btn_confirm,btn_back;
    ImageView image_worker;

    DbOperation_Users db_user;
    DbOperation_Jops db_jop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        user_id = getIntent().getIntExtra("user_id",0);

        worker = (LinearLayout)findViewById(R.id.worker_active);
        jop_name = (TextView)findViewById(R.id.jop_name);
        btn_confirm = (Button)findViewById(R.id.confirm);
        image_worker = (ImageView)findViewById(R.id.worker);
        btn_back=(Button)findViewById(R.id.back);

        btn_confirm.setVisibility(View.INVISIBLE);
        db_user = new DbOperation_Users(this);
        db_jop = new DbOperation_Jops(this);
        User user = new User();
        user = db_user.getUser_Info(user_id);
        name = db_jop.getJopNames(user.getJOP_ID());
        jop_name.setText(name);


        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_worker.setImageResource(R.drawable.ic_map_worker_active);
                btn_confirm.setVisibility(View.VISIBLE);

                worker_id = 3; // Will get from map later
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), OptionOrder.class);
                in.putExtra("user_id",user_id);
                in.putExtra("worker_id",worker_id);
                in.putExtra("state",1);
                startActivity(in);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
