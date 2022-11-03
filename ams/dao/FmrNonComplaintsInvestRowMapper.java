
package com.ams.dao;

import com.ams.model.FmrNonComplaintsInvest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FmrNonComplaintsInvestRowMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrNonComplaintsInvest bi = new FmrNonComplaintsInvest();
        bi.fundCode = rs.getString("fund_code");
        bi.transDate = rs.getString("transdate");
        bi.nonComplaintInvestment = rs.getString("non_complaint_investment");
        bi.typeOfInvestment = rs.getString("type_of_investment");
        bi.exposureLimit = rs.getString("exposure_limit");
        bi.percentOfNetAsset = rs.getString("percent_of_net_asset");
        bi.percentOfTotalAsset = rs.getString("percent_of_total_asset");
        bi.excessExposurePerOfNet = rs.getString("excess_exposure_per_of_net");
        bi.excessExposurePerOfTotal = rs.getString("excess_exposure_per_of_total");
        return bi;

    }

}