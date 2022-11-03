package com.ams.model;


import com.ams.model.BaseModel;


public class FundCommentary
        extends BaseModel {
    public String fundCode;
    public String commentary;
    public String transDate;


    public String getTransDate() {
        return this.transDate;

    }


    public void setTransDate(String transDate) {
        this.transDate = transDate;

    }


    public String getFundCode() {
        return this.fundCode;

    }


    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;

    }


    public String getCommentary() {
        return this.commentary;

    }


    public void setCommentary(String commentary) {
        this.commentary = commentary;

    }

}