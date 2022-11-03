package com.ams.model;


import java.util.Comparator;
import java.util.List;


public class fmr_asset_allocation_model {
    public String code;
    public String secName;
    public String prev_month;
    public String curr_month;


    public String getCode() {
        return this.code;

    }


    public void setCode(String code) {
        this.code = code;

    }


    public String getSecName() {
        return this.secName;

    }


    public void setSecName(String secName) {
        this.secName = secName;

    }


    public String getPrev_month() {
        return String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(this.prev_month))});

    }


    public void setPrev_month(String prev_month) {
        this.prev_month = prev_month;

    }


    public String getCurr_month() {
        return String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(this.curr_month))});

    }


    public double getCurr_monthDouble() {
        return Double.parseDouble(this.curr_month);

    }


    public void setCurr_month(String curr_month) {
        this.curr_month = curr_month;

    }


    public static List<com.ams.model.fmr_asset_allocation_model> filter(List<com.ams.model.fmr_asset_allocation_model> currlist, boolean filterByName) {
        currlist.removeIf(aa -> (Float.parseFloat(aa.getPrev_month()) <= 0.0F && Float.parseFloat(aa.getCurr_month()) <= 0.0F));
        if (filterByName) {
            currlist.sort(Comparator.comparing(com.ams.model.fmr_asset_allocation_model::getSecName));

        } else {
            currlist.sort(Comparator.<com.ams.model.fmr_asset_allocation_model>comparingDouble(com.ams.model.fmr_asset_allocation_model::getCurr_monthDouble).reversed());
        }
        return currlist;

    }

}