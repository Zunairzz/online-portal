/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import com.ams.dao.AppRoleDAO;
/*    */ import com.ams.dao.AppUserDAO;
/*    */ import com.ams.model.AppUser;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.core.GrantedAuthority;
/*    */ import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*    */ import org.springframework.security.core.userdetails.User;
/*    */ import org.springframework.security.core.userdetails.UserDetails;
/*    */ import org.springframework.security.core.userdetails.UserDetailsService;
/*    */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class UserDetailsServiceImpl
/*    */   implements UserDetailsService
/*    */ {
/*    */   @Autowired
/*    */   private AppUserDAO appUserDAO;
/*    */   @Autowired
/*    */   private AppRoleDAO appRoleDAO;
/*    */   
/*    */   public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
/* 35 */     AppUser appUser = this.appUserDAO.findUserAccount(userName);
/* 36 */     List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
/* 37 */     List<GrantedAuthority> grantList = new ArrayList<>();
/* 38 */     if (roleNames != null) {
/* 39 */       for (String role : roleNames) {
/* 40 */         SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
/* 41 */         grantList.add(simpleGrantedAuthority);
/*    */       } 
/*    */     }
/* 44 */     return (UserDetails)new User(appUser.getUserName(), appUser
/* 45 */         .getEncrytedPassword(), grantList);
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\UserDetailsServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */