/*     */ package WEB-INF.classes.com.ams.model;
/*     */ 
/*     */ import com.ams.model.FmrAssetAllocMain;
/*     */ import com.ams.model.FmrFundBasicInfo;
/*     */ import com.ams.model.FmrTopTfcModel;
/*     */ import com.ams.model.FofPlanSection;
/*     */ import com.ams.model.PlanPerformance;
/*     */ import com.ams.model.fmr.FundDefinitionReportModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FmrPlanPdfReportModal
/*     */ {
/*     */   String header;
/*     */   FundDefinitionReportModel fundDef;
/*     */   FmrFundBasicInfo basicInfo;
/*  22 */   PlanPerformance performanceGrid = new PlanPerformance();
/*  23 */   List<PlanPerformance> planPerformance = new ArrayList<>();
/*     */   List<Map<String, Object>> fpf_p_f1_data;
/*     */   List<Map<String, Object>> fif_perform_data1;
/*     */   List<Map<String, Object>> fif_asset_header;
/*     */   List<Map<String, Object>> fmr_tech_info_sql_data;
/*  28 */   FmrTopTfcModel topTfcModel = new FmrTopTfcModel();
/*     */   List<Map<String, Object>> non_compliant;
/*  30 */   FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();
/*     */   String fif_pieChart;
/*  32 */   FofPlanSection planData = new FofPlanSection(); String benchMarkDesc;
/*     */   String barChart;
/*     */   String sectAllocChart;
/*     */   String fundPerform1Date;
/*     */   String fundAssetAllocDate1;
/*     */   String fundAssetAllocDate2;
/*     */   
/*     */   public PlanPerformance getPerformanceGrid() {
/*  40 */     return this.performanceGrid;
/*     */   }
/*     */   
/*     */   public FofPlanSection getPlanData() {
/*  44 */     return this.planData;
/*     */   }
/*     */   
/*     */   public void setPlanData(FofPlanSection planData) {
/*  48 */     this.planData = planData;
/*     */   }
/*     */   
/*     */   public void setPerformanceGrid(PlanPerformance performanceGrid) {
/*  52 */     this.performanceGrid = performanceGrid;
/*     */   }
/*     */   
/*     */   public List<PlanPerformance> getPlanPerformance() {
/*  56 */     return this.planPerformance;
/*     */   }
/*     */   
/*     */   public void setPlanPerformance(List<PlanPerformance> planPerformance) {
/*  60 */     this.planPerformance = planPerformance;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFpf_p_f1_data() {
/*  64 */     return this.fpf_p_f1_data;
/*     */   }
/*     */   
/*     */   public void setFpf_p_f1_data(List<Map<String, Object>> fpf_p_f1_data) {
/*  68 */     this.fpf_p_f1_data = fpf_p_f1_data;
/*     */   }
/*     */   
/*     */   public String getBenchMarkDesc() {
/*  72 */     return this.benchMarkDesc;
/*     */   }
/*     */   
/*     */   public void setBenchMarkDesc(String benchMarkDesc) {
/*  76 */     this.benchMarkDesc = benchMarkDesc;
/*     */   }
/*     */   
/*     */   public FmrAssetAllocMain getAssetAllocMain() {
/*  80 */     return this.assetAllocMain;
/*     */   }
/*     */   
/*     */   public void setAssetAllocMain(FmrAssetAllocMain assetAllocMain) {
/*  84 */     this.assetAllocMain = assetAllocMain;
/*     */   }
/*     */   
/*     */   public String getFundPerform1Date() {
/*  88 */     return this.fundPerform1Date;
/*     */   }
/*     */   
/*     */   public void setFundPerform1Date(String fundPerform1Date) {
/*  92 */     this.fundPerform1Date = fundPerform1Date;
/*     */   }
/*     */   
/*     */   public String getFundAssetAllocDate1() {
/*  96 */     return this.fundAssetAllocDate1;
/*     */   }
/*     */   
/*     */   public void setFundAssetAllocDate1(String fundAssetAllocDate1) {
/* 100 */     this.fundAssetAllocDate1 = fundAssetAllocDate1;
/*     */   }
/*     */   
/*     */   public String getFundAssetAllocDate2() {
/* 104 */     return this.fundAssetAllocDate2;
/*     */   }
/*     */   
/*     */   public void setFundAssetAllocDate2(String fundAssetAllocDate2) {
/* 108 */     this.fundAssetAllocDate2 = fundAssetAllocDate2;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getNon_compliant() {
/* 112 */     return this.non_compliant;
/*     */   }
/*     */   
/*     */   public void setNon_compliant(List<Map<String, Object>> non_compliant) {
/* 116 */     this.non_compliant = non_compliant;
/*     */   }
/*     */   
/*     */   public String getSectAllocChart() {
/* 120 */     return this.sectAllocChart;
/*     */   }
/*     */   
/*     */   public void setSectAllocChart(String sectAllocChart) {
/* 124 */     this.sectAllocChart = sectAllocChart;
/*     */   }
/*     */   
/*     */   public String getBarChart() {
/* 128 */     return this.barChart;
/*     */   }
/*     */   
/*     */   public void setBarChart(String barChart) {
/* 132 */     this.barChart = barChart;
/*     */   }
/*     */   
/*     */   public String getHeader() {
/* 136 */     return this.header;
/*     */   }
/*     */   
/*     */   public void setHeader(String header) {
/* 140 */     this.header = header;
/*     */   }
/*     */   
/*     */   public FundDefinitionReportModel getFundDef() {
/* 144 */     return this.fundDef;
/*     */   }
/*     */   
/*     */   public void setFundDef(FundDefinitionReportModel fundDef) {
/* 148 */     this.fundDef = fundDef;
/*     */   }
/*     */   
/*     */   public FmrFundBasicInfo getBasicInfo() {
/* 152 */     return this.basicInfo;
/*     */   }
/*     */   
/*     */   public void setBasicInfo(FmrFundBasicInfo basicInfo) {
/* 156 */     this.basicInfo = basicInfo;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFif_perform_data1() {
/* 160 */     return this.fif_perform_data1;
/*     */   }
/*     */   
/*     */   public void setFif_perform_data1(List<Map<String, Object>> fif_perform_data1) {
/* 164 */     this.fif_perform_data1 = fif_perform_data1;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFif_asset_header() {
/* 168 */     return this.fif_asset_header;
/*     */   }
/*     */   
/*     */   public void setFif_asset_header(List<Map<String, Object>> fif_asset_header) {
/* 172 */     this.fif_asset_header = fif_asset_header;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFmr_tech_info_sql_data() {
/* 176 */     return this.fmr_tech_info_sql_data;
/*     */   }
/*     */   
/*     */   public void setFmr_tech_info_sql_data(List<Map<String, Object>> fmr_tech_info_sql_data) {
/* 180 */     this.fmr_tech_info_sql_data = fmr_tech_info_sql_data;
/*     */   }
/*     */   
/*     */   public String getFif_pieChart() {
/* 184 */     return this.fif_pieChart;
/*     */   }
/*     */   
/*     */   public void setFif_pieChart(String fif_pieChart) {
/* 188 */     this.fif_pieChart = fif_pieChart;
/*     */   }
/*     */   
/*     */   public FmrTopTfcModel getTopTfcModel() {
/* 192 */     return this.topTfcModel;
/*     */   }
/*     */   
/*     */   public void setTopTfcModel(FmrTopTfcModel topTfcModel) {
/* 196 */     this.topTfcModel = topTfcModel;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrPlanPdfReportModal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */