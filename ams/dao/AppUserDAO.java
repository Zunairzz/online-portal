/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.AppUser;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.annotation.Transactional;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository
/*    */ @Transactional
/*    */ public class AppUserDAO
/*    */ {
/*    */   public AppUser findUserAccount(String userName) {
/* 28 */     AppUser userInfo = new AppUser();
/* 29 */     userInfo.setUserId(Long.valueOf("1"));
/* 30 */     userInfo.setUserName(userName);
/* 31 */     userInfo.setEncrytedPassword(userName);
/* 32 */     return userInfo;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\AppUserDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */