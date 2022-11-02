/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChartOfAccountModel
/*    */   extends BaseModel
/*    */ {
/*    */   public String id;
/*    */   public String glCode;
/*    */   public String description;
/*    */   public String transDate;
/*    */   public String fundCode;
/*    */   public String fDate;
/*    */   public String tDate;
/*    */   
/*    */   public String getfDate() {
/* 22 */     return this.fDate;
/*    */   }
/*    */   
/*    */   public void setfDate(String fDate) {
/* 26 */     this.fDate = fDate;
/*    */   }
/*    */   
/*    */   public String gettDate() {
/* 30 */     return this.tDate;
/*    */   }
/*    */   
/*    */   public void settDate(String tDate) {
/* 34 */     this.tDate = tDate;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 42 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getFundCode() {
/* 46 */     return this.fundCode;
/*    */   }
/*    */   
/*    */   public void setFundCode(String fundCode) {
/* 50 */     this.fundCode = fundCode;
/*    */   }
/*    */   
/*    */   public String getGlCode() {
/* 54 */     return this.glCode;
/*    */   }
/*    */   
/*    */   public void setGlCode(String glCode) {
/* 58 */     this.glCode = glCode;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 62 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 66 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getTransDate() {
/* 70 */     return this.transDate;
/*    */   }
/*    */   
/*    */   public void setTransDate(String transDate) {
/* 74 */     this.transDate = transDate;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\ChartOfAccountModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */