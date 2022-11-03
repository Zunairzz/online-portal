package com.ams.dao;

import com.ams.model.FundCommentary;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FundCommentaryMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FundCommentary customer = new FundCommentary();
        customer.setFundCode(rs.getString("FUND_CODE"));
        customer.setCommentary(rs.getString("DESCRIPTION"));
        return customer;

    }

}