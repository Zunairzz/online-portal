/*    */ package WEB-INF.classes.com.ams.model;
/*    */ 
/*    */ import com.ams.model.fmr_asset_allocation_model;
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
/*    */ public class FmrAssetAllocMain
/*    */ {
/*    */   String description;
/*    */   String totalPrevAlloc;
/*    */   String totalCurrAlloc;
/*    */   List<fmr_asset_allocation_model> fif_prev_asset_alloc;
/*    */   
/*    */   public String getDescription() {
/* 22 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 26 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getTotalPrevAlloc() {
/* 30 */     return this.totalPrevAlloc;
/*    */   }
/*    */   
/*    */   public void setTotalPrevAlloc(String totalPrevAlloc) {
/* 34 */     this.totalPrevAlloc = totalPrevAlloc;
/*    */   }
/*    */   
/*    */   public String getTotalCurrAlloc() {
/* 38 */     return this.totalCurrAlloc;
/*    */   }
/*    */   
/*    */   public void setTotalCurrAlloc(String totalCurrAlloc) {
/* 42 */     this.totalCurrAlloc = totalCurrAlloc;
/*    */   }
/*    */   
/*    */   public List<fmr_asset_allocation_model> getFif_prev_asset_alloc() {
/* 46 */     return this.fif_prev_asset_alloc;
/*    */   }
/*    */   
/*    */   public void setFif_prev_asset_alloc(List<fmr_asset_allocation_model> fif_prev_asset_alloc) {
/* 50 */     setTotalPrevAlloc(String.valueOf(Math.round(fif_prev_asset_alloc.stream().mapToDouble(o -> Double.valueOf(o.getPrev_month()).doubleValue()).sum())));
/* 51 */     setTotalCurrAlloc(String.valueOf(Math.round(fif_prev_asset_alloc.stream().mapToDouble(o -> o.getCurr_monthDouble()).sum())));
/* 52 */     this.fif_prev_asset_alloc = fif_prev_asset_alloc;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FmrAssetAllocMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */