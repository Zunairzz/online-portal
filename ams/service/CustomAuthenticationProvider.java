/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.springframework.security.authentication.AuthenticationProvider;
/*    */ import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.core.AuthenticationException;
/*    */ import org.springframework.stereotype.Component;
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
/*    */ @Component
/*    */ public class CustomAuthenticationProvider
/*    */   implements AuthenticationProvider
/*    */ {
/*    */   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
/* 27 */     String username = authentication.getPrincipal() + "";
/* 28 */     String password = authentication.getCredentials() + "";
/* 29 */     ArrayList<String> userRights = new ArrayList();
/* 30 */     userRights.add("ADMIN");
/* 31 */     return (Authentication)new UsernamePasswordAuthenticationToken(username, password, userRights);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean supports(Class<?> authentication) {
/* 36 */     return authentication.equals(UsernamePasswordAuthenticationToken.class);
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean shouldAuthenticateAgainstThirdPartySystem() {
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\CustomAuthenticationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */