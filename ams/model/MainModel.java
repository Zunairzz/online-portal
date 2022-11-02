/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainModel
/*    */   extends BaseModel
/*    */ {
/*    */   public String fundCode;
/*    */   public String transDate;
/*    */   public String result;
/*    */   public String fromDate;
/*    */   public String toDate;
/*    */   public String asOnDate;
/*    */   public String navDate;
/*    */   public String searchFolioNumber;
/*    */   
/*    */   public String getNavDate() {
/* 24 */     return this.navDate;
/*    */   }
/*    */   
/*    */   public void setNavDate(String navDate) {
/* 28 */     this.navDate = navDate;
/*    */   }
/*    */   
/*    */   public String getAsOnDate() {
/* 32 */     return this.asOnDate;
/*    */   }
/*    */   
/*    */   public void setAsOnDate(String asOnDate) {
/* 36 */     this.asOnDate = asOnDate;
/*    */   }
/*    */   
/*    */   public String getSearchFolioNumber() {
/* 40 */     return this.searchFolioNumber;
/*    */   }
/*    */   
/*    */   public void setSearchFolioNumber(String searchFolioNumber) {
/* 44 */     this.searchFolioNumber = searchFolioNumber;
/*    */   }
/*    */   
/*    */   public String getFromDate() {
/* 48 */     return this.fromDate;
/*    */   }
/*    */   
/*    */   public void setFromDate(String fromDate) {
/* 52 */     this.fromDate = fromDate;
/*    */   }
/*    */   
/*    */   public String getToDate() {
/* 56 */     return this.toDate;
/*    */   }
/*    */   
/*    */   public void setToDate(String toDate) {
/* 60 */     this.toDate = toDate;
/*    */   }
/*    */   
/*    */   public String getFundCode() {
/* 64 */     return this.fundCode;
/*    */   }
/*    */   
/*    */   public void setFundCode(String fundCode) {
/* 68 */     this.fundCode = fundCode;
/*    */   }
/*    */   
/*    */   public String getResult() {
/* 72 */     return this.result;
/*    */   }
/*    */   
/*    */   public void setResult(String result) {
/* 76 */     this.result = result;
/*    */   }
/*    */   
/*    */   public String getTransDate() {
/* 80 */     return this.transDate;
/*    */   }
/*    */   
/*    */   public void setTransDate(String transDate) {
/* 84 */     this.transDate = transDate;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\MainModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */