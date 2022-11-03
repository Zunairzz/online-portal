package com.ams.utility;

import client.sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class SmsSender {

    @Value("${service.sms.proxy}")
    private String proxyAddress;

    @Value("${service.sms.proxy.port}")
    private String proxyPort;


    @Async
    public String ConversionSms(String cName, String cellno, String id, String fname, String fcat, String tdate) {

        String cntct = "", body = "";

        if (cellno != null) {

            cntct = cellno;

        }

        try {

            body = String.format(getConversionTemplate(), new Object[]{fname, fcat, tdate});

            cntct = (cntct.charAt(0) == '0') ? ("92" + cntct.substring(1, cntct.length())) : cntct;

            sender.send(cntct, body, id, (this.proxyAddress == null || this.proxyAddress.isEmpty()) ? "" : this.proxyAddress, (this.proxyPort == null || this.proxyPort
                    .isEmpty()) ? "" : this.proxyPort);

        } catch (Exception e) {

            this.logger.error("Error orrucred in sms sending. " + e.toString());

        }

        return body;

    }


    private String getConversionTemplate() {

        String tmpl = "Your conversion request to %s marked as “%s” received on %s is in process. For details, please call within 24 hours on 042-111225262.";


        return tmpl;

    }


    Logger logger = LoggerFactory.getLogger(com.ams.utility.SmsSender.class);

}