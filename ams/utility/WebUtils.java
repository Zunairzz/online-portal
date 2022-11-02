/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.springframework.security.core.GrantedAuthority;
/*    */ import org.springframework.security.core.userdetails.User;
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
/*    */ public class WebUtils
/*    */ {
/*    */   public static String toString(User user) {
/* 20 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 22 */     sb.append("UserName:").append(user.getUsername());
/*    */     
/* 24 */     Collection<GrantedAuthority> authorities = user.getAuthorities();
/* 25 */     if (authorities != null && !authorities.isEmpty()) {
/* 26 */       sb.append(" (");
/* 27 */       boolean first = true;
/* 28 */       for (GrantedAuthority a : authorities) {
/* 29 */         if (first) {
/* 30 */           sb.append(a.getAuthority());
/* 31 */           first = false; continue;
/*    */         } 
/* 33 */         sb.append(", ").append(a.getAuthority());
/*    */       } 
/*    */       
/* 36 */       sb.append(")");
/*    */     } 
/* 38 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\WebUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */