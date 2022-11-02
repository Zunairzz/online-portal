/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ import com.ams.model.FmrFundBasicInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmrModel
/*    */   extends BaseModel
/*    */ {
/*    */   public String fundTitle;
/*    */   public String comments;
/*    */   public String objective;
/*    */   public String members;
/*    */   public String launchDate;
/*    */   public FmrFundBasicInfo basicInfo;
/*    */   
/*    */   public FmrFundBasicInfo getBasicInfo() {
/* 22 */     return this.basicInfo;
/*    */   }
/*    */   
/*    */   public void setBasicInfo(FmrFundBasicInfo basicInfo) {
/* 26 */     this.basicInfo = basicInfo;
/*    */   }
/*    */   
/*    */   public String getFundTitle() {
/* 30 */     return this.fundTitle;
/*    */   }
/*    */   
/*    */   public void setFundTitle(String fundTitle) {
/* 34 */     this.fundTitle = fundTitle;
/*    */   }
/*    */   
/*    */   public String getComments() {
/* 38 */     return this.comments;
/*    */   }
/*    */   
/*    */   public void setComments(String comments) {
/* 42 */     this.comments = comments;
/*    */   }
/*    */   
/*    */   public String getObjective() {
/* 46 */     return this.objective;
/*    */   }
/*    */   
/*    */   public void setObjective(String objective) {
/* 50 */     this.objective = objective;
/*    */   }
/*    */   
/*    */   public String getMembers() {
/* 54 */     return this.members;
/*    */   }
/*    */   
/*    */   public void setMembers(String members) {
/* 58 */     this.members = members;
/*    */   }
/*    */   
/*    */   public void setLaunchDate(String launchDate) {
/* 62 */     this.launchDate = launchDate;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */