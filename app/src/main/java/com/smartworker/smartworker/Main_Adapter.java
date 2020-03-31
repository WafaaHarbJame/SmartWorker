package com.smartworker.smartworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.smartworker.smartworker.login.Jop;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_Adapter extends BaseAdapter implements Filterable {

    Context context;
    List<Jop> list;
    List<Jop> orgList;

    public Main_Adapter(Context context, List<Jop> list) {
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
            myView=   layoutInflater.inflate(R.layout.to_addapter_items,parent,false);

            ViewHolder vh= new ViewHolder();

            vh.image  =myView.findViewById(R.id.image_view);
            vh.tv = myView.findViewById(R.id.jop_name);
            myView.setTag(vh);
        } else {
            myView=convertView;
        }

        Jop j=list.get(position);
        ViewHolder vh=(ViewHolder)myView.getTag();
        vh.image.setImageBitmap(Utile.getImage(j.getImage()));
        vh.tv.setText(j.getName());
        return myView;
    }


    class ViewHolder{
        CircleImageView image ;
        TextView tv  ;
    }

    @Override
    public Filter getFilter() {
        Filter MyFilter= new Filter() {

            FilterResults result = new FilterResults();

            @Override
            protected FilterResults performFiltering(CharSequence s) {
                ArrayList<Jop> temps=new ArrayList<Jop>();
                CharSequence nn = s;
                for(int i=0;i<orgList.size();i++){
                    Jop j=orgList.get(i);
                    String myID = j.getId()+"";
                    for (int y=0;y<s.length();y++){
                        nn = s.toString().toUpperCase();
                    }
                    if(j.getName().contains(s)||j.getName().contains(nn)|| myID.contains(s)){
                        temps.add(j);
                    }
                }
                result.values=temps;
                result.count=temps.size();
                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list=(ArrayList)results.values;
                notifyDataSetChanged();


            }
        };


        return MyFilter;
    }
}
