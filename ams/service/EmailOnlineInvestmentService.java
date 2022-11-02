/*     */ package WEB-INF.classes.com.ams.service;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.mail.javamail.JavaMailSender;
/*     */ import org.springframework.mail.javamail.MimeMessagePreparator;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class EmailOnlineInvestmentService
/*     */ {
/*  32 */   private static final Logger log = LoggerFactory.getLogger(com.ams.service.EmailOnlineInvestmentService.class);
/*  33 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); @Autowired
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   @Scheduled(fixedRate = 30000L)
/*     */   public void SendTransEmailNotifycation() {
/*  37 */     if (this.flag_email_alert != null && this.flag_email_alert.equals("true")) {
/*  38 */       System.out.println("Email service for Online payment running........");
/*  39 */       List<Map<String, Object>> data = null;
/*     */       try {
/*  41 */         data = this.jdbcTemplate.queryForList("select id TRANS_ID,\n       notification_record_date,\n       notification_send_time,\n       notification_send_to E_MAIL,\n       notification_text,\n       notification_trans_type,\n       notification_trans_id,\n       notification_subject,\n       log_id,\n       sending_status,\n       nvl(attempts, 0) attempts ,\n       reason,\n       notification_type,\n       notification_folio_number\n  from UNIT_TRANS_NOTIFICATION_QUEUE\n where notification_trans_type = 'SALE'\n   and notification_subject = 'ABL - Online Payment' and sending_status='P' ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         for (Map<String, Object> dt : data) {
/*  59 */           if (isValidString(dt.get("TRANS_ID")) && isValidString(dt.get("E_MAIL")) && isValidString(dt.get("NOTIFICATION_TEXT")) && 
/*  60 */             isValidString(dt.get("NOTIFICATION_SUBJECT")) && isValidString(dt.get("ATTEMPTS"))) {
/*  61 */             sendSimpleMessage(dt.get("TRANS_ID").toString(), dt.get("E_MAIL").toString(), dt.get("notification_text").toString(), dt.get("notification_subject").toString(), dt.get("attempts").toString()); continue;
/*     */           } 
/*  63 */           this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set attempts='" + (Integer.parseInt(dt.get("attempts").toString()) + 1) + "' where id = '" + dt.get("TRANS_ID").toString() + "'");
/*     */         }
/*     */       
/*  66 */       } catch (Exception e) {
/*  67 */         log.info(e.toString());
/*  68 */         generateErrorLog(e.toString());
/*     */       } 
/*     */     } else {
/*     */       
/*  72 */       System.out.println("Email service for Online payment Stopped........");
/*     */     } 
/*     */   } @Autowired
/*     */   public JavaMailSender emailSender; @Value("${service.email.alert.online}")
/*     */   private String flag_email_alert; private boolean isValidString(Object str) {
/*  77 */     return (str != null && !str.toString().equals(""));
/*     */   }
/*     */ 
/*     */   
/*     */   public void SendMobileAlerts() {
/*  82 */     System.out.println("SendMobileAlerts running........");
/*  83 */     List<Map<String, Object>> data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSimpleMessage(String trans_id, String to, String notification_text, String subject, String attempts) {
/*  97 */     int attempt = Integer.parseInt(attempts) + 1;
/*  98 */     String[] bcc = new String[1];
/*  99 */     bcc[0] = "Operation@ablamc.com";
/*     */     try {
/* 101 */       this.emailSender.send((MimeMessagePreparator)new Object(this, to, bcc, subject, notification_text));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set sending_status = 'S',attempts='" + attempt + "' where id = '" + trans_id + "'");
/* 113 */     } catch (Exception e) {
/* 114 */       log.info(e.toString());
/* 115 */       generateErrorLog(e.toString());
/* 116 */       this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set attempts='" + attempt + "' where id = '" + trans_id + "'");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateErrorLog(String err) {
/*     */     try {
/* 122 */       this.emailSender.send((MimeMessagePreparator)new Object(this, err));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 131 */     catch (Exception e) {
/* 132 */       log.info(e.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getInvestmentTemplate() {
/* 137 */     String tmpl = "<html>\n\n<body>\n\nThe following online request has been sent to ABL Asset Management at 12/04/2019 01:04 AM<div style=\" width:700px;border: 1px solid;text-align:center; background-color:#CCCCCC\">\nOnline Request Detail\n</div>\n<div style=\"width:700px; border-left:1px solid; border-right:1px solid;border-bottom:1px solid;\">\n  <table>\n        <tr><td>Request Type:</td><td>Online Purchase of Unit</td></tr>\n        <tr><td>Date:</td><td>12/04/2019</td></tr>\n        <tr><td>Account Title:</td><td>Mr ADEEL ATHER</td></tr>\n        <tr><td>Folio Number:</td><td>13114</td></tr>\n        <tr><td>Transaction ID:</td><td>94181</td></tr>\n        <tr><td>Contact No:</td><td>03008660868</td></tr>\n        <tr><td>Fund:</td><td>ABL CASH FUND</td></tr>\n        <tr><td>Consumer Number:</td><td>18204089441702868205</td></tr>\n        <tr><td>Amount:</td><td>250000/-</td></tr>\n\n  </table>\n</div><br/><br/><div style=\"font-size:12px\">Disclaimer:<br/><br/>\nThe information transmitted is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged material. Any review, retransmission, dissemination or other use of, or taking of any action in reliance upon, this information by persons or entities other than the intended recipient is prohibited. If you received this in error, please contact the sender and delete the material from any computer. Please note that any views or opinions presented in this email are solely those of the author and do not necessarily represent those of the company. Finally, the recipient shall check this email and any attachments for the presence of viruses. The company accepts no liability for any damage caused by any virus transmitted by this email.</div>\n\n</body>\n\n</html>";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     return tmpl;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\EmailOnlineInvestmentService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */