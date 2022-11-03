package com.ams.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FmrTopTfcModel {
    List<Map<String, Object>> tfcData = new ArrayList<>();
    String total;


    public List<Map<String, Object>> getTfcData() {
        return this.tfcData;

    }


    public void setTfcData(List<Map<String, Object>> tfcData) {
        this.tfcData = tfcData;

    }


    public String getTotal() {
        return this.total;

    }


    public void setTotal(String total) {
        this.total = total;

    }

}