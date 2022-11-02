/*     */ package WEB-INF.classes.com.ams.service;
/*     */ 
/*     */ import com.ams.utility.SmsSender;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.mail.MailException;
/*     */ import org.springframework.mail.SimpleMailMessage;
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
/*     */ @Component
/*     */ public class EmailScheduledTasks
/*     */ {
/*  34 */   private static final Logger log = LoggerFactory.getLogger(com.ams.service.EmailScheduledTasks.class);
/*  35 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); @Value("${service.sms.alert.ums}")
/*     */   private String flag_sms_alert; @Value("${service.email.alert.ums}")
/*     */   private String flag_email_alert;
/*     */   @Scheduled(fixedRate = 600000L)
/*     */   public void SendTransEmailNotifycation() {
/*  40 */     if (this.flag_email_alert != null && this.flag_email_alert.equals("true")) {
/*     */       
/*  42 */       System.out.println("Email service for UMS transactions running........");
/*  43 */       List<Map<String, Object>> data = null;
/*     */       try {
/*  45 */         data = this.jdbcTemplate.queryForList("select TRANS_ID,\n       c.e_mail,\n       c.client_name,\n       trans_type,\n       c.phone_mobile,\n       f.fund_name FNAME,\n       f.riskpriority FCAT,\n       to_char(sysdate, 'dd Mon YYYY') TDATE , ua.folio_number,\nnvl((select email_risk_flag from trans_notify_risk_config r where r.folio_number = ua.folio_number), 0) email_risk_flag \n  from trans_notify t, unit_sale us, unit_account ua, client c, fund f, payment_detail pd\nwhere t.trans_id = us.sale_id\n   and pd.payment_id = us.payment_id\n   and upper(pd.client_bank_acc) not like '%CGT%'\n   and us.fund_code = f.fund_code\n   and t.trans_type = 'SALE'\n   and us.folio_number = ua.folio_number\n   and ua.primary_client = c.client_code\n   and sale_date >= to_char(sysdate, 'dd-MON-YYYY') \n   and c.e_mail is not null and us.post = '0' \n   and t.sent in (0)union\nselect TRANS_ID,\n       c.e_mail,\n       c.client_name,\n       trans_type,\n       c.phone_mobile,\n       f.fund_name FNAME,\n       f.riskpriority FCAT,\n       to_char(sysdate, 'dd Mon YYYY') TDATE, ua.folio_number,  nvl((select email_risk_flag from trans_notify_risk_config r where r.folio_number = ua.folio_number), 0) email_risk_flag \n  from trans_notify    t,\n       unit_redemption ur,\n       unit_account    ua,\n       client          c,\n       fund            f\n where t.trans_id = ur.redemption_id\n   and ur.fund_code = f.fund_code\n   and t.trans_type = 'REDEMPTION'\n   and ur.folio_number = ua.folio_number\n   and ua.primary_client = c.client_code\n   and ur.redemption_date >= to_char(sysdate, 'dd-MON-YYYY') \n   and c.e_mail is not null and ur.post = '1' \n   and t.sent in (0)\nunion\nselect TRANS_ID,\n       c.e_mail,\n       c.client_name,\n       trans_type,\n       c.phone_mobile,\n       f.fund_name FNAME,\n       f.riskpriority FCAT,\n       to_char(sysdate, 'dd Mon YYYY') TDATE, ua.folio_number,  nvl((select email_risk_flag from trans_notify_risk_config r where r.folio_number = ua.folio_number), 0) email_risk_flag \n  from trans_notify t, unit_transfer us, unit_account ua, client c, fund f\n where t.trans_id = us.transfer_id\n   and us.to_fund_code = f.fund_code\n   and t.trans_type = 'CONVERSION'\n   and us.from_folio_no = ua.folio_number\n   and ua.primary_client = c.client_code\n   and transfer_date >= to_char(sysdate, 'dd-MON-YYYY') \n   and c.e_mail is not null and us.post = '1' \n   and t.sent in (0) \n");
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
/* 108 */         for (Map<String, Object> dt : data) {
/* 109 */           if (isValidString(dt.get("TRANS_ID")) && isValidString(dt.get("CLIENT_NAME")) && isValidString(dt.get("E_MAIL")) && isValidString(dt.get("TRANS_TYPE")) && 
/* 110 */             isValidString(dt.get("FCAT"))) {
/* 111 */             processEmailMessage(dt.get("TRANS_ID").toString(), dt.get("CLIENT_NAME").toString(), dt.get("E_MAIL").toString(), dt.get("TRANS_TYPE").toString(), dt
/* 112 */                 .get("FNAME").toString(), (dt.get("FCAT") == null) ? "Very Low" : dt.get("FCAT").toString(), dt.get("TDATE").toString(), dt.get("EMAIL_RISK_FLAG").toString(), dt.get("FOLIO_NUMBER").toString()); continue;
/*     */           } 
/* 114 */           this.jdbcTemplate.update("update trans_notify set sent = 2 where trans_id = '" + dt.get("TRANS_ID").toString() + "'");
/*     */         }
/*     */       
/* 117 */       } catch (Exception e) {
/* 118 */         e.printStackTrace();
/* 119 */         generateErrorLog(e.toString());
/*     */       } 
/*     */     } else {
/* 122 */       System.out.println("Email service for UMS transactions Stopped........");
/*     */     } 
/*     */   } @Autowired
/*     */   SmsSender sms; @Autowired
/*     */   private JdbcTemplate jdbcTemplate; @Autowired
/*     */   public JavaMailSender emailSender; @Scheduled(fixedRate = 15000L)
/*     */   public void SendTransSmsNotifycation() {
/* 129 */     if (this.flag_sms_alert != null && this.flag_sms_alert.equals("true")) {
/* 130 */       System.out.println("Sms service for UMS transactions running........");
/* 131 */       List<Map<String, Object>> data = null;
/*     */       try {
/* 133 */         data = this.jdbcTemplate.queryForList("select TRANS_ID,\n       c.e_mail,\n       c.client_name,\n       trans_type,\n       c.phone_mobile,\n       f.fund_name,\n       f.riskpriority,\n       to_char(sysdate, 'dd Mon YYYY') trans_date , ua.folio_number,  nvl((select sms_risk_flag from trans_notify_risk_config r where r.folio_number = ua.folio_number), 0) sms_risk_flag \n  from trans_notify t, unit_transfer us, unit_account ua, client c, fund f \n where t.trans_id = us.transfer_id\n   and t.trans_type = 'CONVERSION'\n   and us.from_folio_no = ua.folio_number\n   and ua.primary_client = c.client_code\n   and transfer_date >= to_char(sysdate, 'dd-MON-YYYY') \n   and c.phone_mobile is not null\n   and f.fund_code = us.to_fund_code\n   and nvl(t.sms_sent, 0) in (0) \n");
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
/* 152 */         for (Map<String, Object> dt : data) {
/* 153 */           if (isValidString(dt.get("TRANS_ID")) && isValidString(dt.get("CLIENT_NAME")) && 
/* 154 */             isValidString(dt.get("TRANS_TYPE")) && isValidString(dt.get("PHONE_MOBILE")) && isValidString(dt.get("RISKPRIORITY"))) {
/* 155 */             processConvSms(dt.get("TRANS_ID").toString(), dt.get("CLIENT_NAME").toString(), "", dt.get("TRANS_TYPE").toString(), dt.get("PHONE_MOBILE").toString(), dt
/* 156 */                 .get("FUND_NAME").toString(), dt.get("RISKPRIORITY").toString() + " Risk Profile", dt.get("TRANS_DATE").toString(), dt.get("SMS_RISK_FLAG").toString(), dt
/* 157 */                 .get("FOLIO_NUMBER").toString()); continue;
/*     */           } 
/* 159 */           this.jdbcTemplate.update("update trans_notify set sms_sent = 2 where trans_id = '" + dt.get("TRANS_ID").toString() + "'");
/*     */         }
/*     */       
/* 162 */       } catch (Exception e) {
/* 163 */         e.printStackTrace();
/* 164 */         generateErrorLog(e.toString());
/*     */       } 
/*     */     } else {
/* 167 */       System.out.println("SMS service for UMS transactions Stopped........");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processEmailMessage(String trans_id, String cName, String to, String transType, String fname, String fcat, String tdate, String flagRisk, String folioNumber) {
/* 174 */     String[] bcc = new String[2];
/* 175 */     bcc[0] = "Operation@ablamc.com";
/* 176 */     bcc[1] = "ISD-Contactus@ablamc.com";
/*     */     
/*     */     try {
/* 179 */       SimpleMailMessage message = new SimpleMailMessage();
/* 180 */       String body = "", subject = "";
/* 181 */       if (transType.equals("SALE")) {
/* 182 */         subject = "Investment Alert";
/* 183 */         body = String.format(getInvestmentTemplate(flagRisk), new Object[] { fname, fcat, tdate, fcat, fcat });
/* 184 */         message.setBcc(bcc);
/* 185 */       } else if (transType.equals("REDEMPTION")) {
/* 186 */         subject = "Redemption Alert";
/* 187 */         body = String.format(getRedemptionTemplate(flagRisk), new Object[] { cName });
/* 188 */       } else if (transType.equals("CONVERSION")) {
/* 189 */         subject = "FoF / Conversion Alert";
/* 190 */         body = String.format(getConversionTemplate(flagRisk), new Object[] { fname, fcat, tdate, fcat, fcat });
/* 191 */         message.setBcc(bcc);
/*     */       } 
/* 193 */       message.setFrom("contactus@ablamc.com");
/* 194 */       message.setTo(to.replace(" ", ""));
/* 195 */       message.setSubject(subject);
/* 196 */       message.setText(body);
/* 197 */       message.setBcc("mohammad.bassam@ablamc.com");
/* 198 */       this.emailSender.send(message);
/* 199 */       this.jdbcTemplate.update("update trans_notify set sent = 1 where trans_id = '" + trans_id + "'");
/*     */       
/* 201 */       if (flagRisk != null && flagRisk.equals("0")) {
/* 202 */         this.jdbcTemplate.update("INSERT INTO trans_notify_risk_config SELECT '" + folioNumber + "', 0,0 FROM dual\nWHERE NOT EXISTS (SELECT NULL FROM trans_notify_risk_config WHERE folio_number = '" + folioNumber + "') ");
/*     */         
/* 204 */         this.jdbcTemplate.update("update trans_notify_risk_config set email_risk_flag = '1' WHERE folio_number = '" + folioNumber + "' ");
/*     */       }
/*     */     
/* 207 */     } catch (MailException e) {
/* 208 */       e.printStackTrace();
/* 209 */       generateErrorLog(e.toString());
/* 210 */       this.jdbcTemplate.update("update trans_notify set sent = 2 where trans_id = '" + trans_id + "'");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processConvSms(String trans_id, String cName, String to, String transType, String mobile, String fname, String fcat, String tdate, String riskFlag, String folioNumber) {
/*     */     try {
/* 217 */       String body = "", subject = "";
/* 218 */       if (transType.equals("SALE")) {
/* 219 */         subject = "Investment Notification";
/* 220 */         body = String.format(getInvestmentTemplate(riskFlag), new Object[] { cName });
/* 221 */       } else if (transType.equals("REDEMPTION")) {
/* 222 */         subject = "Redemption Notification";
/* 223 */         body = String.format(getRedemptionTemplate(riskFlag), new Object[] { cName });
/* 224 */       } else if (transType.equals("CONVERSION")) {
/* 225 */         body = this.sms.ConversionSms(cName, mobile, "1", fname, fcat, tdate);
/* 226 */         this.jdbcTemplate.update("update trans_notify set sms_sent = 1, SMS_NOTIFICATION_TEXT = '" + body + "' where trans_id = '" + trans_id + "'");
/*     */       } 
/*     */       
/* 229 */       if (riskFlag != null && riskFlag.equals("0")) {
/* 230 */         this.jdbcTemplate.update("INSERT INTO trans_notify_risk_config SELECT '" + folioNumber + "', 0,0 FROM dual\nWHERE NOT EXISTS (SELECT NULL FROM trans_notify_risk_config WHERE folio_number = '" + folioNumber + "') ");
/*     */         
/* 232 */         this.jdbcTemplate.update("update trans_notify_risk_config set sms_risk_flag = '1' WHERE folio_number = '" + folioNumber + "' ");
/*     */       }
/*     */     
/* 235 */     } catch (Exception e) {
/* 236 */       log.info(e.toString());
/* 237 */       generateErrorLog(e.toString());
/* 238 */       this.jdbcTemplate.update("update trans_notify set sms_sent = 2 where trans_id = '" + trans_id + "'");
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidString(Object str) {
/* 243 */     return (str != null && !str.toString().equals(""));
/*     */   }
/*     */   
/*     */   private void generateErrorLog(String err) {
/*     */     try {
/* 248 */       this.emailSender.send((MimeMessagePreparator)new Object(this, err));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 257 */     catch (Exception e) {
/* 258 */       e.printStackTrace();
/*     */     } 
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
/*     */   private String getInvestmentTemplate(String riskFlag) {
/* 285 */     String tmpl = "Thank you for investing in ABL Funds. Your investment request in %s marked as “%s Risk Profile” has been received on %s and is in process. \n\nThis email has been sent to confirm your intention to invest in “%s” risk profile fund managed by ABL Asset Management. If you have any objection and/or need further understanding related to investing in Funds with “%s” risk profile, please contact us within 24 hours through our sales/distributor representative or call us directly on 042-111225262.";
/*     */ 
/*     */     
/* 288 */     if (riskFlag.equals("1")) {
/* 289 */       tmpl = "Thank you for investing in ABL Funds. Your investment request in %s marked as “%s Risk Profile” has been received on %s and is in process. \n\nThis email has been sent to confirm your intention to invest in “%s” risk profile fund managed by ABL Asset Management. If you have any objection and/or need further understanding related to investing in Funds with “%s” risk profile, please contact us within 24 hours through our sales/distributor representative or call us directly on 042-111225262.";
/*     */     }
/*     */     
/* 292 */     return tmpl;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getRedemptionTemplate(String riskFlag) {
/* 297 */     String tmpl = "Dear %s \nYour redemption has been processed today and your payment will be sent to you in next two working days.\nIf you have any queries, Please call us on 042-111-225-262";
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (riskFlag.equals("1")) {
/* 302 */       tmpl = "Dear %s \n\nYour redemption has been processed today and your payment will be sent to you in next two working days.\nIf you have any queries, Please call us on 042-111-225-262";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 307 */     return tmpl;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getConversionTemplate(String riskFlag) {
/* 312 */     String tmpl = "Your conversion request to %s marked as “%s Risk Profile” has been received on %s and is in process. \n\nThis email has been sent to confirm your intention to convert your investment to “%s” risk profile fund managed by ABL Asset Management. If you have any objection and/or need further understanding related to conversion in Funds with “%s” risk profile, please contact us within 24 hours through our sales/distributor representative or call us directly on 042-111225262. ";
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (riskFlag.equals("1")) {
/* 317 */       tmpl = "Your conversion request to %s marked as “%s Risk Profile” has been received on %s and is in process. \n\nThis email has been sent to confirm your intention to convert your investment to “%s” risk profile fund managed by ABL Asset Management. If you have any objection and/or need further understanding related to conversion in Funds with “%s” risk profile, please contact us within 24 hours through our sales/distributor representative or call us directly on 042-111225262. ";
/*     */     }
/*     */ 
/*     */     
/* 321 */     return tmpl;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\service\EmailScheduledTasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */