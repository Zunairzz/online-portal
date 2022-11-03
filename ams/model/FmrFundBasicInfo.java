package com.ams.model;

import com.ams.model.BaseModel;


public class FmrFundBasicInfo
        extends BaseModel {
    public String id;
    public String transDate;
    public String fundCode;
    public String fund_type;
    public String category;
    public String benchmarkLabel;
    public String benchmark;
    public String dealing_days;
    public String cutt_off_time;
    public String pricing_mechanism;
    public String management_fee;
    public String front_end_load;
    public String trustee;
    public String auditor;
    public String asset_manager_rating;
    public String risk_prof_fund;
    public String perform_ranking;
    public String fund_manger;
    public String listing;
    public String comments;
    public String launchdate;
    public String netassets;
    public String netassetexfof;
    public String assetallocationInvestedAmount;
    public String fundTer;
    public String nav;
    public String ter_gov_levy;
    public String swwf_nav_unit;
    public double swwf;
    public double smexp;


    public double getSmexp() {
        return this.smexp;

    }


    public void setSmexp(double smexp) {
        this.smexp = smexp;

    }


    public double getSwwf() {
        return this.swwf;

    }


    public void setSwwf(double swwf) {
        this.swwf = swwf;

    }


    public String getSwwf_nav_unit() {
        return this.swwf_nav_unit;

    }


    public void setSwwf_nav_unit(String swwf_nav_unit) {
        this.swwf_nav_unit = swwf_nav_unit;

    }


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


    public String getTer_gov_levy() {
        return this.ter_gov_levy;

    }


    public void setTer_gov_levy(String ter_gov_levy) {
        this.ter_gov_levy = ter_gov_levy;

    }


    public String getNav() {
        return this.nav;

    }


    public void setNav(String nav) {
        this.nav = nav;

    }


    public String getAssetallocationInvestedAmount() {
        return this.assetallocationInvestedAmount;

    }


    public void setAssetallocationInvestedAmount(String assetallocationInvestedAmount) {
        this.assetallocationInvestedAmount = assetallocationInvestedAmount;

    }


    public String getBenchmarkLabel() {
        return this.benchmarkLabel;

    }


    public void setBenchmarkLabel(String benchmarkLabel) {
        this.benchmarkLabel = benchmarkLabel;

    }


    public String getId() {
        return this.id;

    }


    public void setId(String id) {
        this.id = id;

    }


    public String getTransdate() {
        return this.transDate;

    }


    public void setTransdate(String transdate) {
        this.transDate = transdate;

    }


    public String getFund_code() {
        return this.fundCode;

    }


    public void setFund_code(String fundCode) {
        this.fundCode = fundCode;

    }


    public String getFund_type() {
        return this.fund_type;

    }


    public void setFund_type(String fund_type) {
        this.fund_type = fund_type;

    }


    public String getCategory() {
        return this.category;

    }


    public void setCategory(String category) {
        this.category = category;

    }


    public String getBenchmark() {
        return this.benchmark;

    }


    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;

    }


    public String getDealing_days() {
        return this.dealing_days;

    }


    public void setDealing_days(String dealing_days) {
        this.dealing_days = dealing_days;

    }


    public String getCutt_off_time() {
        return this.cutt_off_time;

    }


    public void setCutt_off_time(String cutt_off_time) {
        this.cutt_off_time = cutt_off_time;

    }


    public String getPricing_mechanism() {
        return this.pricing_mechanism;

    }


    public void setPricing_mechanism(String pricing_mechanism) {
        this.pricing_mechanism = pricing_mechanism;

    }


    public String getManagement_fee() {
        return this.management_fee;

    }


    public void setManagement_fee(String management_fee) {
        this.management_fee = management_fee;

    }


    public String getFront_end_load() {
        return this.front_end_load;

    }


    public void setFront_end_load(String front_end_load) {
        this.front_end_load = front_end_load;

    }


    public String getTrustee() {
        return this.trustee;

    }


    public void setTrustee(String trustee) {
        this.trustee = trustee;

    }


    public String getAuditor() {
        return this.auditor;

    }


    public void setAuditor(String auditor) {
        this.auditor = auditor;

    }


    public String getAsset_manager_rating() {
        return this.asset_manager_rating;

    }


    public void setAsset_manager_rating(String asset_manager_rating) {
        this.asset_manager_rating = asset_manager_rating;

    }


    public String getRisk_prof_fund() {
        return this.risk_prof_fund;

    }


    public void setRisk_prof_fund(String risk_prof_fund) {
        this.risk_prof_fund = risk_prof_fund;

    }


    public String getPerform_ranking() {
        return this.perform_ranking;

    }


    public void setPerform_ranking(String perform_ranking) {
        this.perform_ranking = perform_ranking;

    }


    public String getFund_manger() {
        return this.fund_manger;

    }


    public void setFund_manger(String fund_manger) {
        this.fund_manger = fund_manger;

    }


    public String getListing() {
        return this.listing;

    }


    public void setListing(String listing) {
        this.listing = listing;

    }


    public String getComments() {
        return this.comments;

    }


    public void setComments(String comments) {
        this.comments = comments;

    }


    public String getLaunchdate() {
        return this.launchdate;

    }


    public void setLaunchdate(String launchdate) {
        this.launchdate = launchdate;

    }


    public String getNetassets() {
        return this.netassets;

    }


    public void setNetassets(String netassets) {
        this.netassets = netassets;

    }


    public String getNetassetexfof() {
        return this.netassetexfof;

    }


    public void setNetassetexfof(String netassetexfof) {
        this.netassetexfof = netassetexfof;
        setAssetallocationInvestedAmount(String.format("%.2f", new Object[]{Double.valueOf(Double.valueOf(getNetassets()).doubleValue() - Double.valueOf(getNetassetexfof()).doubleValue())}));

    }


    public String getFundTer() {
        return this.fundTer;

    }


    public void setFundTer(String fundTer) {
        this.fundTer = fundTer;

    }

}