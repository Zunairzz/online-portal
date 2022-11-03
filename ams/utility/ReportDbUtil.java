package com.ams.utility;

import com.ams.dao.FmrFundBasicInfoMapper;
import com.ams.model.FmrAssetAllocMain;
import com.ams.model.FmrFundBasicInfo;
import com.ams.model.FmrPdfReportModal;
import com.ams.model.FmrPlanPdfReportModal;
import com.ams.model.FmrTopTfcModel;
import com.ams.model.FundPerformance;
import com.ams.model.MainModel;
import com.ams.model.PlanPerformance;
import com.ams.model.fmr.FmrEconomyDto;
import com.ams.model.fmr.FundDefinitionReportModel;
import com.ams.model.fmr_asset_allocation_model;
import com.ams.utility.AmsFundPerformance;

import java.sql.Date;
import java.text.DecimalFormat;
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


public class ReportDbUtil {

    public void FixedIncomeModelMap(List<FmrPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {
        FundDefinitionReportModel input = new FundDefinitionReportModel();

        String fmr_economy_qry = "select * from fmr_economy_def fb";
        List<Map<String, Object>> fmr_economy_section = jdbcTemplate.queryForList(fmr_economy_qry);
        for (Map<String, Object> row : fmr_economy_section) {
            if (row.get("TYPE").toString().equals("PARA_CONTENT")) {
                input.setEconomy_comm1(row.get("COMM1").toString());
                input.setEconomy_comm2(row.get("COMM2").toString());
                input.setEconomy_comm3(row.get("COMM3").toString());
                continue;
            }
            if (row.get("TYPE").toString().equals("PARA_HEADING")) {
                input.setEconomy_head1(row.get("COMM1").toString());
                input.setEconomy_head2(row.get("COMM2").toString());
                input.setEconomy_head3(row.get("COMM3").toString());
                continue;
            }
            if (row.get("TYPE").toString().equals("PARA_CONTENT_ISL")) {
                input.setIsl_Economy_comm1(row.get("COMM1").toString());
                input.setIsl_Economy_comm2(row.get("COMM2").toString());
                input.setIsl_Economy_comm3(row.get("COMM3").toString());
                continue;
            }
            if (row.get("TYPE").toString().equals("PARA_HEADING_ISL")) {
                input.setIsl_Economy_head1(row.get("COMM1").toString());
                input.setIsl_Economy_head2(row.get("COMM2").toString());
                input.setIsl_Economy_head3(row.get("COMM3").toString());

            }

        }

        List<FmrEconomyDto> FmrEconomyList = new ArrayList<>();
        String FmrEconomy_qry = "select description, field_1, field_2, field_3, field_4 from fmr_economy_economic_summary order by id";
        List<Map<String, Object>> FmrEconomy_rows = jdbcTemplate.queryForList(FmrEconomy_qry);
        for (Map<String, Object> row : FmrEconomy_rows) {
            FmrEconomyDto dt = new FmrEconomyDto();
            dt.setDescription(checkEmptyString(row.get("DESCRIPTION")));
            dt.setField1(checkEmptyString(row.get("FIELD_1")));
            dt.setField2(checkEmptyString(row.get("FIELD_2")));
            dt.setField3(checkEmptyString(row.get("FIELD_3")));
            dt.setField4(checkEmptyString(row.get("FIELD_4")));
            FmrEconomyList.add(dt);

        }
        input.setEconomySummary(FmrEconomyList);

        List<FmrEconomyDto> FmrEconomyTable2List = new ArrayList<>();
        String FmrEconomyTable2_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_2 order by id";
        List<Map<String, Object>> FmrEconomyTable2_rows = jdbcTemplate.queryForList(FmrEconomyTable2_qry);
        for (Map<String, Object> row : FmrEconomyTable2_rows) {
            FmrEconomyDto dt = new FmrEconomyDto();
            dt.setDescription(row.get("DESCRIPTION").toString());
            dt.setField1(checkEmptyString(row.get("FIELD_1")));
            dt.setField2(checkEmptyString(row.get("FIELD_2")));
            dt.setField3(checkEmptyString(row.get("FIELD_3")));
            dt.setField4(checkEmptyString(row.get("FIELD_4")));
            dt.setField5(checkEmptyString(row.get("FIELD_5")));
            FmrEconomyTable2List.add(dt);

        }
        input.setEconomyTable2(FmrEconomyTable2List);

        List<FmrEconomyDto> FmrEconomyTable3List = new ArrayList<>();

        String FmrEconomyTable3_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_3 where islamic = 0 order by id";
        List<Map<String, Object>> FmrEconomyTable3_rows = jdbcTemplate.queryForList(FmrEconomyTable3_qry);
        for (Map<String, Object> row : FmrEconomyTable3_rows) {
            FmrEconomyDto dt = new FmrEconomyDto();
            dt.setDescription(row.get("DESCRIPTION").toString());
            dt.setField1(checkEmptyString(row.get("FIELD_1")));
            dt.setField2(checkEmptyString(row.get("FIELD_2")));
            dt.setField3(checkEmptyString(row.get("FIELD_3")));
            dt.setField4(checkEmptyString(row.get("FIELD_4")));
            dt.setField5(checkEmptyString(row.get("FIELD_5")));
            FmrEconomyTable3List.add(dt);

        }
        input.setEconomyTable3(FmrEconomyTable3List);

        List<FmrEconomyDto> islamicFmrEconomyTable3List = new ArrayList<>();

        String islFmrEconomyTable3_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_3 where islamic = 1 order by id";
        List<Map<String, Object>> islFmrEconomyTable3_rows = jdbcTemplate.queryForList(islFmrEconomyTable3_qry);
        for (Map<String, Object> row : islFmrEconomyTable3_rows) {
            FmrEconomyDto dt = new FmrEconomyDto();
            dt.setDescription(row.get("DESCRIPTION").toString());
            dt.setField1(checkEmptyString(row.get("FIELD_1")));
            dt.setField2(checkEmptyString(row.get("FIELD_2")));
            dt.setField3(checkEmptyString(row.get("FIELD_3")));
            dt.setField4(checkEmptyString(row.get("FIELD_4")));
            dt.setField5(checkEmptyString(row.get("FIELD_5")));
            islamicFmrEconomyTable3List.add(dt);

        }
        input.setIslamicEconomyTable3(islamicFmrEconomyTable3List);

        List<String> memberList = new ArrayList<>();
        String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";
        List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);
        for (Map<String, Object> row : fmr_comm_member_rows) {
            memberList.add(row.get("MEMBER").toString());

        }

        String trans_date_sql = "select trim(to_char(to_date('" + transDate + "', 'dd/MM/yyyy'), 'Month'))||' '||to_char(to_date('" + transDate + "', 'dd/MM/yyyy'), 'YYYY') dt from dual";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(trans_date_sql);
        for (Map<String, Object> row : rows) {
            context.setVariable("fmr_trans_date", row.get("DT").toString());

        }

        context.setVariable("input", input);
        input.setFundCode(fund1);
        input.setTransDate(transDate);

        try {
            String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, to_char(f.inception_date , 'MON dd, yyyy') inception_date , fmr.members, f.fund_name, commentary, objective, to_char(to_date('" + transDate + "' , 'dd/MM/yyyy') , 'MONTHYYYY') fmr_trans_date, fmr.left_header header_img, nvl(fmr.weighted_avg, 0) weighted_avg, nvl(fmr.leverage, 0) leverage  from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00001, 00003, 00004, 00005, 00031) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
            for (Map<String, Object> f : fundMain) {
                FmrPdfReportModal fpModal = new FmrPdfReportModal();
                FundDefinitionReportModel fundDef = new FundDefinitionReportModel();
                fundDef.setId(f.get("id".toUpperCase()).toString());
                fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());
                fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());
                fundDef.setMemberList(memberList);
                fundDef.setCommentary(f.get("commentary".toUpperCase()).toString());
                fundDef.setObjective(f.get("objective".toUpperCase()).toString());
                fundDef.setFundName(f.get("fund_name".toUpperCase()).toString());
                fundDef.setLaunchDate(f.get("inception_date".toUpperCase()).toString());
                fundDef.setWeighted_avg(f.get("weighted_avg".toUpperCase()).toString());
                fundDef.setLeverage(f.get("leverage".toUpperCase()).toString());
                fpModal.setFundDef(fundDef);


                String fif_basicInfo_sql = "select TRANSDATE, ID, t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV, BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS, t.nav nav1,  (select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy, (select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf, (select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav,(select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) smexp from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";

                FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo) jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper) new FmrFundBasicInfoMapper());
                fpModal.setBasicInfo(fif_basicInfo1);

                String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";


                List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);
                for (Map<String, Object> dt : fund_perform_dates_list) {
                    fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());
                    fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());
                    fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());

                    input.currMonth = dt.get("FUNDASSETALLOCDATE1").toString();
                    input.prevMonth = dt.get("FUNDASSETALLOCDATE2").toString();

                }
                fpModal.setFif_perform_data(getPerformance1(fundDef, transDate, jdbcTemplate));
                fpModal.setFif_perform_data1(getPerformance2(fundDef, transDate, jdbcTemplate));
                List<Map<String, Object>> fif_asset_header = getAssetAllocHeader(fundDef.getFundCode(), transDate, jdbcTemplate);
                getAssetAllocBody(fpModal, fundDef.getFundCode(), transDate, false, jdbcTemplate);
                fpModal.setFif_asset_header(fif_asset_header);


                String fmr_tech_info_sql = "select transdate, description, decode(value, 0 , 'NIL', value) value \n  from fmr_tech_info\n where FUND_CODE = '" + fundDef.getFundCode() + "'\n   and TRANSDATE = (select max(transdate) from fmr_tech_info where FUND_CODE = '" + fundDef.getFundCode() + "') ";
                List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
                fpModal.setFmr_tech_info_sql_data(fmr_tech_info_sql_data);
                populateTopTenSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
                populateTopTfcSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);

                String non_complaints = "select * from fmr_non_complaints_invest where FUND_CODE = '" + fundDef.fundCode + "' and TRANSDATE=to_date('" + transDate + "' , 'dd/MM/yyyy')";
                List<Map<String, Object>> non_complaints_data = jdbcTemplate.queryForList(non_complaints);
                fpModal.setNon_compliant(non_complaints_data);
                fpModal.setBenchMarkDesc("Benchmark");
                fpModal.getBasicInfo().setBenchmarkLabel("Benchmark");
                if (fundDef.fundCode.equals("00001")) {
                    fpModal.setBenchMarkDesc("6 Months Kibor");
                } else if (fundDef.fundCode.equals("00003")) {
                    fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
                    fpModal.setBenchMarkDesc("Benchmark*");
                } else if (fundDef.fundCode.equals("00005")) {
                    fpModal.setBenchMarkDesc("6 Months PKRV");
                } else if (fundDef.fundCode.equals("00004")) {
                    fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
                    fpModal.setBenchMarkDesc("Benchmark*");

                }
                fpModal.setHeader(f.get("header_img".toUpperCase()).toString());
                fpModal.setFif_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));
                fpModal.setBarChart(AmsFundPerformance.fundBenchMarkData(fpModal, fundDef.fundCode, transDate, contextPath, jdbcTemplate));
                reportList.add(fpModal);

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        context.setVariable("cf_report", reportList);

    }


    public void EqfModalMap(List<FmrPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {
        List<String> memberList = new ArrayList<>();
        String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";
        List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);
        for (Map<String, Object> row : fmr_comm_member_rows) {
            memberList.add(row.get("MEMBER").toString());

        }
        FundDefinitionReportModel input = new FundDefinitionReportModel();
        input.setFundCode(fund1);
        input.setTransDate(transDate);

        try {
            String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, to_char(f.inception_date , 'MON dd, yyyy') inception_date, fmr.members, f.fund_name, commentary, objective, f.fund_short_name , fmr.left_header header_img, nvl(fmr.weighted_avg, 0) weighted_avg, nvl(fmr.leverage, 0) leverage from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00002,00008,00020,00023 , 00024 ,00025) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
            for (Map<String, Object> f : fundMain) {
                FmrPdfReportModal fpModal = new FmrPdfReportModal();
                FundDefinitionReportModel fundDef = new FundDefinitionReportModel();
                fundDef.setId(f.get("id".toUpperCase()).toString());
                fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());
                fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());
                fundDef.setMemberList(memberList);
                fundDef.setCommentary(f.get("commentary".toUpperCase()).toString());
                fundDef.setObjective(f.get("objective".toUpperCase()).toString());
                fundDef.setFundName(f.get("fund_name".toUpperCase()).toString());
                fundDef.setLaunchDate(f.get("inception_date".toUpperCase()).toString());
                fundDef.setWeighted_avg(f.get("weighted_avg".toUpperCase()).toString());
                fundDef.setLeverage(f.get("leverage".toUpperCase()).toString());
                fpModal.setFundDef(fundDef);


                String fif_basicInfo_sql = "select TRANSDATE, ID, t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV ,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS, t.nav nav1  ,(select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy,\n(select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf,\n(select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav,(select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) SMEXP  from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";

                FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo) jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper) new FmrFundBasicInfoMapper());
                fpModal.setBasicInfo(fif_basicInfo1);
                String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";


                List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);
                for (Map<String, Object> dt : fund_perform_dates_list) {
                    fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());
                    fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());
                    fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());
                }
                fpModal.setFif_perform_data(getPerformance1(fundDef, transDate, jdbcTemplate));
                fpModal.setFif_perform_data1(getPerformance2(fundDef, transDate, jdbcTemplate));
                List<Map<String, Object>> fif_asset_header = getAssetAllocHeader(fundDef.getFundCode(), transDate, jdbcTemplate);
                getAssetAllocBody(fpModal, fundDef.getFundCode(), transDate, false, jdbcTemplate);
                fpModal.setFif_asset_header(fif_asset_header);

                String fmr_tech_info_sql = "select transdate, description, decode(value, 0 , 'NIL', value) value \n  from fmr_tech_info\n where FUND_CODE = '" + fundDef.getFundCode() + "'\n   and TRANSDATE = to_date('" + transDate + "' , 'dd/MM/yyyy')";
                List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
                fpModal.setFmr_tech_info_sql_data(fmr_tech_info_sql_data);
                populateTopTenSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
                populateTopTfcSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
                String non_complaints = "select * from fmr_non_complaints_invest where FUND_CODE = '" + fundDef.fundCode + "' and TRANSDATE=to_date('" + transDate + "' , 'dd/MM/yyyy')";
                List<Map<String, Object>> non_complaints_data = jdbcTemplate.queryForList(non_complaints);
                fpModal.setNon_compliant(non_complaints_data);
                fpModal.setBenchMarkDesc("Benchmark");
                fpModal.getBasicInfo().setBenchmarkLabel("Benchmark");
                int upperBound = 40;
                if (fundDef.fundCode.equals("00008")) {
                    fpModal.setBenchMarkDesc("KMI-30");

                }
                if (fundDef.fundCode.equals("00002")) {
                    fpModal.setBenchMarkDesc("KSE-100");

                }
                if (fundDef.fundCode.equals("00020")) {
                    fpModal.setBenchMarkDesc("KMI-30");

                }
                if (fundDef.fundCode.equals("00025")) {

                    fpModal.setBenchMarkDesc("Benchmark*");
                    fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
                    upperBound = 55;
                } else if (fundDef.fundCode.equals("00023")) {
                    fpModal.setBenchMarkDesc("Benchmark");


                    upperBound = 55;

                }

                if (fundDef.fundCode.equals("00024")) {


                    fpModal.setBenchMarkDesc("Benchmark*");

                    fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");

                    upperBound = 100;

                }


                if (fundDef.fundCode.equals("00023")) {

                    fpModal.setFif_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));

                } else {

                    fpModal.setFif_pieChart(AmsFundPerformance.fundSectorAllocationData(fundDef.fundCode, transDate, contextPath, jdbcTemplate, upperBound));

                }

                if (fundDef.fundCode.equals("00024")) {

                    fpModal.setIaaf_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));

                }


                fpModal.setHeader(f.get("header_img".toUpperCase()).toString());


                fpModal.setBarChart(AmsFundPerformance.fundBenchMarkTimeSeries(fpModal, fundDef.fundCode, f.get("fund_short_name".toUpperCase()).toString(), transDate, contextPath, jdbcTemplate));

                reportList.add(fpModal);

            }

        } catch (Exception ex) {

            System.out.println(ex);

            String str = "";

        }

        context.setVariable("eq_fund_report", reportList);

    }


    public void FundPlanModalMap(List<FmrPlanPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {

        List<String> memberList = new ArrayList<>();

        String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";

        List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);

        for (Map<String, Object> row : fmr_comm_member_rows) {

            memberList.add(row.get("MEMBER").toString());

        }

        FmrPdfReportModal fpRepModal = new FmrPdfReportModal();

        FundDefinitionReportModel input = new FundDefinitionReportModel();

        input.setFundCode(fund1);

        input.setTransDate(transDate);

        try {

            String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, fmr.members, f.fund_name, commentary, objective from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00002, 00008, 00027) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";

            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {

                FmrPlanPdfReportModal fpModal = new FmrPlanPdfReportModal();

                FundDefinitionReportModel fundDef = new FundDefinitionReportModel();

                fundDef.setId(f.get("id".toUpperCase()).toString());

                fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());

                fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());

                fundDef.setMemberList(memberList);

                fundDef.setObjective(f.get("objective".toUpperCase()).toString());

                fpModal.setFundDef(fundDef);


                String fif_basicInfo_sql = "select TRANSDATE, ID,t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV ,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS ,(select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy,\n(select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf,\n(select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav, (select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) smexp from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";

                FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo) jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper) new FmrFundBasicInfoMapper());

                fpModal.setBasicInfo(fif_basicInfo1);


                String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";


                List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);

                for (Map<String, Object> dt : fund_perform_dates_list) {

                    fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());

                    fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());

                    fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());

                }


                String fpf_p_f1 = "select FNDID , decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, B_MONTH1, B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, BYTD,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,   b_since,     decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,       decode(decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)), null, 'N/A', decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) || '%') ALPHA \n  from fund_performence fp \n where FNDID in ('00014', '00016', '00019', '00011', '00012', '00013', '00017', '00018', '00021', '00022', '00026', '00033') \n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";


                if (f.get("fund_code".toUpperCase()).toString().equals("00027")) {

                    fpf_p_f1 = "select FNDID , decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, B_MONTH1, B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, BYTD, decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,   b_since,     decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,       decode(decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)), null, 'N/A', decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) || '%') ALPHA \n  from fund_performence fp \n where FNDID in ('00027', '00028', '00029', '00030','00032') \n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') and B_MONTH1 is not null order by descp ";

                }


                List<Map<String, Object>> fpf_performacne = jdbcTemplate.queryForList(fpf_p_f1);

                PlanPerformance p1 = new PlanPerformance();

                p1.setDesc("Apr-19");

                for (Map<String, Object> dt : fpf_performacne) {

                    FundPerformance f1 = new FundPerformance();

                    if (dt.get("DESCP").toString().toLowerCase().trim().equals("BenchMark".toLowerCase())) {

                        f1.setBmmonth1(checkIfNull(dt.get("B_MONTH1")));

                        f1.setBmYTD(checkIfNull(dt.get("bYTD")));

                        f1.setBmmonth3(checkIfNull(dt.get("B_MONTH3")));

                        f1.setBmmonth6(checkIfNull(dt.get("B_MONTH6")));

                        f1.setBmyear1(checkIfNull(dt.get("B_YEAR1")));

                        f1.setBmyear3(checkIfNull(dt.get("B_YEAR3")));

                        f1.setBmyear5(checkIfNull(dt.get("B_YEAR5")));

                        f1.setBmsi(checkIfNull(dt.get("b_since")));

                    } else {

                        f1.setMonth1(checkIfNull(dt.get("B_MONTH1")));

                        f1.setYTD(checkIfNull(dt.get("bYTD")));

                        f1.setMonth3(checkIfNull(dt.get("B_MONTH3")));

                        f1.setMonth6(checkIfNull(dt.get("B_MONTH6")));

                        f1.setYear1(checkIfNull(dt.get("B_YEAR1")));

                        f1.setYear3(checkIfNull(dt.get("B_YEAR3")));

                        f1.setYear5(checkIfNull(dt.get("B_YEAR5")));

                        f1.setSi(checkIfNull(dt.get("b_since")));

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
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00033")) {

                        p1.setF12(populateFofGrid(p1.getF12(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00027")) {

                        p1.setF1(populateFofGrid(p1.getF1(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00028")) {

                        p1.setF2(populateFofGrid(p1.getF2(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00029")) {

                        p1.setF3(populateFofGrid(p1.getF3(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00030")) {

                        p1.setF4(populateFofGrid(p1.getF4(), f1));
                        continue;

                    }
                    if (dt.get("FNDID").toString().equals("00032")) {

                        p1.setF5(populateFofGrid(p1.getF5(), f1));

                    }

                }

                fpModal.setPerformanceGrid(p1);

                if (fundDef.fundCode.equals("00002")) {

                    fpModal.setHeader("fund_fpf.png");

                    String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_PLAN \n where fund_code in (14, 16, 19) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";


                    List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);

                    fpModal.getPlanData().setTechInfoPlan1(fmr_tech_info_sql_data);

                    fpModal.getPlanData().setAssetAllocFund1(getAssetAllocBody(fpRepModal, "00014", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund2(getAssetAllocBody(fpRepModal, "00016", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund3(getAssetAllocBody(fpRepModal, "00019", transDate, true, jdbcTemplate));

                    populateTerCommentsFof(fpModal.getFundDef(), "00002", jdbcTemplate);

                } else if (fundDef.fundCode.equals("00008")) {

                    fpModal.setHeader("fund_ifpf.png");

                    String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_PLAN \n where fund_code in (11,12,13 ,17 ,18 ,21, 22, 26, 33) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";


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

                    fpModal.getPlanData().setAssetAllocFund12(getAssetAllocBody(fpRepModal, "00033", transDate, true, jdbcTemplate));

                    populateTerCommentsFof(fpModal.getFundDef(), "00008", jdbcTemplate);

                } else if (fundDef.fundCode.equals("00027")) {

                    fpModal.setHeader("fund_sfp.png");

                    String fmr_tech_info_sql = "select description, net_assets, nav, decode(get_total_investment_of_scheme(fund_code, price_date), 1,  net_assets, ROUND(get_net_assets_as_on_date_func(fund_code, price_date) - get_total_investment_of_scheme(fund_code, price_date), 2)) as NETASSETEXFOF from Tech_INFO_FOR_PLAN \n where fund_code in (27,28,29,30,32) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";


                    List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);

                    fpModal.getPlanData().setTechInfoPlan1(fmr_tech_info_sql_data);

                    fpModal.getPlanData().setAssetAllocFund1(getAssetAllocBody(fpRepModal, "00027", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund2(getAssetAllocBody(fpRepModal, "00028", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund3(getAssetAllocBody(fpRepModal, "00029", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund4(getAssetAllocBody(fpRepModal, "00030", transDate, true, jdbcTemplate));

                    fpModal.getPlanData().setAssetAllocFund5(getAssetAllocBody(fpRepModal, "00032", transDate, true, jdbcTemplate));

                    populateTerCommentsFof(fpModal.getFundDef(), "00027", jdbcTemplate);

                }

                reportList.add(fpModal);

            }

        } catch (Exception ex) {

            System.out.println(ex);

            ex.printStackTrace();

        }


        context.setVariable("fund_plan_report", reportList);

    }


    private String checkIfNull(Object o) {

        try {

            if (o != null && !o.toString().equals("")) {

                return o.toString() + "%";

            }

        } catch (Exception e) {

            System.out.println("exception in null check");

        }

        return "N/A";

    }


    private String checkEmptyString(Object o) {

        try {

            if (o != null && !o.toString().equals("")) {

                return o.toString();

            }

        } catch (Exception e) {

            System.out.println("exception in null check");

        }

        return "";

    }


    private String twoDecimalFormatting(String val) {

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        String value = "";

        try {

            value = decimalFormat.format(Double.valueOf(val));

        } catch (NumberFormatException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;

    }


    private String fourDecimalFormatting(String val) {

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");

        String value = "";

        try {

            value = decimalFormat.format(Double.valueOf(val));

        } catch (NumberFormatException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return value;

    }


    private void populateTerCommentsFof(FundDefinitionReportModel fundDef, String pFundCode, JdbcTemplate jdbcTemplate) {

        if (pFundCode.equals("00002")) {

            String mainSql = "select f.fund_code, commentary, (select ter from fmr_fund_details fb where f.fund_code = fb.fund_code) ter,  (select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf ,(select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit, (select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (14, 16, 19) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {


                String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());

                String commentary = f.get("commentary".toUpperCase()).toString();

                String swwf = f.get("swwf".toUpperCase()).toString();

                String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());

                String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());


                if (f.get("fund_code".toUpperCase()).toString().equals("00014")) {


                    fundDef.planDef.setTer1(ter);

                    fundDef.planDef.setComment1(commentary);

                    fundDef.planDef.setSwwf1(swwf);

                    fundDef.planDef.setSwwfunit1(swwfunit);

                    fundDef.planDef.setGovtlevy1(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00016")) {

                    fundDef.planDef.setTer2(ter);

                    fundDef.planDef.setComment2(commentary);

                    fundDef.planDef.setSwwf2(Double.valueOf(swwf).doubleValue());

                    fundDef.planDef.setSwwfunit2(swwfunit);

                    fundDef.planDef.setGovtlevy2(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00019")) {

                    fundDef.planDef.setTer3(ter);

                    fundDef.planDef.setComment3(commentary);

                    fundDef.planDef.setSwwf3(Double.valueOf(swwf).doubleValue());

                    fundDef.planDef.setSwwfunit3(swwfunit);

                    fundDef.planDef.setGovtlevy3(govtLevies);

                }

            }

        } else if (pFundCode.equals("00027")) {

            String mainSql = "select f.fund_code, commentary, (select nvl(ter , 'N/A') from fmr_fund_details fb where f.fund_code = fb.fund_code) ter,(select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf , (select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit,(select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (27, 28, 29, 30,32) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {


                String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());

                String commentary = f.get("commentary".toUpperCase()).toString();

                double swwf = Double.valueOf(f.get("swwf".toUpperCase()).toString()).doubleValue();

                String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());

                String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());

                String smexp = twoDecimalFormatting(f.get("smexp".toUpperCase()).toString());


                if (f.get("fund_code".toUpperCase()).toString().equals("00027")) {

                    fundDef.planDef.setTer12(ter);

                    fundDef.planDef.setComment12(commentary);

                    fundDef.planDef.setSwwf12(swwf);

                    fundDef.planDef.setSwwfunit12(swwfunit);

                    fundDef.planDef.setGovtlevy12(govtLevies);

                    fundDef.planDef.setSmexp12(smexp);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00028")) {

                    fundDef.planDef.setTer13(ter);

                    fundDef.planDef.setComment13(commentary);

                    fundDef.planDef.setSwwf13(swwf);

                    fundDef.planDef.setSwwfunit13(swwfunit);

                    fundDef.planDef.setGovtlevy13(govtLevies);

                    fundDef.planDef.setSmexp13(smexp);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00029")) {

                    fundDef.planDef.setTer14(ter);

                    fundDef.planDef.setComment14(commentary);

                    fundDef.planDef.setSwwf14(swwf);

                    fundDef.planDef.setSwwfunit14(swwfunit);

                    fundDef.planDef.setGovtlevy14(govtLevies);

                    fundDef.planDef.setSmexp14(smexp);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00030")) {

                    fundDef.planDef.setTer15(ter);

                    fundDef.planDef.setComment15(commentary);

                    fundDef.planDef.setSwwf15(swwf);

                    fundDef.planDef.setSwwfunit15(swwfunit);

                    fundDef.planDef.setGovtlevy15(govtLevies);

                    fundDef.planDef.setSmexp15(smexp);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00032")) {

                    fundDef.planDef.setTer16(ter);

                    fundDef.planDef.setComment16(commentary);

                    fundDef.planDef.setSwwf16(swwf);

                    fundDef.planDef.setSwwfunit16(swwfunit);

                    fundDef.planDef.setGovtlevy16(govtLevies);

                    fundDef.planDef.setSmexp16(smexp);

                }

            }

        } else if (pFundCode.equals("00008")) {

            String mainSql = "select f.fund_code, commentary, (select ter from fmr_fund_details fb where f.fund_code = fb.fund_code) ter, (select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf ,(select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit,(select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (11,12,13 ,17 ,18 ,21, 22,26,33) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";


            List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);

            for (Map<String, Object> f : fundMain) {


                String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());

                String commentary = f.get("commentary".toUpperCase()).toString();

                double swwf = Double.valueOf(f.get("swwf".toUpperCase()).toString()).doubleValue();

                String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());

                String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());


                if (f.get("fund_code".toUpperCase()).toString().equals("00011")) {

                    fundDef.planDef.setTer4(ter);

                    fundDef.planDef.setComment4(commentary);

                    fundDef.planDef.setSwwf4(swwf);

                    fundDef.planDef.setSwwfunit4(swwfunit);

                    fundDef.planDef.setGovtlevy4(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00012")) {

                    fundDef.planDef.setTer5(ter);

                    fundDef.planDef.setComment5(commentary);

                    fundDef.planDef.setSwwf5(swwf);

                    fundDef.planDef.setSwwfunit5(swwfunit);

                    fundDef.planDef.setGovtlevy5(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00013")) {

                    fundDef.planDef.setTer6(ter);

                    fundDef.planDef.setComment6(commentary);

                    fundDef.planDef.setSwwf6(swwf);

                    fundDef.planDef.setSwwfunit6(swwfunit);

                    fundDef.planDef.setGovtlevy6(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00017")) {

                    fundDef.planDef.setTer7(ter);

                    fundDef.planDef.setComment7(commentary);

                    fundDef.planDef.setSwwf7(swwf);

                    fundDef.planDef.setSwwfunit7(swwfunit);

                    fundDef.planDef.setGovtlevy7(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00018")) {

                    fundDef.planDef.setTer8(ter);

                    fundDef.planDef.setComment8(commentary);

                    fundDef.planDef.setSwwf8(swwf);

                    fundDef.planDef.setSwwfunit8(swwfunit);

                    fundDef.planDef.setGovtlevy8(f.get("ter_gov_levy".toUpperCase()).toString());
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00021")) {

                    fundDef.planDef.setTer9(ter);

                    fundDef.planDef.setComment9(commentary);

                    fundDef.planDef.setSwwf9(swwf);

                    fundDef.planDef.setSwwfunit9(swwfunit);

                    fundDef.planDef.setGovtlevy9(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00022")) {

                    fundDef.planDef.setTer10(ter);

                    fundDef.planDef.setComment10(commentary);

                    fundDef.planDef.setSwwf10(swwf);

                    fundDef.planDef.setSwwfunit10(swwfunit);

                    fundDef.planDef.setGovtlevy10(f.get("ter_gov_levy".toUpperCase()).toString());
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00026")) {

                    fundDef.planDef.setTer11(ter);

                    fundDef.planDef.setComment11(commentary);

                    fundDef.planDef.setSwwf11(swwf);

                    fundDef.planDef.setSwwfunit11(swwfunit);

                    fundDef.planDef.setGovtlevy11(govtLevies);
                    continue;

                }
                if (f.get("fund_code".toUpperCase()).toString().equals("00033")) {

                    fundDef.planDef.setTer17(ter);

                    fundDef.planDef.setComment17(commentary);

                    fundDef.planDef.setSwwf17(swwf);

                    fundDef.planDef.setSwwfunit17(swwfunit);

                    fundDef.planDef.setGovtlevy17(govtLevies);

                }

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

        if (f1.getBmyear3() == null || f1.getBmyear3().equals("")) {

            f1.setBmyear3(f2.getBmyear3());

        }

        if (f1.getBmyear5() == null || f1.getBmyear5().equals("")) {

            f1.setBmyear5(f2.getBmyear5());

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

        if (f1.getYear3() == null || f1.getYear3().equals("")) {

            f1.setYear3(f2.getYear3());

        }

        if (f1.getYear5() == null || f1.getYear5().equals("")) {

            f1.setYear5(f2.getYear5());

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

        String top_tfc = "select * from (select SCHEME_NAME, PREVIOUS_ASSET_PERCENTAGE , TOTAL_ASSET_PERCENTAGE, (select sum(TOTAL_ASSET_PERCENTAGE) from fmr_top_ten_holding where FUND_CODE = '" + fundcode + "' and TRANS_DATE = to_date('" + transDate + "', 'dd/MM/yyyy')) TOTAL_ASSET_PERCENTAGESUM from fmr_top_ten_holding where FUND_CODE='" + fundcode + "' and TRANS_DATE=to_date('" + transDate + "' , 'dd/MM/yyyy') order by TOTAL_ASSET_PERCENTAGE desc )  where ROWNUM <= 10";


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

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();

        List<fmr_asset_allocation_model> currlist = new ArrayList<>();

        String prev_sql = "select  nvl(code, 0) code, aa_asset_description , nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth,(select sum(aa_prevmth) from fmr_prev_asset_alloocation where fund_code = 1 and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) aa_prevmthTotal \n from fmr_prev_asset_alloocation\n where fund_code = '" + fundcode + "' and code is not null  and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'), -1), 'YYYYMM'), 'YYYYMM')) ";


        List<Map<String, Object>> prev_data = jdbcTemplate.queryForList(prev_sql);

        String curr_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation where fund_code = '" + fundcode + "' and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal  from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "'  and code is not null \n and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') order by AA_CURRMTH ";


        List<Map<String, Object>> curr_data = jdbcTemplate.queryForList(curr_sql);

        if (prev_data.size() > curr_data
                .size()) {

            for (Map<String, Object> row : prev_data) {

                fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();

                assetAllocModel.setCode(row.get("CODE").toString());

                assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());

                if (row.get("AA_PREVMTH") != null) {

                    assetAllocModel.setPrev_month(row.get("AA_PREVMTH").toString());

                    assetAllocModel.setCurr_month("0");

                }

                if (row.get("AA_PREVMTHTOTAL") != null) {

                    assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.parseDouble(row.get("AA_PREVMTHTOTAL").toString())));

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

                        assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_CURRMTHTOTAL").toString())));

                    }

                }

            }

            String curr_new_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth , nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation where fund_code = '" + fundcode + "'  and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal\n from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "'and code is not null \n and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and code not in (select code from fmr_prev_asset_alloocation where fund_code = '" + fundcode + "' and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) order by AA_CURRMTH ";


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

                    assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(row.get("AA_CURRMTHTOTAL").toString())));

                }

                currlist.add(assetAllocModel);

            }


            for (fmr_asset_allocation_model crow : currlist) {

                for (Map<String, Object> prow : prev_data) {


                    String pr = prow.get("CODE").toString();

                    String cr = crow.getCode();

                    if (cr.equals(pr)) {

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                    } else if (prow.get("AA_ASSET_DESCRIPTION").toString().equalsIgnoreCase(crow.getSecName())) {

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                    }

                    if (prow.get("AA_PREVMTHTOTAL") != null) {

                        assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_PREVMTHTOTAL").toString())));

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

                    assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(row.get("AA_CURRMTHTOTAL").toString())));

                }

                currlist.add(assetAllocModel);

            }


            for (fmr_asset_allocation_model crow : currlist) {


                for (Map<String, Object> prow : prev_data) {

                    String pr = prow.get("CODE").toString();


                    String cr = crow.getCode();


                    if (cr.equals(pr)) {

                        System.out.println("Code getSecName:" + crow.getSecName() + ":");

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                        System.out.println("Code AA_PREVMTH:" + prow.get("AA_PREVMTH").toString() + ":");

                    } else if (prow.get("AA_ASSET_DESCRIPTION").toString().equalsIgnoreCase(crow.getSecName())) {

                        crow.setPrev_month(prow.get("AA_PREVMTH").toString());

                        System.out.println("Desc getSecName :" + crow.getSecName() + ":");

                        System.out.println("Desc AA_PREVMTH:" + prow.get("AA_PREVMTH").toString() + ":");

                    }

                    if (prow.get("AA_PREVMTHTOTAL") != null) {

                        assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_PREVMTHTOTAL").toString())));

                    }

                }

            }

        }

        assetAllocMain.setFif_prev_asset_alloc(fmr_asset_allocation_model.filter(currlist, filterByName));


        fpModal.setAssetAllocMain(assetAllocMain);

        return assetAllocMain;

    }


    private List<Map<String, Object>> getPerformance1(FundDefinitionReportModel fundDef, String transDate, JdbcTemplate jdbcTemplate) {

        String fperformance_sql = "select decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, nvl(B_MONTH1, 0) B_MONTH1, nvl(BYTD, 0) BYTD,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select BM_ALPHA \n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ALPHA \n  from fund_performence fp \n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";


        return jdbcTemplate.queryForList(fperformance_sql);

    }


    private List<Map<String, Object>> getPerformance2(FundDefinitionReportModel fundDef, String transDate, JdbcTemplate jdbcTemplate) {

        String fperformance_sql1 = "select B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, B_SINCE, decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp \n  from fund_performence\n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";


        return jdbcTemplate.queryForList(fperformance_sql1);

    }


    public MainModel ExecuteFmrProcess(String transDate, String fType, JdbcTemplate jdbcTemplate) {

        MainModel obj = new MainModel();

        try {

            SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");

            Date fromDate = df.parse(transDate);

            Date sqlDate = new Date(fromDate.getTime());

            SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(jdbcTemplate)).withProcedureName("FMR_EXECUTION_PROCESS");

            MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("P_DATE1", transDate).addValue("fund_cat", Integer.valueOf(Integer.parseInt(fType)));

            Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);

            if (out.get("RESULT") != null) {

                obj.setResult("Operation Successfull");

            } else {

                obj.setResult("Operation Failed");

            }

        } catch (Exception ex) {

            obj.setResult("Operation Failed");

            Logger.getLogger(com.ams.utility.ReportDbUtil.class.getName()).log(Level.SEVERE, (String) null, ex);

        }

        return obj;

    }


    public MainModel ExecuteFmrPreCalc(String pcFromDateSearch, String pcToDateSearch, String fType, String pctransType, JdbcTemplate jdbcTemplate) {

        MainModel obj = new MainModel();

        try {

            SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");

            Date fromDate = df.parse(pcFromDateSearch);

            Date fsqlDate = new Date(fromDate.getTime());

            Date tDate = df.parse(pcToDateSearch);

            Date tsqlDate = new Date(tDate.getTime());

            SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(jdbcTemplate)).withProcedureName("fmr_pre_calc");

            MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("p_StartDate", pcFromDateSearch).addValue("p_EndDate", pcToDateSearch).addValue("fund_cat", Integer.valueOf(Integer.parseInt(fType))).addValue("process_type", pctransType);

            Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);

            if (out.get("RESULT") != null && out.get("RESULT").toString().equals("1")) {

                obj.setResult("Operation Successfull");

            } else {

                obj.setResult("Operation Failed");

            }

        } catch (Exception ex) {

            obj.setResult("Operation Failed");

            Logger.getLogger(com.ams.utility.ReportDbUtil.class.getName()).log(Level.SEVERE, (String) null, ex);

        }

        return obj;

    }

}