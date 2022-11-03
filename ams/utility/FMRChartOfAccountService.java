package com.ams.utility;

import com.ams.utility.FMRChartOfAccountServiceHandler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class FMRChartOfAccountService
        implements ServletContextListener {
    private ScheduledExecutorService scheduler;


    public void contextInitialized(ServletContextEvent event) {
        System.out.println("====== 1 =======");
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        ServletContext context = event.getServletContext();
        String date = context.getInitParameter("date");
        String hours = context.getInitParameter("hours");
        String minutes = context.getInitParameter("minutes");
        System.out.println("====== 2 =======");

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Karachi"));


        ZonedDateTime nextRun = now.withDayOfMonth(Integer.parseInt(date)).withHour(Integer.parseInt(hours)).withMinute(Integer.parseInt(minutes)).withSecond(0);
        if (now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusMonths(1L);
        System.out.println("====== 3 =======");
        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();


        this.scheduler.scheduleAtFixedRate((Runnable) new FMRChartOfAccountServiceHandler(), initalDelay, TimeUnit.DAYS.toSeconds(1L), TimeUnit.SECONDS);

    }


    public void contextDestroyed(ServletContextEvent event) {
        this.scheduler.shutdownNow();

    }

}