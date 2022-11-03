package com.ams.dao;

import com.ams.model.CreditRatingOfBanks;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CreditRattingOfBanksRowMapper
        implements RowMapper<Object> {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        CreditRatingOfBanks cr = new CreditRatingOfBanks();
        cr.setGlBankCode(rs.getString("gl_bank_code"));
        cr.setGlBankName(rs.getString("gl_bank_name"));
        cr.setRating(rs.getString("rating"));
        return cr;
    }
}
