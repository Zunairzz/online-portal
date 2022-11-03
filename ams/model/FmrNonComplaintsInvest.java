package com.ams.model;

import com.ams.model.BaseModel;


public class FmrNonComplaintsInvest
        extends BaseModel {
    public String fundCode;
    public String fundName;
    public String transDate;
    public String nonComplaintInvestment;
    public String typeOfInvestment;
    public String exposureLimit;
    public String percentOfNetAsset;
    public String percentOfTotalAsset;
    public String excessExposurePerOfNet;
    public String excessExposurePerOfTotal;


    public String getFundCode() {
        return this.fundCode;

    }


    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;

    }


    public String getFundName() {
        return this.fundName;

    }


    public void setFundName(String fundName) {
        this.fundName = fundName;

    }


    public String getTransDate() {
        return this.transDate;

    }


    public void setTransDate(String transDate) {
        this.transDate = transDate;

    }


    public String getNonComplaintInvestment() {
        return this.nonComplaintInvestment;

    }


    public void setNonComplaintInvestment(String nonComplaintInvestment) {
        this.nonComplaintInvestment = nonComplaintInvestment;

    }


    public String getTypeOfInvestment() {
        return this.typeOfInvestment;

    }


    public void setTypeOfInvestment(String typeOfInvestment) {
        this.typeOfInvestment = typeOfInvestment;

    }


    public String getExposureLimit() {
        return this.exposureLimit;

    }


    public void setExposureLimit(String exposureLimit) {
        this.exposureLimit = exposureLimit;

    }


    public String getPercentOfNetAsset() {
        return this.percentOfNetAsset;

    }


    public void setPercentOfNetAsset(String percentOfNetAsset) {
        this.percentOfNetAsset = percentOfNetAsset;

    }


    public String getPercentOfTotalAsset() {
        return this.percentOfTotalAsset;

    }


    public void setPercentOfTotalAsset(String percentOfTotalAsset) {
        this.percentOfTotalAsset = percentOfTotalAsset;

    }


    public String getExcessExposurePerOfNet() {
        return this.excessExposurePerOfNet;

    }


    public void setExcessExposurePerOfNet(String excessExposurePerOfNet) {
        this.excessExposurePerOfNet = excessExposurePerOfNet;

    }


    public String getExcessExposurePerOfTotal() {
        return this.excessExposurePerOfTotal;

    }


    public void setExcessExposurePerOfTotal(String excessExposurePerOfTotal) {
        this.excessExposurePerOfTotal = excessExposurePerOfTotal;

    }

}