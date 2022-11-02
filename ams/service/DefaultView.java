/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.web.servlet.ViewResolver;
/*    */ import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
/*    */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*    */ import org.springframework.web.servlet.view.InternalResourceViewResolver;
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
/*    */ @Configuration
/*    */ public class DefaultView
/*    */   implements WebMvcConfigurer
/*    */ {
/*    */   public void addViewControllers(ViewControllerRegistry registry) {
/* 25 */     registry.setOrder(-2147483648);
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public ViewResolver getViewResolver() {
/* 30 */     InternalResourceViewResolver resolver = new InternalResourceViewResolver();
/* 31 */     resolver.setSuffix(".jsp");
/* 32 */     return (ViewResolver)resolver;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\DefaultView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */