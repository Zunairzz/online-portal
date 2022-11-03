package com.ams.model;


import com.ams.model.BaseModel;
import com.ams.model.FmrFundBasicInfo;


public class FmrModel
        extends BaseModel {
    public String fundTitle;
    public String comments;
    public String objective;
    public String members;
    public String launchDate;
    public FmrFundBasicInfo basicInfo;


    public FmrFundBasicInfo getBasicInfo() {
        return this.basicInfo;

    }


    public void setBasicInfo(FmrFundBasicInfo basicInfo) {
        this.basicInfo = basicInfo;

    }


    public String getFundTitle() {
        return this.fundTitle;

    }


    public void setFundTitle(String fundTitle) {
        this.fundTitle = fundTitle;

    }


    public String getComments() {
        return this.comments;

    }


    public void setComments(String comments) {
        this.comments = comments;

    }


    public String getObjective() {
        return this.objective;

    }


    public void setObjective(String objective) {
        this.objective = objective;

    }


    public String getMembers() {
        return this.members;

    }


    public void setMembers(String members) {
        this.members = members;

    }


    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;

    }

}