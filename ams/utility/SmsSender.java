/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import client.sender;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.scheduling.annotation.Async;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class SmsSender
/*    */ {
/*    */   @Value("${service.sms.proxy}")
/*    */   private String proxyAddress;
/*    */   @Value("${service.sms.proxy.port}")
/*    */   private String proxyPort;
/*    */   
/*    */   @Async
/*    */   public String ConversionSms(String cName, String cellno, String id, String fname, String fcat, String tdate) {
/* 24 */     String cntct = "", body = "";
/* 25 */     if (cellno != null) {
/* 26 */       cntct = cellno;
/*    */     }
/*    */     try {
/* 29 */       body = String.format(getConversionTemplate(), new Object[] { fname, fcat, tdate });
/* 30 */       cntct = (cntct.charAt(0) == '0') ? ("92" + cntct.substring(1, cntct.length())) : cntct;
/* 31 */       sender.send(cntct, body, id, (this.proxyAddress == null || this.proxyAddress.isEmpty()) ? "" : this.proxyAddress, (this.proxyPort == null || this.proxyPort
/* 32 */           .isEmpty()) ? "" : this.proxyPort);
/* 33 */     } catch (Exception e) {
/* 34 */       this.logger.error("Error orrucred in sms sending. " + e.toString());
/*    */     } 
/* 36 */     return body;
/*    */   }
/*    */   
/*    */   private String getConversionTemplate() {
/* 40 */     String tmpl = "Your conversion request to %s marked as “%s” received on %s is in process. For details, please call within 24 hours on 042-111225262.";
/*    */     
/* 42 */     return tmpl;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   Logger logger = LoggerFactory.getLogger(com.ams.utility.SmsSender.class);
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\SmsSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */