
package com.ams.dao;

import com.ams.model.fmr.FmrNoncompliant;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FmrNoncompliantMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrNoncompliant bi = new FmrNoncompliant();


        bi.non_complaint_investment = rs.getString("non_complaint_investment");
        bi.type_of_investment = rs.getString("type_of_investment");
        bi.exposure_limit = rs.getString("exposure_limit");
        bi.percent_of_net_asset = rs.getString("percent_of_net_asset");
        bi.percent_of_total_asset = rs.getString("percent_of_total_asset");
        bi.excess_exposure_per_of_net = rs.getString("excess_exposure_per_of_net");
        bi.excess_exposure_per_of_total = rs.getString("excess_exposure_per_of_total");
        return bi;

    }

}