package com.ams.utility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class FMRChartOfAccountServiceHandler
        implements Runnable {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public FMRChartOfAccountServiceHandler() {

        System.out.println("======= FMRChartOfAccountServiceHandler =======");

    }


    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;

    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    public void run() {

        try {
            System.out.println("=== start search list of previous month records for updation ===");

            fmrChartOfAccount();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void fmrChartOfAccount() {
        55
        System.out.println("=== fmrChartOfAccount ===");


        try {
            Connection connection = getCleanDBConnection();
            String query = "select fca.fundcode, fca.gl_code, fca.description, fca.trans_date, fca.id from fmr_chart_of_account fca where Trans_date = (select last_day(to_date((ADD_MONTHS(sysdate, -2)), 'dd/MM/yy')) from dual)";
            Statement cmd = connection.createStatement();
            ResultSet rs = cmd.executeQuery(query);
            String sql = "insert into fmr_chart_of_account (fundcode, gl_code, description, trans_date, id) values (?, ?, ?,?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            String maxIDQuery = "select max(ID) as ID from fmr_chart_of_account t";
            Statement cmdd = connection.createStatement();
            ResultSet rsMax = cmdd.executeQuery(maxIDQuery);
            int maxID = 0;
            while (rsMax.next()) {
                maxID = rsMax.getInt("ID");

            }
            System.out.println("maxID : " + maxID);
            int index = 1;
            while (rs.next()) {
                System.out.println("index : " + index);
                ps.setString(1, rs.getString("fundcode"));
                ps.setString(2, rs.getString("gl_code"));
                ps.setString(3, rs.getString("description"));

                LocalDate dateTime = LocalDate.now();
                System.out.println("date time" + dateTime);
                LocalDate newDateTime = dateTime.minusDays(1L);
                System.out.println("upate date time" + newDateTime);
                Date mySqlDate = Date.valueOf(newDateTime);

                ps.setDate(4, mySqlDate);
                ps.setInt(5, maxID + index);
                ps.addBatch();
                index++;

            }
            int[] result = ps.executeBatch();
            System.out.println("inserted batch rows size iss : " + result.length);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public static Connection getCleanDBConnection() throws SQLException {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db.ablamc.com:1521:ORCL", "clean", "clean");

            Logger.getLogger(com.ams.utility.FMRChartOfAccountServiceHandler.class.getName()).log(Level.SEVERE, (String) null, "Fetching clean db connection...");
            return con;
        } catch (Exception e) {
            e.printStackTrace();

            return null;

        }

    }

}