/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class FmrTopTfcModel
/*    */ {
/* 18 */   List<Map<String, Object>> tfcData = new ArrayList<>();
/*    */   String total;
/*    */   
/*    */   public List<Map<String, Object>> getTfcData() {
/* 22 */     return this.tfcData;
/*    */   }
/*    */   
/*    */   public void setTfcData(List<Map<String, Object>> tfcData) {
/* 26 */     this.tfcData = tfcData;
/*    */   }
/*    */   
/*    */   public String getTotal() {
/* 30 */     return this.total;
/*    */   }
/*    */   
/*    */   public void setTotal(String total) {
/* 34 */     this.total = total;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrTopTfcModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */