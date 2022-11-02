/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import com.ams.utility.FMRChartOfAccountServiceHandler;
/*    */ import java.time.Duration;
/*    */ import java.time.ZoneId;
/*    */ import java.time.ZonedDateTime;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
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
/*    */ public class FMRChartOfAccountService
/*    */   implements ServletContextListener
/*    */ {
/*    */   private ScheduledExecutorService scheduler;
/*    */   
/*    */   public void contextInitialized(ServletContextEvent event) {
/* 29 */     System.out.println("====== 1 =======");
/* 30 */     this.scheduler = Executors.newSingleThreadScheduledExecutor();
/* 31 */     ServletContext context = event.getServletContext();
/* 32 */     String date = context.getInitParameter("date");
/* 33 */     String hours = context.getInitParameter("hours");
/* 34 */     String minutes = context.getInitParameter("minutes");
/* 35 */     System.out.println("====== 2 =======");
/*    */     
/* 37 */     ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Karachi"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     ZonedDateTime nextRun = now.withDayOfMonth(Integer.parseInt(date)).withHour(Integer.parseInt(hours)).withMinute(Integer.parseInt(minutes)).withSecond(0);
/* 44 */     if (now.compareTo(nextRun) > 0)
/* 45 */       nextRun = nextRun.plusMonths(1L); 
/* 46 */     System.out.println("====== 3 =======");
/* 47 */     Duration duration = Duration.between(now, nextRun);
/* 48 */     long initalDelay = duration.getSeconds();
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
/* 60 */     this.scheduler.scheduleAtFixedRate((Runnable)new FMRChartOfAccountServiceHandler(), initalDelay, TimeUnit.DAYS.toSeconds(1L), TimeUnit.SECONDS);
/*    */   }
/*    */ 
/*    */   
/*    */   public void contextDestroyed(ServletContextEvent event) {
/* 65 */     this.scheduler.shutdownNow();
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\FMRChartOfAccountService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */