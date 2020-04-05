package com.smartworker.smartworker.orders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.smartworker.smartworker.Main_Adapter;
import com.smartworker.smartworker.R;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.login.Jop;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Order_Adapter extends BaseAdapter {
    Context context;
    List<Order> list;
    List<Order> orgList;


    public Order_Adapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
        this.orgList = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {

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
            myView.setTag(vh);
        } else {
            myView=convertView;
        }
        DbOperation_Jops db_j = new DbOperation_Jops(context);
        final Order o = list.get(position);
        ViewHolder vh=(ViewHolder)myView.getTag();
        vh.catagory.setText(db_j.getJopNames(o.getCatagoris()));
        vh.detailes.setText(o.getDescription());
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,ShowOrder.class);
                in.putExtra("order_id",o.getId());
                in.putExtra("user_id",o.getUser_id());
                context.startActivity(in);            }
        });
        if(o.getState() == 1){
            vh.state.setText("In Wait..");
        }else if(o.getState() == 2){
            vh.state.setText("IN Progress..");
        }else {
            vh.state.setText("Done");
        }

        vh.ts.setText(o.getTime_add());

        vh.date.setText(o.getDate_add());
        return myView;
    }


    class ViewHolder{
        TextView catagory,detailes,state,ts,date;
    }


}
