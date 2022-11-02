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
/*    */ public class FmrCategory
/*    */   extends BaseModel
/*    */ {
/*    */   public String CATID;
/*    */   public String CATNAME;
/*    */   public String CATSORT;
/*    */   public String CATCALCULATION;
/*    */   public String ISDELETED;
/*    */   
/*    */   public String getCATID() {
/* 21 */     return this.CATID;
/*    */   }
/*    */   
/*    */   public void setCATID(String CATID) {
/* 25 */     this.CATID = CATID;
/*    */   }
/*    */   
/*    */   public String getCATNAME() {
/* 29 */     return this.CATNAME;
/*    */   }
/*    */   
/*    */   public void setCATNAME(String CATNAME) {
/* 33 */     this.CATNAME = CATNAME;
/*    */   }
/*    */   
/*    */   public String getCATSORT() {
/* 37 */     return this.CATSORT;
/*    */   }
/*    */   
/*    */   public void setCATSORT(String CATSORT) {
/* 41 */     this.CATSORT = CATSORT;
/*    */   }
/*    */   
/*    */   public String getCATCALCULATION() {
/* 45 */     return this.CATCALCULATION;
/*    */   }
/*    */   
/*    */   public void setCATCALCULATION(String CATCALCULATION) {
/* 49 */     this.CATCALCULATION = CATCALCULATION;
/*    */   }
/*    */   
/*    */   public String getISDELETED() {
/* 53 */     return this.ISDELETED;
/*    */   }
/*    */   
/*    */   public void setISDELETED(String ISDELETED) {
/* 57 */     this.ISDELETED = ISDELETED;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */