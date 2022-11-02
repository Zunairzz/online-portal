/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FundManageModel
/*    */   extends BaseModel
/*    */ {
/*    */   public String asOnDate;
/*    */   public String pFromDate;
/*    */   public String pToDate;
/*    */   public String pFundCode;
/*    */   public List<Map<String, Object>> responseData;
/*    */   
/*    */   public List<Map<String, Object>> getResponseData() {
/* 24 */     return this.responseData;
/*    */   }
/*    */   
/*    */   public void setResponseData(List<Map<String, Object>> responseData) {
/* 28 */     this.responseData = responseData;
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
/*    */   public String getpFundCode() {
/* 40 */     return this.pFundCode;
/*    */   }
/*    */   
/*    */   public void setpFundCode(String pFundCode) {
/* 44 */     this.pFundCode = pFundCode;
/*    */   }
/*    */   
/*    */   public String getpFromDate() {
/* 48 */     return this.pFromDate;
/*    */   }
/*    */   
/*    */   public void setpFromDate(String pFromDate) {
/* 52 */     this.pFromDate = pFromDate;
/*    */   }
/*    */   
/*    */   public String getpToDate() {
/* 56 */     return this.pToDate;
/*    */   }
/*    */   
/*    */   public void setpToDate(String pToDate) {
/* 60 */     this.pToDate = pToDate;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FundManageModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */