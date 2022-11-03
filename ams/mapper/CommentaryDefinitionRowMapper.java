package com.ams.mapper;

import com.ams.model.fmr.FmrEconomyDto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CommentaryDefinitionRowMapper implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrEconomyDto customer = new FmrEconomyDto();
        if (rs.getString("TYPE").equals("PARA_CONTENT")) {
            customer.setComm1(rs.getString("COMM1"));
            customer.setComm2(rs.getString("COMM2"));
            customer.setComm3(rs.getString("COMM3"));
        } else if (rs.getString("TYPE").equals("PARA_HEADING")) {
            customer.setHead1(rs.getString("COMM1"));
            customer.setHead2(rs.getString("COMM2"));
            customer.setHead3(rs.getString("COMM3"));
        } else if (rs.getString("TYPE").equals("PARA_CONTENT_ISL")) {
            customer.setComm1(rs.getString("COMM1"));
            customer.setComm2(rs.getString("COMM2"));
            customer.setComm3(rs.getString("COMM3"));
        } else if (rs.getString("TYPE").equals("PARA_HEADING_ISL")) {
            customer.setHead1(rs.getString("COMM1"));
            customer.setHead2(rs.getString("COMM2"));
            customer.setHead3(rs.getString("COMM3"));

        }

        return customer;

    }

}