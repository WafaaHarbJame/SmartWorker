package com.smartworker.smartworker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class CreateOPenHelperSQL extends SQLiteOpenHelper {


    public CreateOPenHelperSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table JOPS(JOP_ID INTEGER  PRIMARY KEY AUTOINCREMENT,JOP_NAME TEXT,IMAGE_SRC BLOB)");

        db.execSQL("create table CITIES(CITY_ID INTEGER  PRIMARY KEY AUTOINCREMENT,CITY_NAME TEXT,LONGITUDE REAL,LATITUDE REAL)");

        db.execSQL("create table ORDERS(ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "USER_ID INTEGER,WORKER_ID INTEGER,CATAGORY INTEGER,CASES INTEGER,TIME_ADD TEXT,DATE_ADD TEXT,DESCRIPTION TEXT,IMAGE_SRC TEXT,PRICE INTEGER,TIME_ACCEPT TEXT,DATE_ACCEPT TEXT ,COMMAND TEXT,STATE INTEGER,ACC INTEGER,FOREIGN KEY(USER_ID) REFERENCES USERS(ID),FOREIGN KEY(WORKER_ID) REFERENCES USERS(ID),FOREIGN KEY(CATAGORY) REFERENCES JOPS(JOP_ID))");

        db.execSQL("create table USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "FIRST_NAME TEXT ,LAST_NAME TEXT ,PHONE_NUMBER TEXT,"
                + "PASSWORD TEXT,MEMBER_SHIP INTEGER,JOP_ID INTEGER," +
                "IMAGE_SRC BLOB,MAP TEXT,LONGITUDE TEXT,LATITUDE TEXT,ORDERS_ID INTEGER,CITY_ID INTEGER,CITY_NAME TEXT, FOREIGN KEY(JOP_ID) REFERENCES JOPS(JOP_ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists USERS");
        db.execSQL("drop table if exists JOPS");
        db.execSQL("drop table if exists  ORDERS");
        db.execSQL("drop table if exists  CITIES");

        //db.execSQL("drop table Worker_Info");
        //db.execSQL("drop table Customer_Info");
        onCreate(db);
    }


}
