package com.ams.model.fmr;

import com.ams.model.BaseModel;


public class FmrFundPerformance
        extends BaseModel {
    public String fundCode;
    public String transDate;
    public String stDev;
    public String sharpRatio;
    public String alpha;
    public String bstDev;
    public String bsharpRatio;
    public String balpha;


    public String getFundCode() {
        return this.fundCode;

    }


    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;

    }


    public String getTransDate() {
        return this.transDate;

    }


    public void setTransDate(String transDate) {
        this.transDate = transDate;

    }


    public String getStDev() {
        return this.stDev;

    }


    public void setStDev(String stDev) {
        this.stDev = stDev;

    }


    public String getSharpRatio() {
        return this.sharpRatio;

    }


    public void setSharpRatio(String sharpRatio) {
        this.sharpRatio = sharpRatio;

    }


    public String getAlpha() {
        return this.alpha;

    }


    public void setAlpha(String alpha) {
        this.alpha = alpha;

    }


    public String getBstDev() {
        return this.bstDev;

    }


    public void setBstDev(String bstDev) {
        this.bstDev = bstDev;

    }


    public String getBsharpRatio() {
        return this.bsharpRatio;

    }


    public void setBsharpRatio(String bsharpRatio) {
        this.bsharpRatio = bsharpRatio;

    }


    public String getBalpha() {
        return this.balpha;

    }


    public void setBalpha(String balpha) {
        this.balpha = balpha;

    }

}