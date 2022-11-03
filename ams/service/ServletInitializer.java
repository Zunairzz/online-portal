package com.ams.service;

import com.ams.service.AmsServicesApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


public class ServletInitializer
        extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(new Class[]{AmsServicesApplication.class});

    }

}