/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import com.ams.service.UserDetailsServiceImpl;
/*    */ import com.ams.utility.EncrytedPasswordUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*    */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*    */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*    */ import org.springframework.security.core.userdetails.UserDetailsService;
/*    */ import org.springframework.security.crypto.password.PasswordEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ public class WebSecurityConfig
/*    */   extends WebSecurityConfigurerAdapter
/*    */ {
/*    */   @Autowired
/*    */   UserDetailsServiceImpl userDetailsService;
/*    */   
/*    */   @Bean
/*    */   public EncrytedPasswordUtils passwordEncoder() {
/* 30 */     EncrytedPasswordUtils bCryptPasswordEncoder = new EncrytedPasswordUtils();
/* 31 */     return bCryptPasswordEncoder;
/*    */   }
/*    */   
/*    */   @Autowired
/*    */   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
/* 36 */     auth.userDetailsService((UserDetailsService)this.userDetailsService).passwordEncoder((PasswordEncoder)passwordEncoder());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void configure(HttpSecurity http) throws Exception {
/* 42 */     http.csrf().disable();
/* 43 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().antMatchers(new String[] { "/login", "/logout", "/Content/**" })).permitAll();
/* 44 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().antMatchers(new String[] { "/**" })).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
/* 45 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().antMatchers(new String[] { "/admin" })).access("hasRole('ROLE_ADMIN')");
/* 46 */     ((HttpSecurity)http.authorizeRequests().and()).exceptionHandling().accessDeniedPage("/403");
/* 47 */     ((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)http.authorizeRequests().and()).formLogin()
/* 48 */       .loginProcessingUrl("/j_spring_security_check"))
/* 49 */       .loginPage("/login")
/* 50 */       .failureUrl("/login?error=true"))
/* 51 */       .usernameParameter("username")
/* 52 */       .passwordParameter("password")
/* 53 */       .and()).logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\WebSecurityConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */