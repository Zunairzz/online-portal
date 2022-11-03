package com.ams.model;

import com.ams.model.FmrModel;

import java.util.ArrayList;
import java.util.List;


public class FmrFundWiseData {
    public List<FmrModel> data = new ArrayList<>();


    public List<FmrModel> getData() {
        return this.data;

    }


    public void setData(List<FmrModel> data) {
        this.data = data;

    }

}