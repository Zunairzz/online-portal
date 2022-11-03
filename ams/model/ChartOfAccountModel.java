package com.ams.model;

import com.ams.model.BaseModel;


public class ChartOfAccountModel
        extends BaseModel {
    public String id;
    public String glCode;
    public String description;
    public String transDate;
    public String fundCode;
    public String fDate;
    public String tDate;


    public String getfDate() {
        return this.fDate;

    }


    public void setfDate(String fDate) {
        this.fDate = fDate;

    }


    public String gettDate() {
        return this.tDate;

    }


    public void settDate(String tDate) {
        this.tDate = tDate;

    }


    public String getId() {
        return this.id;

    }


    public void setId(String id) {
        this.id = id;

    }


    public String getFundCode() {
        return this.fundCode;

    }


    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;

    }


    public String getGlCode() {
        return this.glCode;

    }


    public void setGlCode(String glCode) {
        this.glCode = glCode;

    }


    public String getDescription() {
        return this.description;

    }


    public void setDescription(String description) {
        this.description = description;

    }


    public String getTransDate() {
        return this.transDate;

    }


    public void setTransDate(String transDate) {
        this.transDate = transDate;

    }

}