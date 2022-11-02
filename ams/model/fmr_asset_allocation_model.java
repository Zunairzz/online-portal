/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ public class fmr_asset_allocation_model
/*    */ {
/*    */   public String code;
/*    */   public String secName;
/*    */   public String prev_month;
/*    */   public String curr_month;
/*    */   
/*    */   public String getCode() {
/* 23 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(String code) {
/* 27 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getSecName() {
/* 31 */     return this.secName;
/*    */   }
/*    */   
/*    */   public void setSecName(String secName) {
/* 35 */     this.secName = secName;
/*    */   }
/*    */   
/*    */   public String getPrev_month() {
/* 39 */     return String.format("%.2f", new Object[] { Float.valueOf(Float.parseFloat(this.prev_month)) });
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPrev_month(String prev_month) {
/* 44 */     this.prev_month = prev_month;
/*    */   }
/*    */   
/*    */   public String getCurr_month() {
/* 48 */     return String.format("%.2f", new Object[] { Float.valueOf(Float.parseFloat(this.curr_month)) });
/*    */   }
/*    */   
/*    */   public double getCurr_monthDouble() {
/* 52 */     return Double.parseDouble(this.curr_month);
/*    */   }
/*    */   
/*    */   public void setCurr_month(String curr_month) {
/* 56 */     this.curr_month = curr_month;
/*    */   }
/*    */   
/*    */   public static List<com.ams.model.fmr_asset_allocation_model> filter(List<com.ams.model.fmr_asset_allocation_model> currlist, boolean filterByName) {
/* 60 */     currlist.removeIf(aa -> (Float.parseFloat(aa.getPrev_month()) <= 0.0F && Float.parseFloat(aa.getCurr_month()) <= 0.0F));
/* 61 */     if (filterByName) {
/* 62 */       currlist.sort(Comparator.comparing(com.ams.model.fmr_asset_allocation_model::getSecName));
/*    */     } else {
/* 64 */       currlist.sort(Comparator.<com.ams.model.fmr_asset_allocation_model>comparingDouble(com.ams.model.fmr_asset_allocation_model::getCurr_monthDouble).reversed());
/* 65 */     }  return currlist;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\fmr_asset_allocation_model.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */