/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.BaseModel;
/*    */ import com.ams.model.ChartOfAccountModel;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChartOfAccountResponse
/*    */   extends BaseModel
/*    */ {
/*    */   List<ChartOfAccountModel> chartOfAccountList;
/*    */   
/*    */   public List<ChartOfAccountModel> getChartOfAccountList() {
/* 19 */     return this.chartOfAccountList;
/*    */   }
/*    */   
/*    */   public void setChartOfAccountList(List<ChartOfAccountModel> chartOfAccountList) {
/* 23 */     this.chartOfAccountList = chartOfAccountList;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\ChartOfAccountResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */