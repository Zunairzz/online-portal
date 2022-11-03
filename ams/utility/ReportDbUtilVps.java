package com.ams.utility;

import com.ams.dao.FmrFundBasicInfoMapper;
import com.ams.model.FmrAssetAllocMain;
import com.ams.model.FmrFundBasicInfo;
import com.ams.model.FmrPdfReportModal;
import com.ams.model.FmrPlanPdfReportModal;
import com.ams.model.FmrTopTfcModel;
import com.ams.model.FundDefinition;
import com.ams.model.FundPerformance;
import com.ams.model.MainModel;
import com.ams.model.PlanPerformance;
import com.ams.model.fmr_asset_allocation_model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.thymeleaf.context.Context;


public class ReportDbUtilVps {

    public void VpsModalMap(List<FmrPlanPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {

        FmrPdfReportModal fpRepModal = new FmrPdfReportModal();

        FundDefinition input = new FundDefinition();

        input.setFundCode(fund1);

        input.setTransDate(transDate);

        try {

            String mainSql = "select id,\n       to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date,\n       fmr.fund_code,\n       fmr.members,\n       fmr.fund_name,\n       commentary,\n       objective,\n       ter\n  from fmr_def fmr\n where fmr.fund_code in (10001)\n   and id = (select max(id)\n               from fmr_def ff\n              where ff.fund_code = fmr.fund_code\n                and post = 1) order by fmr.fund_code ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {

                FmrPlanPdfReportModal fpModal = new FmrPlanPdfReportModal();

                FundDefinition fundDef = new FundDefinition();

                fundDef.setId(f.get("id".toUpperCase()).toString());

                fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());

                fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());

                fundDef.setMemberList(f.get("members".toUpperCase()).toString());

                fundDef.setObjective(f.get("objective".toUpperCase()).toString());


                String fif_basicInfo_sql = "select TRANSDATE, ID,fund_code, FUND_TYPE,CATEGORY, to_char(launchdate, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV ,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS from fmr_fund_basic_info t \nwhere fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) ";

                FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo) jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper) new FmrFundBasicInfoMapper());

                fpModal.setBasicInfo(fif_basicInfo1);


                String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";


                List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);

                for (Map<String, Object> dt : fund_perform_dates_list) {

                    fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());

                    fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());

                    fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());

                }

                String fpf_p_f1 = "select FNDID , decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, nvl(B_MONTH1, 0) B_MONTH1, nvl(B_MONTH3, 0) B_MONTH3, nvl(B_MONTH6, 0) B_MONTH6, nvl(B_YEAR1, 0) B_YEAR1, nvl(BYTD, 0) bYTD, nvl(ST_DEV, 0) ST_DEV, nvl(b_since, 0) b_since, nvl(SHARP_RATIO, 0) SHARP_RATIO, decode(ALPHA, null, 'N/A', ALPHA || '%') ALPHA \n  from fund_performence\n where FNDID in ('00014', '00016', '00019', '00011', '00012', '00013', '00017', '00018', '00021', '00022', '00026' ) \n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') and B_MONTH1 is not null order by descp ";


                List<Map<String, Object>> fpf_performacne = jdbcTemplate.queryForList(fpf_p_f1);

                PlanPerformance p1 = new PlanPerformance();

                p1.setDesc("Apr-19");

                for (Map<String, Object> dt : fpf_performacne) {

                    FundPerformance f1 = new FundPerformance();

                    if (dt.get("DESCP").toString().toLowerCase().trim().equals("BenchMark".toLowerCase())) {

                        f1.setBmmonth1(dt.get("B_MONTH1").toString());

                        f1.setBmYTD(dt.get("bYTD").toString());

                        f1.setBmmonth3(dt.get("B_MONTH3").toString());

                        f1.setBmmonth6(dt.get("B_MONTH6").toString());

                        f1.setBmyear1(dt.get("B_YEAR1").toString());

                        f1.setBmsi(dt.get("b_since").toString());

                    } else {

                        f1.setMonth1(dt.get("B_MONTH1").toString());

                        f1.setYTD(dt.get("bYTD").toString());

                        f1.setMonth3(dt.get("B_MONTH3").toString());

                        f1.setMonth6(dt.get("B_MONTH6").toString());

                        f1.setYear1(dt.get("B_YEAR1").toString());

                        f1.setSi(dt.get("b_since").toString());

                    }

                    if (dt.get("FNDID").toString().equals("00014")) {

                        p1.setF1(populateFofGrid(p1.getF1(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00016")) {

                        p1.setF2(populateFofGrid(p1.getF2(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00019")) {

                        p1.setF3(populateFofGrid(p1.getF3(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00011")) {

                        p1.setF4(populateFofGrid(p1.getF4(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00012")) {

                        p1.setF5(populateFofGrid(p1.getF5(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00013")) {

                        p1.setF6(populateFofGrid(p1.getF6(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00017")) {

                        p1.setF7(populateFofGrid(p1.getF7(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00018")) {

                        p1.setF8(populateFofGrid(p1.getF8(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00021")) {

                        p1.setF9(populateFofGrid(p1.getF9(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00022")) {

                        p1.setF10(populateFofGrid(p1.getF10(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00026")) {

                        p1.setF11(populateFofGrid(p1.getF11(), f1));

                    }

                }

                fpModal.setPerformanceGrid(p1);


                if (fundDef.fundCode.equals("10001")) {

                    fpModal.setHeader("fund_p_mpf.png");

                    String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_vps \n where fund_code in (10001, 10002, 10003) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";


                    List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);

                    fpModal.getPlanData().setTechInfoPlan1(fmr_tech_info_sql_data);

                    fpModal.getPlanData().setAssetAllocFund1(getAssetAllocBody(fpRepModal, "00014", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund2(getAssetAllocBody(fpRepModal, "00016", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund3(getAssetAllocBody(fpRepModal, "00019", transDate, true, jdbcTemplate));

                } else if (fundDef.fundCode.equals("00008")) {

                    fpModal.setHeader("fund_ip_mpf.png");

                    String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_PLAN \n where fund_code in (11,12,13 ,17 ,18 ,21, 22,26) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";


                    List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);

                    fpModal.getPlanData().setTechInfoPlan2(fmr_tech_info_sql_data);

                    fpModal.getPlanData().setAssetAllocFund4(getAssetAllocBody(fpRepModal, "00011", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund5(getAssetAllocBody(fpRepModal, "00012", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund6(getAssetAllocBody(fpRepModal, "00013", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund7(getAssetAllocBody(fpRepModal, "00017", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund8(getAssetAllocBody(fpRepModal, "00018", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund9(getAssetAllocBody(fpRepModal, "00021", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund10(getAssetAllocBody(fpRepModal, "00022", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund11(getAssetAllocBody(fpRepModal, "00026", transDate, true, jdbcTemplate));

                }


                reportList.add(fpModal);

            }

        } catch (Exception ex) {

            System.out.println(ex);

            String str = "";

        }

        context.setVariable("pf_fund_report", reportList);

    }


    private void populateTerCommentsFof(FundDefinition fundDef, String pFundCode, JdbcTemplate jdbcTemplate) {

        if (pFundCode.equals("10001")) {

            String mainSql = "select fmr.fund_code,\n       commentary,\n       (select ter\n          from fmr_fund_basic_info fb\n         where fmr.fund_code = fb.fund_code) ter\n  from fmr_def fmr\n where fmr.fund_code in (10001, 10002, 10003)\n   and id = (select max(id)\n               from fmr_def ff\n              where ff.fund_code = fmr.fund_code\n                and post = 1) ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {

                if (f.get("fund_code".toUpperCase()).toString().equals("10001")) {

                    continue;

                }

                if (f.get("fund_code".toUpperCase()).toString().equals("10002")) {

                    continue;

                }

                if (f.get("fund_code".toUpperCase()).toString().equals("10003")) ;


            }


        } else if (pFundCode.equals("20001")) {

            String mainSql = "select fmr.fund_code,\n       commentary,\n       (select ter\n          from fmr_fund_basic_info fb\n         where fmr.fund_code = fb.fund_code) ter\n  from fmr_def fmr\n where fmr.fund_code in (20001, 20002, 20003)\n   and id = (select max(id)\n               from fmr_def ff\n              where ff.fund_code = fmr.fund_code\n                and post = 1) ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {

                if (f.get("fund_code".toUpperCase()).toString().equals("20001")) {

                    continue;

                }

                if (f.get("fund_code".toUpperCase()).toString().equals("20002")) {

                    continue;

                }

                if (f.get("fund_code".toUpperCase()).toString().equals("20003")) ;

            }

        }

    }


    private FundPerformance populateFofGrid(FundPerformance f1, FundPerformance f2) {

        if (f1.getBmmonth1() == null || f1.getBmmonth1().equals("")) {

            f1.setBmmonth1(f2.getBmmonth1());

        }

        if (f1.getBmYTD() == null || f1.getBmYTD().equals("")) {

            f1.setBmYTD(f2.getBmYTD());

        }

        if (f1.getBmmonth3() == null || f1.getBmmonth3().equals("")) {

            f1.setBmmonth3(f2.getBmmonth3());

        }

        if (f1.getBmmonth6() == null || f1.getBmmonth6().equals("")) {

            f1.setBmmonth6(f2.getBmmonth6());

        }

        if (f1.getBmyear1() == null || f1.getBmyear1().equals("")) {

            f1.setBmyear1(f2.getBmyear1());

        }

        if (f1.getBmsi() == null || f1.getBmsi().equals("")) {
            f1.setBmsi(f2.getBmsi());

        }
        if (f1.getMonth1() == null || f1.getMonth1().equals("")) {
            f1.setMonth1(f2.getMonth1());

        }
        if (f1.getYTD() == null || f1.getYTD().equals("")) {
            f1.setYTD(f2.getYTD());

        }
        if (f1.getMonth3() == null || f1.getMonth3().equals("")) {
            f1.setMonth3(f2.getMonth3());

        }
        if (f1.getMonth6() == null || f1.getMonth6().equals("")) {
            f1.setMonth6(f2.getMonth6());

        }
        if (f1.getYear1() == null || f1.getYear1().equals("")) {
            f1.setYear1(f2.getYear1());

        }
        if (f1.getSi() == null || f1.getSi().equals("")) {
            f1.setSi(f2.getSi());

        }
        return f1;

    }


    private void populateTopTfcSection(FmrPdfReportModal fpModal, String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
        FmrTopTfcModel model = new FmrTopTfcModel();
        String top_tfc = "select SCHEME_NAME, PREVIOUS_ASSET_PERCENTAGE , TOTAL_ASSET_PERCENTAGE, (select sum(TOTAL_ASSET_PERCENTAGE) from fmr_top_tfc_sukuk_holding where FUND_CODE = '" + fundcode + "' and TRANS_DATE = to_date('" + transDate + "', 'dd/MM/yyyy')) TOTAL_ASSET_PERCENTAGESUM from fmr_top_tfc_sukuk_holding where FUND_CODE='" + fundcode + "' and TRANS_DATE=to_date('" + transDate + "' , 'dd/MM/yyyy') order by TOTAL_ASSET_PERCENTAGE desc ";

        List<Map<String, Object>> top_tfc_data = jdbcTemplate.queryForList(top_tfc);
        for (Map<String, Object> row : top_tfc_data) {
            if (row.get("TOTAL_ASSET_PERCENTAGESUM") != null) {
                model.setTotal(row.get("TOTAL_ASSET_PERCENTAGESUM").toString());

            }

        }
        model.setTfcData(top_tfc_data);
        fpModal.setTopTfcModel(model);

    }


    private void populateTopTenSection(FmrPdfReportModal fpModal, String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
        FmrTopTfcModel model = new FmrTopTfcModel();
        String top_tfc = "select SCHEME_NAME, PREVIOUS_ASSET_PERCENTAGE , TOTAL_ASSET_PERCENTAGE, (select sum(TOTAL_ASSET_PERCENTAGE) from fmr_top_ten_holding where FUND_CODE = '" + fundcode + "' and TRANS_DATE = to_date('" + transDate + "', 'dd/MM/yyyy')) TOTAL_ASSET_PERCENTAGESUM from fmr_top_ten_holding where FUND_CODE='" + fundcode + "' and TRANS_DATE=to_date('" + transDate + "' , 'dd/MM/yyyy') order by TOTAL_ASSET_PERCENTAGE desc ";

        List<Map<String, Object>> top_tfc_data = jdbcTemplate.queryForList(top_tfc);
        for (Map<String, Object> row : top_tfc_data) {
            if (row.get("TOTAL_ASSET_PERCENTAGESUM") != null) {
                model.setTotal(row.get("TOTAL_ASSET_PERCENTAGESUM").toString());

            }

        }
        model.setTfcData(top_tfc_data);
        fpModal.setTopTenHoldingModel(model);

    }


    private List<Map<String, Object>> getAssetAllocHeader(String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
        String fassetallocation_sql = "select to_char(p.aa_trans_date , 'Monthdd,yyyy') prev, to_char(c.aa_trans_date , 'Monthdd,yyyy') curr \n  from fmr_prev_asset_alloocation p, fmr_current_asset_alloocation c\n where p.fund_code = '" + fundcode + "'\n   and p.aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'), -1), 'YYYYMM'), 'YYYYMM'))\n   and c.aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') ";


        List<Map<String, Object>> fif_perform_data1 = jdbcTemplate.queryForList(fassetallocation_sql);
        return fif_perform_data1;

    }


    private FmrAssetAllocMain getAssetAllocBody(FmrPdfReportModal fpModal, String fundcode, String transDate, boolean filterByName, JdbcTemplate jdbcTemplate) {
        FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();
        List<fmr_asset_allocation_model> currlist = new ArrayList<>();
        String prev_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth,(select sum(aa_prevmth) from fmr_prev_asset_alloocation where fund_code = 1 and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) aa_prevmthTotal \n  from fmr_prev_asset_alloocation\n where fund_code = '" + fundcode + "' and code is not null \n   and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'), -1), 'YYYYMM'), 'YYYYMM')) ";


        List<Map<String, Object>> prev_data = jdbcTemplate.queryForList(prev_sql);
        String curr_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation  where fund_code = '" + fundcode + "' and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal\n  from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "' and code is not null \n   and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') order by AA_CURRMTH ";


        List<Map<String, Object>> curr_data = jdbcTemplate.queryForList(curr_sql);
        if (prev_data.size() > curr_data.size()) {
            for (Map<String, Object> row : prev_data) {
                fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();
                assetAllocModel.setCode(row.get("CODE").toString());
                assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
                if (row.get("AA_PREVMTH") != null) {
                    assetAllocModel.setPrev_month(row.get("AA_PREVMTH").toString());
                    assetAllocModel.setCurr_month("0");

                }
                if (row.get("AA_PREVMTHTOTAL") != null) {
                    assetAllocMain.setTotalPrevAlloc(row.get("AA_PREVMTHTOTAL").toString());

                }
                currlist.add(assetAllocModel);

            }
            for (fmr_asset_allocation_model crow : currlist) {
                for (Map<String, Object> prow : curr_data) {
                    String pr = prow.get("CODE").toString();
                    String cr = crow.getCode();
                    if (cr.equals(pr)) {
                        crow.setCurr_month(prow.get("AA_CURRMTH").toString());

                    }
                    if (prow.get("AA_CURRMTHTOTAL") != null) {
                        assetAllocMain.setTotalCurrAlloc(prow.get("AA_CURRMTHTOTAL").toString());

                    }

                }

            }
            String curr_new_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation  where fund_code = '" + fundcode + "' and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal\n  from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "' and code is not null \n   and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and code not in (select code from fmr_prev_asset_alloocation where fund_code = '" + fundcode + "' and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) order by AA_CURRMTH ";


            348
            List<Map<String, Object>> curr_new_data = jdbcTemplate.queryForList(curr_new_sql);
            for (Map<String, Object> row : curr_new_data) {
                fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();
                assetAllocModel.setCode(row.get("CODE").toString());
                assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
                if (row.get("AA_CURRMTH") != null) {
                    assetAllocModel.setCurr_month(row.get("AA_CURRMTH").toString());
                    assetAllocModel.setPrev_month("0");

                }
                if (row.get("AA_CURRMTHTOTAL") != null) {
                    assetAllocMain.setTotalCurrAlloc(row.get("AA_CURRMTHTOTAL").toString());

                }
                currlist.add(assetAllocModel);

            }
            for (fmr_asset_allocation_model crow : currlist) {
                for (Map<String, Object> prow : prev_data) {

                    String pr = prow.get("CODE").toString();

                    String cr = crow.getCode();

                    if (cr.equals(pr)) {

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                    }

                    if (prow.get("AA_PREVMTHTOTAL") != null) {

                        assetAllocMain.setTotalPrevAlloc(prow.get("AA_PREVMTHTOTAL").toString());

                    }

                }

            }

        } else {


            for (Map<String, Object> row : curr_data) {

                fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();

                assetAllocModel.setCode(row.get("CODE").toString());

                assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());

                if (row.get("AA_CURRMTH") != null) {

                    assetAllocModel.setCurr_month(row.get("AA_CURRMTH").toString());

                    assetAllocModel.setPrev_month("0");

                }

                if (row.get("AA_CURRMTHTOTAL") != null) {

                    assetAllocMain.setTotalCurrAlloc(row.get("AA_CURRMTHTOTAL").toString());

                }

                currlist.add(assetAllocModel);

            }

            for (fmr_asset_allocation_model crow : currlist) {

                for (Map<String, Object> prow : prev_data) {

                    String pr = prow.get("CODE").toString();

                    String cr = crow.getCode();

                    if (cr.equals(pr)) {

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                    }

                    if (prow.get("AA_PREVMTHTOTAL") != null) {

                        assetAllocMain.setTotalPrevAlloc(prow.get("AA_PREVMTHTOTAL").toString());

                    }

                }

            }

        }

        assetAllocMain.setFif_prev_asset_alloc(fmr_asset_allocation_model.filter(currlist, filterByName));

        fpModal.setAssetAllocMain(assetAllocMain);

        return assetAllocMain;

    }


    private List<Map<String, Object>> getPerformance1(FundDefinition fundDef, String transDate, JdbcTemplate jdbcTemplate) {

        String fperformance_sql = "select decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, nvl(B_MONTH1, 0) B_MONTH1, nvl(bYTD, 0) bYTD, nvl(ST_DEV, 0) ST_DEV, nvl(SHARP_RATIO, 0) SHARP_RATIO, nvl(ALPHA, 0) ALPHA \n  from fund_performence\n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";


        return jdbcTemplate.queryForList(fperformance_sql);

    }


    private List<Map<String, Object>> getPerformance2(FundDefinition fundDef, String transDate, JdbcTemplate jdbcTemplate) {

        String fperformance_sql1 = "select nvl(B_MONTH3, 0) B_MONTH3, nvl(B_MONTH6, 0) B_MONTH6, nvl(B_YEAR1, 0) B_YEAR1, nvl(B_YEAR3, 0) B_YEAR3, nvl(B_YEAR5, 0) B_YEAR5, nvl(B_SINCE, 0) B_SINCE, decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp \n  from fund_performence\n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";


        return jdbcTemplate.queryForList(fperformance_sql1);

    }


    public MainModel ExecuteFmrProcess(String transDate, JdbcTemplate jdbcTemplate) {

        MainModel obj = new MainModel();

        try {

            SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");

            Date fromDate = df.parse(transDate);

            Date sqlDate = new Date(fromDate.getTime());

            SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(jdbcTemplate)).withProcedureName("FMR_EXECUTION_PROCESS");

            MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("p_Date1", sqlDate);

            Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);

            if (out.get("RESULT") != null) {

                obj.setResult(out.get("RESULT").toString());

            } else {

                obj.setResult("0");

            }

        } catch (Exception ex) {

            obj.setResult("0");

            Logger.getLogger(com.ams.utility.ReportDbUtilVps.class.getName()).log(Level.SEVERE, (String) null, ex);

        }

        return obj;

    }

}