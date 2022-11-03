package com.ams.model;

import com.ams.model.BaseModel;


public class MainModel extends BaseModel {
    public String fundCode;
    public String transDate;
    public String result;
    public String fromDate;
    public String toDate;
    public String asOnDate;
    public String navDate;
    public String searchFolioNumber;

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getNavDate() {
        return navDate;
    }

    public void setNavDate(String navDate) {
        this.navDate = navDate;
    }

    public String getSearchFolioNumber() {
        return searchFolioNumber;
    }

    public void setSearchFolioNumber(String searchFolioNumber) {
        this.searchFolioNumber = searchFolioNumber;
    }
}