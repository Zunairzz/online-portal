/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ import com.ams.model.FundCommentary;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FundCommentaryResponse
/*    */   extends BaseModel
/*    */ {
/*    */   List<FundCommentary> fundCommentaryList;
/*    */   
/*    */   public List<FundCommentary> getFundCommentaryList() {
/* 19 */     return this.fundCommentaryList;
/*    */   }
/*    */   
/*    */   public void setFundCommentaryList(List<FundCommentary> fundCommentaryList) {
/* 23 */     this.fundCommentaryList = fundCommentaryList;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FundCommentaryResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */