/*      */ package WEB-INF.classes.com.ams.utility;
/*      */ 
/*      */ import com.ams.dao.FmrFundBasicInfoMapper;
/*      */ import com.ams.model.FmrAssetAllocMain;
/*      */ import com.ams.model.FmrFundBasicInfo;
/*      */ import com.ams.model.FmrPdfReportModal;
/*      */ import com.ams.model.FmrPlanPdfReportModal;
/*      */ import com.ams.model.FmrTopTfcModel;
/*      */ import com.ams.model.FundPerformance;
/*      */ import com.ams.model.MainModel;
/*      */ import com.ams.model.PlanPerformance;
/*      */ import com.ams.model.fmr.FmrEconomyDto;
/*      */ import com.ams.model.fmr.FundDefinitionReportModel;
/*      */ import com.ams.model.fmr_asset_allocation_model;
/*      */ import com.ams.utility.AmsFundPerformance;
/*      */ import java.sql.Date;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.springframework.jdbc.core.JdbcTemplate;
/*      */ import org.springframework.jdbc.core.RowMapper;
/*      */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*      */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*      */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*      */ import org.thymeleaf.context.Context;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ReportDbUtil
/*      */ {
/*      */   public void FixedIncomeModelMap(List<FmrPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {
/*   41 */     FundDefinitionReportModel input = new FundDefinitionReportModel();
/*      */     
/*   43 */     String fmr_economy_qry = "select * from fmr_economy_def fb";
/*   44 */     List<Map<String, Object>> fmr_economy_section = jdbcTemplate.queryForList(fmr_economy_qry);
/*   45 */     for (Map<String, Object> row : fmr_economy_section) {
/*   46 */       if (row.get("TYPE").toString().equals("PARA_CONTENT")) {
/*   47 */         input.setEconomy_comm1(row.get("COMM1").toString());
/*   48 */         input.setEconomy_comm2(row.get("COMM2").toString());
/*   49 */         input.setEconomy_comm3(row.get("COMM3").toString()); continue;
/*   50 */       }  if (row.get("TYPE").toString().equals("PARA_HEADING")) {
/*   51 */         input.setEconomy_head1(row.get("COMM1").toString());
/*   52 */         input.setEconomy_head2(row.get("COMM2").toString());
/*   53 */         input.setEconomy_head3(row.get("COMM3").toString()); continue;
/*   54 */       }  if (row.get("TYPE").toString().equals("PARA_CONTENT_ISL")) {
/*   55 */         input.setIsl_Economy_comm1(row.get("COMM1").toString());
/*   56 */         input.setIsl_Economy_comm2(row.get("COMM2").toString());
/*   57 */         input.setIsl_Economy_comm3(row.get("COMM3").toString()); continue;
/*   58 */       }  if (row.get("TYPE").toString().equals("PARA_HEADING_ISL")) {
/*   59 */         input.setIsl_Economy_head1(row.get("COMM1").toString());
/*   60 */         input.setIsl_Economy_head2(row.get("COMM2").toString());
/*   61 */         input.setIsl_Economy_head3(row.get("COMM3").toString());
/*      */       } 
/*      */     } 
/*      */     
/*   65 */     List<FmrEconomyDto> FmrEconomyList = new ArrayList<>();
/*   66 */     String FmrEconomy_qry = "select description, field_1, field_2, field_3, field_4 from fmr_economy_economic_summary order by id";
/*   67 */     List<Map<String, Object>> FmrEconomy_rows = jdbcTemplate.queryForList(FmrEconomy_qry);
/*   68 */     for (Map<String, Object> row : FmrEconomy_rows) {
/*   69 */       FmrEconomyDto dt = new FmrEconomyDto();
/*   70 */       dt.setDescription(checkEmptyString(row.get("DESCRIPTION")));
/*   71 */       dt.setField1(checkEmptyString(row.get("FIELD_1")));
/*   72 */       dt.setField2(checkEmptyString(row.get("FIELD_2")));
/*   73 */       dt.setField3(checkEmptyString(row.get("FIELD_3")));
/*   74 */       dt.setField4(checkEmptyString(row.get("FIELD_4")));
/*   75 */       FmrEconomyList.add(dt);
/*      */     } 
/*   77 */     input.setEconomySummary(FmrEconomyList);
/*      */     
/*   79 */     List<FmrEconomyDto> FmrEconomyTable2List = new ArrayList<>();
/*   80 */     String FmrEconomyTable2_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_2 order by id";
/*   81 */     List<Map<String, Object>> FmrEconomyTable2_rows = jdbcTemplate.queryForList(FmrEconomyTable2_qry);
/*   82 */     for (Map<String, Object> row : FmrEconomyTable2_rows) {
/*   83 */       FmrEconomyDto dt = new FmrEconomyDto();
/*   84 */       dt.setDescription(row.get("DESCRIPTION").toString());
/*   85 */       dt.setField1(checkEmptyString(row.get("FIELD_1")));
/*   86 */       dt.setField2(checkEmptyString(row.get("FIELD_2")));
/*   87 */       dt.setField3(checkEmptyString(row.get("FIELD_3")));
/*   88 */       dt.setField4(checkEmptyString(row.get("FIELD_4")));
/*   89 */       dt.setField5(checkEmptyString(row.get("FIELD_5")));
/*   90 */       FmrEconomyTable2List.add(dt);
/*      */     } 
/*   92 */     input.setEconomyTable2(FmrEconomyTable2List);
/*      */     
/*   94 */     List<FmrEconomyDto> FmrEconomyTable3List = new ArrayList<>();
/*      */     
/*   96 */     String FmrEconomyTable3_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_3 where islamic = 0 order by id";
/*   97 */     List<Map<String, Object>> FmrEconomyTable3_rows = jdbcTemplate.queryForList(FmrEconomyTable3_qry);
/*   98 */     for (Map<String, Object> row : FmrEconomyTable3_rows) {
/*   99 */       FmrEconomyDto dt = new FmrEconomyDto();
/*  100 */       dt.setDescription(row.get("DESCRIPTION").toString());
/*  101 */       dt.setField1(checkEmptyString(row.get("FIELD_1")));
/*  102 */       dt.setField2(checkEmptyString(row.get("FIELD_2")));
/*  103 */       dt.setField3(checkEmptyString(row.get("FIELD_3")));
/*  104 */       dt.setField4(checkEmptyString(row.get("FIELD_4")));
/*  105 */       dt.setField5(checkEmptyString(row.get("FIELD_5")));
/*  106 */       FmrEconomyTable3List.add(dt);
/*      */     } 
/*  108 */     input.setEconomyTable3(FmrEconomyTable3List);
/*      */     
/*  110 */     List<FmrEconomyDto> islamicFmrEconomyTable3List = new ArrayList<>();
/*      */     
/*  112 */     String islFmrEconomyTable3_qry = "select id, description, field_1, field_2, field_3, field_4, field_5 from fmr_economy_table_3 where islamic = 1 order by id";
/*  113 */     List<Map<String, Object>> islFmrEconomyTable3_rows = jdbcTemplate.queryForList(islFmrEconomyTable3_qry);
/*  114 */     for (Map<String, Object> row : islFmrEconomyTable3_rows) {
/*  115 */       FmrEconomyDto dt = new FmrEconomyDto();
/*  116 */       dt.setDescription(row.get("DESCRIPTION").toString());
/*  117 */       dt.setField1(checkEmptyString(row.get("FIELD_1")));
/*  118 */       dt.setField2(checkEmptyString(row.get("FIELD_2")));
/*  119 */       dt.setField3(checkEmptyString(row.get("FIELD_3")));
/*  120 */       dt.setField4(checkEmptyString(row.get("FIELD_4")));
/*  121 */       dt.setField5(checkEmptyString(row.get("FIELD_5")));
/*  122 */       islamicFmrEconomyTable3List.add(dt);
/*      */     } 
/*  124 */     input.setIslamicEconomyTable3(islamicFmrEconomyTable3List);
/*      */     
/*  126 */     List<String> memberList = new ArrayList<>();
/*  127 */     String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";
/*  128 */     List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);
/*  129 */     for (Map<String, Object> row : fmr_comm_member_rows) {
/*  130 */       memberList.add(row.get("MEMBER").toString());
/*      */     }
/*      */     
/*  133 */     String trans_date_sql = "select trim(to_char(to_date('" + transDate + "', 'dd/MM/yyyy'), 'Month'))||' '||to_char(to_date('" + transDate + "', 'dd/MM/yyyy'), 'YYYY') dt from dual";
/*  134 */     List<Map<String, Object>> rows = jdbcTemplate.queryForList(trans_date_sql);
/*  135 */     for (Map<String, Object> row : rows) {
/*  136 */       context.setVariable("fmr_trans_date", row.get("DT").toString());
/*      */     }
/*      */     
/*  139 */     context.setVariable("input", input);
/*  140 */     input.setFundCode(fund1);
/*  141 */     input.setTransDate(transDate);
/*      */     try {
/*  143 */       String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, to_char(f.inception_date , 'MON dd, yyyy') inception_date , fmr.members, f.fund_name, commentary, objective, to_char(to_date('" + transDate + "' , 'dd/MM/yyyy') , 'MONTHYYYY') fmr_trans_date, fmr.left_header header_img, nvl(fmr.weighted_avg, 0) weighted_avg, nvl(fmr.leverage, 0) leverage  from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00001, 00003, 00004, 00005, 00031) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*  144 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  145 */       for (Map<String, Object> f : fundMain) {
/*  146 */         FmrPdfReportModal fpModal = new FmrPdfReportModal();
/*  147 */         FundDefinitionReportModel fundDef = new FundDefinitionReportModel();
/*  148 */         fundDef.setId(f.get("id".toUpperCase()).toString());
/*  149 */         fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());
/*  150 */         fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());
/*  151 */         fundDef.setMemberList(memberList);
/*  152 */         fundDef.setCommentary(f.get("commentary".toUpperCase()).toString());
/*  153 */         fundDef.setObjective(f.get("objective".toUpperCase()).toString());
/*  154 */         fundDef.setFundName(f.get("fund_name".toUpperCase()).toString());
/*  155 */         fundDef.setLaunchDate(f.get("inception_date".toUpperCase()).toString());
/*  156 */         fundDef.setWeighted_avg(f.get("weighted_avg".toUpperCase()).toString());
/*  157 */         fundDef.setLeverage(f.get("leverage".toUpperCase()).toString());
/*  158 */         fpModal.setFundDef(fundDef);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  167 */         String fif_basicInfo_sql = "select TRANSDATE, ID, t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV, BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS, t.nav nav1,  (select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy, (select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf, (select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav,(select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) smexp from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";
/*      */         
/*  169 */         FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo)jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper)new FmrFundBasicInfoMapper());
/*  170 */         fpModal.setBasicInfo(fif_basicInfo1);
/*      */         
/*  172 */         String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";
/*      */ 
/*      */ 
/*      */         
/*  176 */         List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);
/*  177 */         for (Map<String, Object> dt : fund_perform_dates_list) {
/*  178 */           fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());
/*  179 */           fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());
/*  180 */           fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());
/*      */           
/*  182 */           input.currMonth = dt.get("FUNDASSETALLOCDATE1").toString();
/*  183 */           input.prevMonth = dt.get("FUNDASSETALLOCDATE2").toString();
/*      */         } 
/*  185 */         fpModal.setFif_perform_data(getPerformance1(fundDef, transDate, jdbcTemplate));
/*  186 */         fpModal.setFif_perform_data1(getPerformance2(fundDef, transDate, jdbcTemplate));
/*  187 */         List<Map<String, Object>> fif_asset_header = getAssetAllocHeader(fundDef.getFundCode(), transDate, jdbcTemplate);
/*  188 */         getAssetAllocBody(fpModal, fundDef.getFundCode(), transDate, false, jdbcTemplate);
/*  189 */         fpModal.setFif_asset_header(fif_asset_header);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  194 */         String fmr_tech_info_sql = "select transdate, description, decode(value, 0 , 'NIL', value) value \n  from fmr_tech_info\n where FUND_CODE = '" + fundDef.getFundCode() + "'\n   and TRANSDATE = (select max(transdate) from fmr_tech_info where FUND_CODE = '" + fundDef.getFundCode() + "') ";
/*  195 */         List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
/*  196 */         fpModal.setFmr_tech_info_sql_data(fmr_tech_info_sql_data);
/*  197 */         populateTopTenSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
/*  198 */         populateTopTfcSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
/*      */         
/*  200 */         String non_complaints = "select * from fmr_non_complaints_invest where FUND_CODE = '" + fundDef.fundCode + "' and TRANSDATE=to_date('" + transDate + "' , 'dd/MM/yyyy')";
/*  201 */         List<Map<String, Object>> non_complaints_data = jdbcTemplate.queryForList(non_complaints);
/*  202 */         fpModal.setNon_compliant(non_complaints_data);
/*  203 */         fpModal.setBenchMarkDesc("Benchmark");
/*  204 */         fpModal.getBasicInfo().setBenchmarkLabel("Benchmark");
/*  205 */         if (fundDef.fundCode.equals("00001")) {
/*  206 */           fpModal.setBenchMarkDesc("6 Months Kibor");
/*  207 */         } else if (fundDef.fundCode.equals("00003")) {
/*  208 */           fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
/*  209 */           fpModal.setBenchMarkDesc("Benchmark*");
/*  210 */         } else if (fundDef.fundCode.equals("00005")) {
/*  211 */           fpModal.setBenchMarkDesc("6 Months PKRV");
/*  212 */         } else if (fundDef.fundCode.equals("00004")) {
/*  213 */           fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
/*  214 */           fpModal.setBenchMarkDesc("Benchmark*");
/*      */         } 
/*  216 */         fpModal.setHeader(f.get("header_img".toUpperCase()).toString());
/*  217 */         fpModal.setFif_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));
/*  218 */         fpModal.setBarChart(AmsFundPerformance.fundBenchMarkData(fpModal, fundDef.fundCode, transDate, contextPath, jdbcTemplate));
/*  219 */         reportList.add(fpModal);
/*      */       } 
/*  221 */     } catch (Exception ex) {
/*  222 */       ex.printStackTrace();
/*      */     } 
/*  224 */     context.setVariable("cf_report", reportList);
/*      */   }
/*      */ 
/*      */   
/*      */   public void EqfModalMap(List<FmrPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {
/*  229 */     List<String> memberList = new ArrayList<>();
/*  230 */     String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";
/*  231 */     List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);
/*  232 */     for (Map<String, Object> row : fmr_comm_member_rows) {
/*  233 */       memberList.add(row.get("MEMBER").toString());
/*      */     }
/*  235 */     FundDefinitionReportModel input = new FundDefinitionReportModel();
/*  236 */     input.setFundCode(fund1);
/*  237 */     input.setTransDate(transDate);
/*      */     try {
/*  239 */       String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, to_char(f.inception_date , 'MON dd, yyyy') inception_date, fmr.members, f.fund_name, commentary, objective, f.fund_short_name , fmr.left_header header_img, nvl(fmr.weighted_avg, 0) weighted_avg, nvl(fmr.leverage, 0) leverage from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00002,00008,00020,00023 , 00024 ,00025) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*  240 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  241 */       for (Map<String, Object> f : fundMain) {
/*  242 */         FmrPdfReportModal fpModal = new FmrPdfReportModal();
/*  243 */         FundDefinitionReportModel fundDef = new FundDefinitionReportModel();
/*  244 */         fundDef.setId(f.get("id".toUpperCase()).toString());
/*  245 */         fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());
/*  246 */         fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());
/*  247 */         fundDef.setMemberList(memberList);
/*  248 */         fundDef.setCommentary(f.get("commentary".toUpperCase()).toString());
/*  249 */         fundDef.setObjective(f.get("objective".toUpperCase()).toString());
/*  250 */         fundDef.setFundName(f.get("fund_name".toUpperCase()).toString());
/*  251 */         fundDef.setLaunchDate(f.get("inception_date".toUpperCase()).toString());
/*  252 */         fundDef.setWeighted_avg(f.get("weighted_avg".toUpperCase()).toString());
/*  253 */         fundDef.setLeverage(f.get("leverage".toUpperCase()).toString());
/*  254 */         fpModal.setFundDef(fundDef);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  263 */         String fif_basicInfo_sql = "select TRANSDATE, ID, t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV ,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS, t.nav nav1  ,(select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy,\n(select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf,\n(select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav,(select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) SMEXP  from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";
/*      */         
/*  265 */         FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo)jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper)new FmrFundBasicInfoMapper());
/*  266 */         fpModal.setBasicInfo(fif_basicInfo1);
/*  267 */         String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";
/*      */ 
/*      */ 
/*      */         
/*  271 */         List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);
/*  272 */         for (Map<String, Object> dt : fund_perform_dates_list) {
/*  273 */           fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());
/*  274 */           fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());
/*  275 */           fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());
/*      */         } 
/*  277 */         fpModal.setFif_perform_data(getPerformance1(fundDef, transDate, jdbcTemplate));
/*  278 */         fpModal.setFif_perform_data1(getPerformance2(fundDef, transDate, jdbcTemplate));
/*  279 */         List<Map<String, Object>> fif_asset_header = getAssetAllocHeader(fundDef.getFundCode(), transDate, jdbcTemplate);
/*  280 */         getAssetAllocBody(fpModal, fundDef.getFundCode(), transDate, false, jdbcTemplate);
/*  281 */         fpModal.setFif_asset_header(fif_asset_header);
/*      */ 
/*      */         
/*  284 */         String fmr_tech_info_sql = "select transdate, description, decode(value, 0 , 'NIL', value) value \n  from fmr_tech_info\n where FUND_CODE = '" + fundDef.getFundCode() + "'\n   and TRANSDATE = to_date('" + transDate + "' , 'dd/MM/yyyy')";
/*      */         
/*  286 */         List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
/*  287 */         fpModal.setFmr_tech_info_sql_data(fmr_tech_info_sql_data);
/*  288 */         populateTopTenSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
/*  289 */         populateTopTfcSection(fpModal, fundDef.getFundCode(), transDate, jdbcTemplate);
/*  290 */         String non_complaints = "select * from fmr_non_complaints_invest where FUND_CODE = '" + fundDef.fundCode + "' and TRANSDATE=to_date('" + transDate + "' , 'dd/MM/yyyy')";
/*  291 */         List<Map<String, Object>> non_complaints_data = jdbcTemplate.queryForList(non_complaints);
/*  292 */         fpModal.setNon_compliant(non_complaints_data);
/*  293 */         fpModal.setBenchMarkDesc("Benchmark");
/*  294 */         fpModal.getBasicInfo().setBenchmarkLabel("Benchmark");
/*  295 */         int upperBound = 40;
/*  296 */         if (fundDef.fundCode.equals("00008"))
/*      */         {
/*  298 */           fpModal.setBenchMarkDesc("KMI-30");
/*      */         }
/*  300 */         if (fundDef.fundCode.equals("00002"))
/*      */         {
/*  302 */           fpModal.setBenchMarkDesc("KSE-100");
/*      */         }
/*  304 */         if (fundDef.fundCode.equals("00020"))
/*      */         {
/*  306 */           fpModal.setBenchMarkDesc("KMI-30");
/*      */         }
/*  308 */         if (fundDef.fundCode.equals("00025")) {
/*      */           
/*  310 */           fpModal.setBenchMarkDesc("Benchmark*");
/*  311 */           fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
/*  312 */           upperBound = 55;
/*  313 */         } else if (fundDef.fundCode.equals("00023")) {
/*  314 */           fpModal.setBenchMarkDesc("Benchmark");
/*      */           
/*  316 */           upperBound = 55;
/*      */         } 
/*  318 */         if (fundDef.fundCode.equals("00024")) {
/*      */           
/*  320 */           fpModal.setBenchMarkDesc("Benchmark*");
/*  321 */           fpModal.getBasicInfo().setBenchmarkLabel("Benchmark*");
/*  322 */           upperBound = 100;
/*      */         } 
/*      */         
/*  325 */         if (fundDef.fundCode.equals("00023")) {
/*  326 */           fpModal.setFif_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));
/*      */         } else {
/*  328 */           fpModal.setFif_pieChart(AmsFundPerformance.fundSectorAllocationData(fundDef.fundCode, transDate, contextPath, jdbcTemplate, upperBound));
/*      */         } 
/*  330 */         if (fundDef.fundCode.equals("00024")) {
/*  331 */           fpModal.setIaaf_pieChart(AmsFundPerformance.fundPortfolioQualityData1(fundDef.fundCode, transDate, contextPath, jdbcTemplate));
/*      */         }
/*      */         
/*  334 */         fpModal.setHeader(f.get("header_img".toUpperCase()).toString());
/*      */         
/*  336 */         fpModal.setBarChart(AmsFundPerformance.fundBenchMarkTimeSeries(fpModal, fundDef.fundCode, f.get("fund_short_name".toUpperCase()).toString(), transDate, contextPath, jdbcTemplate));
/*  337 */         reportList.add(fpModal);
/*      */       } 
/*  339 */     } catch (Exception ex) {
/*  340 */       System.out.println(ex);
/*  341 */       String str = "";
/*      */     } 
/*  343 */     context.setVariable("eq_fund_report", reportList);
/*      */   }
/*      */ 
/*      */   
/*      */   public void FundPlanModalMap(List<FmrPlanPdfReportModal> reportList, String fund1, String transDate, String contextPath, Context context, JdbcTemplate jdbcTemplate) {
/*  348 */     List<String> memberList = new ArrayList<>();
/*  349 */     String fmr_comm_member_qry = "select id, member from fmr_comm_member fb order by id";
/*  350 */     List<Map<String, Object>> fmr_comm_member_rows = jdbcTemplate.queryForList(fmr_comm_member_qry);
/*  351 */     for (Map<String, Object> row : fmr_comm_member_rows) {
/*  352 */       memberList.add(row.get("MEMBER").toString());
/*      */     }
/*  354 */     FmrPdfReportModal fpRepModal = new FmrPdfReportModal();
/*  355 */     FundDefinitionReportModel input = new FundDefinitionReportModel();
/*  356 */     input.setFundCode(fund1);
/*  357 */     input.setTransDate(transDate);
/*      */     try {
/*  359 */       String mainSql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, fmr.members, f.fund_name, commentary, objective from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (00002, 00008, 00027) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*  360 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  361 */       for (Map<String, Object> f : fundMain) {
/*  362 */         FmrPlanPdfReportModal fpModal = new FmrPlanPdfReportModal();
/*  363 */         FundDefinitionReportModel fundDef = new FundDefinitionReportModel();
/*  364 */         fundDef.setId(f.get("id".toUpperCase()).toString());
/*  365 */         fundDef.setTransDate(f.get("trans_date".toUpperCase()).toString());
/*  366 */         fundDef.setFundCode(f.get("fund_code".toUpperCase()).toString());
/*  367 */         fundDef.setMemberList(memberList);
/*  368 */         fundDef.setObjective(f.get("objective".toUpperCase()).toString());
/*  369 */         fpModal.setFundDef(fundDef);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  377 */         String fif_basicInfo_sql = "select TRANSDATE, ID,t.fund_code, t.FUND_TYPE,CATEGORY, to_char(f.inception_date, 'Month dd,yyyy') LAUNCHDATE, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETS, ROUND(get_net_assets_as_on_date_func('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000 - get_total_investment_of_scheme('" + fundDef.getFundCode() + "', to_date('" + transDate + "' , 'dd/MM/yyyy'))/1000000, 2) as NETASSETEXFOF, (select nav from unit_nav where fund_code = '" + fundDef.getFundCode() + "' and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy')) NAV ,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS ,(select nvl(ter, 0) from fmr_fund_details where fund_code = t.fund_code) ter, (select nvl(govtlevyratio, 0) from fmr_fund_details where fund_code = t.fund_code) ter_gov_levy,\n(select nvl(Swwf, 0) from fmr_fund_details where fund_code = t.fund_code) swwf,\n(select nvl(swwfunit, 0) from fmr_fund_details where fund_code = t.fund_code) swwf_nav, (select nvl(smexp, 0) from fmr_fund_details where fund_code = t.fund_code) smexp from fmr_fund_basic_info t, fund f \nwhere t.fund_code = '" + fundDef.getFundCode() + "' and id = (select max(id) from fmr_fund_basic_info tt where fund_code = t.fund_code) and t.fund_code = f.fund_code ";
/*  378 */         FmrFundBasicInfo fif_basicInfo1 = (FmrFundBasicInfo)jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper)new FmrFundBasicInfoMapper());
/*  379 */         fpModal.setBasicInfo(fif_basicInfo1);
/*      */         
/*  381 */         String fund_perform_dates = "select to_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundPerform1Date,\nto_char(to_date('" + transDate + "', 'dd/MM/yyyy') , 'Monthdd, yyyy') fundAssetAllocDate1,\nto_char(last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM')) , 'Monthdd, yyyy') fundAssetAllocDate2\nfrom dual";
/*      */ 
/*      */ 
/*      */         
/*  385 */         List<Map<String, Object>> fund_perform_dates_list = jdbcTemplate.queryForList(fund_perform_dates);
/*  386 */         for (Map<String, Object> dt : fund_perform_dates_list) {
/*  387 */           fpModal.setFundPerform1Date(dt.get("FUNDPERFORM1DATE").toString());
/*  388 */           fpModal.setFundAssetAllocDate1(dt.get("FUNDASSETALLOCDATE1").toString());
/*  389 */           fpModal.setFundAssetAllocDate2(dt.get("FUNDASSETALLOCDATE2").toString());
/*      */         } 
/*      */ 
/*      */         
/*  393 */         String fpf_p_f1 = "select FNDID , decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, B_MONTH1, B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, BYTD,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,   b_since,     decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,       decode(decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)), null, 'N/A', decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) || '%') ALPHA \n  from fund_performence fp \n where FNDID in ('00014', '00016', '00019', '00011', '00012', '00013', '00017', '00018', '00021', '00022', '00026', '00033') \n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  438 */         if (f.get("fund_code".toUpperCase()).toString().equals("00027"))
/*      */         {
/*  440 */           fpf_p_f1 = "select FNDID , decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, B_MONTH1, B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, BYTD, decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,   b_since,     decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,       decode(decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)), null, 'N/A', decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) || '%') ALPHA \n  from fund_performence fp \n where FNDID in ('00027', '00028', '00029', '00030','00032') \n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') and B_MONTH1 is not null order by descp ";
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  486 */         List<Map<String, Object>> fpf_performacne = jdbcTemplate.queryForList(fpf_p_f1);
/*  487 */         PlanPerformance p1 = new PlanPerformance();
/*  488 */         p1.setDesc("Apr-19");
/*  489 */         for (Map<String, Object> dt : fpf_performacne) {
/*  490 */           FundPerformance f1 = new FundPerformance();
/*  491 */           if (dt.get("DESCP").toString().toLowerCase().trim().equals("BenchMark".toLowerCase())) {
/*  492 */             f1.setBmmonth1(checkIfNull(dt.get("B_MONTH1")));
/*  493 */             f1.setBmYTD(checkIfNull(dt.get("bYTD")));
/*  494 */             f1.setBmmonth3(checkIfNull(dt.get("B_MONTH3")));
/*  495 */             f1.setBmmonth6(checkIfNull(dt.get("B_MONTH6")));
/*  496 */             f1.setBmyear1(checkIfNull(dt.get("B_YEAR1")));
/*  497 */             f1.setBmyear3(checkIfNull(dt.get("B_YEAR3")));
/*  498 */             f1.setBmyear5(checkIfNull(dt.get("B_YEAR5")));
/*  499 */             f1.setBmsi(checkIfNull(dt.get("b_since")));
/*      */           } else {
/*  501 */             f1.setMonth1(checkIfNull(dt.get("B_MONTH1")));
/*  502 */             f1.setYTD(checkIfNull(dt.get("bYTD")));
/*  503 */             f1.setMonth3(checkIfNull(dt.get("B_MONTH3")));
/*  504 */             f1.setMonth6(checkIfNull(dt.get("B_MONTH6")));
/*  505 */             f1.setYear1(checkIfNull(dt.get("B_YEAR1")));
/*  506 */             f1.setYear3(checkIfNull(dt.get("B_YEAR3")));
/*  507 */             f1.setYear5(checkIfNull(dt.get("B_YEAR5")));
/*  508 */             f1.setSi(checkIfNull(dt.get("b_since")));
/*      */           } 
/*  510 */           if (dt.get("FNDID").toString().equals("00014")) {
/*  511 */             p1.setF1(populateFofGrid(p1.getF1(), f1)); continue;
/*  512 */           }  if (dt.get("FNDID").toString().equals("00016")) {
/*  513 */             p1.setF2(populateFofGrid(p1.getF2(), f1)); continue;
/*  514 */           }  if (dt.get("FNDID").toString().equals("00019")) {
/*  515 */             p1.setF3(populateFofGrid(p1.getF3(), f1)); continue;
/*  516 */           }  if (dt.get("FNDID").toString().equals("00011")) {
/*  517 */             p1.setF4(populateFofGrid(p1.getF4(), f1)); continue;
/*  518 */           }  if (dt.get("FNDID").toString().equals("00012")) {
/*  519 */             p1.setF5(populateFofGrid(p1.getF5(), f1)); continue;
/*  520 */           }  if (dt.get("FNDID").toString().equals("00013")) {
/*  521 */             p1.setF6(populateFofGrid(p1.getF6(), f1)); continue;
/*  522 */           }  if (dt.get("FNDID").toString().equals("00017")) {
/*  523 */             p1.setF7(populateFofGrid(p1.getF7(), f1)); continue;
/*  524 */           }  if (dt.get("FNDID").toString().equals("00018")) {
/*  525 */             p1.setF8(populateFofGrid(p1.getF8(), f1)); continue;
/*  526 */           }  if (dt.get("FNDID").toString().equals("00021")) {
/*  527 */             p1.setF9(populateFofGrid(p1.getF9(), f1)); continue;
/*  528 */           }  if (dt.get("FNDID").toString().equals("00022")) {
/*  529 */             p1.setF10(populateFofGrid(p1.getF10(), f1)); continue;
/*  530 */           }  if (dt.get("FNDID").toString().equals("00026")) {
/*  531 */             p1.setF11(populateFofGrid(p1.getF11(), f1)); continue;
/*  532 */           }  if (dt.get("FNDID").toString().equals("00033")) {
/*  533 */             p1.setF12(populateFofGrid(p1.getF12(), f1)); continue;
/*  534 */           }  if (dt.get("FNDID").toString().equals("00027")) {
/*  535 */             p1.setF1(populateFofGrid(p1.getF1(), f1)); continue;
/*  536 */           }  if (dt.get("FNDID").toString().equals("00028")) {
/*  537 */             p1.setF2(populateFofGrid(p1.getF2(), f1)); continue;
/*  538 */           }  if (dt.get("FNDID").toString().equals("00029")) {
/*  539 */             p1.setF3(populateFofGrid(p1.getF3(), f1)); continue;
/*  540 */           }  if (dt.get("FNDID").toString().equals("00030")) {
/*  541 */             p1.setF4(populateFofGrid(p1.getF4(), f1)); continue;
/*  542 */           }  if (dt.get("FNDID").toString().equals("00032")) {
/*  543 */             p1.setF5(populateFofGrid(p1.getF5(), f1));
/*      */           }
/*      */         } 
/*  546 */         fpModal.setPerformanceGrid(p1);
/*  547 */         if (fundDef.fundCode.equals("00002")) {
/*  548 */           fpModal.setHeader("fund_fpf.png");
/*  549 */           String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_PLAN \n where fund_code in (14, 16, 19) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";
/*      */ 
/*      */           
/*  552 */           List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
/*  553 */           fpModal.getPlanData().setTechInfoPlan1(fmr_tech_info_sql_data);
/*  554 */           fpModal.getPlanData().setAssetAllocFund1(getAssetAllocBody(fpRepModal, "00014", transDate, true, jdbcTemplate));
/*  555 */           fpModal.getPlanData().setAssetAllocFund2(getAssetAllocBody(fpRepModal, "00016", transDate, true, jdbcTemplate));
/*  556 */           fpModal.getPlanData().setAssetAllocFund3(getAssetAllocBody(fpRepModal, "00019", transDate, true, jdbcTemplate));
/*  557 */           populateTerCommentsFof(fpModal.getFundDef(), "00002", jdbcTemplate);
/*  558 */         } else if (fundDef.fundCode.equals("00008")) {
/*  559 */           fpModal.setHeader("fund_ifpf.png");
/*  560 */           String fmr_tech_info_sql = "select description, net_assets, nav \n  from Tech_INFO_FOR_PLAN \n where fund_code in (11,12,13 ,17 ,18 ,21, 22, 26, 33) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";
/*      */ 
/*      */           
/*  563 */           List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
/*  564 */           fpModal.getPlanData().setTechInfoPlan2(fmr_tech_info_sql_data);
/*  565 */           fpModal.getPlanData().setAssetAllocFund4(getAssetAllocBody(fpRepModal, "00011", transDate, true, jdbcTemplate));
/*  566 */           fpModal.getPlanData().setAssetAllocFund5(getAssetAllocBody(fpRepModal, "00012", transDate, true, jdbcTemplate));
/*  567 */           fpModal.getPlanData().setAssetAllocFund6(getAssetAllocBody(fpRepModal, "00013", transDate, true, jdbcTemplate));
/*  568 */           fpModal.getPlanData().setAssetAllocFund7(getAssetAllocBody(fpRepModal, "00017", transDate, true, jdbcTemplate));
/*  569 */           fpModal.getPlanData().setAssetAllocFund8(getAssetAllocBody(fpRepModal, "00018", transDate, true, jdbcTemplate));
/*  570 */           fpModal.getPlanData().setAssetAllocFund9(getAssetAllocBody(fpRepModal, "00021", transDate, true, jdbcTemplate));
/*  571 */           fpModal.getPlanData().setAssetAllocFund10(getAssetAllocBody(fpRepModal, "00022", transDate, true, jdbcTemplate));
/*  572 */           fpModal.getPlanData().setAssetAllocFund11(getAssetAllocBody(fpRepModal, "00026", transDate, true, jdbcTemplate));
/*  573 */           fpModal.getPlanData().setAssetAllocFund12(getAssetAllocBody(fpRepModal, "00033", transDate, true, jdbcTemplate));
/*  574 */           populateTerCommentsFof(fpModal.getFundDef(), "00008", jdbcTemplate);
/*  575 */         } else if (fundDef.fundCode.equals("00027")) {
/*  576 */           fpModal.setHeader("fund_sfp.png");
/*  577 */           String fmr_tech_info_sql = "select description, net_assets, nav, decode(get_total_investment_of_scheme(fund_code, price_date), 1,  net_assets, ROUND(get_net_assets_as_on_date_func(fund_code, price_date) - get_total_investment_of_scheme(fund_code, price_date), 2)) as NETASSETEXFOF from Tech_INFO_FOR_PLAN \n where fund_code in (27,28,29,30,32) and price_date = to_date('" + transDate + "' , 'dd/MM/yyyy') ";
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  582 */           List<Map<String, Object>> fmr_tech_info_sql_data = jdbcTemplate.queryForList(fmr_tech_info_sql);
/*  583 */           fpModal.getPlanData().setTechInfoPlan1(fmr_tech_info_sql_data);
/*  584 */           fpModal.getPlanData().setAssetAllocFund1(getAssetAllocBody(fpRepModal, "00027", transDate, true, jdbcTemplate));
/*  585 */           fpModal.getPlanData().setAssetAllocFund2(getAssetAllocBody(fpRepModal, "00028", transDate, true, jdbcTemplate));
/*  586 */           fpModal.getPlanData().setAssetAllocFund3(getAssetAllocBody(fpRepModal, "00029", transDate, true, jdbcTemplate));
/*  587 */           fpModal.getPlanData().setAssetAllocFund4(getAssetAllocBody(fpRepModal, "00030", transDate, true, jdbcTemplate));
/*  588 */           fpModal.getPlanData().setAssetAllocFund5(getAssetAllocBody(fpRepModal, "00032", transDate, true, jdbcTemplate));
/*  589 */           populateTerCommentsFof(fpModal.getFundDef(), "00027", jdbcTemplate);
/*      */         } 
/*  591 */         reportList.add(fpModal);
/*      */       } 
/*  593 */     } catch (Exception ex) {
/*  594 */       System.out.println(ex);
/*  595 */       ex.printStackTrace();
/*      */     } 
/*      */     
/*  598 */     context.setVariable("fund_plan_report", reportList);
/*      */   }
/*      */   
/*      */   private String checkIfNull(Object o) {
/*      */     try {
/*  603 */       if (o != null && !o.toString().equals("")) {
/*  604 */         return o.toString() + "%";
/*      */       }
/*  606 */     } catch (Exception e) {
/*  607 */       System.out.println("exception in null check");
/*      */     } 
/*  609 */     return "N/A";
/*      */   }
/*      */   
/*      */   private String checkEmptyString(Object o) {
/*      */     try {
/*  614 */       if (o != null && !o.toString().equals("")) {
/*  615 */         return o.toString();
/*      */       }
/*  617 */     } catch (Exception e) {
/*  618 */       System.out.println("exception in null check");
/*      */     } 
/*  620 */     return "";
/*      */   }
/*      */   
/*      */   private String twoDecimalFormatting(String val) {
/*  624 */     DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
/*  625 */     String value = "";
/*      */     try {
/*  627 */       value = decimalFormat.format(Double.valueOf(val));
/*  628 */     } catch (NumberFormatException e) {
/*  629 */       e.printStackTrace();
/*  630 */     } catch (Exception e) {
/*  631 */       e.printStackTrace();
/*      */     } 
/*  633 */     return value;
/*      */   }
/*      */   
/*      */   private String fourDecimalFormatting(String val) {
/*  637 */     DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");
/*  638 */     String value = "";
/*      */     try {
/*  640 */       value = decimalFormat.format(Double.valueOf(val));
/*  641 */     } catch (NumberFormatException e) {
/*  642 */       e.printStackTrace();
/*  643 */     } catch (Exception e) {
/*  644 */       e.printStackTrace();
/*      */     } 
/*  646 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateTerCommentsFof(FundDefinitionReportModel fundDef, String pFundCode, JdbcTemplate jdbcTemplate) {
/*  651 */     if (pFundCode.equals("00002")) {
/*  652 */       String mainSql = "select f.fund_code, commentary, (select ter from fmr_fund_details fb where f.fund_code = fb.fund_code) ter,  (select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf ,(select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit, (select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (14, 16, 19) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  657 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  658 */       for (Map<String, Object> f : fundMain) {
/*      */         
/*  660 */         String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());
/*  661 */         String commentary = f.get("commentary".toUpperCase()).toString();
/*  662 */         String swwf = f.get("swwf".toUpperCase()).toString();
/*  663 */         String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());
/*  664 */         String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());
/*      */         
/*  666 */         if (f.get("fund_code".toUpperCase()).toString().equals("00014")) {
/*      */           
/*  668 */           fundDef.planDef.setTer1(ter);
/*  669 */           fundDef.planDef.setComment1(commentary);
/*  670 */           fundDef.planDef.setSwwf1(swwf);
/*  671 */           fundDef.planDef.setSwwfunit1(swwfunit);
/*  672 */           fundDef.planDef.setGovtlevy1(govtLevies); continue;
/*  673 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00016")) {
/*  674 */           fundDef.planDef.setTer2(ter);
/*  675 */           fundDef.planDef.setComment2(commentary);
/*  676 */           fundDef.planDef.setSwwf2(Double.valueOf(swwf).doubleValue());
/*  677 */           fundDef.planDef.setSwwfunit2(swwfunit);
/*  678 */           fundDef.planDef.setGovtlevy2(govtLevies); continue;
/*  679 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00019")) {
/*  680 */           fundDef.planDef.setTer3(ter);
/*  681 */           fundDef.planDef.setComment3(commentary);
/*  682 */           fundDef.planDef.setSwwf3(Double.valueOf(swwf).doubleValue());
/*  683 */           fundDef.planDef.setSwwfunit3(swwfunit);
/*  684 */           fundDef.planDef.setGovtlevy3(govtLevies);
/*      */         } 
/*      */       } 
/*  687 */     } else if (pFundCode.equals("00027")) {
/*  688 */       String mainSql = "select f.fund_code, commentary, (select nvl(ter , 'N/A') from fmr_fund_details fb where f.fund_code = fb.fund_code) ter,(select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf , (select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit,(select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (27, 28, 29, 30,32) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  693 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  694 */       for (Map<String, Object> f : fundMain) {
/*      */         
/*  696 */         String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());
/*  697 */         String commentary = f.get("commentary".toUpperCase()).toString();
/*  698 */         double swwf = Double.valueOf(f.get("swwf".toUpperCase()).toString()).doubleValue();
/*  699 */         String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());
/*  700 */         String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());
/*  701 */         String smexp = twoDecimalFormatting(f.get("smexp".toUpperCase()).toString());
/*      */         
/*  703 */         if (f.get("fund_code".toUpperCase()).toString().equals("00027")) {
/*  704 */           fundDef.planDef.setTer12(ter);
/*  705 */           fundDef.planDef.setComment12(commentary);
/*  706 */           fundDef.planDef.setSwwf12(swwf);
/*  707 */           fundDef.planDef.setSwwfunit12(swwfunit);
/*  708 */           fundDef.planDef.setGovtlevy12(govtLevies);
/*  709 */           fundDef.planDef.setSmexp12(smexp); continue;
/*  710 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00028")) {
/*  711 */           fundDef.planDef.setTer13(ter);
/*  712 */           fundDef.planDef.setComment13(commentary);
/*  713 */           fundDef.planDef.setSwwf13(swwf);
/*  714 */           fundDef.planDef.setSwwfunit13(swwfunit);
/*  715 */           fundDef.planDef.setGovtlevy13(govtLevies);
/*  716 */           fundDef.planDef.setSmexp13(smexp); continue;
/*  717 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00029")) {
/*  718 */           fundDef.planDef.setTer14(ter);
/*  719 */           fundDef.planDef.setComment14(commentary);
/*  720 */           fundDef.planDef.setSwwf14(swwf);
/*  721 */           fundDef.planDef.setSwwfunit14(swwfunit);
/*  722 */           fundDef.planDef.setGovtlevy14(govtLevies);
/*  723 */           fundDef.planDef.setSmexp14(smexp); continue;
/*  724 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00030")) {
/*  725 */           fundDef.planDef.setTer15(ter);
/*  726 */           fundDef.planDef.setComment15(commentary);
/*  727 */           fundDef.planDef.setSwwf15(swwf);
/*  728 */           fundDef.planDef.setSwwfunit15(swwfunit);
/*  729 */           fundDef.planDef.setGovtlevy15(govtLevies);
/*  730 */           fundDef.planDef.setSmexp15(smexp); continue;
/*  731 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00032")) {
/*  732 */           fundDef.planDef.setTer16(ter);
/*  733 */           fundDef.planDef.setComment16(commentary);
/*  734 */           fundDef.planDef.setSwwf16(swwf);
/*  735 */           fundDef.planDef.setSwwfunit16(swwfunit);
/*  736 */           fundDef.planDef.setGovtlevy16(govtLevies);
/*  737 */           fundDef.planDef.setSmexp16(smexp);
/*      */         } 
/*      */       } 
/*  740 */     } else if (pFundCode.equals("00008")) {
/*  741 */       String mainSql = "select f.fund_code, commentary, (select ter from fmr_fund_details fb where f.fund_code = fb.fund_code) ter, (select nvl(swwf, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwf ,(select nvl(govtlevyratio, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) ter_gov_levy, (select nvl(swwfunit, 0) from fmr_fund_details fb where f.fund_code = fb.fund_code) swwfunit,(select nvl(smexp, 0) from fmr_fund_details where fund_code = f.fund_code) smexp from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code in (11,12,13 ,17 ,18 ,21, 22,26,33) and id = (select max(id) from fmr_def ff where ff.fund_code = fmr.fund_code and post=1) ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  746 */       List<Map<String, Object>> fundMain = jdbcTemplate.queryForList(mainSql);
/*  747 */       for (Map<String, Object> f : fundMain) {
/*      */         
/*  749 */         String ter = twoDecimalFormatting(f.get("ter".toUpperCase()).toString());
/*  750 */         String commentary = f.get("commentary".toUpperCase()).toString();
/*  751 */         double swwf = Double.valueOf(f.get("swwf".toUpperCase()).toString()).doubleValue();
/*  752 */         String swwfunit = fourDecimalFormatting(f.get("swwfunit".toUpperCase()).toString());
/*  753 */         String govtLevies = twoDecimalFormatting(f.get("ter_gov_levy".toUpperCase()).toString());
/*      */         
/*  755 */         if (f.get("fund_code".toUpperCase()).toString().equals("00011")) {
/*  756 */           fundDef.planDef.setTer4(ter);
/*  757 */           fundDef.planDef.setComment4(commentary);
/*  758 */           fundDef.planDef.setSwwf4(swwf);
/*  759 */           fundDef.planDef.setSwwfunit4(swwfunit);
/*  760 */           fundDef.planDef.setGovtlevy4(govtLevies); continue;
/*  761 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00012")) {
/*  762 */           fundDef.planDef.setTer5(ter);
/*  763 */           fundDef.planDef.setComment5(commentary);
/*  764 */           fundDef.planDef.setSwwf5(swwf);
/*  765 */           fundDef.planDef.setSwwfunit5(swwfunit);
/*  766 */           fundDef.planDef.setGovtlevy5(govtLevies); continue;
/*  767 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00013")) {
/*  768 */           fundDef.planDef.setTer6(ter);
/*  769 */           fundDef.planDef.setComment6(commentary);
/*  770 */           fundDef.planDef.setSwwf6(swwf);
/*  771 */           fundDef.planDef.setSwwfunit6(swwfunit);
/*  772 */           fundDef.planDef.setGovtlevy6(govtLevies); continue;
/*  773 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00017")) {
/*  774 */           fundDef.planDef.setTer7(ter);
/*  775 */           fundDef.planDef.setComment7(commentary);
/*  776 */           fundDef.planDef.setSwwf7(swwf);
/*  777 */           fundDef.planDef.setSwwfunit7(swwfunit);
/*  778 */           fundDef.planDef.setGovtlevy7(govtLevies); continue;
/*  779 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00018")) {
/*  780 */           fundDef.planDef.setTer8(ter);
/*  781 */           fundDef.planDef.setComment8(commentary);
/*  782 */           fundDef.planDef.setSwwf8(swwf);
/*  783 */           fundDef.planDef.setSwwfunit8(swwfunit);
/*  784 */           fundDef.planDef.setGovtlevy8(f.get("ter_gov_levy".toUpperCase()).toString()); continue;
/*  785 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00021")) {
/*  786 */           fundDef.planDef.setTer9(ter);
/*  787 */           fundDef.planDef.setComment9(commentary);
/*  788 */           fundDef.planDef.setSwwf9(swwf);
/*  789 */           fundDef.planDef.setSwwfunit9(swwfunit);
/*  790 */           fundDef.planDef.setGovtlevy9(govtLevies); continue;
/*  791 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00022")) {
/*  792 */           fundDef.planDef.setTer10(ter);
/*  793 */           fundDef.planDef.setComment10(commentary);
/*  794 */           fundDef.planDef.setSwwf10(swwf);
/*  795 */           fundDef.planDef.setSwwfunit10(swwfunit);
/*  796 */           fundDef.planDef.setGovtlevy10(f.get("ter_gov_levy".toUpperCase()).toString()); continue;
/*  797 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00026")) {
/*  798 */           fundDef.planDef.setTer11(ter);
/*  799 */           fundDef.planDef.setComment11(commentary);
/*  800 */           fundDef.planDef.setSwwf11(swwf);
/*  801 */           fundDef.planDef.setSwwfunit11(swwfunit);
/*  802 */           fundDef.planDef.setGovtlevy11(govtLevies); continue;
/*  803 */         }  if (f.get("fund_code".toUpperCase()).toString().equals("00033")) {
/*  804 */           fundDef.planDef.setTer17(ter);
/*  805 */           fundDef.planDef.setComment17(commentary);
/*  806 */           fundDef.planDef.setSwwf17(swwf);
/*  807 */           fundDef.planDef.setSwwfunit17(swwfunit);
/*  808 */           fundDef.planDef.setGovtlevy17(govtLevies);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private FundPerformance populateFofGrid(FundPerformance f1, FundPerformance f2) {
/*  816 */     if (f1.getBmmonth1() == null || f1.getBmmonth1().equals("")) {
/*  817 */       f1.setBmmonth1(f2.getBmmonth1());
/*      */     }
/*  819 */     if (f1.getBmYTD() == null || f1.getBmYTD().equals("")) {
/*  820 */       f1.setBmYTD(f2.getBmYTD());
/*      */     }
/*  822 */     if (f1.getBmmonth3() == null || f1.getBmmonth3().equals("")) {
/*  823 */       f1.setBmmonth3(f2.getBmmonth3());
/*      */     }
/*  825 */     if (f1.getBmmonth6() == null || f1.getBmmonth6().equals("")) {
/*  826 */       f1.setBmmonth6(f2.getBmmonth6());
/*      */     }
/*  828 */     if (f1.getBmyear1() == null || f1.getBmyear1().equals("")) {
/*  829 */       f1.setBmyear1(f2.getBmyear1());
/*      */     }
/*  831 */     if (f1.getBmyear3() == null || f1.getBmyear3().equals("")) {
/*  832 */       f1.setBmyear3(f2.getBmyear3());
/*      */     }
/*  834 */     if (f1.getBmyear5() == null || f1.getBmyear5().equals("")) {
/*  835 */       f1.setBmyear5(f2.getBmyear5());
/*      */     }
/*  837 */     if (f1.getBmsi() == null || f1.getBmsi().equals("")) {
/*  838 */       f1.setBmsi(f2.getBmsi());
/*      */     }
/*  840 */     if (f1.getMonth1() == null || f1.getMonth1().equals("")) {
/*  841 */       f1.setMonth1(f2.getMonth1());
/*      */     }
/*  843 */     if (f1.getYTD() == null || f1.getYTD().equals("")) {
/*  844 */       f1.setYTD(f2.getYTD());
/*      */     }
/*  846 */     if (f1.getMonth3() == null || f1.getMonth3().equals("")) {
/*  847 */       f1.setMonth3(f2.getMonth3());
/*      */     }
/*  849 */     if (f1.getMonth6() == null || f1.getMonth6().equals("")) {
/*  850 */       f1.setMonth6(f2.getMonth6());
/*      */     }
/*  852 */     if (f1.getYear1() == null || f1.getYear1().equals("")) {
/*  853 */       f1.setYear1(f2.getYear1());
/*      */     }
/*  855 */     if (f1.getYear3() == null || f1.getYear3().equals("")) {
/*  856 */       f1.setYear3(f2.getYear3());
/*      */     }
/*  858 */     if (f1.getYear5() == null || f1.getYear5().equals("")) {
/*  859 */       f1.setYear5(f2.getYear5());
/*      */     }
/*  861 */     if (f1.getSi() == null || f1.getSi().equals("")) {
/*  862 */       f1.setSi(f2.getSi());
/*      */     }
/*  864 */     return f1;
/*      */   }
/*      */   
/*      */   private void populateTopTfcSection(FmrPdfReportModal fpModal, String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
/*  868 */     FmrTopTfcModel model = new FmrTopTfcModel();
/*  869 */     String top_tfc = "select SCHEME_NAME, PREVIOUS_ASSET_PERCENTAGE , TOTAL_ASSET_PERCENTAGE, (select sum(TOTAL_ASSET_PERCENTAGE) from fmr_top_tfc_sukuk_holding where FUND_CODE = '" + fundcode + "' and TRANS_DATE = to_date('" + transDate + "', 'dd/MM/yyyy')) TOTAL_ASSET_PERCENTAGESUM from fmr_top_tfc_sukuk_holding where FUND_CODE='" + fundcode + "' and TRANS_DATE=to_date('" + transDate + "' , 'dd/MM/yyyy') order by TOTAL_ASSET_PERCENTAGE desc ";
/*      */     
/*  871 */     List<Map<String, Object>> top_tfc_data = jdbcTemplate.queryForList(top_tfc);
/*  872 */     for (Map<String, Object> row : top_tfc_data) {
/*  873 */       if (row.get("TOTAL_ASSET_PERCENTAGESUM") != null) {
/*  874 */         model.setTotal(row.get("TOTAL_ASSET_PERCENTAGESUM").toString());
/*      */       }
/*      */     } 
/*  877 */     model.setTfcData(top_tfc_data);
/*  878 */     fpModal.setTopTfcModel(model);
/*      */   }
/*      */   
/*      */   private void populateTopTenSection(FmrPdfReportModal fpModal, String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
/*  882 */     FmrTopTfcModel model = new FmrTopTfcModel();
/*  883 */     String top_tfc = "select * from (select SCHEME_NAME, PREVIOUS_ASSET_PERCENTAGE , TOTAL_ASSET_PERCENTAGE, (select sum(TOTAL_ASSET_PERCENTAGE) from fmr_top_ten_holding where FUND_CODE = '" + fundcode + "' and TRANS_DATE = to_date('" + transDate + "', 'dd/MM/yyyy')) TOTAL_ASSET_PERCENTAGESUM from fmr_top_ten_holding where FUND_CODE='" + fundcode + "' and TRANS_DATE=to_date('" + transDate + "' , 'dd/MM/yyyy') order by TOTAL_ASSET_PERCENTAGE desc )  where ROWNUM <= 10";
/*      */     
/*  885 */     List<Map<String, Object>> top_tfc_data = jdbcTemplate.queryForList(top_tfc);
/*  886 */     for (Map<String, Object> row : top_tfc_data) {
/*  887 */       if (row.get("TOTAL_ASSET_PERCENTAGESUM") != null) {
/*  888 */         model.setTotal(row.get("TOTAL_ASSET_PERCENTAGESUM").toString());
/*      */       }
/*      */     } 
/*  891 */     model.setTfcData(top_tfc_data);
/*  892 */     fpModal.setTopTenHoldingModel(model);
/*      */   }
/*      */   
/*      */   private List<Map<String, Object>> getAssetAllocHeader(String fundcode, String transDate, JdbcTemplate jdbcTemplate) {
/*  896 */     String fassetallocation_sql = "select to_char(p.aa_trans_date , 'Monthdd,yyyy') prev, to_char(c.aa_trans_date , 'Monthdd,yyyy') curr \n  from fmr_prev_asset_alloocation p, fmr_current_asset_alloocation c\n where p.fund_code = '" + fundcode + "'\n   and p.aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "', 'dd/MM/yyyy'), -1), 'YYYYMM'), 'YYYYMM'))\n   and c.aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') ";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  901 */     List<Map<String, Object>> fif_perform_data1 = jdbcTemplate.queryForList(fassetallocation_sql);
/*  902 */     return fif_perform_data1;
/*      */   }
/*      */ 
/*      */   
/*      */   private FmrAssetAllocMain getAssetAllocBody(FmrPdfReportModal fpModal, String fundcode, String transDate, boolean filterByName, JdbcTemplate jdbcTemplate) {
/*  907 */     DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
/*  908 */     FmrAssetAllocMain assetAllocMain = new FmrAssetAllocMain();
/*  909 */     List<fmr_asset_allocation_model> currlist = new ArrayList<>();
/*  910 */     String prev_sql = "select  nvl(code, 0) code, aa_asset_description , nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth,(select sum(aa_prevmth) from fmr_prev_asset_alloocation where fund_code = 1 and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) aa_prevmthTotal \n from fmr_prev_asset_alloocation\n where fund_code = '" + fundcode + "' and code is not null  and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'), -1), 'YYYYMM'), 'YYYYMM')) ";
/*      */     
/*  912 */     List<Map<String, Object>> prev_data = jdbcTemplate.queryForList(prev_sql);
/*  913 */     String curr_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation where fund_code = '" + fundcode + "' and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal  from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "'  and code is not null \n and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') order by AA_CURRMTH ";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  918 */     List<Map<String, Object>> curr_data = jdbcTemplate.queryForList(curr_sql);
/*  919 */     if (prev_data.size() > curr_data
/*  920 */       .size()) {
/*  921 */       for (Map<String, Object> row : prev_data) {
/*  922 */         fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();
/*  923 */         assetAllocModel.setCode(row.get("CODE").toString());
/*  924 */         assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
/*  925 */         if (row.get("AA_PREVMTH") != null) {
/*  926 */           assetAllocModel.setPrev_month(row.get("AA_PREVMTH").toString());
/*  927 */           assetAllocModel.setCurr_month("0");
/*      */         } 
/*  929 */         if (row.get("AA_PREVMTHTOTAL") != null)
/*      */         {
/*  931 */           assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.parseDouble(row.get("AA_PREVMTHTOTAL").toString())));
/*      */         }
/*  933 */         currlist.add(assetAllocModel);
/*      */       } 
/*      */       
/*  936 */       for (fmr_asset_allocation_model crow : currlist) {
/*  937 */         for (Map<String, Object> prow : curr_data) {
/*      */           
/*  939 */           String pr = prow.get("CODE").toString();
/*  940 */           String cr = crow.getCode();
/*  941 */           if (cr.equals(pr)) {
/*  942 */             crow.setCurr_month(prow.get("AA_CURRMTH").toString());
/*      */           }
/*  944 */           if (prow.get("AA_CURRMTHTOTAL") != null) {
/*  945 */             assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_CURRMTHTOTAL").toString())));
/*      */           }
/*      */         } 
/*      */       } 
/*  949 */       String curr_new_sql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth , nvl(aa_currmth, 0) aa_currmth, (select sum(aa_currmth) from fmr_current_asset_alloocation where fund_code = '" + fundcode + "'  and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy')) aa_currmthTotal\n from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "'and code is not null \n and aa_trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and code not in (select code from fmr_prev_asset_alloocation where fund_code = '" + fundcode + "' and aa_trans_date = last_day(to_date(to_char(add_months(to_date('" + transDate + "','dd/MM/yyyy'),-1),'YYYYMM'),'YYYYMM'))) order by AA_CURRMTH ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  954 */       List<Map<String, Object>> curr_new_data = jdbcTemplate.queryForList(curr_new_sql);
/*  955 */       for (Map<String, Object> row : curr_new_data) {
/*  956 */         fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();
/*  957 */         assetAllocModel.setCode(row.get("CODE").toString());
/*  958 */         assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
/*  959 */         if (row.get("AA_CURRMTH") != null) {
/*  960 */           assetAllocModel.setCurr_month(row.get("AA_CURRMTH").toString());
/*  961 */           assetAllocModel.setPrev_month("0");
/*      */         } 
/*  963 */         if (row.get("AA_CURRMTHTOTAL") != null)
/*      */         {
/*  965 */           assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(row.get("AA_CURRMTHTOTAL").toString())));
/*      */         }
/*  967 */         currlist.add(assetAllocModel);
/*      */       } 
/*      */       
/*  970 */       for (fmr_asset_allocation_model crow : currlist) {
/*  971 */         for (Map<String, Object> prow : prev_data) {
/*      */           
/*  973 */           String pr = prow.get("CODE").toString();
/*  974 */           String cr = crow.getCode();
/*  975 */           if (cr.equals(pr)) {
/*  976 */             crow.setPrev_month(prow.get("AA_PREVMTH").toString());
/*  977 */           } else if (prow.get("AA_ASSET_DESCRIPTION").toString().equalsIgnoreCase(crow.getSecName())) {
/*  978 */             crow.setPrev_month(prow.get("AA_PREVMTH").toString());
/*      */           } 
/*  980 */           if (prow.get("AA_PREVMTHTOTAL") != null) {
/*  981 */             assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_PREVMTHTOTAL").toString())));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/*  986 */       for (Map<String, Object> row : curr_data) {
/*  987 */         fmr_asset_allocation_model assetAllocModel = new fmr_asset_allocation_model();
/*  988 */         assetAllocModel.setCode(row.get("CODE").toString());
/*  989 */         assetAllocModel.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
/*  990 */         if (row.get("AA_CURRMTH") != null) {
/*  991 */           assetAllocModel.setCurr_month(row.get("AA_CURRMTH").toString());
/*  992 */           assetAllocModel.setPrev_month("0");
/*      */         } 
/*  994 */         if (row.get("AA_CURRMTHTOTAL") != null)
/*      */         {
/*  996 */           assetAllocMain.setTotalCurrAlloc(decimalFormat.format(Double.valueOf(row.get("AA_CURRMTHTOTAL").toString())));
/*      */         }
/*  998 */         currlist.add(assetAllocModel);
/*      */       } 
/*      */       
/* 1001 */       for (fmr_asset_allocation_model crow : currlist) {
/*      */         
/* 1003 */         for (Map<String, Object> prow : prev_data) {
/* 1004 */           String pr = prow.get("CODE").toString();
/*      */           
/* 1006 */           String cr = crow.getCode();
/*      */           
/* 1008 */           if (cr.equals(pr)) {
/* 1009 */             System.out.println("Code getSecName:" + crow.getSecName() + ":");
/* 1010 */             crow.setPrev_month(prow.get("AA_PREVMTH").toString());
/* 1011 */             System.out.println("Code AA_PREVMTH:" + prow.get("AA_PREVMTH").toString() + ":");
/* 1012 */           } else if (prow.get("AA_ASSET_DESCRIPTION").toString().equalsIgnoreCase(crow.getSecName())) {
/* 1013 */             crow.setPrev_month(prow.get("AA_PREVMTH").toString());
/* 1014 */             System.out.println("Desc getSecName :" + crow.getSecName() + ":");
/* 1015 */             System.out.println("Desc AA_PREVMTH:" + prow.get("AA_PREVMTH").toString() + ":");
/*      */           } 
/* 1017 */           if (prow.get("AA_PREVMTHTOTAL") != null) {
/* 1018 */             assetAllocMain.setTotalPrevAlloc(decimalFormat.format(Double.valueOf(prow.get("AA_PREVMTHTOTAL").toString())));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1023 */     assetAllocMain.setFif_prev_asset_alloc(fmr_asset_allocation_model.filter(currlist, filterByName));
/*      */     
/* 1025 */     fpModal.setAssetAllocMain(assetAllocMain);
/* 1026 */     return assetAllocMain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Map<String, Object>> getPerformance1(FundDefinitionReportModel fundDef, String transDate, JdbcTemplate jdbcTemplate) {
/* 1208 */     String fperformance_sql = "select decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp, nvl(B_MONTH1, 0) B_MONTH1, nvl(BYTD, 0) BYTD,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_st_dev\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ST_DEV,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select bm_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_sharp_ratio\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) SHARP_RATIO,  decode(decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE),\n              'BenchMark',\n              (select BM_ALPHA \n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date),\n              (select fund_alpha\n                 from pre_fund_performence_calc f\n                where f.fndid = fp.fndid\n                  and f.trans_date = fp.trans_date)) ALPHA \n  from fund_performence fp \n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";
/*      */     
/* 1210 */     return jdbcTemplate.queryForList(fperformance_sql);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Map<String, Object>> getPerformance2(FundDefinitionReportModel fundDef, String transDate, JdbcTemplate jdbcTemplate) {
/* 1216 */     String fperformance_sql1 = "select B_MONTH3, B_MONTH6, B_YEAR1, B_YEAR3, B_YEAR5, B_SINCE, decode(PERFORMENCE_TYPE, 'Fund', descp, PERFORMENCE_TYPE) descp \n  from fund_performence\n where FNDID = '" + fundDef.getFundCode() + "'\n   and trans_date = to_date('" + transDate + "' , 'dd/MM/yyyy') order by descp ";
/*      */ 
/*      */     
/* 1219 */     return jdbcTemplate.queryForList(fperformance_sql1);
/*      */   }
/*      */ 
/*      */   
/*      */   public MainModel ExecuteFmrProcess(String transDate, String fType, JdbcTemplate jdbcTemplate) {
/* 1224 */     MainModel obj = new MainModel();
/*      */     try {
/* 1226 */       SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
/* 1227 */       Date fromDate = df.parse(transDate);
/* 1228 */       Date sqlDate = new Date(fromDate.getTime());
/* 1229 */       SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(jdbcTemplate)).withProcedureName("FMR_EXECUTION_PROCESS");
/* 1230 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("P_DATE1", transDate).addValue("fund_cat", Integer.valueOf(Integer.parseInt(fType)));
/* 1231 */       Map<String, Object> out = jdbcCall.execute((SqlParameterSource)mapSqlParameterSource);
/* 1232 */       if (out.get("RESULT") != null) {
/* 1233 */         obj.setResult("Operation Successfull");
/*      */       } else {
/* 1235 */         obj.setResult("Operation Failed");
/*      */       } 
/* 1237 */     } catch (Exception ex) {
/* 1238 */       obj.setResult("Operation Failed");
/* 1239 */       Logger.getLogger(com.ams.utility.ReportDbUtil.class.getName()).log(Level.SEVERE, (String)null, ex);
/*      */     } 
/* 1241 */     return obj;
/*      */   }
/*      */ 
/*      */   
/*      */   public MainModel ExecuteFmrPreCalc(String pcFromDateSearch, String pcToDateSearch, String fType, String pctransType, JdbcTemplate jdbcTemplate) {
/* 1246 */     MainModel obj = new MainModel();
/*      */     try {
/* 1248 */       SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
/* 1249 */       Date fromDate = df.parse(pcFromDateSearch);
/* 1250 */       Date fsqlDate = new Date(fromDate.getTime());
/* 1251 */       Date tDate = df.parse(pcToDateSearch);
/* 1252 */       Date tsqlDate = new Date(tDate.getTime());
/* 1253 */       SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(jdbcTemplate)).withProcedureName("fmr_pre_calc");
/* 1254 */       MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("p_StartDate", pcFromDateSearch).addValue("p_EndDate", pcToDateSearch).addValue("fund_cat", Integer.valueOf(Integer.parseInt(fType))).addValue("process_type", pctransType);
/* 1255 */       Map<String, Object> out = jdbcCall.execute((SqlParameterSource)mapSqlParameterSource);
/* 1256 */       if (out.get("RESULT") != null && out.get("RESULT").toString().equals("1")) {
/* 1257 */         obj.setResult("Operation Successfull");
/*      */       } else {
/* 1259 */         obj.setResult("Operation Failed");
/*      */       } 
/* 1261 */     } catch (Exception ex) {
/* 1262 */       obj.setResult("Operation Failed");
/* 1263 */       Logger.getLogger(com.ams.utility.ReportDbUtil.class.getName()).log(Level.SEVERE, (String)null, ex);
/*      */     } 
/* 1265 */     return obj;
/*      */   }
/*      */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\ReportDbUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */