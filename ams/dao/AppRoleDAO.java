/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ @Repository
/*    */ @Transactional
/*    */ public class AppRoleDAO
/*    */ {
/*    */   public List<String> getRoleNames(Long userId) {
/* 24 */     List<String> roles = new ArrayList<>();
/* 25 */     roles.add("ROLE_ADMIN");
/*    */     
/* 27 */     return roles;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\AppRoleDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */