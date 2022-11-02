/*    */ package WEB-INF.classes.com.ams.service;
/*    */ 
/*    */ import org.springframework.boot.SpringApplication;
/*    */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ 
/*    */ @ComponentScan({"com.ams"})
/*    */ @SpringBootApplication
/*    */ @EnableScheduling
/*    */ public class AmsServicesApplication
/*    */ {
/*    */   public static void main(String[] args) {
/* 14 */     SpringApplication.run(com.ams.service.AmsServicesApplication.class, args);
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\AmsServicesApplication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */