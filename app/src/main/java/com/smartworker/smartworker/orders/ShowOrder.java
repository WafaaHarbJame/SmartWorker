package com.smartworker.smartworker.orders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
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

    int order_id, user_id;
    boolean goMain = false;
    int acc = 0;
    String state;
    TextView tb_category, tb_case, tb_time, tb_date, tb_description, tb_price, tb_time_accept, tb_date_accept, tb_state, tb_command, tb_order_id;
    ConstraintLayout l_state, l_price, l_time_accept, l_date_accept, l_command;
    Button btn_back_customer, btn_back_worker, btn_add_price, btn_accept, btn_remove, btn_remove_by_customer;
    LinearLayout action_but;
    ImageView tb_image;
    Order order;
    DbOperation_Orders db_order;
    DbOperation_Jops db_jop;
    DbOperation_Users db_user;
    int membership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        order_id = getIntent().getIntExtra("order_id", -1);
        user_id = getIntent().getIntExtra("user_id", -1);

        goMain = getIntent().getBooleanExtra("goMain", false);
        db_user = new DbOperation_Users(this);

        membership = db_user.getMember(user_id);

        if (order_id == -1) {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        tb_category = findViewById(R.id.tb_category);
        tb_case = findViewById(R.id.tb_case);
        tb_time = findViewById(R.id.tb_time);
        tb_date = findViewById(R.id.tb_date);
        tb_description = findViewById(R.id.tb_description);

        tb_price = findViewById(R.id.tb_price);
        tb_time_accept = findViewById(R.id.tb_time_accept);
        tb_date_accept = findViewById(R.id.tb_date_accept);
        tb_state = findViewById(R.id.tb_state);
        tb_command = findViewById(R.id.tb_command);
        tb_order_id = findViewById(R.id.tb_order_id);

        tb_image = findViewById(R.id.tb_image);
        btn_back_customer = findViewById(R.id.back_customer);
        btn_back_worker = findViewById(R.id.back_worker);
        btn_add_price = findViewById(R.id.btn_add_price);
        btn_accept = findViewById(R.id.btn_accept);
        btn_remove = findViewById(R.id.btn_remove);
        btn_remove_by_customer = findViewById(R.id.btn_remove_by_customer);

        l_state = findViewById(R.id.l_state);
        l_price = findViewById(R.id.l_price);
        l_time_accept = findViewById(R.id.l_time_accept);
        l_date_accept = findViewById(R.id.l_date_accept);
        l_command = findViewById(R.id.l_command);

        action_but = findViewById(R.id.action_but);

        db_order = new DbOperation_Orders(this);
        db_jop = new DbOperation_Jops(this);
        db_user = new DbOperation_Users(this);

        btn_back_worker.setVisibility(View.GONE);
        btn_add_price.setVisibility(View.GONE);
        btn_remove_by_customer.setVisibility(View.GONE);
        btn_back_customer.setVisibility(View.GONE);
        btn_accept.setVisibility(View.GONE);
        btn_remove.setVisibility(View.GONE);

        l_price.setVisibility(View.GONE);
        l_time_accept.setVisibility(View.GONE);
        l_date_accept.setVisibility(View.GONE);
        l_command.setVisibility(View.GONE);

        order = new Order();
        order = db_order.getOrder(order_id);
        acc = order.getAcc();


        String jobName = db_jop.getJopNames(order.getCatagoris());
        tb_order_id.setText(String.valueOf(order_id));
        tb_category.setText(jobName);
        if (order.getCases() == 1) {
            tb_case.setText("Urgent");
        } else {
            tb_case.setText("Scheduler");
        }
        tb_time.setText(order.getTime_add());
        tb_date.setText(order.getDate_add());
        tb_description.setText(order.getDescription());
        if(order.getImageUri()!=null) {
            tb_image.setImageURI(Uri.parse(order.getImageUri()));
        }

        if (db_user.getMember(user_id) == 0 && acc == 0 && order.getState() == 1) {
            btn_remove_by_customer.setVisibility(View.VISIBLE);
            btn_back_customer.setVisibility(View.VISIBLE);
            state = "In Wait..";
            tb_state.setText(state);
        } else if (db_user.getMember(user_id) == 0 && acc == 1 && order.getState() == 1) {
            btn_back_customer.setVisibility(View.VISIBLE);
            btn_accept.setVisibility(View.VISIBLE);
            btn_remove.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice() + "");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Wait..";
            tb_state.setText(state);
        } else if (db_user.getMember(user_id) == 0 && order.getState() == 2) {
            btn_back_customer.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice() + "");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Progress..";
            tb_state.setText(state);
        } else if (db_user.getMember(user_id) == 1 && acc == 0 && order.getState() == 1) {
            btn_back_worker.setVisibility(View.VISIBLE);
            btn_add_price.setVisibility(View.VISIBLE);
            state = "In Wait..";
            tb_state.setText(state);
        } else if (db_user.getMember(user_id) == 1 && acc == 1 && order.getState() == 1) {
            btn_back_worker.setVisibility(View.VISIBLE);
            btn_remove_by_customer.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice() + "");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Wait..";
            tb_state.setText(state);
        } else if (db_user.getMember(user_id) == 1 && order.getState() == 2) {
            btn_back_worker.setVisibility(View.VISIBLE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice() + "");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "In Progress..";
            tb_state.setText(state);
        } else if (order.getState() == 3) {
            btn_add_price.setVisibility(View.GONE);
            btn_remove_by_customer.setVisibility(View.GONE);
            btn_accept.setVisibility(View.GONE);
            btn_remove.setVisibility(View.GONE);
            l_price.setVisibility(View.VISIBLE);
            l_time_accept.setVisibility(View.VISIBLE);
            l_date_accept.setVisibility(View.VISIBLE);
            l_command.setVisibility(View.VISIBLE);
            tb_price.setText(order.getPrice() + "");
            tb_time_accept.setText(order.getTime_accept());
            tb_date_accept.setText(order.getDate_accept());
            tb_command.setText(order.getCommand());
            state = "Done";
            tb_state.setText(state);
        }

        btn_back_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goMain) {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    in.putExtra("user_id", order.getUser_id());
                    startActivity(in);
                } else {
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
                in.putExtra("order_id", db_order.getOrderId(order_id));
                in.putExtra("user_id", user_id);
                startActivity(in);
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.deleteOrder(order_id);
                Intent in = new Intent(getApplicationContext(), Orders.class);
                in.putExtra("user_id", user_id);
                startActivity(in);
            }
        });
        btn_remove_by_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.deleteOrder(order_id);
                Intent in = new Intent(getApplicationContext(), Orders.class);
                in.putExtra("user_id", user_id);
                startActivity(in);
            }
        });
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_order.UpdateToProgress(order_id);
                Intent in = new Intent(getApplicationContext(), ShowOrder.class);
                in.putExtra("order_id", order_id);
                in.putExtra("user_id", user_id);
                startActivity(in);
            }
        });


    }

}
