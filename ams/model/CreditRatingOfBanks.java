package com.ams.model;

import com.ams.model.BaseModel;


public class CreditRatingOfBanks
        extends BaseModel {
    public String glBankCode;
    public String glBankName;
    public String rating;


    public String getGlBankCode() {
        return this.glBankCode;

    }


    public void setGlBankCode(String glBankCode) {
        this.glBankCode = glBankCode;

    }


    public String getGlBankName() {
        return this.glBankName;

    }


    public void setGlBankName(String glBankName) {
        this.glBankName = glBankName;

    }


    public String getRating() {
        return this.rating;

    }


    public void setRating(String rating) {
        this.rating = rating;

    }

}