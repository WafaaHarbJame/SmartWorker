package com.smartworker.smartworker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.smartworker.smartworker.login.User;

public class DbOperation_Users {

     private static final String DATABASENAME = "SmartWorker.db";
     static final String Table = "USERS";

    Context context;
    SQLiteDatabase db;

    public DbOperation_Users(Context context){
        CreateOPenHelperSQL dbase=new CreateOPenHelperSQL(context,DATABASENAME,null,5);
        this.context=context;
        db=dbase.getWritableDatabase();
    }
    public boolean insert(User user) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME",user.getFARST_NAME());
        cv.put("LAST_NAME",user.getLAST_NAME());
        cv.put("PHONE_NUMBER",user.getPHONE_NUMBER());
        cv.put("PASSWORD",user.getPASSWORD());
        cv.put("MEMBER_SHIP",user.getMEMBER_SHIP());
        cv.put("JOP_ID",user.getJOP_ID());
        cv.put("IMAGE_SRC",user.getIMAGE());
        cv.put("MAP",user.getMAP());
        long inserted = db.insert(Table,null,cv);
        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean Update(User user) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME",user.getFARST_NAME());
        cv.put("LAST_NAME",user.getLAST_NAME());
        cv.put("MEMBER_SHIP",user.getMEMBER_SHIP());
        cv.put("JOP_ID",user.getJOP_ID());
        cv.put("IMAGE_SRC",user.getIMAGE());
        cv.put("MAP",user.getMAP());
        long updated = db.update(Table,cv,"ID=?",new String[]{user.getID()+""});
        if(updated > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean getUser(String phon , String pass){
        Cursor cr = db.rawQuery("select * from "+Table+" where PHONE_NUMBER like '"+phon+"' and PASSWORD like '"+pass+"'" ,null);
        cr.moveToFirst();
        if (!cr.isAfterLast()){
            cr.close();
            return true;
        }else {
            cr.close();
            return false;
        }

    }
    public int getUser_id(String phon){
        Cursor cursor = db.rawQuery("select * from "+Table+" where PHONE_NUMBER like '"+phon+"'" ,null);
        if(!cursor.moveToFirst()){
            cursor.close();
            return -1;
        }else {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }

    }
    public User getUser_Info(int id){
        Cursor cr = db.rawQuery("select * from "+Table+" where ID = "+id  ,null);
        User user = new User();
        cr.moveToFirst();
        if (!cr.isAfterLast()){
            user.setID(cr.getInt(0));
            user.setFARST_NAME(cr.getString(1));
            user.setLAST_NAME(cr.getString(2));
            user.setPHONE_NUMBER(cr.getString(3));
            user.setMAP(cr.getString(cr.getColumnIndex("MAP")));
            user.setIMAGE(getImage(id));
            if(cr.getInt(5) == 0){
                user.setMEMBER_SHIP(cr.getInt(5));
            }else {
                user.setMEMBER_SHIP(cr.getInt(5));
                user.setJOP_ID(cr.getInt(6));
            }
            cr.close();

        }else {
            cr.close();
        }
        return user;

    }

    public byte[] getImage(int id){
        Cursor cr = db.rawQuery("select * from "+Table+" where ID = "+id ,null);

        byte[] result ;
        cr.moveToFirst();
        result = cr.getBlob(cr.getColumnIndex("IMAGE_SRC"));
        cr.close();
        return result;


    }
    public boolean isRigester(String phon){
        Cursor cr = db.rawQuery("select * from "+Table+" where PHONE_NUMBER like '"+phon+"'" ,null);
        cr.moveToFirst();
        if (!cr.isAfterLast()){
            cr.close();
            return true;
        }else {
            cr.close();
            return false;
        }

    }
    public int getMember(int id){
        Cursor cr = db.rawQuery("select * from "+Table+" where ID = ?" ,new String[]{Integer.toString(id)});
            int result;
            cr.moveToFirst();
        if (!cr.isAfterLast()) {
            result = cr.getInt(cr.getColumnIndex("MEMBER_SHIP"));
            cr.close();
            return result;
        }else {
            cr.close();
            return 0;
        }

    }
    public int delete(int id){
        DbOperation_Orders db_orders = new DbOperation_Orders(context);
        if(getMember(id) == 0){
            db_orders.deleteOrdersToCustomer(id);
        }else {
            db_orders.deleteOrdersToWorrker(id);
        }

        return db.delete(Table,"ID =? ",new String[]{Integer.toString(id)});

    }
    public int getJopId(int id){
        Cursor cr = db.rawQuery("select * from "+Table+" where ID = ?" ,new String[]{Integer.toString(id)});
        int result;
        cr.moveToFirst();
        if (!cr.isAfterLast()) {
            result = cr.getInt(cr.getColumnIndex("JOP_ID"));
            cr.close();
            return result;
        }else {
            cr.close();
            return 0;
        }
    }

}
