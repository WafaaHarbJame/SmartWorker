package com.smartworker.smartworker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.smartworker.smartworker.login.Jop;
import com.smartworker.smartworker.login.User;
import com.smartworker.smartworker.orders.Order;

import java.util.ArrayList;
import java.util.List;

public class DbOperation_Orders {

    static final String DATABASENAME = "SmartWorker.db";
    static final String Table = "ORDERS";
    Context context;
    SQLiteDatabase db;


    public DbOperation_Orders(Context context) {
        CreateOPenHelperSQL dbase = new CreateOPenHelperSQL(context, DATABASENAME, null, 8);
        this.context = context;
        db = dbase.getWritableDatabase();
    }

    public boolean insert(Order order) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("USER_ID", order.getUser_id());
        cv.put("WORKER_ID", order.getWorker_id());
        cv.put("CATAGORY", order.getCatagoris());
        cv.put("CASES", order.getCases());
        cv.put("TIME_ADD", order.getTime_add());
        cv.put("DATE_ADD", order.getDate_add());
        cv.put("DESCRIPTION", order.getDescription());
        cv.put("IMAGE_SRC", order.getImageUri());
        cv.put("STATE", order.getState());
        cv.put("ACC", order.getAcc());
        long inserted = db.insert(Table, null, cv);
        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(Order order, int id) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("PRICE", order.getPrice());
        cv.put("TIME_ACCEPT", order.getTime_accept());
        cv.put("DATE_ACCEPT", order.getDate_accept());
        cv.put("COMMAND", order.getCommand());
        cv.put("STATE", order.getState());
        cv.put("ACC", order.getAcc());
        long updated = db.update(Table, cv, "ORDER_ID=?", new String[]{id + ""});
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Order> getALLOrders(int id, int member) {
        Cursor cursor;
        if (member == 0) {
            cursor = db.rawQuery("select * from " + Table + " where USER_ID = '" + id + "'", null);
        } else {
            cursor = db.rawQuery("select * from " + Table + " where WORKER_ID = '" + id + "'", null);
        }
        cursor.moveToFirst();
        List<Order> list = new ArrayList<Order>();
        while (!cursor.isAfterLast()) {
            Order order = new Order();
            order.setId(cursor.getInt(cursor.getColumnIndex("ORDER_ID")));
            order.setUser_id(cursor.getInt(cursor.getColumnIndex("USER_ID")));
            order.setWorker_id(cursor.getInt(cursor.getColumnIndex("WORKER_ID")));
            order.setCatagoris(cursor.getInt(cursor.getColumnIndex("CATAGORY")));
            order.setCases(cursor.getInt(cursor.getColumnIndex("CASES")));
            order.setTime_add(cursor.getString(cursor.getColumnIndex("TIME_ADD")));
            order.setDate_add(cursor.getString(cursor.getColumnIndex("DATE_ADD")));
            order.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            order.setImageUri(cursor.getString(cursor.getColumnIndex("IMAGE_SRC")));
            order.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
            order.setTime_accept(cursor.getString(cursor.getColumnIndex("TIME_ACCEPT")));
            order.setDate_accept(cursor.getString(cursor.getColumnIndex("DATE_ACCEPT")));
            order.setCommand(cursor.getString(cursor.getColumnIndex("COMMAND")));
            order.setState(cursor.getInt(cursor.getColumnIndex("STATE")));
            order.setAcc(cursor.getInt(cursor.getColumnIndex("ACC")));
            list.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int getOrderId(int id) {
        int result;
        Cursor cursor = db.rawQuery("select * from " + Table + " where ORDER_ID = '" + id + "'", null);
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex("ORDER_ID"));
            cursor.close();
        } else {
            result = -1;
        }
        return result;
    }

    public int getOrderIdLast() {
        int result;
        Cursor cursor = db.rawQuery("select * from " + Table, null);
        if (cursor.moveToLast()) {
            result = cursor.getInt(cursor.getColumnIndex("ORDER_ID"));
            cursor.close();
        } else {
            result = -1;
        }
        return result;
    }

    public Order getOrder(int id) {
        Cursor cursor = db.rawQuery("select * from " + Table + " where ORDER_ID = '" + id + "'", null);
        Order order = new Order();
        if (cursor.moveToFirst()) {
            order.setId(id);
            order.setUser_id(cursor.getInt(cursor.getColumnIndex("USER_ID")));
            order.setWorker_id(cursor.getInt(cursor.getColumnIndex("WORKER_ID")));
            order.setCatagoris(cursor.getInt(cursor.getColumnIndex("CATAGORY")));
            order.setCases(cursor.getInt(cursor.getColumnIndex("CASES")));
            order.setTime_add(cursor.getString(cursor.getColumnIndex("TIME_ADD")));
            order.setDate_add(cursor.getString(cursor.getColumnIndex("DATE_ADD")));
            order.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            order.setImageUri(cursor.getString(cursor.getColumnIndex("IMAGE_SRC")));
            order.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
            order.setTime_accept(cursor.getString(cursor.getColumnIndex("TIME_ACCEPT")));
            order.setDate_accept(cursor.getString(cursor.getColumnIndex("DATE_ACCEPT")));
            order.setCommand(cursor.getString(cursor.getColumnIndex("COMMAND")));
            order.setState(cursor.getInt(cursor.getColumnIndex("STATE")));
            order.setAcc(cursor.getInt(cursor.getColumnIndex("ACC")));
        }
        cursor.close();
        return order;
    }

    public int deleteOrdersToCustomer(int id) {

        return db.delete(Table, "USER_ID =? ", new String[]{Integer.toString(id)});

    }

    public int deleteOrdersToWorrker(int id) {

        return db.delete(Table, "WORKER_ID =? ", new String[]{Integer.toString(id)});

    }

    public int deleteOrder(int id) {

        return db.delete(Table, "ORDER_ID =? ", new String[]{Integer.toString(id)});

    }

    public boolean UpdateToProgress(int id) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("STATE", 2);
        long updated = db.update(Table, cv, "ORDER_ID=?", new String[]{id + ""});
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }
}
