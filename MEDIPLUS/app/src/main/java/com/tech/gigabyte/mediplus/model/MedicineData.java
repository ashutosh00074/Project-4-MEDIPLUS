package com.tech.gigabyte.mediplus.model;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Medicine Model Class
 * Getter-Setter
 */

public class MedicineData
{
    private String name, desc,price, type, inst, cat;
    private int id;

    public MedicineData(String name, String desc, String price, String type, String inst, int id, String cat) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.type = type;
        this.inst = inst;
        this.id = id;
        this.cat = cat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }


}
