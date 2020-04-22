package com.smartworker.smartworker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartworker.smartworker.Cities;
import com.smartworker.smartworker.login.Jop;

import java.util.ArrayList;
import java.util.List;

public class DbOperation_Jops {

    static final String DATABASENAME = "SmartWorker.db";
    static final String Table = "JOPS";
    static final String CitiesTable = "CITIES";

    Context context;
    SQLiteDatabase db;


    public DbOperation_Jops(Context context){
        CreateOPenHelperSQL dbase=new CreateOPenHelperSQL(context,DATABASENAME,null,8);
        this.context=context;

        db=dbase.getWritableDatabase();
    }

    public boolean insert_jop(String s,byte[] i){
            ContentValues cv = new ContentValues();
            cv.put("JOP_NAME",s);
            cv.put("IMAGE_SRC",i);
            long inserted = db.insert(Table,null,cv);

        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }




    public List<String> getALLCities_names(){
        Cursor cursor=db.rawQuery("select * from CITIES order by CITY_NAME ",null);
        cursor.moveToFirst();
        List<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()){
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }



    public boolean insert_new_cities(String CITY_NAME,Double latitude,Double longitude ){
        ContentValues cv = new ContentValues();
        cv.put("CITY_NAME",CITY_NAME);
        cv.put("LONGITUDE",longitude);
        cv.put("LATITUDE",latitude);
        long inserted = db.insert(CitiesTable,null,cv);

        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean insert_cities(String[] s){
        long inserted = 0;
        for(int i=0;i<s.length;i++){
            ContentValues cv = new ContentValues();
            cv.put("CITY_NAME",s[i]);
//            cv.put("LAT",s[i]);
//            cv.put("LNG",s[i]);
            inserted = db.insert(CitiesTable,null,cv);

        }
        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean insert_jops(String[] s){
        long inserted = 0;
        for(int i=0;i<s.length;i++){
            ContentValues cv = new ContentValues();
            cv.put("JOP_NAME",s[i]);
            inserted = db.insert(Table,null,cv);

        }
        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }
    public List<String> getALLJops_names(){
        Cursor cursor=db.rawQuery("select * from JOPS",null);
        cursor.moveToFirst();
        List<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()){
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public int getJopID(String name){
        Cursor cursor=db.rawQuery("select * from JOPS where JOP_NAME like '"+name+"'",null);
        int jop_id = -1;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            jop_id = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return jop_id;
    }

    public List<Jop> getALLJops(){
        Cursor cursor=db.rawQuery("select * from JOPS",null);
        cursor.moveToFirst();
        List<Jop> list = new ArrayList<Jop>();
        while(!cursor.isAfterLast()){
            Jop jop_info = new Jop();
            jop_info.setId(cursor.getInt(0));
            jop_info.setName(cursor.getString(1));
            jop_info.setImage(cursor.getBlob(2));
            list.add(jop_info);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public List<Cities> getALLCities(){
        Cursor cursor=db.rawQuery("select * from CITIES order by CITY_NAME ",null);
        cursor.moveToFirst();
        List<Cities> list = new ArrayList<Cities>();
        while(!cursor.isAfterLast()){
            Cities cities = new Cities();
            cities.setId(cursor.getInt(0));
            cities.setName(cursor.getString(1));
            cities.setLatitude(cursor.getDouble(3));
            cities.setLongitude(cursor.getDouble(2));

            list.add(cities);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getJopNames(int id){
        String name = "";
        Cursor cursor=db.rawQuery("select * from "+Table+" where JOP_ID = '"+id+"'",null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            name = cursor.getString(1);
        }
        cursor.close();
        return name;
    }
    public int getSize(){
        int size = 0;
        Cursor cursor=db.rawQuery("select * from JOPS",null);
        size = cursor.getCount();
        return size;
    }
}
