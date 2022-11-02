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
/*    */ public class CreditRatingOfBanks
/*    */   extends BaseModel
/*    */ {
/*    */   public String glBankCode;
/*    */   public String glBankName;
/*    */   public String rating;
/*    */   
/*    */   public String getGlBankCode() {
/* 19 */     return this.glBankCode;
/*    */   }
/*    */   
/*    */   public void setGlBankCode(String glBankCode) {
/* 23 */     this.glBankCode = glBankCode;
/*    */   }
/*    */   
/*    */   public String getGlBankName() {
/* 27 */     return this.glBankName;
/*    */   }
/*    */   
/*    */   public void setGlBankName(String glBankName) {
/* 31 */     this.glBankName = glBankName;
/*    */   }
/*    */   
/*    */   public String getRating() {
/* 35 */     return this.rating;
/*    */   }
/*    */   
/*    */   public void setRating(String rating) {
/* 39 */     this.rating = rating;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\CreditRatingOfBanks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */