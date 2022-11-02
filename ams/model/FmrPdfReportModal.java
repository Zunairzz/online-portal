/*     */ package WEB-INF.classes.com.ams.model;
/*     */ 
/*     */ import com.ams.model.FmrAssetAllocMain;
/*     */ import com.ams.model.FmrFundBasicInfo;
/*     */ import com.ams.model.FmrTopTfcModel;
/*     */ import com.ams.model.fmr.FundDefinitionReportModel;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FmrPdfReportModal
/*     */ {
/*     */   String header;
/*     */   FundDefinitionReportModel fundDef;
/*     */   FmrFundBasicInfo basicInfo;
/*     */   List<Map<String, Object>> fif_perform_data;
/*     */   List<Map<String, Object>> fif_perform_data1;
/*     */   List<Map<String, Object>> fif_asset_header;
/*     */   List<Map<String, Object>> fmr_tech_info_sql_data;
/*  25 */   FmrTopTfcModel topTfcModel = new FmrTopTfcModel();
/*  26 */   FmrTopTfcModel topTenHoldingModel = new FmrTopTfcModel();
/*     */   List<Map<String, Object>> non_compliant;
/*  28 */   FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();
/*     */   
/*     */   String fif_pieChart;
/*     */   
/*     */   String iaaf_pieChart;
/*     */   
/*     */   String benchMarkDesc;
/*     */   String barChart;
/*     */   
/*     */   public String getIaaf_pieChart() {
/*  38 */     return this.iaaf_pieChart;
/*     */   }
/*     */ 
/*     */   
/*     */   String sectAllocChart;
/*     */   String fundPerform1Date;
/*     */   String fundAssetAllocDate1;
/*     */   String fundAssetAllocDate2;
/*     */   
/*     */   public void setIaaf_pieChart(String iaaf_pieChart) {
/*  48 */     this.iaaf_pieChart = iaaf_pieChart;
/*     */   }
/*     */ 
/*     */   
/*     */   public FmrTopTfcModel getTopTenHoldingModel() {
/*  53 */     return this.topTenHoldingModel;
/*     */   }
/*     */   
/*     */   public void setTopTenHoldingModel(FmrTopTfcModel topTenHoldingModel) {
/*  57 */     this.topTenHoldingModel = topTenHoldingModel;
/*     */   }
/*     */   
/*     */   public String getBenchMarkDesc() {
/*  61 */     return this.benchMarkDesc;
/*     */   }
/*     */   
/*     */   public void setBenchMarkDesc(String benchMarkDesc) {
/*  65 */     this.benchMarkDesc = benchMarkDesc;
/*     */   }
/*     */   
/*     */   public FmrAssetAllocMain getAssetAllocMain() {
/*  69 */     return this.assetAllocMain;
/*     */   }
/*     */   
/*     */   public void setAssetAllocMain(FmrAssetAllocMain assetAllocMain) {
/*  73 */     if (this.basicInfo != null) {
/*  74 */       Float amount = Float.valueOf(Float.parseFloat(this.basicInfo.getNetassets()) - Float.parseFloat(this.basicInfo.getNetassetexfof()));
/*  75 */       if (amount.floatValue() > 0.0F) {
/*  76 */         String otherAmountByFof = String.format("%.2f", new Object[] { amount });
/*  77 */         assetAllocMain.setDescription("Others Amount Invested by Fund of Funds is Rs. " + otherAmountByFof + " million.");
/*     */       } else {
/*  79 */         assetAllocMain.setDescription("Others Amount Invested by Fund of Funds is Nil.");
/*     */       } 
/*     */     } 
/*  82 */     this.assetAllocMain = assetAllocMain;
/*     */   }
/*     */   
/*     */   public String getFundPerform1Date() {
/*  86 */     return this.fundPerform1Date;
/*     */   }
/*     */   
/*     */   public void setFundPerform1Date(String fundPerform1Date) {
/*  90 */     this.fundPerform1Date = fundPerform1Date;
/*     */   }
/*     */   
/*     */   public String getFundAssetAllocDate1() {
/*  94 */     return this.fundAssetAllocDate1;
/*     */   }
/*     */   
/*     */   public void setFundAssetAllocDate1(String fundAssetAllocDate1) {
/*  98 */     this.fundAssetAllocDate1 = fundAssetAllocDate1;
/*     */   }
/*     */   
/*     */   public String getFundAssetAllocDate2() {
/* 102 */     return this.fundAssetAllocDate2;
/*     */   }
/*     */   
/*     */   public void setFundAssetAllocDate2(String fundAssetAllocDate2) {
/* 106 */     this.fundAssetAllocDate2 = fundAssetAllocDate2;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getNon_compliant() {
/* 110 */     return this.non_compliant;
/*     */   }
/*     */   
/*     */   public void setNon_compliant(List<Map<String, Object>> non_compliant) {
/* 114 */     this.non_compliant = non_compliant;
/*     */   }
/*     */   
/*     */   public String getSectAllocChart() {
/* 118 */     return this.sectAllocChart;
/*     */   }
/*     */   
/*     */   public void setSectAllocChart(String sectAllocChart) {
/* 122 */     this.sectAllocChart = sectAllocChart;
/*     */   }
/*     */   
/*     */   public String getBarChart() {
/* 126 */     return this.barChart;
/*     */   }
/*     */   
/*     */   public void setBarChart(String barChart) {
/* 130 */     this.barChart = barChart;
/*     */   }
/*     */   
/*     */   public String getHeader() {
/* 134 */     return this.header;
/*     */   }
/*     */   
/*     */   public void setHeader(String header) {
/* 138 */     this.header = header;
/*     */   }
/*     */   
/*     */   public FundDefinitionReportModel getFundDef() {
/* 142 */     return this.fundDef;
/*     */   }
/*     */   
/*     */   public void setFundDef(FundDefinitionReportModel fundDef) {
/* 146 */     this.fundDef = fundDef;
/*     */   }
/*     */   
/*     */   public FmrFundBasicInfo getBasicInfo() {
/* 150 */     return this.basicInfo;
/*     */   }
/*     */   
/*     */   public void setBasicInfo(FmrFundBasicInfo basicInfo) {
/* 154 */     this.basicInfo = basicInfo;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFif_perform_data() {
/* 158 */     return this.fif_perform_data;
/*     */   }
/*     */   
/*     */   public void setFif_perform_data(List<Map<String, Object>> fif_perform_data) {
/* 162 */     this.fif_perform_data = fif_perform_data;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFif_perform_data1() {
/* 166 */     return this.fif_perform_data1;
/*     */   }
/*     */   
/*     */   public void setFif_perform_data1(List<Map<String, Object>> fif_perform_data1) {
/* 170 */     this.fif_perform_data1 = fif_perform_data1;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFif_asset_header() {
/* 174 */     return this.fif_asset_header;
/*     */   }
/*     */   
/*     */   public void setFif_asset_header(List<Map<String, Object>> fif_asset_header) {
/* 178 */     this.fif_asset_header = fif_asset_header;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> getFmr_tech_info_sql_data() {
/* 182 */     return this.fmr_tech_info_sql_data;
/*     */   }
/*     */   
/*     */   public void setFmr_tech_info_sql_data(List<Map<String, Object>> fmr_tech_info_sql_data) {
/* 186 */     this.fmr_tech_info_sql_data = fmr_tech_info_sql_data;
/*     */   }
/*     */   
/*     */   public String getFif_pieChart() {
/* 190 */     return this.fif_pieChart;
/*     */   }
/*     */   
/*     */   public void setFif_pieChart(String fif_pieChart) {
/* 194 */     this.fif_pieChart = fif_pieChart;
/*     */   }
/*     */   
/*     */   public FmrTopTfcModel getTopTfcModel() {
/* 198 */     return this.topTfcModel;
/*     */   }
/*     */   
/*     */   public void setTopTfcModel(FmrTopTfcModel topTfcModel) {
/* 202 */     this.topTfcModel = topTfcModel;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrPdfReportModal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */