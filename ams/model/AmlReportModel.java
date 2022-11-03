package com.ams.model;

import com.ams.model.BaseModel;


public class AmlReportModel
        extends BaseModel {
    public String pFromDate;
    public String pToDate;
    public String folioNumber;
    public String title;
    public String accountDate;
    public String lastActivityDate;
    public String activityType;
    public String currentBalance;


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


    public String getFolioNumber() {
        return this.folioNumber;

    }


    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;

    }


    public String getTitle() {
        return this.title;

    }


    public void setTitle(String title) {
        this.title = title;

    }


    public String getAccountDate() {
        return this.accountDate;

    }


    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;

    }


    public String getLastActivityDate() {
        return this.lastActivityDate;

    }


    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;

    }


    public String getActivityType() {
        return this.activityType;

    }


    public void setActivityType(String activityType) {
        this.activityType = activityType;

    }


    public String getCurrentBalance() {
        return this.currentBalance;

    }


    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;

    }

}