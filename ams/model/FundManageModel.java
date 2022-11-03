package com.ams.model;

import com.ams.model.BaseModel;

import java.util.List;
import java.util.Map;


public class FundManageModel
        extends BaseModel {
    public String asOnDate;
    public String pFromDate;
    public String pToDate;
    public String pFundCode;
    public List<Map<String, Object>> responseData;


    public List<Map<String, Object>> getResponseData() {
        return this.responseData;

    }


    public void setResponseData(List<Map<String, Object>> responseData) {
        this.responseData = responseData;

    }


    public String getAsOnDate() {
        return this.asOnDate;

    }


    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;

    }


    public String getpFundCode() {
        return this.pFundCode;

    }


    public void setpFundCode(String pFundCode) {
        this.pFundCode = pFundCode;

    }


    public String getpFromDate() {
        return this.pFromDate;

    }


    public void setpFromDate(String pFromDate) {
        this.pFromDate = pFromDate;

    }


    public String getpToDate() {
        return this.pToDate;

    }


    public void setpToDate(String pToDate) {
        this.pToDate = pToDate;

    }

}