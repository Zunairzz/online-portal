package com.ams.controller;

import com.ams.common.ResponseCodes;
import com.ams.dao.CreditRattingOfBanksRowMapper;
import com.ams.dao.FmrFundBasicInfoMapper;
import com.ams.dao.FmrNonComplaintsInvestRowMapper;
import com.ams.dao.FmrNoncompliantMapper;
import com.ams.dao.FundDefinitionRowMapper;
import com.ams.mapper.CommentaryDefinitionRowMapper;
import com.ams.mapper.EconomySummaryDefinitionRowMapper;
import com.ams.model.CreditRatingOfBanks;
import com.ams.model.FmrFundBasicInfo;
import com.ams.model.FmrNonComplaintsInvest;
import com.ams.model.FundDefinition;
import com.ams.model.FundManageModel;
import com.ams.model.MainModel;
import com.ams.model.fmr.FmrEconomyDto;
import com.ams.model.fmr.FmrFundPerformance;
import com.ams.model.fmr.FmrNoncompliant;
import com.ams.utility.ReportDbUtil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
@RequestMapping({"/fmr/fmr"})
public class FmrController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Object> list() {
        return null;
    }


    @GetMapping({"/generateReport"})
    public MainModel get(@RequestParam(value = "transDate", required = false) String transDate, @RequestParam(value = "fType", required = false) String fType) {
        ReportDbUtil r = new ReportDbUtil();
        MainModel resp = r.ExecuteFmrProcess(transDate, fType, this.jdbcTemplate);
        return resp;
    }

    @PostMapping(path = {"/getBenchmarkData"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getBenchmarkData(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select fund_name fund, to_char(price_date , 'dd/MM/yyyy') price_date, benchmar benchmark \n  from fmr_benchmark b, fund f \n where b.fund_code = f.fund_code \n   and price_date >= to_date('" + model.getFromDate() + "', 'dd/MM/yyyy')\n   and price_date <= to_date('" + model.getToDate() + "', 'dd/MM/yyyy')  ";
            if (model.getFundCode() != null && !model.getFundCode().equals("ALL")) {
                qry = qry + " and f.fund_code = '" + model.getFundCode() + "' ";
            }
            qry = qry + " order by price_date ";
            data = this.jdbcTemplate.queryForList(qry);

        } catch (Exception e) {
            System.out.println(e.toString());
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/getBmStdData"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getBmStdData(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select f.fund_name fndid,\n       to_char(trans_date, 'dd/MM/yyyy') trans_date,\n       fund_st_dev,\n       fund_sharp_ratio,\n       fund_alpha,\n       bm_st_dev,\n       bm_sharp_ratio\n  from pre_fund_performence_calc b, fund f\n where b.fndid = f.fund_code  and trans_date >= to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') and trans_date <= to_date('" + model.getToDate() + "', 'dd/MM/yyyy') \n";
            if (model.getFundCode() != null && !model.getFundCode().equals("ALL")) {
                qry = qry + " and b.fndid = '" + model.getFundCode() + "' ";
            }
            qry = qry + " order by trans_date ";
            data = this.jdbcTemplate.queryForList(qry);

        } catch (Exception e) {
            System.out.println(e.toString());
            return data;
        }
        return data;
    }


    @GetMapping({"/fmrPreCalc"})
    public MainModel fmrPreCalc(@RequestParam(value = "pcFromDateSearch", required = false) String pcFromDateSearch, @RequestParam(value = "pcToDateSearch", required = false) String pcToDateSearch, @RequestParam(value = "fType", required = false) String fType, @RequestParam(value = "pctransType", required = false) String pctransType) {
        ReportDbUtil r = new ReportDbUtil();

        MainModel resp = r.ExecuteFmrPreCalc(pcFromDateSearch, pcToDateSearch, fType, pctransType, this.jdbcTemplate);
        return resp;
    }


    @PostMapping(path = {"/getFrmDefinition"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition getFmrDefinition(@RequestBody FundDefinition input) {
        FundDefinition fundDef = getFrmDefinitionImpl(input);
        return fundDef;
    }

    @PostMapping(path = {"/saveFundDefinition"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object saveFundDefinition(@RequestBody FundDefinition input) {
        FundDefinition fundDef = saveFrmDefinitionImpl(input);
        return fundDef;
    }

    @PostMapping(path = {"/updateFundDefinition"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object updateFundDefinition(@RequestBody FundDefinition input) {
        FundDefinition fundDef = updateFundDefinitionImpl(input);
        return fundDef;
    }

    private FundDefinition getFrmDefinitionImpl(FundDefinition input) {
        FundDefinition fmr = new FundDefinition();
        try {

            String sql = "select to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, fmr.members, f.fund_name, commentary, objective from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code = ? and fmr.trans_date = to_date(?, 'dd/MM/yyyy')";

            fmr = (FundDefinition) this.jdbcTemplate.queryForObject(sql, new Object[]{input.fundCode, input.transDate}, (RowMapper) new FundDefinitionRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    private FundDefinition updateFundDefinitionImpl(FundDefinition fundDef) {
        FundDefinition fmr = new FundDefinition();
        try {

            String SQL = "update fmr_def set members = ? , commentary = ?, objective = ? where fund_code = ? and trans_date = to_date(?, 'dd/MM/yyyy')";

            int resp = this.jdbcTemplate.update(SQL, new Object[]{fundDef.getMembers(), fundDef.getCommentary(), fundDef.getObjective(), fundDef.getFundCode(), fundDef.getTransDate()});

            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }


        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fmr;
    }


    private FundDefinition saveFrmDefinitionImpl(FundDefinition fundDef) {
        FundDefinition fmr = new FundDefinition();
        try {

            String sql = "INSERT \nWHEN not exists(SELECT 1 FROM fmr_def WHERE fund_code = ? and trans_date = last_day(to_date(to_char(add_months(get_system_date,-1),'YYYYMM'),'YYYYMM'))) \nTHEN\nINTO fmr_def (fund_code, members, commentary, objective, trans_date) \nSELECT ?, ?, ?, ?, last_day(to_date(to_char(add_months(get_system_date,-1),'YYYYMM'),'YYYYMM')) FROM DUAL";


            int resp = this.jdbcTemplate.update(sql, new Object[]{fundDef.getFundCode(), fundDef.getFundCode(), fundDef.getMembers(), fundDef.getCommentary(), fundDef.getObjective()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
                System.out.println("Record inserted with ID = " + fundDef.getFundCode());
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    @PostMapping(path = {"/getFundBasicInfoList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundBasicInfoList() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select id, to_char(transdate, 'dd/MM/yyyy')  as trans_date, \n       f.fund_name,\n       fb.fund_type,\n       fb.category,\n       fb.benchmark,\n       fb.dealing_days,\n       fb.management_fee\n  from fmr_fund_basic_info fb, fund f\n where fb.fund_code = f.fund_code\n order by id desc");


        } catch (Exception e) {
            return data;
        }
        return data;
    }


    @PostMapping(path = {"/getFundBasicInformation"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrFundBasicInfo getFundBasicInformation(@RequestBody FmrFundBasicInfo input) {
        FmrFundBasicInfo fmr = new FmrFundBasicInfo();
        try {

            String sql = "select TRANSDATE, ID,fund_code, FUND_TYPE,CATEGORY,LAUNCHDATE,NETASSETS,NETASSETEXFOF,NAV,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS,ter, nav nav1 from fmr_fund_basic_info t \nwhere id = ? ";


            fmr = (FmrFundBasicInfo) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id}, (RowMapper) new FmrFundBasicInfoMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }

    @PostMapping(path = {"/saveFundPerformance"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrFundPerformance saveFundPerformance(@RequestBody FmrFundPerformance input) {
        List<Map<String, Object>> data = null;
        FmrFundPerformance fmr = new FmrFundPerformance();
        try {
            if (input.getFundCode() != null) {

                data = this.jdbcTemplate.queryForList("select 1 from fund_performence where fndid = '" + input.getFundCode() + "' and trans_date = to_date('" + input.getTransDate() + "' , 'dd/MM/yyyy') ");
            }
            if (data != null && data.size() > 0) {


                String qry = "update fund_performence f set  f.st_dev = ?, f.sharp_ratio = ?, f.alpha = ?  \nwhere fndid = '" + input.getFundCode() + "' and trans_date = to_date('" + input.getTransDate() + "' , 'dd/MM/yyyy') and PERFORMENCE_TYPE='Fund' ";

                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getStDev(), input.getSharpRatio(), input.getAlpha()});

                qry = "update fund_performence f set  f.st_dev = ?, f.sharp_ratio = ?, f.alpha = ?  \nwhere fndid = '" + input.getFundCode() + "' and trans_date = to_date('" + input.getTransDate() + "' , 'dd/MM/yyyy') and PERFORMENCE_TYPE='BenchMark' ";

                resp = this.jdbcTemplate.update(qry, new Object[]{input.getBstDev(), input.getBsharpRatio(), input.getBalpha()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            }

        } catch (Exception e) {
            return fmr;
        }
        return fmr;
    }


    @PostMapping(path = {"/saveFundBasicInformation"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrFundBasicInfo saveFundBasicInformation(@RequestBody FmrFundBasicInfo input) {
        List<Map<String, Object>> data = null;
        FmrFundBasicInfo fmr = new FmrFundBasicInfo();
        try {
            if (input.id != null) {

                data = this.jdbcTemplate.queryForList("select 1 from fmr_fund_basic_info where id = '" + input.id + "'");
            }
            if (data != null && data.size() > 0) {

                String qry = "update fmr_fund_basic_info set fund_type = ?, category = ?, benchmark = ?, dealing_days = ?, cutt_off_time = ?, pricing_mechanism = ?, management_fee = ?, \nfront_end_load = ?, trustee = ?, auditor = ?, asset_manager_rating = ?, risk_prof_fund = ?, perform_ranking = ?, fund_manger = ?, listing = ?, comments = ?, ter = ?, nav = ? \nwhere id = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getFund_type(), input.getCategory(), input.getBenchmark(), input.getDealing_days(), input
                        .getCutt_off_time(), input.getPricing_mechanism(), input.getManagement_fee(), input.getFront_end_load(), input.getTrustee(), input.getAuditor(), input.getAsset_manager_rating(), input.getRisk_prof_fund(), input.getPerform_ranking(), input
                        .getFund_manger(), input.getListing(), input.getComments(), input.getFundTer(), input.getNav(), input.getId()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else {
                int resp = 0;

                if (isValidString(input.getFund_code()) && isValidString(input.getBenchmark()) && isValidString(input.getFund_type())) {

                    String qry = "INSERT INTO fmr_fund_basic_info (id , transdate, fund_code, fund_type, category, benchmark, dealing_days, cutt_off_time, pricing_mechanism, management_fee, \nfront_end_load, trustee, auditor, asset_manager_rating, risk_prof_fund, perform_ranking, fund_manger, listing, comments, ter) \nSELECT (select nvl(max(id)+1,1) from fmr_fund_basic_info) , last_day(to_date(to_char(add_months(get_system_date,-1),'YYYYMM'),'YYYYMM')) ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? FROM DUAL ";

                    resp = this.jdbcTemplate.update(qry, new Object[]{input.getFund_code(), input.getFund_type(), input.getCategory(), input.getBenchmark(), input.getDealing_days(), input
                            .getCutt_off_time(), input.getPricing_mechanism(), input.getManagement_fee(), input.getFront_end_load(), input.getTrustee(), input.getAuditor(), input.getAsset_manager_rating(), input.getRisk_prof_fund(), input.getPerform_ranking(), input
                            .getFund_manger(), input.getListing(), input.getComments(), input.getFundTer()});
                    if (resp == 1) {
                        fmr.setResponse_code(ResponseCodes.SUCCESS);
                    } else {
                        fmr.setResponse_code(ResponseCodes.FAILURE);
                    }
                }
            }

        } catch (Exception e) {
            return fmr;
        }
        return fmr;
    }


    @PostMapping(path = {"/getFundDefList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundDefList() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select id,\n       to_char(trans_date, 'dd/MM/yyyy') as trans_date,\n       f.fund_name,\n       SUBSTR(members ,0, 20) as members,\n       SUBSTR(objective ,0, 20) as objective,\n       SUBSTR(commentary ,0, 20) as commentary\n  from fmr_def fb, fund f\n where fb.fund_code = f.fund_code and fb.post = '1' \n order by id desc");
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/getFmrCommMemberList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFmrCommMemberList() {
        List<Map<String, Object>> data = null;
        try {
            data = this.jdbcTemplate.queryForList("select id, member from fmr_comm_member order by id");

        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/getFundPerformanceList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundPerformanceList(@RequestBody FundDefinition input) {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select f.id, to_char(trans_date , 'dd/MM/yyyy') trans_date, fund_name, nvl(f.st_dev, 0) st_dev, nvl(f.sharp_ratio, 0) sharp_ratio, nvl(f.alpha, 0) alpha, performence_type \n  from fund_performence f, fund ff\n where fndid = '" + input

                    .getFundCode() + "' and trans_date = to_date('" + input.getTransDate() + "' , 'dd/MM/yyyy') \n   and fndid = fund_code\n order by performence_type");

        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/getFundNonComplaintList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundNonComplaintList() {
        List<Map<String, Object>> data = null;
        try {

            String query = "select nci.fund_code,\n       to_char(transdate, 'dd/MM/yyyy') as transdate,\n       non_complaint_investment,\n       type_of_investment, \n       exposure_limit, \n       percent_of_net_asset,   \n       percent_of_total_asset, \n       excess_exposure_per_of_net, \n       excess_exposure_per_of_total    \n  from fmr_non_complaints_invest nci, fund f where nci.fund_code = f.fund_code and f.active = 1 and f.post = 1 ";

            data = this.jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/getFundDefInformation"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition getFundDefInformation(@RequestBody FundDefinition input) {
        FundDefinition fmr = new FundDefinition();
        try {

            String sql = "select id,\n       to_char(trans_date, 'dd/MM/yyyy') as trans_date,\n       f.fund_code, f.fund_name,\n       members, '' member, \n       objective,\n       commentary, WEIGHTED_AVG, LEVERAGE \n  from fmr_def fb, fund f \n where fb.fund_code = f.fund_code and id = ? ";

            fmr = (FundDefinition) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id}, (RowMapper) new FundDefinitionRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }

    @PostMapping(path = {"/getBankCreditRating"}, consumes = {"application/json"}, produces = {"application/json"})
    public CreditRatingOfBanks getBankCreditRating(@RequestBody CreditRatingOfBanks input) {
        CreditRatingOfBanks crb = new CreditRatingOfBanks();
        try {

            String sql = "select cr.gl_bank_code,\n       cr.gl_bank_name, cr.rating\n  from credit_rating_of_banks cr";
            crb = (CreditRatingOfBanks) this.jdbcTemplate.queryForObject(sql, new Object[]{input
                    .getGlBankCode()}, (RowMapper) new CreditRattingOfBanksRowMapper());
            crb.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            crb.setResponse_code(ResponseCodes.FAILURE);
        }
        return crb;
    }

    @PostMapping(path = {"/getFmrMemberById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition getFmrMemberById(@RequestBody FundDefinition input) {
        FundDefinition fmr = new FundDefinition();
        try {

            String sql = "select id,\n       '' trans_date,\n       '' fund_code, '' fund_name, \n       '' members, member, \n       '' objective,\n       '' commentary \n  from fmr_comm_member fb \n where id = ? ";
            fmr = (FundDefinition) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id}, (RowMapper) new FundDefinitionRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    @PostMapping(path = {"/deleteFundDef"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object deleteFundDef(@RequestBody FundDefinition fundDef) {
        FundDefinition fmr = new FundDefinition();
        try {
            String sql = "delete from fmr_def where id = '" + fundDef.getId() + "' ";
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

    @PostMapping(path = {"/removeMemberById"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object removeMemberById(@RequestBody FundDefinition fundDef) {
        FundDefinition fmr = new FundDefinition();
        try {
            String sql = "delete from fmr_comm_member where id = '" + fundDef.getId() + "' ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fundDef;
    }


    @PostMapping(path = {"/saveFundDef"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition saveFundDef(@RequestBody FundDefinition input) {
        List<Map<String, Object>> data = null;
        FundDefinition fmr = new FundDefinition();
        try {
            if (input.id != null) {
                data = this.jdbcTemplate.queryForList("select 1 from fmr_def where id = '" + input.id + "'");
            }
            if (data != null && data.size() > 0) {


                String qry = "update fmr_def\n   set fund_code = ?,\n   members = ?,\n   objective = ?,\n   commentary = ?, WEIGHTED_AVG = ?, LEVERAGE = ? \n where id = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getFundCode(), input.getMemberString(), input.getObjective(), input.getCommentary(), input
                        .getWeightAvg(), input.getLeverage(), input.getId()});

                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else {
                int resp = 0;
                if (isValidString(input.getFundCode())) {


                    String qry = "INSERT INTO fmr_def (id , TRANS_DATE, fund_code, members, objective, commentary , left_header, right_header) \nSELECT (select nvl(max(id)+1,1) from fmr_def) , last_day(add_months(get_system_date, -1)) ,?,?,?,?, '" + getLeftHeader(input.getFundCode()) + "', '" + getRightHeader(input.getFundCode()) + "' FROM DUAL ";

                    resp = this.jdbcTemplate.update(qry, new Object[]{input.getFundCode(), input.getMembers(), input.getObjective(), input.getCommentary()});
                    if (resp == 1) {
                        fmr.setResponse_code(ResponseCodes.SUCCESS);
                    } else {
                        fmr.setResponse_code(ResponseCodes.FAILURE);
                    }
                }
            }

        } catch (Exception e) {
            return fmr;
        }

        return fmr;
    }

    @PostMapping(path = {"/eoiReportXML"}, consumes = {"application/json"}, produces = {"application/json"})
    private List<Map<String, Object>> eoiReportXML(@RequestBody MainModel model) {
        System.out.println("----------- getReport ---------------");


        String fassetallocation_sql = "select * from eoi_trans_dividend t where t.dec_id = '" + model.getFundCode() + "'";
        List<Map<String, Object>> eoiReportList = this.jdbcTemplate.queryForList(fassetallocation_sql);
        System.out.println("eoiReportList size is :: " + eoiReportList.size());


        return eoiReportList;
    }

    @PostMapping(path = {"/dividendReport"}, consumes = {"application/json"}, produces = {"application/json"})
    private List<Map<String, Object>> dividendReport(@RequestBody MainModel model) {
        System.out.println("----------- getReport ---------------");


        String fassetallocation_sql = "select d.folio_number,\n(select ff.fund_name from fund ff where ff.fund_code=d.fund_code)FUNDNAME,\n       ua.title title,\n       decode(ua.primary_client,'','CORPORATE','INDIVIDUAL') CLIENT_TYPE,\n       d.closing_balance,\n       d.gross_dividend,\n       d.wht,\n       (D.GROSS_DIVIDEND-D.WHT) NetDividend,\n       DECODE (d.reinvest_additional,'R',d.divid_units) DividUnits,\n        DECODE (d.reinvest_additional,'A',d.divid_units) EOIUnits,\n       DECODE(D.DIVID_UNITS, '', (d.gross_dividend - d.wht)) CASHDIVIDEND,\n       DECODE (d.reinvest_additional,'A','EOI','DIV') REINVEST,\n       d.dec_id\n  from dividend d, unit_account ua\nwhere d.folio_number = ua.folio_number\n  and d.dec_id = '" + model.getFundCode() + "' order by d.dec_id, d.reinvest_additional\n";

        List<Map<String, Object>> eoiReportList = this.jdbcTemplate.queryForList(fassetallocation_sql);
        System.out.println("eoiReportList size is :: " + eoiReportList.size());

        return eoiReportList;
    }

    @PostMapping(path = {"/eoiTransDetReport"}, consumes = {"application/json"}, produces = {"application/json"})
    private List<Map<String, Object>> eoiTransDetReport(@RequestBody MainModel model) {
        System.out.println("----------- getReport ---------------");


        String eoi_trans_det_sql = "select t.*, f.fund_name fund_name from eoi_trans_det t, fund f where f.fund_code=t.fund_code and t.dec_id = '" + model.getFundCode() + "'";
        List<Map<String, Object>> eoiTransDetList = this.jdbcTemplate.queryForList(eoi_trans_det_sql);
        System.out.println("eoiTransDetReportList size is :: " + eoiTransDetList.size());

        return eoiTransDetList;
    }


    @PostMapping(path = {"/saveNonComplaintInvestment"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrNonComplaintsInvest saveNonComplaintInvestment(@RequestBody FmrNonComplaintsInvest input) {
        List<Map<String, Object>> data = null;
        FmrNonComplaintsInvest fmr = new FmrNonComplaintsInvest();
        try {
            if (input.fundCode != null) {

                data = this.jdbcTemplate.queryForList("select 1 from fmr_non_complaints_invest where fund_code = '" + input.getFundCode() + "' and transdate = to_date('" + input.getTransDate() + "', 'dd/MM/yyyy') and non_complaint_investment = '" + input.getNonComplaintInvestment() + "' and type_of_investment = '" + input.getTypeOfInvestment() + "'");
            }
            if (data != null && data.size() > 0) {


                String qry = "update fmr_non_complaints_invest\n   set exposure_limit = ?,\n   percent_of_net_asset = ?,\n   percent_of_total_asset = ?,\n   excess_exposure_per_of_net = ?, excess_exposure_per_of_total = ? \n where fund_code = ? and transdate = to_date(?, 'dd/MM/yyyy') and non_complaint_investment = ? and type_of_investment = ?";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getExposureLimit(), input.getPercentOfNetAsset(), input.getPercentOfTotalAsset(), input.getExcessExposurePerOfNet(), input
                        .getExcessExposurePerOfTotal(), input.getFundCode(), input.getTransDate(), input.getNonComplaintInvestment(), input.getTypeOfInvestment()});

                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else {
                int resp = 0;
                if (isValidString(input.getFundCode())) {

                    String qry = "INSERT INTO fmr_non_complaints_invest (fund_code , transdate, non_complaint_investment, type_of_investment, exposure_limit, percent_of_net_asset , percent_of_total_asset, excess_exposure_per_of_net, excess_exposure_per_of_total) \nSELECT ? , to_date(?, 'dd/MM/yyyy') ,?,?,?,?, ?, ?, ? FROM DUAL ";


                    resp = this.jdbcTemplate.update(qry, new Object[]{input.getFundCode(), input.getTransDate(), input.getNonComplaintInvestment(), input.getTypeOfInvestment(),
                            Integer.valueOf(Integer.parseInt(input.getExposureLimit())), Double.valueOf(Double.parseDouble(input.getPercentOfNetAsset())), Double.valueOf(Double.parseDouble(input.getPercentOfTotalAsset())),
                            Double.valueOf(Double.parseDouble(input.getExcessExposurePerOfNet())), Double.valueOf(Double.parseDouble(input.getExcessExposurePerOfTotal()))});
                    if (resp == 1) {
                        fmr.setResponse_code(ResponseCodes.SUCCESS);
                    } else {
                        fmr.setResponse_code(ResponseCodes.FAILURE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return fmr;
        }

        return fmr;
    }


    @PostMapping(path = {"/deleteNonComplaintInvestment"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object deleteNonComplaintInvestment(@RequestBody FmrNonComplaintsInvest input) {
        FmrNonComplaintsInvest cr = new FmrNonComplaintsInvest();
        try {

            String sql = "delete from fmr_non_complaints_invest where fund_code = '" + input.getFundCode() + "' and transdate = to_date('" + input.getTransDate() + "', 'dd/MM/yyyy') and non_complaint_investment = '" + input.getNonComplaintInvestment() + "' and type_of_investment = '" + input.getTypeOfInvestment() + "'";

            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                cr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                cr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            cr.setResponse_code(ResponseCodes.FAILURE);
        }

        return cr;
    }


    @PostMapping(path = {"/emlaakUploader"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object emlaakUploader(@RequestBody FmrNonComplaintsInvest input) {
        List<Map<String, Object>> data = null;
        FmrNonComplaintsInvest cr = new FmrNonComplaintsInvest();


        try {

            String sql = "select\n    f.fund_name, \n    c.nic_passport,\n    ua.folio_number,\n    Sale_id Transction_id,\n    Decode (c.client_type,'01','Normal','Sahulat') Acount_Type,\n    plan_name as unit_type,\n    NULL as CLASS,\n    amount,\n    OFFER_PRICE nav,\n    QUANTITY unit,\n    NULL PAYMENT_ID,\n    'SALE'  transaction_Type,\n    SALE_DATE transaction_date\nfrom \n fund f, \n  unit_account ua, client c, unit_plan up,\n  unit_sale us,PAYMENT_DETAIL pd   \nwhere ua.primary_client= c.client_code\nand ua.folio_number = '25498'\nand ua.folio_number = us.folio_number\nand up.plan_id = ua.plan_id\nand f.fund_code = us.fund_code\nand us.PAYMENT_ID = pd.PAYMENT_ID\nand SALE_DATE BETWEEN to_date('" + input.getTransDate() + "', 'dd/MM/yyyy') and to_date('" + input.getFundCode() + "', 'dd/MM/yyyy')";
            data = this.jdbcTemplate.queryForList(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            cr.setResponse_code(ResponseCodes.FAILURE);
        }

        return data;
    }


    @PostMapping(path = {"/getCreditRatingList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getCreditRatingList() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select cr.gl_bank_code,\n       cr.gl_bank_name,\n       cr.rating\n  from credit_rating_of_banks cr\n");


        } catch (Exception e) {
            return data;
        }
        return data;
    }


    @PostMapping(path = {"/getRatingList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getRatingList(@RequestBody FundManageModel model) {
        List<Map<String, Object>> data = null;
        try {
            System.out.println("getRatingList execute ....");

            data = this.jdbcTemplate.queryForList("SELECT quality_rating_description from quality_rating where post = '1' order by quality_rating_code ");
        } catch (Exception e) {
            System.out.println(data);

            return data;
        }
        return data;
    }

    @PostMapping(path = {"/saveBankCreditRating"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition saveBankCreditRating(@RequestBody CreditRatingOfBanks input) {
        List<Map<String, Object>> data = null;
        FundDefinition fmr = new FundDefinition();
        try {
            if (input.getGlBankCode() != null) {
                data = this.jdbcTemplate.queryForList("select 1 from credit_rating_of_banks where gl_bank_code = '" + input.getGlBankCode() + "'");
            }

            if (data != null && data.size() > 0) {


                String qry = "update credit_rating_of_banks\n   set gl_bank_name = ?,\n   rating = ?\n where gl_bank_code = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getGlBankName(), input.getRating(), input.getGlBankCode()});

                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else {

                int resp = 0;
                if (isValidString(input.getGlBankCode())) {


                    String qry = "INSERT INTO credit_rating_of_banks (gl_bank_code , gl_bank_name, rating) \nSELECT ?,?,? from dual";


                    resp = this.jdbcTemplate.update(qry, new Object[]{input.getGlBankCode(), input.getGlBankName(), input.getRating()});

                    if (resp == 1) {
                        fmr.setResponse_code(ResponseCodes.SUCCESS);
                    } else {
                        fmr.setResponse_code(ResponseCodes.FAILURE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return fmr;
        }

        return fmr;
    }


    @PostMapping(path = {"/deleteCreditRating"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object deleteCreditRating(@RequestBody CreditRatingOfBanks input) {
        CreditRatingOfBanks cr = new CreditRatingOfBanks();
        try {
            String sql = "delete from credit_rating_of_banks where gl_bank_code = '" + input.getGlBankCode() + "' ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                cr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                cr.setResponse_code(ResponseCodes.FAILURE);
            }

        } catch (Exception ex) {
            cr.setResponse_code(ResponseCodes.FAILURE);
        }

        return cr;
    }

    @PostMapping(path = {"/getCreditRatingInformation"}, consumes = {"application/json"}, produces = {"application/json"})
    public CreditRatingOfBanks getCreditRatingInformation(@RequestBody CreditRatingOfBanks input) {
        CreditRatingOfBanks fmr = new CreditRatingOfBanks();
        try {

            String sql = "select cr.gl_bank_code,\n       cr.gl_bank_name, cr.rating\n  from credit_rating_of_banks cr \n where cr.gl_bank_code = ? ";


            fmr = (CreditRatingOfBanks) this.jdbcTemplate.queryForObject(sql, new Object[]{input
                    .getGlBankCode()}, (RowMapper) new CreditRattingOfBanksRowMapper());
            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
            ex.printStackTrace();
        }
        return fmr;
    }


    @PostMapping(path = {"/saveMemberById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FundDefinition saveMemberById(@RequestBody FundDefinition input) {
        List<Map<String, Object>> data = null;
        FundDefinition fmr = new FundDefinition();
        try {
            if (input.id != null) {

                data = this.jdbcTemplate.queryForList("select 1 from fmr_comm_member where id = '" + input.id + "'");
            }
            if (data != null && data.size() > 0) {
                String qry = "update fmr_comm_member \n   set member = ?  where id = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getMemberString(), input.getId()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else {
                int resp = 0;
                String qry = "INSERT INTO fmr_comm_member(id , member) values(?,?) \n";
                resp = this.jdbcTemplate.update(qry, new Object[]{input.getId(), input.getMemberString()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            }

        } catch (Exception e) {
            return fmr;
        }
        return fmr;
    }

    private boolean isValidString(String str) {
        return (str != null && !str.equals(""));
    }

    private String getLeftHeader(String fundCode) {
        String header = "";
        if (fundCode.equals("00001")) {
            header = "AIF_left_header.png";

        } else if (fundCode.equals("00003")) {
            header = "Cash_left_header.png";

        } else if (fundCode.equals("00004")) {
            header = "AIIF_left_header.png";

        } else if (fundCode.equals("00005")) {
            header = "GSF_left_header.png";
        }
        return header;
    }

    private String getRightHeader(String fundCode) {
        String header = "";
        if (fundCode.equals("00001")) {
            header = "AIF_right_header.png";

        } else if (fundCode.equals("00003")) {
            header = "Cash_right_header.png";

        } else if (fundCode.equals("00004")) {
            header = "AIIF_right_header.png";

        } else if (fundCode.equals("00005")) {
            header = "GSF_right_header.png";
        }
        return header;
    }


    @PostMapping(path = {"/getNoncompliantDetails"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrNoncompliant getNoncompliantDetails(@RequestBody FmrFundBasicInfo input) {
        FmrNoncompliant fmr = new FmrNoncompliant();
        try {

            String sql = "select fund_code, \n       to_char(transdate , 'dd/MM/yyyy') transdate ,  \n       non_complaint_investment,   \n       type_of_investment, \n       exposure_limit, \n       percent_of_net_asset,   \n       percent_of_total_asset, \n       excess_exposure_per_of_net, \n       excess_exposure_per_of_total    \n  from fmr_non_complaints_invest   \n where non_complaint_investment = 'DHCL'   \n   and transdate = '28-feb-2019'   ";


            fmr = (FmrNoncompliant) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new FmrNoncompliantMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    @PostMapping(path = {"/getNoncompliantInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrNonComplaintsInvest getNoncompliantInfo(@RequestBody FmrNonComplaintsInvest input) {
        FmrNonComplaintsInvest fmr = new FmrNonComplaintsInvest();
        try {

            String sql = "select nci.fund_code, \n       to_char(transdate , 'dd/MM/yyyy') transdate ,  \n       non_complaint_investment,   \n       type_of_investment, \n       exposure_limit, \n       percent_of_net_asset,   \n       percent_of_total_asset, \n       excess_exposure_per_of_net, \n       excess_exposure_per_of_total    \n  from fmr_non_complaints_invest nci, fund f where nci.fund_code = f.fund_code and f.active = 1 and f.post = 1 ";


            if (input.getFundCode() != null && input.getFundCode().length() > 0)
                sql = sql + " and nci.fund_code = '" + input.getFundCode() + "' ";
            if (input.getTypeOfInvestment() != null && input.getTypeOfInvestment().length() > 0)
                sql = sql + " and type_of_investment = '" + input.getTypeOfInvestment() + "' ";
            if (input.getTransDate() != null && input.getTransDate().length() > 0)
                sql = sql + "   and transdate = to_date('" + input.getTransDate() + "', 'dd/MM/yyyy')";
            if (input.getNonComplaintInvestment() != null && input.getNonComplaintInvestment().length() > 0)
                sql = sql + "   and non_complaint_investment = '" + input.getNonComplaintInvestment() + "' ";

            fmr = (FmrNonComplaintsInvest) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new FmrNonComplaintsInvestRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    @PostMapping(path = {"/getCommentaryDef"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getCommentaryDef() {
        List<Map<String, Object>> data = null;
        try {
            System.out.println("select comm1, comm2, comm3 from fmr_economy_def where type = 'PARA_CONTENT'");

            data = this.jdbcTemplate.queryForList("select comm1, comm2, comm3 from fmr_economy_def where type = 'PARA_CONTENT'");

        } catch (Exception e) {
            return data;
        }
        return data;
    }


    @PostMapping(path = {"/getIslamicCommentaryDef"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getIslamicCommentaryDef() {
        List<Map<String, Object>> data = null;
        try {
            System.out.println("select comm1, comm2, comm3 from fmr_economy_def where type = 'PARA_CONTENT_ISL'");

            data = this.jdbcTemplate.queryForList("select comm1, comm2, comm3 from fmr_economy_def where type = 'PARA_CONTENT_ISL'");

        } catch (Exception e) {
            return data;
        }
        return data;
    }


    @PostMapping(path = {"/updateCommentaryDefinition"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto updateCommentaryDefinition(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {

            String qry = "update fmr_economy_def\n   set comm1 = ?,\n   comm2 = ?,\n   comm3 = ?  where type = 'PARA_CONTENT' \n";


            int resp = this.jdbcTemplate.update(qry, new Object[]{input.getComm1(), input.getComm2(), input.getComm3()});


            qry = "update fmr_economy_def\n   set comm1 = ?,\n   comm2 = ?,\n   comm3 = ?  where type = 'PARA_HEADING' \n";


            resp = this.jdbcTemplate.update(qry, new Object[]{input.getHead1(), input.getHead2(), input.getHead3()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }


        } catch (Exception e) {
            return fmr;
        }

        return fmr;
    }


    @PostMapping(path = {"/updateIslamicCommentaryDefinition"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto updateIslamicCommentaryDefinition(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {

            String qry = "update fmr_economy_def\n   set comm1 = ?,\n   comm2 = ?,\n   comm3 = ?  where type = 'PARA_CONTENT_ISL' \n";


            int resp = this.jdbcTemplate.update(qry, new Object[]{input.getComm1(), input.getComm2(), input.getComm3()});


            qry = "update fmr_economy_def\n   set comm1 = ?,\n   comm2 = ?,\n   comm3 = ?  where type = 'PARA_HEADING_ISL' \n";


            resp = this.jdbcTemplate.update(qry, new Object[]{input.getHead1(), input.getHead2(), input.getHead3()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }


        } catch (Exception e) {
            return fmr;
        }

        return fmr;
    }


    @PostMapping(path = {"/getCommentaryDefinitionInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto getCommentaryDefinitionInfo(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "select * from fmr_economy_def where type = 'PARA_HEADING'";

            fmr = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new CommentaryDefinitionRowMapper());


            sql = "select * from fmr_economy_def where type = 'PARA_CONTENT'";

            FmrEconomyDto cont = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new CommentaryDefinitionRowMapper());


            fmr.setComm1(cont.getComm1());
            fmr.setComm2(cont.getComm2());
            fmr.setComm3(cont.getComm3());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }


    @PostMapping(path = {"/getCommentaryDefinitionIslamicInfo"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto getCommentaryDefinitionIslamicInfo(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "select * from fmr_economy_def where type = 'PARA_HEADING_ISL'";

            fmr = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new CommentaryDefinitionRowMapper());


            sql = "select * from fmr_economy_def where type = 'PARA_CONTENT_ISL'";

            FmrEconomyDto cont = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[0], (RowMapper) new CommentaryDefinitionRowMapper());


            fmr.setComm1(cont.getComm1());
            fmr.setComm2(cont.getComm2());
            fmr.setComm3(cont.getComm3());

            fmr.setResponse_code(ResponseCodes.SUCCESS);

        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        1151 return fmr;
    }


    @PostMapping(path = {"/getEconomySummary"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getEconomySummary() {
        List<Map<String, Object>> data = null;
        try {

            data = this.jdbcTemplate.queryForList("select id, description, field_1 field1, field_2 field2, field_3 field3, field_4 field4 from fmr_economy_ECONOMIC_SUMMARY order by id");

        } catch (Exception e) {
        }
        return data;
    }


    @PostMapping(path = {"/saveEconomySummaryById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto saveEconomySummaryById(@RequestBody FmrEconomyDto input) {
        List<Map<String, Object>> data = null;
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            data = this.jdbcTemplate.queryForList("select 1 from fmr_economy_ECONOMIC_SUMMARY where id = '" + input.getId() + "'");
        }
        if (data != null && data.size() > 0) {
            String qry = "update fmr_economy_ECONOMIC_SUMMARY \n   set description = ? , \nfield_1 = ? , \nfield_2 = ? , \nfield_3 = ? , \nfield_4 = ?   where id = ? ";


            int resp = this.jdbcTemplate.update(qry, new Object[]{input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4(), input.getId()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }
        } else if (!input.getId().isEmpty() && !input.getDescription().isEmpty()) {
            int resp = 0;
            String qry = "INSERT INTO fmr_economy_ECONOMIC_SUMMARY(id, description, field_1, field_2, field_3, field_4) values(?,?,?,?,?,?) \n";
            resp = this.jdbcTemplate.update(qry, new Object[]{input.getId(), input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4()});
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }
        }
    } catch(
    Exception e)

    {
        e.printStackTrace();
        return fmr;
    }
       return fmr;
}


    @PostMapping(path = {"/getEconSummaryById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto getEconSummaryById(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "select id,\n       description, field_1, field_2, field_3, field_4, '' field_5, id \n  from fmr_economy_economic_summary fb \n where id = ? ";


            fmr = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id}, (RowMapper) new EconomySummaryDefinitionRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }

    @PostMapping(path = {"/removeEconSummaryById"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object removeEconSummaryById(@RequestBody FmrEconomyDto fundDef) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "delete from fmr_economy_economic_summary where id = '" + fundDef.getId() + "' ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }
        } catch (Exception ex) {
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fundDef;
    }


    @PostMapping(path = {"/getEconomyGovtSecurity"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getEconomyGovtSecurity() {
        List<Map<String, Object>> data = null;

        try {
            data = this.jdbcTemplate.queryForList("select id, description, field_1 field1, field_2 field2, field_3 field3, field_4 field4, field_5 field5 from fmr_economy_table_2 order by id");
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }

        return data;
    }


    @PostMapping(path = {"/saveEconomyGovtSecurityById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto saveEconomyGovtSecurityById(@RequestBody FmrEconomyDto input) {
        List<Map<String, Object>> data = null;
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            if (input.id != null) {
                data = this.jdbcTemplate.queryForList("select 1 from fmr_economy_table_2 where id = '" + input.getId() + "'");
            }
            if (data != null && data.size() > 0) {
                String qry = "update fmr_economy_table_2 \n   set description = ? , \nfield_1 = ? , \nfield_2 = ? , \nfield_3 = ? , \nfield_4 = ?,field_5 = ?   where id = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4(), input.getField5(), input.getId()});
                if (resp == 1) {
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else if (!input.getId().isEmpty() && !input.getDescription().isEmpty()) {
                int resp = 0;
                String qry = "INSERT INTO fmr_economy_table_2(id, description, field_1, field_2, field_3, field_4, field_5) values(?,?,?,?,?,?,?) \n";
                resp = this.jdbcTemplate.update(qry, new Object[]{input.getId(), input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4(), input.getField5()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fmr;
        }
        return fmr;
    }


    @PostMapping(path = {"/getEconGovtSecurityById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto getEconGovtSecurityById(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "select id,\n       description, field_1, field_2, field_3, field_4, field_5, id \n  from fmr_economy_table_2 fb \n where id = ? ";


            fmr = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id}, (RowMapper) new EconomySummaryDefinitionRowMapper());

            fmr.setResponse_code(ResponseCodes.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }

    @PostMapping(path = {"/removeEconGovtSecurityById"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object removeEconGovtSecurityById(@RequestBody FmrEconomyDto fundDef) {
        FmrEconomyDto fmr = new FmrEconomyDto();

        try {
            String sql = "delete from fmr_economy_table_2 where id = '" + fundDef.getId() + "' ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fundDef;
    }


    @PostMapping(path = {"/getEconomyEqMarket"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getEconomyEqMarket(@RequestBody FmrEconomyDto input) {
        List<Map<String, Object>> data = null;

        try {
            String sql = "select id, description, field_1 field1, field_2 field2, field_3 field3, field_4 field4, field_5 field5 from fmr_economy_table_3 where islamic = " + input.getIslamic() + " order by id";
            data = this.jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }

        return data;
    }


    @PostMapping(path = {"/saveEconomyEqMarketById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto saveEconomyEqMarketById(@RequestBody FmrEconomyDto input) {
        List<Map<String, Object>> data = null;
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            if (input.id != null) {
                data = this.jdbcTemplate.queryForList("select 1 from fmr_economy_table_3 where id = '" + input.getId() + "'");
            }
            if (data != null && data.size() > 0) {
                String qry = "update fmr_economy_table_3 \n   set description = ? , \nfield_1 = ? , \nfield_2 = ? , \nfield_3 = ? , \nfield_4 = ? ,field_5 = ?, islamic = ?  where id = ? ";


                int resp = this.jdbcTemplate.update(qry, new Object[]{input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4(), input.getField5(), input.getIslamic(), input.getId()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            } else if (!input.getId().isEmpty() && !input.getDescription().isEmpty()) {
                int resp = 0;
                String qry = "INSERT INTO fmr_economy_table_3(id, description, field_1, field_2, field_3, field_4, field_5,islamic) values(?,?,?,?,?,?,?,?) \n";
                resp = this.jdbcTemplate.update(qry, new Object[]{input.getId(), input.getDescription(), input.getField1(), input.getField2(), input.getField3(), input.getField4(), input.getField5(), input.getIslamic()});
                if (resp == 1) {
                    fmr.setResponse_code(ResponseCodes.SUCCESS);
                } else {
                    fmr.setResponse_code(ResponseCodes.FAILURE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fmr;
        }
        return fmr;
    }


    @PostMapping(path = {"/getEconEqMarketById"}, consumes = {"application/json"}, produces = {"application/json"})
    public FmrEconomyDto getEconEqMarketById(@RequestBody FmrEconomyDto input) {
        FmrEconomyDto fmr = new FmrEconomyDto();
        try {
            String sql = "select id,\n       description, field_1, field_2, field_3, field_4, field_5, id \n  from fmr_economy_table_3 fb \n where id = ? and islamic = ?";


            fmr = (FmrEconomyDto) this.jdbcTemplate.queryForObject(sql, new Object[]{input.id, input
                    .getIslamic()}, (RowMapper) new EconomySummaryDefinitionRowMapper());
            fmr.setResponse_code(ResponseCodes.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }
        return fmr;
    }

    @PostMapping(path = {"/removeEconEqMarketById"}, consumes = {"application/json"}, produces = {"application/json"})
    public Object removeEconEqMarketById(@RequestBody FmrEconomyDto fundDef) {
        FmrEconomyDto fmr = new FmrEconomyDto();

        try {
            String sql = "delete from fmr_economy_table_3 where id = '" + fundDef.getId() + "' ";
            int resp = this.jdbcTemplate.update(sql);
            if (resp == 1) {
                fmr.setResponse_code(ResponseCodes.SUCCESS);
            } else {
                fmr.setResponse_code(ResponseCodes.FAILURE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fmr.setResponse_code(ResponseCodes.FAILURE);
        }

        return fundDef;
    }
}