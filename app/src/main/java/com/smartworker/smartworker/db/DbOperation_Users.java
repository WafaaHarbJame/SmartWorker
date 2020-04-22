package com.smartworker.smartworker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.smartworker.smartworker.login.Jop;
import com.smartworker.smartworker.login.User;

import java.util.ArrayList;
import java.util.List;

public class DbOperation_Users {

    static final String Table = "USERS";
    private static final String DATABASENAME = "SmartWorker.db";
    Context context;
    SQLiteDatabase db;

    public DbOperation_Users(Context context) {
        CreateOPenHelperSQL dbase = new CreateOPenHelperSQL(context, DATABASENAME, null, 8);
        this.context = context;
        db = dbase.getWritableDatabase();
    }

    public boolean insert(User user) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME", user.getFARST_NAME());
        cv.put("LAST_NAME", user.getLAST_NAME());
        cv.put("PHONE_NUMBER", user.getPHONE_NUMBER());
        cv.put("PASSWORD", user.getPASSWORD());
        cv.put("MEMBER_SHIP", user.getMEMBER_SHIP());
        cv.put("JOP_ID", user.getJOP_ID());
        cv.put("IMAGE_SRC", user.getIMAGE());
        cv.put("MAP", user.getMAP());
        cv.put("LATITUDE", user.getLatitude());
        cv.put("LONGITUDE", user.getLongitude());
        cv.put("CITY_ID", user.getCity_id());
        cv.put("CITY_NAME", user.getCity_name());

        long inserted = db.insert(Table, null, cv);
        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(User user) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME", user.getFARST_NAME());
        cv.put("LAST_NAME", user.getLAST_NAME());
        cv.put("MEMBER_SHIP", user.getMEMBER_SHIP());
        cv.put("JOP_ID", user.getJOP_ID());
        cv.put("IMAGE_SRC", user.getIMAGE());
        cv.put("MAP", user.getMAP());
        cv.put("LATITUDE", user.getLatitude());
        cv.put("LONGITUDE", user.getLongitude());
        cv.put("CITY_NAME", user.getCity_name());
        cv.put("CITY_ID", user.getCity_id());

        long updated = db.update(Table, cv, "ID=?", new String[]{user.getID() + ""});
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean RestoredPassward(User user) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("FIRST_NAME", user.getFARST_NAME());
        cv.put("LAST_NAME", user.getLAST_NAME());
        cv.put("MEMBER_SHIP", user.getMEMBER_SHIP());
        cv.put("JOP_ID", user.getJOP_ID());
        cv.put("IMAGE_SRC", user.getIMAGE());
        cv.put("MAP", user.getMAP());
        cv.put("PHONE_NUMBER", user.getPHONE_NUMBER());
        cv.put("LATITUDE", user.getLatitude());
        cv.put("LONGITUDE", user.getLongitude());
        cv.put("CITY_NAME", user.getCity_name());
        cv.put("PASSWORD",user.getPASSWORD());
        cv.put("CITY_ID", user.getCity_id());

        long updated = db.update(Table, cv, "ID=?", new String[]{user.getID() + ""});
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean getUser(String phon, String pass) {
        Cursor cr = db.rawQuery("select * from " + Table + " where PHONE_NUMBER like '"
                + phon + "' and PASSWORD like '" + pass + "'", null);
        cr.moveToFirst();
        if (!cr.isAfterLast()) {
            cr.close();
            return true;
        } else {
            cr.close();
            return false;
        }

    }

    public int getUser_id(String phon) {
        Cursor cursor = db.rawQuery("select * from " + Table + " where PHONE_NUMBER like '" + phon + "'", null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return -1;
        } else {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }

    }


    

    public List<User> getAllUSERS() {
        Cursor cursor = db.rawQuery("select * from USERS", null);
        cursor.moveToFirst();
        List<User> list = new ArrayList<User>();
        while (!cursor.isAfterLast()) {
            User user_list = new User();
            user_list.setID(cursor.getInt(0));
            user_list.setFARST_NAME(cursor.getString(1));
            user_list.setLAST_NAME(cursor.getString(2));
            user_list.setPHONE_NUMBER(cursor.getString(3));
            user_list.setMAP(cursor.getString(8));
            user_list.setLongitude(cursor.getDouble(9));
            user_list.setLatitude(cursor.getDouble(10));
            user_list.setIMAGE(cursor.getBlob(7));
            user_list.setCity_id(cursor.getInt(12));
            user_list.setCity_name(cursor.getString(13));
            user_list.setJOP_ID(cursor.getInt(6));
//            user_list.setMEMBER_SHIP(cursor.getColumnIndex("MEMBER_SHIP"));
            user_list.setMEMBER_SHIP(cursor.getInt(5));
//            user_list.setJOP_ID(cursor.getInt(7));
            list.add(user_list);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<User> getAllUSERSBYJOBID(int jobId, int cityId) {
        Cursor cursor = db.rawQuery("select * from USERS  " + " where JOP_ID=" + jobId + "  " + " and MEMBER_SHIP=" + "1 " + "and CITY_ID=" + cityId, null);
        cursor.moveToFirst();
        List<User> list = new ArrayList<User>();
        while (!cursor.isAfterLast()) {
            User user_list = new User();
            user_list.setID(cursor.getInt(0));
            user_list.setFARST_NAME(cursor.getString(1));
            user_list.setLAST_NAME(cursor.getString(2));
            user_list.setPHONE_NUMBER(cursor.getString(3));
            user_list.setMAP(cursor.getString(8));
            user_list.setLongitude(cursor.getDouble(9));
            user_list.setLatitude(cursor.getDouble(10));
            user_list.setIMAGE(cursor.getBlob(7));
            user_list.setCity_id(cursor.getInt(12));
            user_list.setCity_name(cursor.getString(13));
            user_list.setJOP_ID(cursor.getInt(6));
//            user_list.setMEMBER_SHIP(cursor.getColumnIndex("MEMBER_SHIP"));
            user_list.setMEMBER_SHIP(cursor.getInt(5));
//            user_list.setJOP_ID(cursor.getInt(7));
            list.add(user_list);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public User getUser_Info(int id) {
        Cursor cr = db.rawQuery("select * from " + Table + " where ID = " + id, null);
        User user = new User();
        cr.moveToFirst();
        if (!cr.isAfterLast()) {
            user.setID(cr.getInt(0));
            user.setFARST_NAME(cr.getString(1));
            user.setLAST_NAME(cr.getString(2));
            user.setPHONE_NUMBER(cr.getString(3));
            //  user.setMAP(cr.getString(cr.getColumnIndex("MAP")));
            user.setMAP(cr.getString(8));
            user.setLongitude(cr.getDouble(9));
            user.setLatitude(cr.getDouble(10));
            user.setCity_id(cr.getInt(12));
            user.setCity_name(cr.getString(13));
            user.setIMAGE(cr.getBlob(7));
            if (cr.getInt(5) == 0) {
                user.setMEMBER_SHIP(cr.getInt(5));
            } else {
                user.setMEMBER_SHIP(cr.getInt(5));
                user.setJOP_ID(cr.getInt(6));
            }
            cr.close();

        } else {
            cr.close();
        }
        return user;

    }


    public boolean isRigester(String phon) {
        Cursor cr = db.rawQuery("select * from " + Table + " where PHONE_NUMBER like '" + phon + "'", null);
        cr.moveToFirst();
        if (!cr.isAfterLast()) {
            cr.close();
            return true;
        } else {
            cr.close();
            return false;
        }

    }

    public int getMember(int id) {
        Cursor cr = db.rawQuery("select * from " + Table + " where ID = ?", new String[]{Integer.toString(id)});
        int result;
        cr.moveToFirst();
        if (!cr.isAfterLast()) {
            result = cr.getInt(cr.getColumnIndex("MEMBER_SHIP"));
            cr.close();
            return result;
        } else {
            cr.close();
            return 0;
        }

    }

    public int delete(int id) {
        DbOperation_Orders db_orders = new DbOperation_Orders(context);
        if (getMember(id) == 0) {
            db_orders.deleteOrdersToCustomer(id);
        } else {
            db_orders.deleteOrdersToWorrker(id);
        }

        return db.delete(Table, "ID =? ", new String[]{Integer.toString(id)});

    }



}
