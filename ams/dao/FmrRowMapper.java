package com.ams.dao;

import com.ams.model.FmrModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FmrRowMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrModel customer = new FmrModel();
        customer.setMembers(rs.getString("MEMBERS"));
        customer.setComments(rs.getString("COMMENTS"));
        customer.setObjective(rs.getString("OBJECTIVES"));
        customer.setLaunchDate(rs.getString("LAUNCH_DATE"));
        customer.setFundTitle(rs.getString("FUND_NAME"));
        return customer;

    }

}