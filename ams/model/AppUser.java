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
/*    */ public class AppUser
/*    */ {
/*    */   private Long userId;
/*    */   private String userName;
/*    */   private String encrytedPassword;
/*    */   
/*    */   public AppUser() {
/* 19 */     this.userId = Long.valueOf("1");
/* 20 */     this.userName = "admin";
/* 21 */     this.encrytedPassword = "$2a$10$Oxao1Xm6JND7DxHp.OLVGOkT/NFeftthZsv1ub/Ux5LpOr6ZRIE86";
/*    */   }
/*    */   
/*    */   public AppUser(Long userId, String userName, String encrytedPassword) {
/* 25 */     this.userId = userId;
/* 26 */     this.userName = userName;
/* 27 */     this.encrytedPassword = encrytedPassword;
/*    */   }
/*    */   
/*    */   public Long getUserId() {
/* 31 */     return this.userId;
/*    */   }
/*    */   
/*    */   public void setUserId(Long userId) {
/* 35 */     this.userId = userId;
/*    */   }
/*    */   
/*    */   public String getUserName() {
/* 39 */     return this.userName;
/*    */   }
/*    */   
/*    */   public void setUserName(String userName) {
/* 43 */     this.userName = userName;
/*    */   }
/*    */   
/*    */   public String getEncrytedPassword() {
/* 47 */     return this.encrytedPassword;
/*    */   }
/*    */   
/*    */   public void setEncrytedPassword(String encrytedPassword) {
/* 51 */     this.encrytedPassword = encrytedPassword;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return this.userName + "/" + this.encrytedPassword;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\AppUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */