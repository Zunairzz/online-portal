package com.ams.model;


import com.ams.model.BaseModel;
import com.ams.model.FundDefinition;

import java.util.List;


public class FundDefinitionResponse
        extends BaseModel {
    List<FundDefinition> fundDefinitionList;


    public List<FundDefinition> getFundDefinitionList() {
        return this.fundDefinitionList;

    }


    public void setFundDefinitionList(List<FundDefinition> fundDefinitionList) {
        this.fundDefinitionList = fundDefinitionList;

    }

}