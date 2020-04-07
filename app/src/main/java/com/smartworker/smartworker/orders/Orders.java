package com.smartworker.smartworker.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.MainActivity;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.account.Profile;
import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Login;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {

    int user_id;
    int membership;
    TextView total,wait,progress,done;
    Button btn_profile,sign_out;
    int t,w,p,d = 0;
    ListView lv;
    List<Order> list;
    DbOperation_Users db_user;
    DbOperation_Orders db_orders;


    int Item_postion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        user_id = getIntent().getIntExtra("user_id",0);
        db_user = new DbOperation_Users(this);
        db_orders = new DbOperation_Orders(this);
        membership = db_user.getMember(user_id);

        list = new ArrayList<Order>();
        list = db_orders.getALLOrders(user_id,membership);

        Log.e("wgetMEMBER_SHIP","getMEMBER_SHIP"+"\n "+ db_user.getMember(user_id));

        total = (TextView)findViewById(R.id.total);
        wait = (TextView)findViewById(R.id.wait);
        progress = (TextView)findViewById(R.id.progress);
        done = (TextView)findViewById(R.id.done);
        btn_profile = (Button)findViewById(R.id.profile);
        sign_out = (Button)findViewById(R.id.sign_out);
        lv = (ListView)findViewById(R.id.list_orders);


        for(int i=0;i<list.size();i++){
            Order order = new Order();
            order = list.get(i);
            if(order.getState() == 1){
                w += 1;
            }else if(order.getState() == 2){
                p += 1;
            }else {
                d += 1;
            }
            t +=1;
        }
        total.setText(t+"");
        progress.setText(p+"");
        wait.setText(w+"");
        done.setText(d+"");

        final Order_Adapter m_ad = new Order_Adapter(this,list);
        lv.setAdapter(m_ad);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item_postion = position;
                registerForContextMenu(view);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Profile.class);
                in.putExtra("user_id",user_id);
                startActivity(in);
            }
        });
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedMy();
            }
        });


    }
    public void onBackPressed() {
        int member = db_user.getMember(user_id);
        if (member != 0) {
            onBackPressedMy();
        } else {
            super.onBackPressed();
        }

    }
    public void onBackPressedMy(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you wont to sign out ?").setCancelable(false)
                .setTitle("Worning")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(getApplicationContext(), Login.class);
                        startActivity(in);
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
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.context_order,menu);
//    }
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.show:
//                Order order = new Order();
//                order = list.get(Item_postion);
//                Intent in = new Intent(getApplicationContext(),ShowOrder.class);
//              // in.putExtra("user_id",user_id);
//                in.putExtra("order_id",order.getId());
//                in.putExtra("user_id",user_id);
//                startActivity(in);
//                Log.e("user_id not order","user_id not order"+user_id);
//                Log.e("order_id","order_id"+order.getId());
//                Log.e("user_id","user_id"+order.getUser_id());
//                break;
//            case R.id.delete:
//
//                break;
//
//        }
//        return super.onContextItemSelected(item);
//    }


/* */
}
