package com.smartworker.smartworker.login;

public class User {

    int ID;
    String FARST_NAME, LAST_NAME, PHONE_NUMBER, PASSWORD, MAP,city_name;
   Double latitude, longitude;
    int MEMBER_SHIP;
    int city_id;
    int JOP_ID = -1;
    byte[] IMAGE;

    public User() {
    }

    public User(int ID, String FARST_NAME, String LAST_NAME, String PHONE_NUMBER, String PASSWORD, String MAP, String city_name, Double latitude, Double longitude, int MEMBER_SHIP, int city_id, int JOP_ID, byte[] IMAGE) {
        this.ID = ID;
        this.FARST_NAME = FARST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.PASSWORD = PASSWORD;
        this.MAP = MAP;
        this.city_name = city_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.MEMBER_SHIP = MEMBER_SHIP;
        this.city_id = city_id;
        this.JOP_ID = JOP_ID;
        this.IMAGE = IMAGE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFARST_NAME() {
        return FARST_NAME;
    }

    public void setFARST_NAME(String FARST_NAME) {
        this.FARST_NAME = FARST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getMAP() {
        return MAP;
    }

    public void setMAP(String MAP) {
        this.MAP = MAP;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getMEMBER_SHIP() {
        return MEMBER_SHIP;
    }

    public void setMEMBER_SHIP(int MEMBER_SHIP) {
        this.MEMBER_SHIP = MEMBER_SHIP;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getJOP_ID() {
        return JOP_ID;
    }

    public void setJOP_ID(int JOP_ID) {
        this.JOP_ID = JOP_ID;
    }

    public byte[] getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(byte[] IMAGE) {
        this.IMAGE = IMAGE;
    }
}