package com.ams.model;

import com.ams.model.BaseModel;
import com.ams.model.ChartOfAccountModel;

import java.util.List;


public class ChartOfAccountResponse
        extends BaseModel {
    List<ChartOfAccountModel> chartOfAccountList;


    public List<ChartOfAccountModel> getChartOfAccountList() {
        return this.chartOfAccountList;

    }


    public void setChartOfAccountList(List<ChartOfAccountModel> chartOfAccountList) {
        this.chartOfAccountList = chartOfAccountList;

    }

}