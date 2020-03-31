package com.smartworker.smartworker.orders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartworker.smartworker.R;

public class ShowOrderBeforAdd extends AppCompatActivity {

    TextView tb_Category,tb_Cases,tb_Time,tb_Date,tb_Description;
    ImageView tb_image;
    Button Add_Order,btn_back;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_befor_add);

        order = new Order();



        tb_Category=(TextView)findViewById(R.id.tb_category);
        tb_Cases=(TextView)findViewById(R.id.tb_case);
        tb_Time=(TextView)findViewById(R.id.tb_time);
        tb_Date=(TextView)findViewById(R.id.tb_date);
        tb_Description=(TextView)findViewById(R.id.tb_description);
        tb_image=(ImageView)findViewById(R.id.tb_image);
        btn_back=(Button)findViewById(R.id.back);
        Add_Order=(Button)findViewById(R.id.btn_add);
        btn_back=(Button)findViewById(R.id.back);






        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
