package com.ams.mapper;


import com.ams.model.fmr.FmrEconomyDto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class EconomySummaryDefinitionRowMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrEconomyDto customer = new FmrEconomyDto();
        customer.setId(rs.getString("ID"));
        customer.setDescription(rs.getString("DESCRIPTION"));
        customer.setField1(rs.getString("FIELD_1"));
        customer.setField2(rs.getString("FIELD_2"));
        customer.setField3(rs.getString("FIELD_3"));
        customer.setField4(rs.getString("FIELD_4"));
        customer.setField5(rs.getString("FIELD_5"));
        return customer;

    }

}