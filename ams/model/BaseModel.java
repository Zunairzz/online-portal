/*    */ package WEB-INF.classes.com.ams.model;
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
/*    */ public class BaseModel
/*    */ {
/*    */   public String ResponseCode;
/*    */   public String reserved;
/*    */   public String operationCode;
/*    */   
/*    */   public String getOperationCode() {
/* 19 */     return this.operationCode;
/*    */   }
/*    */   
/*    */   public void setOperationCode(String operationCode) {
/* 23 */     this.operationCode = operationCode;
/*    */   }
/*    */   
/*    */   public String getResponse_code() {
/* 27 */     return this.ResponseCode;
/*    */   }
/*    */   
/*    */   public void setResponse_code(String ResponseCode) {
/* 31 */     this.ResponseCode = ResponseCode;
/*    */   }
/*    */   
/*    */   public String getReserved() {
/* 35 */     return this.reserved;
/*    */   }
/*    */   
/*    */   public void setReserved(String reserved) {
/* 39 */     this.reserved = reserved;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\BaseModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */