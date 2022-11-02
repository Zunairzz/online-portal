/*     */ package WEB-INF.classes.com.ams.model;
/*     */ 
/*     */ import com.ams.model.BaseModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FmrFundBasicInfo
/*     */   extends BaseModel
/*     */ {
/*     */   public String id;
/*     */   public String transDate;
/*     */   public String fundCode;
/*     */   public String fund_type;
/*     */   public String category;
/*     */   public String benchmarkLabel;
/*     */   public String benchmark;
/*     */   public String dealing_days;
/*     */   public String cutt_off_time;
/*     */   public String pricing_mechanism;
/*     */   public String management_fee;
/*     */   public String front_end_load;
/*     */   public String trustee;
/*     */   public String auditor;
/*     */   public String asset_manager_rating;
/*     */   public String risk_prof_fund;
/*     */   public String perform_ranking;
/*     */   public String fund_manger;
/*     */   public String listing;
/*     */   public String comments;
/*     */   public String launchdate;
/*     */   public String netassets;
/*     */   public String netassetexfof;
/*     */   public String assetallocationInvestedAmount;
/*     */   public String fundTer;
/*     */   public String nav;
/*     */   public String ter_gov_levy;
/*     */   public String swwf_nav_unit;
/*     */   public double swwf;
/*     */   public double smexp;
/*     */   
/*     */   public double getSmexp() {
/*  42 */     return this.smexp;
/*     */   }
/*     */   public void setSmexp(double smexp) {
/*  45 */     this.smexp = smexp;
/*     */   }
/*     */   public double getSwwf() {
/*  48 */     return this.swwf;
/*     */   }
/*     */   
/*     */   public void setSwwf(double swwf) {
/*  52 */     this.swwf = swwf;
/*     */   }
/*     */   
/*     */   public String getSwwf_nav_unit() {
/*  56 */     return this.swwf_nav_unit;
/*     */   }
/*     */   
/*     */   public void setSwwf_nav_unit(String swwf_nav_unit) {
/*  60 */     this.swwf_nav_unit = swwf_nav_unit;
/*     */   }
/*     */   public String getTransDate() {
/*  63 */     return this.transDate;
/*     */   }
/*     */   
/*     */   public void setTransDate(String transDate) {
/*  67 */     this.transDate = transDate;
/*     */   }
/*     */   
/*     */   public String getFundCode() {
/*  71 */     return this.fundCode;
/*     */   }
/*     */   
/*     */   public void setFundCode(String fundCode) {
/*  75 */     this.fundCode = fundCode;
/*     */   }
/*     */   
/*     */   public String getTer_gov_levy() {
/*  79 */     return this.ter_gov_levy;
/*     */   }
/*     */   
/*     */   public void setTer_gov_levy(String ter_gov_levy) {
/*  83 */     this.ter_gov_levy = ter_gov_levy;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNav() {
/*  88 */     return this.nav;
/*     */   }
/*     */   
/*     */   public void setNav(String nav) {
/*  92 */     this.nav = nav;
/*     */   }
/*     */   
/*     */   public String getAssetallocationInvestedAmount() {
/*  96 */     return this.assetallocationInvestedAmount;
/*     */   }
/*     */   
/*     */   public void setAssetallocationInvestedAmount(String assetallocationInvestedAmount) {
/* 100 */     this.assetallocationInvestedAmount = assetallocationInvestedAmount;
/*     */   }
/*     */   
/*     */   public String getBenchmarkLabel() {
/* 104 */     return this.benchmarkLabel;
/*     */   }
/*     */   
/*     */   public void setBenchmarkLabel(String benchmarkLabel) {
/* 108 */     this.benchmarkLabel = benchmarkLabel;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 112 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 116 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getTransdate() {
/* 120 */     return this.transDate;
/*     */   }
/*     */   
/*     */   public void setTransdate(String transdate) {
/* 124 */     this.transDate = transdate;
/*     */   }
/*     */   
/*     */   public String getFund_code() {
/* 128 */     return this.fundCode;
/*     */   }
/*     */   
/*     */   public void setFund_code(String fundCode) {
/* 132 */     this.fundCode = fundCode;
/*     */   }
/*     */   
/*     */   public String getFund_type() {
/* 136 */     return this.fund_type;
/*     */   }
/*     */   
/*     */   public void setFund_type(String fund_type) {
/* 140 */     this.fund_type = fund_type;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 144 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String category) {
/* 148 */     this.category = category;
/*     */   }
/*     */   
/*     */   public String getBenchmark() {
/* 152 */     return this.benchmark;
/*     */   }
/*     */   
/*     */   public void setBenchmark(String benchmark) {
/* 156 */     this.benchmark = benchmark;
/*     */   }
/*     */   
/*     */   public String getDealing_days() {
/* 160 */     return this.dealing_days;
/*     */   }
/*     */   
/*     */   public void setDealing_days(String dealing_days) {
/* 164 */     this.dealing_days = dealing_days;
/*     */   }
/*     */   
/*     */   public String getCutt_off_time() {
/* 168 */     return this.cutt_off_time;
/*     */   }
/*     */   
/*     */   public void setCutt_off_time(String cutt_off_time) {
/* 172 */     this.cutt_off_time = cutt_off_time;
/*     */   }
/*     */   
/*     */   public String getPricing_mechanism() {
/* 176 */     return this.pricing_mechanism;
/*     */   }
/*     */   
/*     */   public void setPricing_mechanism(String pricing_mechanism) {
/* 180 */     this.pricing_mechanism = pricing_mechanism;
/*     */   }
/*     */   
/*     */   public String getManagement_fee() {
/* 184 */     return this.management_fee;
/*     */   }
/*     */   
/*     */   public void setManagement_fee(String management_fee) {
/* 188 */     this.management_fee = management_fee;
/*     */   }
/*     */   
/*     */   public String getFront_end_load() {
/* 192 */     return this.front_end_load;
/*     */   }
/*     */   
/*     */   public void setFront_end_load(String front_end_load) {
/* 196 */     this.front_end_load = front_end_load;
/*     */   }
/*     */   
/*     */   public String getTrustee() {
/* 200 */     return this.trustee;
/*     */   }
/*     */   
/*     */   public void setTrustee(String trustee) {
/* 204 */     this.trustee = trustee;
/*     */   }
/*     */   
/*     */   public String getAuditor() {
/* 208 */     return this.auditor;
/*     */   }
/*     */   
/*     */   public void setAuditor(String auditor) {
/* 212 */     this.auditor = auditor;
/*     */   }
/*     */   
/*     */   public String getAsset_manager_rating() {
/* 216 */     return this.asset_manager_rating;
/*     */   }
/*     */   
/*     */   public void setAsset_manager_rating(String asset_manager_rating) {
/* 220 */     this.asset_manager_rating = asset_manager_rating;
/*     */   }
/*     */   
/*     */   public String getRisk_prof_fund() {
/* 224 */     return this.risk_prof_fund;
/*     */   }
/*     */   
/*     */   public void setRisk_prof_fund(String risk_prof_fund) {
/* 228 */     this.risk_prof_fund = risk_prof_fund;
/*     */   }
/*     */   
/*     */   public String getPerform_ranking() {
/* 232 */     return this.perform_ranking;
/*     */   }
/*     */   
/*     */   public void setPerform_ranking(String perform_ranking) {
/* 236 */     this.perform_ranking = perform_ranking;
/*     */   }
/*     */   
/*     */   public String getFund_manger() {
/* 240 */     return this.fund_manger;
/*     */   }
/*     */   
/*     */   public void setFund_manger(String fund_manger) {
/* 244 */     this.fund_manger = fund_manger;
/*     */   }
/*     */   
/*     */   public String getListing() {
/* 248 */     return this.listing;
/*     */   }
/*     */   
/*     */   public void setListing(String listing) {
/* 252 */     this.listing = listing;
/*     */   }
/*     */   
/*     */   public String getComments() {
/* 256 */     return this.comments;
/*     */   }
/*     */   
/*     */   public void setComments(String comments) {
/* 260 */     this.comments = comments;
/*     */   }
/*     */   
/*     */   public String getLaunchdate() {
/* 264 */     return this.launchdate;
/*     */   }
/*     */   
/*     */   public void setLaunchdate(String launchdate) {
/* 268 */     this.launchdate = launchdate;
/*     */   }
/*     */   
/*     */   public String getNetassets() {
/* 272 */     return this.netassets;
/*     */   }
/*     */   
/*     */   public void setNetassets(String netassets) {
/* 276 */     this.netassets = netassets;
/*     */   }
/*     */   
/*     */   public String getNetassetexfof() {
/* 280 */     return this.netassetexfof;
/*     */   }
/*     */   
/*     */   public void setNetassetexfof(String netassetexfof) {
/* 284 */     this.netassetexfof = netassetexfof;
/* 285 */     setAssetallocationInvestedAmount(String.format("%.2f", new Object[] { Double.valueOf(Double.valueOf(getNetassets()).doubleValue() - Double.valueOf(getNetassetexfof()).doubleValue()) }));
/*     */   }
/*     */   
/*     */   public String getFundTer() {
/* 289 */     return this.fundTer;
/*     */   }
/*     */   
/*     */   public void setFundTer(String fundTer) {
/* 293 */     this.fundTer = fundTer;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrFundBasicInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */