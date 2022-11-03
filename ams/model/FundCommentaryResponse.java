package com.ams.model;


import com.ams.model.BaseModel;
import com.ams.model.FundCommentary;

import java.util.List;


public class FundCommentaryResponse
        extends BaseModel {
    List<FundCommentary> fundCommentaryList;


    public List<FundCommentary> getFundCommentaryList() {
        return this.fundCommentaryList;

    }


    public void setFundCommentaryList(List<FundCommentary> fundCommentaryList) {
        this.fundCommentaryList = fundCommentaryList;

    }

}