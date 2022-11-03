
package com.ams.model.fmr;

import com.ams.model.BaseModel;


public class FmrNoncompliant
        extends BaseModel {
    public String fund_code;
    public String transdate;
    public String non_complaint_investment;
    public String type_of_investment;
    public String exposure_limit;
    public String percent_of_net_asset;
    public String percent_of_total_asset;
    public String excess_exposure_per_of_net;
    public String excess_exposure_per_of_total;


    public String getFund_code() {
        return this.fund_code;

    }


    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;

    }


    public String getTransdate() {
        return this.transdate;

    }


    public void setTransdate(String transdate) {
        this.transdate = transdate;

    }


    public String getNon_complaint_investment() {
        return this.non_complaint_investment;

    }


    public void setNon_complaint_investment(String non_complaint_investment) {
        this.non_complaint_investment = non_complaint_investment;

    }


    public String getType_of_investment() {
        return this.type_of_investment;

    }


    public void setType_of_investment(String type_of_investment) {
        this.type_of_investment = type_of_investment;

    }


    public String getExposure_limit() {
        return this.exposure_limit;

    }


    public void setExposure_limit(String exposure_limit) {
        this.exposure_limit = exposure_limit;

    }


    public String getPercent_of_net_asset() {
        return this.percent_of_net_asset;

    }


    public void setPercent_of_net_asset(String percent_of_net_asset) {
        this.percent_of_net_asset = percent_of_net_asset;

    }


    public String getPercent_of_total_asset() {
        return this.percent_of_total_asset;

    }


    public void setPercent_of_total_asset(String percent_of_total_asset) {
        this.percent_of_total_asset = percent_of_total_asset;

    }


    public String getExcess_exposure_per_of_net() {
        return this.excess_exposure_per_of_net;

    }


    public void setExcess_exposure_per_of_net(String excess_exposure_per_of_net) {
        this.excess_exposure_per_of_net = excess_exposure_per_of_net;

    }


    public String getExcess_exposure_per_of_total() {
        return this.excess_exposure_per_of_total;

    }


    public void setExcess_exposure_per_of_total(String excess_exposure_per_of_total) {
        this.excess_exposure_per_of_total = excess_exposure_per_of_total;

    }

}