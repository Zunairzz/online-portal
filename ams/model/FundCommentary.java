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
/*    */ public class FundCommentary
/*    */   extends BaseModel
/*    */ {
/*    */   public String fundCode;
/*    */   public String commentary;
/*    */   public String transDate;
/*    */   
/*    */   public String getTransDate() {
/* 19 */     return this.transDate;
/*    */   }
/*    */   
/*    */   public void setTransDate(String transDate) {
/* 23 */     this.transDate = transDate;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFundCode() {
/* 29 */     return this.fundCode;
/*    */   }
/*    */   
/*    */   public void setFundCode(String fundCode) {
/* 33 */     this.fundCode = fundCode;
/*    */   }
/*    */   
/*    */   public String getCommentary() {
/* 37 */     return this.commentary;
/*    */   }
/*    */   
/*    */   public void setCommentary(String commentary) {
/* 41 */     this.commentary = commentary;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FundCommentary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */