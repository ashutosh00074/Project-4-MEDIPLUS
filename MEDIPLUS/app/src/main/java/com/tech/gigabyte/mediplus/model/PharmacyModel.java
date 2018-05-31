package com.tech.gigabyte.mediplus.model;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Pharmacy Model Class
 * Getter-Setter
 */

public class PharmacyModel {
    private int p_id;
    private String p_name, p_number, p_address;

    public PharmacyModel(int p_id, String p_name, String p_number, String p_address) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_number = p_number;
        this.p_address = p_address;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public int getP_id() {
        return p_id;

    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_number() {
        return p_number;
    }

    public void setP_number(String p_number) {
        this.p_number = p_number;
    }
}
