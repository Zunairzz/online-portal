
package com.ams.dao;

import com.ams.model.FmrFundBasicInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FmrPlanBasicInfoMapper
        implements RowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FmrFundBasicInfo bi = new FmrFundBasicInfo();
        bi.id = rs.getString("ID");
        bi.transDate = rs.getString("TRANSDATE");
        bi.fundCode = rs.getString("PLAN_CODE");
        bi.fund_type = rs.getString("PLAN_TYPE");
        bi.category = rs.getString("CATEGORY");

        bi.launchdate = rs.getString("LAUNCHDATE");

        bi.benchmark = rs.getString("BENCHMARK");
        bi.dealing_days = rs.getString("DEALING_DAYS");
        bi.cutt_off_time = rs.getString("CUTT_OFF_TIME");
        bi.pricing_mechanism = rs.getString("PRICING_MECHANISM");
        bi.management_fee = rs.getString("MANAGEMENT_FEE");
        bi.front_end_load = rs.getString("FRONT_END_LOAD");
        bi.trustee = rs.getString("TRUSTEE");
        bi.auditor = rs.getString("AUDITOR");

        bi.asset_manager_rating = rs.getString("ASSET_MANAGER_RATING");
        bi.risk_prof_fund = rs.getString("RISK_PROF_FUND");
        bi.perform_ranking = rs.getString("PERFORM_RANKING");

        bi.fund_manger = rs.getString("PLAN_MANGER");
        bi.listing = rs.getString("LISTING");
        bi.comments = rs.getString("COMMENTS");
        return bi;

    }

}