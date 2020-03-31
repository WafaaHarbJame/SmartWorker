package com.smartworker.smartworker.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.AddPrice;
import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.Utile;
import com.smartworker.smartworker.db.DbOperation_Users;

public class ShowOrder extends AppCompatActivity {

    int order_id,user_id;
    boolean goMain = false;
    int acc = 0;
    String state;
    TextView tb_category,tb_case,tb_time,tb_date,tb_description,tb_price,tb_time_accept,tb_date_accept,tb_state,tb_command,tb_order_id;
    ConstraintLayout l_state,l_price,l_time_accept,l_date_accept,l_command;
    Button btn_back_customer,btn_back_worker,btn_add_price,btn_accept,btn_remove,btn_remove_by_customer;
    LinearLayout action_but;
    ImageView tb_image;
    Order order;
    DbOperation_Orders db_order;
    DbOperation_Jops db_jop;
    DbOperation_Users db_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        order_id = getIntent().getIntExtra("order_id",-1);
        user_id = getIntent().getIntExtra("user_id",-1);
        goMain = getIntent().getBooleanExtra("goMain",false);

        if(order_id == -1){
            onBackPressed();
            Toast.makeText(getApplicationContext(),"NOT FOUND",Toast.LENGTH_SHORT).show();
        }
        tb_category = (TextView)findViewById(R.id.tb_category);
        tb_case = (TextView)findViewById(R.id.tb_case);
        tb_time = (TextView)findViewById(R.id.tb_time);
        tb_date = (TextView)findViewById(R.id.tb_date);
        tb_description = (TextView)findViewById(R.id.tb_description);

        tb_price = (TextView)findViewById(R.id.tb_price);
        tb_time_accept = (TextView)findViewById(R.id.tb_time_accept);
        tb_date_accept = (TextView)findViewById(R.id.tb_date_accept);
        tb_state = (TextView)findViewById(R.id.tb_state);
        tb_command = (TextView)findViewById(R.id.tb_command);
        tb_order_id = (TextView)findViewById(R.id.tb_order_id);

        tb_image = (ImageView)findViewById(R.id.tb_image);
        btn_back_customer = (Button)findViewById(R.id.back_customer);
        btn_back_worker = (Button)findViewById(R.id.back_worker);
        btn_add_price = (Button)findViewById(R.id.btn_add_price);
        btn_accept = (Button)findViewById(R.id.btn_accept);
        btn_remove = (Button)findViewById(R.id.btn_remove);
        btn_remove_by_customer = (Button)findViewById(R.id.btn_remove_by_customer);

        l_state = (ConstraintLayout)findViewById(R.id.l_state);
        l_price = (ConstraintLayout)findViewById(R.id.l_price);
        l_time_accept = (ConstraintLayout)findViewById(R.id.l_time_accept);
        l_date_accept = (ConstraintLayout)findViewById(R.id.l_date_accept);
        l_command = (ConstraintLayout)findViewById(R.id.l_command);

        action_but = (LinearLayout)findViewById(R.id.action_but);

        db_order = new DbOperation_Orders(this);
        db_jop = new DbOperation_Jops(this);
        db_user = new DbOperation_Users(this);

        btn_back_worker.setVisibility(View.INVISIBLE);
        btn_add_price.setVisibility(View.INVISIBLE);
        btn_remove_by_customer.setVisibility(View.INVISIBLE);
        btn_back_customer.setVisibility(View.INVISIBLE);
        btn_accept.setVisibility(View.INVISIBLE);
        btn_remove.setVisibility(View.INVISIBLE);

        l_price.setVisibility(View.INVISIBLE);
        l_time_accept.setVisibility(View.INVISIBLE);
        l_date_accept.setVisibility(View.INVISIBLE);
        l_command.setVisibility(View.INVISIBLE);

        order = new Order();
        order = db_order.getOrder(order_id);
        acc = order.getAcc();

        tb_order_id.setText(order_id+"");
        tb_category.setText(db_jop.getJopNames(order.getCatagoris()));
        if(order.getCases() == 1){
            tb_case.setText("Urgent");
        }else {
            tb_case.setText("Scheduler");
        }
        tb_time.setText(order.getTime_add());
        tb_date.setText(order.getDate_add());
        tb_description.setText(order.getDescription());
        tb_image.setImageBitmap(Utile.getImage(order.getImage()));


        if(db_user.getMember(user_id) == 0 && acc == 0 && order.getState() == 1){
            btn_remove_by_customer.setVisibility(View.VISIBLE);
            btn_back_customer.setVisibility(View.VISIBLE);
            state = "In Wait..";
            tb_state.setText(state);
        }else if(db_user.getMember(user_id) == 0 && acc == 1 && order.getState() == 1){
            btn_back_customer.setVisibility(View.VISIBLE);
            btn_accept.setVisibility(View.VISIBLE);
            btn_remove.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice()+"");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Wait..";
            tb_state.setText(state);
        }else if(db_user.getMember(user_id) == 0 && order.getState() == 2){
            btn_back_customer.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice()+"");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Progress..";
            tb_state.setText(state);
        }else if(db_user.getMember(user_id) == 1 && acc == 0 && order.getState() == 1){
            btn_back_worker.setVisibility(View.VISIBLE);
            btn_add_price.setVisibility(View.VISIBLE);
            state = "In Wait..";
            tb_state.setText(state);
        }else if(db_user.getMember(user_id) == 1 && acc == 1 && order.getState() == 1){
            btn_back_worker.setVisibility(View.VISIBLE);
            btn_remove_by_customer.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice()+"");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Wait..";
            tb_state.setText(state);
        }else if(db_user.getMember(user_id) == 1 && order.getState() == 2){
            btn_back_worker.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice()+"");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Progress..";
            tb_state.setText(state);
        }else if(order.getState() == 3){
            btn_add_price.setVisibility(View.INVISIBLE);
            btn_remove_by_customer.setVisibility(View.INVISIBLE);
            btn_accept.setVisibility(View.INVISIBLE);
            btn_remove.setVisibility(View.INVISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice()+"");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "Done";
            tb_state.setText(state);
        }

        btn_back_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goMain){
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    in.putExtra("user_id",order.getUser_id());
                    startActivity(in);
                }else {
                    onBackPressed();
                }

            }
        });

        btn_back_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_add_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), AddPrice.class);
                in.putExtra("order_id",db_order.getOrderId(order_id));
                in.putExtra("user_id",user_id);
                startActivity(in);
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.deleteOrder(order_id);
                Intent in = new Intent(getApplicationContext(), Orders.class);
                in.putExtra("user_id",user_id);
                startActivity(in);
            }
        });
        btn_remove_by_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.deleteOrder(order_id);
                Intent in = new Intent(getApplicationContext(), Orders.class);
                in.putExtra("user_id",user_id);
                startActivity(in);
            }
        });
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.UpdateToProgress(order_id);
                Intent in = new Intent(getApplicationContext(), ShowOrder.class);
                in.putExtra("order_id",order_id);
                in.putExtra("user_id",user_id);
                startActivity(in);
            }
        });




    }
}
