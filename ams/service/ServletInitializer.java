/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import com.ams.service.AmsServicesApplication;
/*    */ import org.springframework.boot.builder.SpringApplicationBuilder;
/*    */ import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*    */ 
/*    */ public class ServletInitializer
/*    */   extends SpringBootServletInitializer {
/*    */   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
/* 10 */     return application.sources(new Class[] { AmsServicesApplication.class });
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\ServletInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */