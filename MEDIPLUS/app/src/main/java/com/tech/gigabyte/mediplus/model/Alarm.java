package com.tech.gigabyte.mediplus.model;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Alarm Model Class
 * Getter-Setter
 */

public class Alarm {
    private String al_name, al_message, al_date, al_time, al_pending_no, al_finished;
    private int al_id;

    public int getAl_id() {
        return al_id;
    }

    public Alarm(String al_name, String al_message, String al_date, String al_time, String al_pending_no, String al_finished, int al_id) {
        this.al_name = al_name;
        this.al_message = al_message;
        this.al_date = al_date;
        this.al_time = al_time;
        this.al_pending_no = al_pending_no;
        this.al_finished = al_finished;
        this.al_id = al_id;
    }

    public String getAl_name() {
        return al_name;
    }

    public void setAl_name(String al_name) {
        this.al_name = al_name;
    }

    public String getAl_message() {
        return al_message;
    }

    public void setAl_message(String al_message) {
        this.al_message = al_message;
    }

    public String getAl_date() {
        return al_date;
    }

    public void setAl_date(String al_date) {
        this.al_date = al_date;
    }

    public String getAl_time() {
        return al_time;
    }

    public void setAl_time(String al_time) {
        this.al_time = al_time;
    }

    public String getAl_pending_no() {
        return al_pending_no;
    }

    public void setAl_pending_no(String al_pending_no) {
        this.al_pending_no = al_pending_no;
    }

    public String getAl_finished() {
        return al_finished;
    }

    public void setAl_finished(String al_finished) {
        this.al_finished = al_finished;
    }
}
