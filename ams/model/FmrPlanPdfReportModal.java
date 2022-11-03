package com.ams.model;

import com.ams.model.FmrAssetAllocMain;
import com.ams.model.FmrFundBasicInfo;
import com.ams.model.FmrTopTfcModel;
import com.ams.model.FofPlanSection;
import com.ams.model.PlanPerformance;
import com.ams.model.fmr.FundDefinitionReportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FmrPlanPdfReportModal {
    String header;
    FundDefinitionReportModel fundDef;
    FmrFundBasicInfo basicInfo;
    PlanPerformance performanceGrid = new PlanPerformance();
    List<PlanPerformance> planPerformance = new ArrayList<>();
    List<Map<String, Object>> fpf_p_f1_data;
    List<Map<String, Object>> fif_perform_data1;
    List<Map<String, Object>> fif_asset_header;
    List<Map<String, Object>> fmr_tech_info_sql_data;
    FmrTopTfcModel topTfcModel = new FmrTopTfcModel();
    List<Map<String, Object>> non_compliant;
    FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();
    String fif_pieChart;
    FofPlanSection planData = new FofPlanSection();
    String benchMarkDesc;
    String barChart;
    String sectAllocChart;
    String fundPerform1Date;
    String fundAssetAllocDate1;
    String fundAssetAllocDate2;


    public PlanPerformance getPerformanceGrid() {
        return this.performanceGrid;

    }


    public FofPlanSection getPlanData() {
        return this.planData;

    }


    public void setPlanData(FofPlanSection planData) {
        this.planData = planData;

    }


    public void setPerformanceGrid(PlanPerformance performanceGrid) {
        this.performanceGrid = performanceGrid;

    }


    public List<PlanPerformance> getPlanPerformance() {
        return this.planPerformance;

    }


    public void setPlanPerformance(List<PlanPerformance> planPerformance) {
        this.planPerformance = planPerformance;

    }


    public List<Map<String, Object>> getFpf_p_f1_data() {
        return this.fpf_p_f1_data;

    }


    public void setFpf_p_f1_data(List<Map<String, Object>> fpf_p_f1_data) {
        this.fpf_p_f1_data = fpf_p_f1_data;

    }


    public String getBenchMarkDesc() {
        return this.benchMarkDesc;

    }


    public void setBenchMarkDesc(String benchMarkDesc) {
        this.benchMarkDesc = benchMarkDesc;

    }


    public FmrAssetAllocMain getAssetAllocMain() {
        return this.assetAllocMain;

    }


    public void setAssetAllocMain(FmrAssetAllocMain assetAllocMain) {
        this.assetAllocMain = assetAllocMain;

    }


    public String getFundPerform1Date() {
        return this.fundPerform1Date;

    }


    public void setFundPerform1Date(String fundPerform1Date) {
        this.fundPerform1Date = fundPerform1Date;

    }


    public String getFundAssetAllocDate1() {
        return this.fundAssetAllocDate1;

    }


    public void setFundAssetAllocDate1(String fundAssetAllocDate1) {
        this.fundAssetAllocDate1 = fundAssetAllocDate1;

    }


    public String getFundAssetAllocDate2() {
        return this.fundAssetAllocDate2;

    }


    public void setFundAssetAllocDate2(String fundAssetAllocDate2) {
        this.fundAssetAllocDate2 = fundAssetAllocDate2;

    }


    public List<Map<String, Object>> getNon_compliant() {
        return this.non_compliant;

    }


    public void setNon_compliant(List<Map<String, Object>> non_compliant) {
        this.non_compliant = non_compliant;

    }


    public String getSectAllocChart() {
        return this.sectAllocChart;

    }


    public void setSectAllocChart(String sectAllocChart) {
        this.sectAllocChart = sectAllocChart;

    }


    public String getBarChart() {
        return this.barChart;

    }


    public void setBarChart(String barChart) {
        this.barChart = barChart;

    }


    public String getHeader() {
        return this.header;

    }


    public void setHeader(String header) {
        this.header = header;

    }


    public FundDefinitionReportModel getFundDef() {
        return this.fundDef;

    }


    public void setFundDef(FundDefinitionReportModel fundDef) {
        this.fundDef = fundDef;

    }


    public FmrFundBasicInfo getBasicInfo() {
        return this.basicInfo;

    }


    public void setBasicInfo(FmrFundBasicInfo basicInfo) {
        this.basicInfo = basicInfo;

    }


    public List<Map<String, Object>> getFif_perform_data1() {
        return this.fif_perform_data1;

    }


    public void setFif_perform_data1(List<Map<String, Object>> fif_perform_data1) {
        this.fif_perform_data1 = fif_perform_data1;

    }


    public List<Map<String, Object>> getFif_asset_header() {
        return this.fif_asset_header;

    }


    public void setFif_asset_header(List<Map<String, Object>> fif_asset_header) {
        this.fif_asset_header = fif_asset_header;

    }


    public List<Map<String, Object>> getFmr_tech_info_sql_data() {
        return this.fmr_tech_info_sql_data;

    }


    public void setFmr_tech_info_sql_data(List<Map<String, Object>> fmr_tech_info_sql_data) {
        this.fmr_tech_info_sql_data = fmr_tech_info_sql_data;

    }


    public String getFif_pieChart() {
        return this.fif_pieChart;

    }


    public void setFif_pieChart(String fif_pieChart) {
        this.fif_pieChart = fif_pieChart;

    }


    public FmrTopTfcModel getTopTfcModel() {
        return this.topTfcModel;

    }


    public void setTopTfcModel(FmrTopTfcModel topTfcModel) {
        this.topTfcModel = topTfcModel;

    }

}