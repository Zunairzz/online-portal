/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.FmrModel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmrFundWiseData
/*    */ {
/* 17 */   public List<FmrModel> data = new ArrayList<>();
/*    */   
/*    */   public List<FmrModel> getData() {
/* 20 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(List<FmrModel> data) {
/* 24 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrFundWiseData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */