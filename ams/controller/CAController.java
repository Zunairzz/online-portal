package com.ams.controller;

import com.ams.common.ResponseCodes;
import com.ams.model.ChartOfAccountModel;
import com.ams.model.ChartOfAccountResponse;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({"/fmr/chartofaccount"})
public class CAController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping({"/getChartAccountList"})
    public ChartOfAccountResponse getChartAccountList() {
        List<ChartOfAccountModel> respList = null;
        ChartOfAccountResponse fmr = new ChartOfAccountResponse();
        try {

            String sql = "select gl_glmf_code code, gl_glmf_description description  from gl_glmf order by gl_glmf_code";
            respList = this.jdbcTemplate.query(sql, new Object[0], (RowMapper) new Object(this));


            fmr.setChartOfAccountList(respList);
            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fmr;
    }


    @PostMapping(path = {"/saveChartOfAccount"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object saveChartOfAccount(@RequestBody ChartOfAccountModel fundDef) {
        ChartOfAccountModel fmr = new ChartOfAccountModel();
        try {

            String sql = "INSERT \nWHEN not exists(SELECT 1 FROM fmr_chart_of_account WHERE fundcode = ? and gl_code = ? and trans_date = to_date(?,'dd/MM/yyyy')) \nTHEN INTO fmr_chart_of_account (id, fundcode, gl_code, description, trans_date) \nSELECT ( select nvl(max(id)+1,1) from fmr_chart_of_account), ?, ?, ?, to_date(?,'dd/MM/yyyy') FROM DUAL";


            int resp = this.jdbcTemplate.update(sql, new Object[]{fundDef.getFundCode(), fundDef.getGlCode(), fundDef.getTransDate(), fundDef.getFundCode(), fundDef.getGlCode(), fundDef.getDescription(), fundDef.getTransDate()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
                System.out.println("Record inserted with ID = " + fundDef.getFundCode());
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fundDef;
    }


    @PostMapping(path = {"/deleteCode"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object deleteCode(@RequestBody ChartOfAccountModel fundDef) {
        ChartOfAccountModel fmr = new ChartOfAccountModel();

        try {

            String sql = "delete from fmr_chart_of_account g where g.fundcode = '" + fundDef.getFundCode() + "' and g.gl_code = '" + fundDef.getGlCode() + "' and g.trans_date = to_date('" + fundDef.getTransDate() + "', 'dd/MM/yyyy') ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
                System.out.println("Record inserted with ID = " + fundDef.getFundCode());
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fundDef;
    }

    @PostMapping(path = {"/getList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getList() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select f.fund_code id, f.fund_name,\n       gl_code,\n       description,\n       to_char(trans_date, 'dd/MM/yyyy') trans_date\n  from fmr_chart_of_account g, fund f\n where g.FUNDCODE = f.fund_code\n order by TRANS_DATE desc");


        } catch (Exception e) {
            return data;
        }
        return data;
    }

    public String getNetAssetsFunc(String vfundcode, String transdate, int ntable) {
        String resp = "0";
        try {
            SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
            Date fromDate = df.parse(transdate);
            Date sqlDate = new Date(fromDate.getTime());

            SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(this.jdbcTemplate)).withCatalogName("FMR").withProcedureName("Proc_Get_TotalAssetAmt");

            MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("pFundCode", vfundcode).addValue("TransDate", sqlDate).addValue("ntable", Integer.valueOf(0));
            Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);
            if (out.get("RESULT") != null) {
                System.out.println("fund code " + out.get("RESULT"));
                resp = out.get("RESULT").toString();
            }

        } catch (ParseException ex) {
            Logger.getLogger(com.ams.controller.CAController.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return resp;
    }


    public String getTotalAssets(String vfundcode, String transdate, int ntable) {
        String resp = "0";
        try {
            if (!vfundcode.equals("") && !transdate.equals("")) {
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                Date fromDate = fmt.parse(transdate);
                Date sqlDate = new Date(fromDate.getTime());


                SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(this.jdbcTemplate)).withCatalogName("FMR").withProcedureName("proc_get_totalassetamt").declareParameters(new SqlParameter[]{new SqlParameter("pfundcode", 12), new SqlParameter("transdate", 91), new SqlParameter("ntable", 12), (SqlParameter) new SqlOutParameter("result", 2)});


                Date pdate = new Date(fmt.parse(transdate).getTime());

                System.out.println("Parameter Fund : " + vfundcode);
                System.out.println("Parameter date : " + pdate);


                MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("pFundCode", vfundcode).addValue("TransDate", pdate).addValue("ntable", "1");
                Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);
                if (out != null) {
                    System.out.println("fund code " + out.get("RESULT"));
                    resp = out.get("result").toString();
                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(com.ams.controller.CAController.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return resp;
    }

    public List<Map<String, Object>> getPortfolioData(String vfundcode, String fData, String tDate) {
        List<Map<String, Object>> data = null;
        try {
            data = this.jdbcTemplate.queryForList("select f.fund_name as name," +
                    "t.symbol, to_char(t.price_date, 'dd/mm/yyyy') price_date," +
                    "t.hft_volume  from equity_portfolio t , fund f " +
                    "where t.fund_code = '" + vfundcode + "' and " +
                    "t.fund_code = f.fund_code  and t.price_date between to_date('" + fData + "','dd/mm/yyyy') " +
                    "and to_date('" + tDate + "','dd/mm/yyyy') order by 3");

        } catch (Exception ex) {
            Logger.getLogger(com.ams.controller.CAController.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return data;
    }

    public List<Map<String, Object>> getEquityExpData(String vfundcode, String fData, String tDate) {
        List<Map<String, Object>> data = null;
        try {
            data = this.jdbcTemplate.queryForList("select sum(f.hft_mark_to_mkt_value) hft_mark_to_mkt_value, to_char(f.price_date, 'dd/mm/yyyy') price_date, f.fund_code\n  from equity_portfolio f\n where f.fund_code = '" + vfundcode + "' \n   and f.price_date between to_date('" + fData + "','dd/mm/yyyy') and to_date('" + tDate + "','dd/mm/yyyy') \n group by f.price_date, f.fund_code\n order by f.price_date");
        } catch (Exception ex) {
            Logger.getLogger(com.ams.controller.CAController.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return data;
    }

    @PostMapping(path = {"/getNetAssets"}, consumes = {"application/json"}, produces = {"application/json"})
    public String getNetAssets(@RequestBody ChartOfAccountModel fundDef) {
        String resp = getNetAssetsFunc(fundDef.getFundCode(), fundDef.getTransDate(), 1);
        return resp;
    }

    @PostMapping(path = {"/getTotalAssets"}, consumes = {"application/json"}, produces = {"application/json"})
    public String getTotalAssets(@RequestBody ChartOfAccountModel fundDef) {
        String resp = getTotalAssets(fundDef.getFundCode(), fundDef.getTransDate(), 1);
        return resp;
    }

    @PostMapping(path = {"/getPortfolioData"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getPortfolioData(@RequestBody ChartOfAccountModel f) {
        List<Map<String, Object>> data = getPortfolioData(f.getFundCode(), f.getfDate(), f.gettDate());
        return data;
    }

    @PostMapping(path = {"/getEquityExpData"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getEquityExpData(@RequestBody ChartOfAccountModel f) {
        List<Map<String, Object>> data = getEquityExpData(f.getFundCode(), f.getfDate(), f.gettDate());
        return data;
    }


    @PostMapping(path = {"/getFundDetailList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundDetailList() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList(" select f.fund_name FundName,\n       TotalAssets,    \n       NetAssets,  \n       NAV,    \n       EquityInvestment,   \n       BankBalance,\n       SWWF,\n       SWWFUnit,\n       TER,\n       GovtLevyRatio,\n       SMExp,\n       Units,\n       TDR,\n       Debt,\n       GovtSec,\n       PIBs\n  from fmr_fund_details fm, fund f\n where f.fund_code = fm.fund_code ");
        } catch (Exception e) {
            return data;
        }
        return data;
    }
}
 