package com.smartworker.smartworker.orders;

import java.sql.Date;
import java.sql.Time;

public class Order {

    int id, user_id, worker_id, catagoris, cases, price, state,acc;
    String description, command, time_add, time_accept, date_add, date_accept;
    byte[] image;



    public Order() {
    }

    public Order(int id, int user_id, int worker_id, int catagoris, int cases, int price, int state, String description, String command, String time_add, String time_accept, String date_add, String date_accept, byte[] image, int acc) {
        this.id = id;
        this.user_id = user_id;
        this.worker_id = worker_id;
        this.catagoris = catagoris;
        this.cases = cases;
        this.price = price;
        this.state = state;
        this.description = description;
        this.command = command;
        this.time_add = time_add;
        this.time_accept = time_accept;
        this.date_add = date_add;
        this.date_accept = date_accept;
        this.image = image;
        this.acc = acc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public int getCatagoris() {
        return catagoris;
    }

    public void setCatagoris(int catagoris) {
        this.catagoris = catagoris;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTime_add() {
        return time_add;
    }

    public void setTime_add(String time_add) {
        this.time_add = time_add;
    }

    public String getTime_accept() {
        return time_accept;
    }

    public void setTime_accept(String time_accept) {
        this.time_accept = time_accept;
    }

    public String getDate_add() {
        return date_add;
    }

    public void setDate_add(String date_add) {
        this.date_add = date_add;
    }

    public String getDate_accept() {
        return date_accept;
    }

    public void setDate_accept(String date_accept) {
        this.date_accept = date_accept;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }
}