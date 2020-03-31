package com.smartworker.smartworker.login;

public class User {

    int ID;
    String FARST_NAME, LAST_NAME, PHONE_NUMBER, PASSWORD, MAP;
    int MEMBER_SHIP;
    int JOP_ID = -1;
    byte[] IMAGE;

    public User() {
    }

    public User(int ID, String FARST_NAME, String LAST_NAME, String PHONE_NUMBER, String PASSWORD, String MAP, int MEMBER_SHIP, int JOP_ID, byte[] IMAGE) {
        this.ID = ID;
        this.FARST_NAME = FARST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.PASSWORD = PASSWORD;
        this.MAP = MAP;
        this.MEMBER_SHIP = MEMBER_SHIP;
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

    public int getMEMBER_SHIP() {
        return MEMBER_SHIP;
    }

    public void setMEMBER_SHIP(int MEMBER_SHIP) {
        this.MEMBER_SHIP = MEMBER_SHIP;
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