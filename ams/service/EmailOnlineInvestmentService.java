package com.ams.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class EmailOnlineInvestmentService {
    private static final Logger log = LoggerFactory.getLogger(com.ams.service.EmailOnlineInvestmentService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Scheduled(fixedRate = 30000L)
    public void SendTransEmailNotifycation() {

        if (this.flag_email_alert != null && this.flag_email_alert.equals("true")) {

            System.out.println("Email service for Online payment running........");

            List<Map<String, Object>> data = null;

            try {

                data = this.jdbcTemplate.queryForList("select id TRANS_ID,\n       notification_record_date,\n       notification_send_time,\n       notification_send_to E_MAIL,\n       notification_text,\n       notification_trans_type,\n       notification_trans_id,\n       notification_subject,\n       log_id,\n       sending_status,\n       nvl(attempts, 0) attempts ,\n       reason,\n       notification_type,\n       notification_folio_number\n  from UNIT_TRANS_NOTIFICATION_QUEUE\n where notification_trans_type = 'SALE'\n   and notification_subject = 'ABL - Online Payment' and sending_status='P' ");


                for (Map<String, Object> dt : data) {

                    if (isValidString(dt.get("TRANS_ID")) && isValidString(dt.get("E_MAIL")) && isValidString(dt.get("NOTIFICATION_TEXT")) &&
                            isValidString(dt.get("NOTIFICATION_SUBJECT")) && isValidString(dt.get("ATTEMPTS"))) {

                        sendSimpleMessage(dt.get("TRANS_ID").toString(), dt.get("E_MAIL").toString(), dt.get("notification_text").toString(), dt.get("notification_subject").toString(), dt.get("attempts").toString());
                        continue;

                    }

                    this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set attempts='" + (Integer.parseInt(dt.get("attempts").toString()) + 1) + "' where id = '" + dt.get("TRANS_ID").toString() + "'");

                }


            } catch (Exception e) {

                log.info(e.toString());

                generateErrorLog(e.toString());

            }

        } else {


            System.out.println("Email service for Online payment Stopped........");

        }

    }

    @Autowired
    public JavaMailSender emailSender;
    @Value("${service.email.alert.online}")
    private String flag_email_alert;

    private boolean isValidString(Object str) {

        return (str != null && !str.toString().equals(""));

    }


    public void SendMobileAlerts() {

        System.out.println("SendMobileAlerts running........");

        List<Map<String, Object>> data = null;

    }


    public void sendSimpleMessage(String trans_id, String to, String notification_text, String subject, String attempts) {

        int attempt = Integer.parseInt(attempts) + 1;

        String[] bcc = new String[1];

        bcc[0] = "Operation@ablamc.com";

        try {
            101
            this.emailSender.send((MimeMessagePreparator) new Object(this, to, bcc, subject, notification_text));


            this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set sending_status = 'S',attempts='" + attempt + "' where id = '" + trans_id + "'");

        } catch (Exception e) {

            log.info(e.toString());

            generateErrorLog(e.toString());

            this.jdbcTemplate.update("update UNIT_TRANS_NOTIFICATION_QUEUE set attempts='" + attempt + "' where id = '" + trans_id + "'");

        }

    }


    private void generateErrorLog(String err) {

        try {

            this.emailSender.send((MimeMessagePreparator) new Object(this, err));


        } catch (Exception e) {

            log.info(e.toString());

        }

    }


    private String getInvestmentTemplate() {

        String tmpl = "<html>\n\n<body>\n\nThe following online request has been sent to ABL Asset Management at 12/04/2019 01:04 AM<div style=\" width:700px;border: 1px solid;text-align:center; background-color:#CCCCCC\">\nOnline Request Detail\n</div>\n<div style=\"width:700px; border-left:1px solid; border-right:1px solid;border-bottom:1px solid;\">\n  <table>\n        <tr><td>Request Type:</td><td>Online Purchase of Unit</td></tr>\n        <tr><td>Date:</td><td>12/04/2019</td></tr>\n        <tr><td>Account Title:</td><td>Mr ADEEL ATHER</td></tr>\n        <tr><td>Folio Number:</td><td>13114</td></tr>\n        <tr><td>Transaction ID:</td><td>94181</td></tr>\n        <tr><td>Contact No:</td><td>03008660868</td></tr>\n        <tr><td>Fund:</td><td>ABL CASH FUND</td></tr>\n        <tr><td>Consumer Number:</td><td>18204089441702868205</td></tr>\n        <tr><td>Amount:</td><td>250000/-</td></tr>\n\n  </table>\n</div><br/><br/><div style=\"font-size:12px\">Disclaimer:<br/><br/>\nThe information transmitted is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged material. Any review, retransmission, dissemination or other use of, or taking of any action in reliance upon, this information by persons or entities other than the intended recipient is prohibited. If you received this in error, please contact the sender and delete the material from any computer. Please note that any views or opinions presented in this email are solely those of the author and do not necessarily represent those of the company. Finally, the recipient shall check this email and any attachments for the presence of viruses. The company accepts no liability for any damage caused by any virus transmitted by this email.</div>\n\n</body>\n\n</html>";


        return tmpl;

    }

}