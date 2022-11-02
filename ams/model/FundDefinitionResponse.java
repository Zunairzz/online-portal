/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ import com.ams.model.FundDefinition;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FundDefinitionResponse
/*    */   extends BaseModel
/*    */ {
/*    */   List<FundDefinition> fundDefinitionList;
/*    */   
/*    */   public List<FundDefinition> getFundDefinitionList() {
/* 19 */     return this.fundDefinitionList;
/*    */   }
/*    */   
/*    */   public void setFundDefinitionList(List<FundDefinition> fundDefinitionList) {
/* 23 */     this.fundDefinitionList = fundDefinitionList;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FundDefinitionResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */