package com.smartworker.smartworker.orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.Main_Adapter;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.SharedPManger;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Orders;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Jop;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Order_Adapter extends BaseAdapter {
    Context context;
    List<Order> list;
    List<Order> orgList;
    DbOperation_Users db_user;
    DbOperation_Orders db_order;
    SharedPManger sharedPManger;
    int user_id;
    String phone;

    public Order_Adapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
        this.orgList = list;
        sharedPManger=new SharedPManger(context);
        db_user=new DbOperation_Users(context);
        db_order= new DbOperation_Orders(context);
        phone=sharedPManger.getDataString("phone");
        user_id=db_user.getUser_id(phone);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position,final View convertView,final ViewGroup parent) {


        View myView = null;
        if (convertView==null){

            LayoutInflater layoutInflater= LayoutInflater.from(context);
            myView=   layoutInflater.inflate(R.layout.to_adapter_order_card,parent,false);
            ViewHolder vh= new ViewHolder();
            vh.catagory  =myView.findViewById(R.id.j_name);
            vh.detailes  =myView.findViewById(R.id.description);
            vh.state  =myView.findViewById(R.id.state);
            vh.ts  =myView.findViewById(R.id.time_start);
            vh.date  =myView.findViewById(R.id.date);
            vh.deleteOrder=myView.findViewById(R.id.deleteOrder);
            vh.updateOrder=myView.findViewById(R.id.updateOrder);
            myView.setTag(vh);
        } else {
            myView=convertView;
        }
         final Order o = list.get(position);
        final DbOperation_Jops db_j = new DbOperation_Jops(context);

        ViewHolder vh=(ViewHolder)myView.getTag();
        vh.catagory.setText(db_j.getJopNames(o.getCatagoris()));
        vh.detailes.setText(o.getDescription());

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,ShowOrder.class);
                in.putExtra("order_id",o.getId());
                in.putExtra("user_id",user_id);
                Log.e("order_id","order_id"+o.getId());
                Log.e("user_id","user_id"+o.getUser_id());
                context.startActivity(in);
            }
        });
        if(o.getState() == 1){
            vh.state.setText("In Wait..");
        }else if(o.getState() == 2){
            vh.state.setText("IN Progress..");
            vh.updateOrder.setVisibility(View.GONE);

        }else {
            vh.state.setText("Done");
            vh.updateOrder.setVisibility(View.GONE);


        }


        vh.deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int membership=db_user.getMember(o.getUser_id());
                if(membership==1){
                    db_order.deleteOrder(o.getId());
                    Intent in = new Intent(context, Orders.class);
                    in.putExtra("user_id", o.getUser_id());
                    in.putExtra("order_id",o.getId());
                    context.startActivity(in);
                    ((Activity)context).finish();

                }
                else {
                    db_order.deleteOrder(o.getId());
                    Intent in = new Intent(context, Orders.class);
                    in.putExtra("user_id", o.getUser_id());
                    in.putExtra("order_id",o.getId());
                    context.startActivity(in);
                    ((Activity)context).finish();

                }

            }
        });

        vh.updateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(context, OptionOrder.class);
                in.putExtra("user_id",user_id);
                in.putExtra("order_id",o.getId());
                in.putExtra("state",o.getState());
                context.startActivity(in);
                ((Activity)context).finish();

            }
        });

        vh.ts.setText(o.getTime_add());

        vh.date.setText(o.getDate_add());
        return myView;
    }


    class ViewHolder{
        TextView catagory,detailes,state,ts,date;
        ImageView deleteOrder,updateOrder;
    }


}
