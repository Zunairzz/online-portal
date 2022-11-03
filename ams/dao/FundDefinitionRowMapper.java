package com.ams.dao;

import com.ams.model.FundDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FundDefinitionRowMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FundDefinition customer = new FundDefinition();
        customer.setId(rs.getString("ID"));
        customer.setFundCode(rs.getString("FUND_CODE"));

        customer.setCommentary(rs.getString("COMMENTARY"));
        customer.setObjective(rs.getString("OBJECTIVE"));
        customer.setTransDate(rs.getString("TRANS_DATE"));
        customer.setMemberList(rs.getString("MEMBERS"));
        customer.setMemberString(rs.getString("MEMBER"));
        customer.setWeightAvg(rs.getString("WEIGHTED_AVG"));
        customer.setLeverage(rs.getString("LEVERAGE"));
        return customer;

    }

}