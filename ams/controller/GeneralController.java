package com.ams.controller;

import com.ams.model.FundManageModel;
import com.ams.model.MainModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({"/general/general"})
public class GeneralController {
    @GetMapping
    public List<Object> list() {
        return null;
    }


    @PostMapping(path = {"/getFundList"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> getFundList(@RequestBody FundManageModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;
                data = this.jdbcTemplate.queryForList("SELECT fund_code, fund_name from fund where post = '1' and active = '1' order by fund_code ");
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(data);
            busy = false;
            return data;
        }
        busy = false;
        return data;
    }


    @PostMapping(path = {"/generateFlashReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> generateFlashReport(@RequestBody FundManageModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;
                data = this.jdbcTemplate.queryForList("SELECT * from table(WEB_GET_FLASH(TO_DATE('" + model.getAsOnDate() + "','dd/MM/yyyy'))) ");
                System.out.println(data);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(data);
            busy = false;
            return data;
        }
        busy = false;
        return data;
    }

    @PostMapping(path = {"/generateNAVReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> generateNAVReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;
                data = this.jdbcTemplate.queryForList(" select f.fund_code,\n       decode(f.fund_code,\n              '00001',\n              'ABL Income Fund',\n              '00002',\n              'ABL Stock Fund',\n              '00003',\n              'ABL Cash Fund',\n              '00004',\n              'ABL Islamic Income Fund',\n              '00005',\n              'ABL Government Securities Fund',\n              '00008',\n              'ABL Islamic Stock Fund',\n              '00011',\n              'ABL IFPF-Conservative Allocation Plan',\n              '00012',\n              'ABL IFPF-Aggressive Allocation Plan',\n              '00013',\n              'ABL IFPF-Active Allocation Plan',\n              '00014',\n              'ABL FPF-Conservative Allocation Plan',\n              '00016',\n              'ABL FPF-Active Allocation Plan',\n              '00017',\n              'ABL-IFPF-Strategic Allocation Plan- I',\n              '00018',\n              'ABL-IFPF-Strategic Allocation Plan- II',\n              '00019',\n              'ABL FPF-Strategic Allocation Plan',\n              '00020',\n              'ABL Islamic Dedicated Stock Fund',\n              '00021',\n              'ABL-IFPF-Strategic Allocation Plan- III',\n              '00022',\n              'ABL-IFPF-Strategic Allocation Plan- IV',\n              '00023',\n              'Allied Capital Protected Fund',\n              '00024',\n              'ABL Islamic Asset Allocation Fund',\n              '00025',\n              'Allied Finergy Fund',\n              '00026',\n              'ABL IFPF Capital Preservation Plan- I',\n              '00027',\n              'ABL Special Savings Plan-I',\n              '00028',\n              'ABL Special Savings Plan-II',\n              '00029',\n              'ABL Special Savings Plan-III\t',\n              '00030',\n              'ABL Special Savings Plan-IV\t',\n              F.fund_name) Fund,\n       to_char(decode(f.fund_code,\n                      '00017',\n                      null,\n                      '00018',\n                      null,\n                      '00021',\n                      null,\n                      '00022',\n                      null,\n                      '00023',\n                      null,\n                      '00026',\n                      null,\n                      u.sale_price),\n               '9999.9999') Offer,\n       to_char(u.redemption_price, '9999.9999') Redemption,\n       to_char(u.nav, '9999.9999') Nav,\n       to_char(decode(f.fund_code, '00003', u.price_date + 1, u.price_date),\n               'dd-MON-yy') price_date\n  from unit_nav u, fund f\n where u.price_date = to_date('" + model.getTransDate() + "','dd/MM/yyyy') \n   and f.fund_code = u.fund_code\n order by u.fund_code ");
            } else {

                return null;
            }

        } catch (Exception e) {
            busy = false;
            return data;
        }
        busy = false;
        return data;
    }

    @PostMapping(path = {"/netAssetsReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> netAssetsReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = "SELECT PRICE_DATE, NETASSET, (select nav_report_util.CALC_TOTAL_UNITS(to_date(price_date,'dd/MM/yyyy')-1,\n                                                                    '" + model.getFundCode() + "') +\n                                   nav_report_util.SOLD_UNITS_FOR_THE_DAY(to_date(price_date,'dd/MM/yyyy'),\n                                                                          '" + model.getFundCode() + "') -\n                                   nav_report_util.REDEMED_UNITS_FOR_THE_DAY(to_date(price_date,'dd/MM/yyyy'),\n                                                                             '" + model.getFundCode() + "')\n                              FROM dual) as UNITS FROM (SELECT to_char(price_date, 'dd/MM/yyyy') price_date, net_assets netAsset \n                      FROM TABLE(net_assets_for_date_range(to_date('" + model.getFromDate() + "', 'dd/MM/yyyy'),\n                                                           to_date('" + model.getToDate() + "', 'dd/MM/yyyy'),\n                                                           '" + model.getFundCode() + "'))\n                     )  ORDER BY to_date(price_date,'dd/MM/yyyy') ";

            data = this.jdbcTemplate.queryForList(qry);
        } catch (Exception e) {
            System.out.println(e.toString());
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/netAssetsMVReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> netAssetsMVReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select to_char(t.Portfolio_Date , 'dd/MM/yyyy') Portfolio_Date,\n       t.Net_Assets,\n       t.market_value,\n       round(t.market_value / t.Net_Assets, 4) * 100 as percentage\n  from avg_protfolio t\n where t.Portfolio_Date between to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') and to_date('" + model.getToDate() + "', 'dd/MM/yyyy')\n   and t.fund_code = '" + model.getFundCode() + "'\n   order by t.Portfolio_Date,t.fund_code ";

            data = this.jdbcTemplate.queryForList(qry);
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/autoRedemptionReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> autoRedemptionReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select t. redemption_id AMCTransactionID,\n       decode(f.fund_code,              00001,              'ABLIF',              00002,              'ABLSF',              00003,              'ABLCF',              00004,              'ABLISIF',              00005,              'ABLGSF') fund_short_name,\n       t.house_account_no Fund_Bank_Account,\n       decode(u.plan_name,              'FLEXIBLE INCOME UNITS',              'INCOME UNIT',              'GROWTH UNITS',              'GROWTH UNIT',              'FIXED INCOME UNITS',              'INCOME UNIT') PLAN_NAMe,\n       'AMC ELECTRONIC' Holding_type,\n       to_char(t.cheque_date, 'dd/mm/yyyy') Payment_date,\n       to_char(t.price_date, 'dd/mm/yyyy') NAV_Date,\n       'N' Payment_To_3rd_Party,\n       t.folio_number,\n       ua.title,\n       t.bank_name,\n       t.bank_address,\n       t.bank_city,\n       ua.bank_branch_code,\n       t.account_number,\n       t.comments Instrument_Type,\n       t.uncertified_quantity No_of_units,\n       get_fund_folio_balance(t.redemption_date,\n                              f.fund_code,\n                              t.folio_number) Closing_Balance,\n       round(t.uncertified_quantity * t.nav, 2) Gross_amount,\n       NVL(t.zakat, 0) zakat_amount,\n       NVL(t.contingent_load, 0) backend_load,\n       '0' processing_charges,\n       '0' discount_amount,\n       round(t.uncertified_quantity * t.nav, 2) - round(nvl(t.cgt, 0)) net_amount,\n       ' ' remarks,\n       'N' CommonACTransactionFlag,\n       ' ' RefernceID,\n       '0' Total_amount,\n       ' ' PrintingLocation,\n       ' ' ClearingZoneCode,\n       'Applicable' TaxStatus,\n       nvl(decode(sign((select sum(utt.capital_gain)\n                         from unit_trans_deduction_history utt\n                        where utt.redemption_price_date = to_date('" + model.getNavDate() + "', 'dd/MM/yyyy') \n                          and utt.folio_number = t.folio_number\n                          and utt.fund_code = t.fund_code\n                          and utt.no_of_days <= 1460\n                          and utt.transaction_id = t.redemption_id)),\n                  -1,\n                  0,\n                  (select sum(utt.capital_gain)\n                     from unit_trans_deduction_history utt\n                    where utt.redemption_price_date = to_date('" + model.getNavDate() + "', 'dd/MM/yyyy') \n                      and utt.folio_number = t.folio_number\n                      and utt.fund_code = t.fund_code\n                      and utt.no_of_days <= 1460\n                      and utt.transaction_id = t.redemption_id)),\n           0) TAXABLE_INCOME,\n       '0' LossAdjustment,\n       round(NVL(t.cgt, 0)) TAX_AMOUNT\n  from unit_redemption t, fund f, unit_plan u, unit_account ua\n where t.redemption_date = to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') \n   and t.fund_code = f.fund_code\n   and u.plan_id = ua.plan_id\n   and ua.folio_number = t.folio_number ";


            if (model.getFundCode() != null && !model.getFundCode().equals("") && !model.getFundCode().equals("ALL")) {
                qry = qry + " and f.fund_code = '" + model.getFundCode() + "' ";
            }
            data = this.jdbcTemplate.queryForList(qry);
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/reallocationReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> reallocationReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select distinct ua.folio_number AccountNumber,\n                ua.title,\n                get_fund_folio_balance(get_system_date(F.FUND_CODE),\n                                       f.fund_code,\n                                       ua.folio_number) before_balance,\n                nvl((Select ur.uncertified_quantity\n                      from unit_redemption ur\n                     where ur.aplan_reallocation_id = apr.reallocation_id\n                       and ur.fund_code = f.fund_code),\n                    0) reallocation,\n                f.fund_name,\n                nvl((Select ur.uncertified_quantity *\n                           (select un.nav\n                              from unit_nav un\n                             where un.fund_code = f.fund_code\n                               and un.price_date =\n                                   get_system_date(UN.FUND_CODE))\n                      from unit_redemption ur\n                     where ur.aplan_reallocation_id = apr.reallocation_id\n                       and ur.fund_code = f.fund_code),\n                    0) amount,\n                get_fund_folio_balance(get_system_date(F.FUND_CODE),\n                                       f.fund_code,\n                                       ua.folio_number) after_balance,\n                nvl((Select ur.cgt\n                      from unit_redemption ur\n                     where ur.aplan_reallocation_id = apr.reallocation_id\n                       and ur.fund_code = f.fund_code),\n                    0) cgt,\n                to_char(apr.reallocation_date , 'dd/MM/yyyy') reallocation_date\n  from aplan_detail            apd,\n       aplan                   ap,\n       aplan_reallocations     apr,\n       unit_account            ua,\n       APLAN_FUND_DISTRIBUTION apfd,\n       fund                    f\n where ap.aplan_id = apd.aplan_id\n   and apr.aplan_detail_id = apd.aplan_detail_id\n   and ua.folio_number = apr.folio_number\n   and ua.aplan_id = ap.aplan_id\n   AND apd.aplan_detail_id = apfd.aplan_detail_id\n   AND f.fund_code = apfd.fund_code\n   and apr.reallocation_date = to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') ";

            if (model.getFundCode() != null && !model.getFundCode().equals("") && !model.getFundCode().equals("ALL")) {
                qry = qry + " and f.fund_code = '" + model.getFundCode() + "' ";
            }
            data = this.jdbcTemplate.queryForList(qry);
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/netAssetsBFReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> netAssetsBFReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;


        try {

            String qry = " select f.fund_short_name, t.FUND_CODE, to_char(t.PRICE_DATE , 'dd/MM/yyyy') PRICE_DATE, t.NET_ASSETS \n  from net_assets_before_fee t, fund f\n where t.price_date between to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') and to_date('" + model.getToDate() + "', 'dd/MM/yyyy')\n   and t.fund_code = f.fund_code\n order by 3 ";


            data = this.jdbcTemplate.queryForList(qry);
        } catch (Exception e) {
            return data;
        }
        return data;
    }

    @PostMapping(path = {"/generate1LinkReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> generate1LinkReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;


                String qry = " select us.sale_id,\n       to_char(sale_date , 'dd/MM/yyyy') sale_date,\n       ua.title,\n       f.fund_name,\n       ua.folio_number,\n       nvl(b.amount, pd.amount) amount,\n       (case when b.amount>0 and b.amount<=10000 then 10 when b.amount>10000 and b.amount<=250000 then 40 when b.amount>250000 and b.amount<=1000000 then 75 when b.amount>1000000 then 150 end) as LinkCharges,\n       nvl(b.amount-(case when b.amount>0 and b.amount<=10000 then 10 when b.amount>10000 and b.amount<=250000 then 40 when b.amount>250000 and b.amount<=1000000 then 75 when b.amount>1000000 then 150 end), pd.amount) as NetLinkCharges,\n       f.sales_load - (f.sales_load*(pd.discount_percentage/100)) fel,\n       pd.offer_price offerNav,\n       un.redemption_price redempNav,\n       round((b.amount/pd.offer_price) , 2) units,\n       (case when round((pd.offer_price-un.redemption_price)*(b.amount/pd.offer_price) , 2)<0 then 0 else round((pd.offer_price-un.redemption_price)*(b.amount/pd.offer_price) , 2) end) frontLoad,\n       decode(us.online_medium, 'O', '1Link', 'A', 'MyAbl') source,\n       to_char(us.posting_date , 'HH:MM am') TransactionTime,\n       (case when us.quantity is not null then 'Yes' else 'No' end) UnitsAllocated , nvl(b.pin_code, cheque_number) pin_code , decode(pd.payment_mode , 'ON' , 'Online', 'Other') payment_mode \n  from unit_sale us, unit_account ua, fund f, payment_detail pd, unit_nav un, investment_by_bank b\n where ua.folio_number = us.folio_number\n and us.payment_id = pd.payment_id\n and b.sale_id(+) = us.sale_id\n   and us.fund_code = f.fund_code and (online_medium='O' or online_medium='A') \n   and un.fund_code = us.fund_code\n and us.sale_date>=to_date('" + model.getFromDate() + "' , 'dd/MM/yyyy') and us.sale_date<=to_date('" + model.getToDate() + "' , 'dd/MM/yyyy') ";

                if (model.getFundCode() != null && !model.getFundCode().equals("") && !model.getFundCode().equals("ALL")) {
                    qry = qry + " and us.fund_code = '" + model.getFundCode() + "' ";
                }
                if (model.getSearchFolioNumber() != null && !model.getSearchFolioNumber().equals("")) {
                    String[] folio = model.getSearchFolioNumber().split(",");
                    qry = qry + " and us.folio_number in ( ";
                    for (String ff : folio) {
                        qry = qry + "'" + ff.trim() + "',";
                    }
                    qry = qry + "'')";
                }


                qry = qry + "   and un.price_date = (\n       select max(unn.price_date) from unit_nav unn where unn.fund_code = us.fund_code and unn.price_date<=us.sale_date \n   ) order by sale_id desc ";


                data = this.jdbcTemplate.queryForList(qry);
            } else {
                return null;
            }

        } catch (Exception e) {
            busy = false;
            return data;
        }
        busy = false;
        return data;
    }


    @PostMapping(path = {"/comprehensiveDetailReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> comprehensiveDetailReport(HttpServletRequest request, @RequestBody MainModel model) {
        String OUTPUT_FILE = "FMR_REPORT.pdf";
        String contextPath = request.getRealPath("/");
        String jasperFileName = contextPath + "/Content/" + OUTPUT_FILE;
        if ((new File(jasperFileName)).exists()) {
            (new File(jasperFileName)).delete();
        }

        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;


                String qry = " select SRNO,\n       to_char(AHI_ACC_DATE , 'dd/MM/yyyy') AHI_ACC_DATE,\n       AHI_ACC_DES,\n       AHI_FOLIO_NUMBER,\n       AHI_NAME,\n       AHI_FNAME,\n       AHI_TYPE,\n       AHI_ACC_TYPE,\n       AHI_CNIC_REGISTRATION_NO,\n       to_char(AHI_CNIC_EXP_DATE_DATE , 'dd/MM/yyyy') AHI_CNIC_EXP_DATE_DATE,\n       AHI_ADDRESS,\n       AHI_CITY,\n       AHI_REGION,\n       AHI_PHONE,\n       AHI_MOBILE,\n       AHI_EMAIL,\n       to_char(AHI_DOB , 'dd/MM/yyyy') AHI_DOB,\n       AHI_RELIGION,\n       AHI_GENDER,\n       AHI_MARITAL_STATUS,\n       AHI_NTN_NO,\n       AHI_MOTHER_NAME,\n       AHI_BUSINESS_CATEGORY,\n       AHI_TRANS_AUTH,\n       AHI_ZAKAT,\n       AHI_DIVIDEND,\n       AHI_REINVEST,\n       AHI_BONUS_CASH,\n       AHI_PAYMENT_MODE,\n       AHI_BANK_NAME,\n       AHI_ACC_NO,\n       AHI_BRANCH_CODE,\n       AHI_BANK_ADDRESS,\n       AHI_BRANCH_CITY,\n       AHI_EMAIL_HOLDER,\n       AHI_PHY_ACC_STAT,\n       AHI_ELEC_ACC_STAT,\n       AHI_WHT,\n       NM_NAME1,\n       NM_RELATION1,\n       NM_SHARE1,\n       NM_NAME2,\n       NM_RELATION2,\n       NM_SHARE2,\n       NM_NAME3,\n       NM_RELATION3,\n       NM_SHARE3,\n       NM_NAME4,\n       NM_RELATION4,\n       NM_SHARE4,\n       JNA_CCODE1,\n       JNA_CNAME1,\n       JNA_CFNAME1,\n       JNA_CNIC1,\n       JNA_CCODE2,\n       JNA_CNAME2,\n       JNA_CFNAME2,\n       JNA_CNIC2,\n       JNA_CCODE3,\n       JNA_CNAME3,\n       JNA_CFNAME3,\n       JNA_CNIC3,\n       AHI_IF,\n       AHI_SF,\n       AHI_CF,\n       AHI_IIF,\n       AHI_GSF,\n       AHI_ISF,\n       AHI_IPPF_2,\n       AHI_IFPF_CONSERVATIVE,\n       AHI_IFPF_AGGRESSIVE,\n       AHI_IFPF_ACTIVE,\n       AHI_FPF_CONSERVATIVE,\n       AHI_FPF_ACTIVEPLAN,\n       AHI_IFPF_SAPI,\n       AHI_IFPF_SAPII,\n       AHI_FPF_SAP,\n       AHI_IDSF,\n       AHI_IFPF_SAPIII,\n       AHI_IFPF_SAPIV,\n       AHI_ALLIED_CPF,\n       AHI_IAAF,\n       AHI_AFF,\n       AHI_IFPF,\n       AHI_SSPI,\n       AHI_SSPII,\n       AHI_SSPIII,\n       AHI_SSPIV,\n       AHI_INV_PLAN,\n       AHI_PAYMENT_INTER,\n       AHI_FIXED_AMT from table(get_Unit_Holder_Data_online(to_date(to_date('" + model.getTransDate() + "', 'dd/MM/yyyy')), '" + model.getFromDate() + "', '" + model.getToDate() + "')) order by ahi_acc_date ";
                data = this.jdbcTemplate.queryForList(qry);
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("sheet1");
                    int rownum = 0;
                    XSSFRow xSSFRow = sheet.createRow(rownum++);
                    addReportHeader((Row) xSSFRow);
                    for (Map<String, Object> user : data) {
                        XSSFRow xSSFRow1 = sheet.createRow(rownum++);
                        createList(user, (Row) xSSFRow1);
                    }

                    FileOutputStream out = new FileOutputStream(new File(contextPath + "//Content//comprehensive_report.xlsx"));
                    workbook.write(out);
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            busy = false;
            return null;
        }
        busy = false;
        return null;
    }

    private static void addReportHeader(Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("SRNO");
        cell = row.createCell(1);
        cell.setCellValue("AHI_ACC_DATE");
        cell = row.createCell(2);
        cell.setCellValue("AHI_ACC_DES");
        cell = row.createCell(3);
        cell.setCellValue("AHI_FOLIO_NUMBER");
        cell = row.createCell(4);
        cell.setCellValue("AHI_NAME");
        cell = row.createCell(5);
        cell.setCellValue("AHI_FNAME");
        cell = row.createCell(6);
        cell.setCellValue("AHI_TYPE");
        cell = row.createCell(7);
        cell.setCellValue("AHI_ACC_TYPE");
        cell = row.createCell(8);
        cell.setCellValue("AHI_CNIC_REGISTRATION_NO");
        cell = row.createCell(9);
        cell.setCellValue("AHI_CNIC_EXP_DATE_DATE");
        cell = row.createCell(10);
        cell.setCellValue("AHI_ADDRESS");
        cell = row.createCell(11);
        cell.setCellValue("AHI_CITY");
        cell = row.createCell(12);
        cell.setCellValue("AHI_REGION");
        cell = row.createCell(13);
        cell.setCellValue("AHI_PHONE");
        cell = row.createCell(14);
        cell.setCellValue("AHI_MOBILE");
        cell = row.createCell(15);
        cell.setCellValue("AHI_EMAIL");
        cell = row.createCell(16);
        cell.setCellValue("AHI_DOB");
        cell = row.createCell(17);
        cell.setCellValue("AHI_RELIGION");
        cell = row.createCell(18);
        cell.setCellValue("AHI_GENDER");
        cell = row.createCell(19);
        cell.setCellValue("AHI_MARITAL_STATUS");
        cell = row.createCell(20);
        cell.setCellValue("AHI_NTN_NO");
        cell = row.createCell(21);
        cell.setCellValue("AHI_MOTHER_NAME");
        cell = row.createCell(22);
        cell.setCellValue("AHI_BUSINESS_CATEGORY");
        cell = row.createCell(23);
        cell.setCellValue("AHI_TRANS_AUTH");
        cell = row.createCell(24);
        cell.setCellValue("AHI_ZAKAT");
        cell = row.createCell(25);
        cell.setCellValue("AHI_DIVIDEND");
        cell = row.createCell(26);
        cell.setCellValue("AHI_REINVEST");
        cell = row.createCell(27);
        cell.setCellValue("AHI_BONUS_CASH");
        cell = row.createCell(28);
        cell.setCellValue("AHI_PAYMENT_MODE");
        cell = row.createCell(29);
        cell.setCellValue("AHI_BANK_NAME");
        cell = row.createCell(30);
        cell.setCellValue("AHI_ACC_NO");
        cell = row.createCell(31);
        cell.setCellValue("AHI_BRANCH_CODE");
        cell = row.createCell(32);
        cell.setCellValue("AHI_BANK_ADDRESS");
        cell = row.createCell(33);
        cell.setCellValue("AHI_BRANCH_CITY");
        cell = row.createCell(34);
        cell.setCellValue("AHI_EMAIL_HOLDER");
        cell = row.createCell(35);
        cell.setCellValue("AHI_PHY_ACC_STAT");
        cell = row.createCell(36);
        cell.setCellValue("AHI_ELEC_ACC_STAT");
        cell = row.createCell(37);
        cell.setCellValue("AHI_WHT");
        cell = row.createCell(38);
        cell.setCellValue("NM_NAME1");
        cell = row.createCell(39);
        cell.setCellValue("NM_RELATION1");
        cell = row.createCell(40);
        cell.setCellValue("NM_SHARE1");
        cell = row.createCell(41);
        cell.setCellValue("NM_NAME2");
        cell = row.createCell(42);
        cell.setCellValue("NM_RELATION2");
        cell = row.createCell(43);
        cell.setCellValue("NM_SHARE2");
        cell = row.createCell(44);
        cell.setCellValue("NM_NAME3");
        cell = row.createCell(45);
        cell.setCellValue("NM_RELATION3");
        cell = row.createCell(46);
        cell.setCellValue("NM_SHARE3");
        cell = row.createCell(47);
        cell.setCellValue("NM_NAME4");
        cell = row.createCell(48);
        cell.setCellValue("NM_RELATION4");
        cell = row.createCell(49);
        cell.setCellValue("NM_SHARE4");
        cell = row.createCell(50);
        cell.setCellValue("JNA_CCODE1");
        cell = row.createCell(51);
        cell.setCellValue("JNA_CNAME1");
        cell = row.createCell(52);
        cell.setCellValue("JNA_CFNAME1");
        cell = row.createCell(53);
        cell.setCellValue("JNA_CNIC1");
        cell = row.createCell(54);
        cell.setCellValue("JNA_CCODE2");
        cell = row.createCell(55);
        cell.setCellValue("JNA_CNAME2");
        cell = row.createCell(56);
        cell.setCellValue("JNA_CFNAME2");
        cell = row.createCell(57);
        cell.setCellValue("JNA_CNIC2");
        cell = row.createCell(58);
        cell.setCellValue("JNA_CCODE3");
        cell = row.createCell(59);
        cell.setCellValue("JNA_CNAME3");
        cell = row.createCell(60);
        cell.setCellValue("JNA_CFNAME3");
        cell = row.createCell(61);
        cell.setCellValue("JNA_CNIC3");
        cell = row.createCell(62);
        cell.setCellValue("AHI_IF");
        cell = row.createCell(63);
        cell.setCellValue("AHI_SF");
        cell = row.createCell(64);
        cell.setCellValue("AHI_CF");
        cell = row.createCell(65);
        cell.setCellValue("AHI_IIF");
        cell = row.createCell(66);
        cell.setCellValue("AHI_GSF");
        cell = row.createCell(67);
        cell.setCellValue("AHI_ISF");
        cell = row.createCell(68);
        cell.setCellValue("AHI_IPPF_2");
        cell = row.createCell(69);
        cell.setCellValue("AHI_IFPF_CONSERVATIVE");
        cell = row.createCell(70);
        cell.setCellValue("AHI_IFPF_AGGRESSIVE");
        cell = row.createCell(71);
        cell.setCellValue("AHI_IFPF_ACTIVE");
        cell = row.createCell(72);
        cell.setCellValue("AHI_FPF_CONSERVATIVE");
        cell = row.createCell(73);
        cell.setCellValue("AHI_FPF_ACTIVEPLAN");
        cell = row.createCell(74);
        cell.setCellValue("AHI_IFPF_SAPI");
        cell = row.createCell(75);
        cell.setCellValue("AHI_IFPF_SAPII");
        cell = row.createCell(76);
        cell.setCellValue("AHI_FPF_SAP");
        cell = row.createCell(77);
        cell.setCellValue("AHI_IDSF");
        cell = row.createCell(78);
        cell.setCellValue("AHI_IFPF_SAPIII");
        cell = row.createCell(79);
        cell.setCellValue("AHI_IFPF_SAPIV");
        cell = row.createCell(80);
        cell.setCellValue("AHI_ALLIED_CPF");
        cell = row.createCell(81);
        cell.setCellValue("AHI_IAAF");
        cell = row.createCell(82);
        cell.setCellValue("AHI_AFF");
        cell = row.createCell(83);
        cell.setCellValue("AHI_IFPF");
        cell = row.createCell(84);
        cell.setCellValue("AHI_SSPI");
        cell = row.createCell(85);
        cell.setCellValue("AHI_SSPII");
        cell = row.createCell(86);
        cell.setCellValue("AHI_SSPIII");
        cell = row.createCell(87);
        cell.setCellValue("AHI_SSPIV");
        cell = row.createCell(88);
        cell.setCellValue("AHI_INV_PLAN");
        cell = row.createCell(89);
        cell.setCellValue("AHI_PAYMENT_INTER");
        cell = row.createCell(90);
        cell.setCellValue("AHI_FIXED_AMT");
    }

    private static void createList(Map<String, Object> user, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(checkIfNull(user.get("SRNO")));
        cell = row.createCell(1);
        cell.setCellValue(checkIfNull(user.get("AHI_ACC_DATE")));
        cell = row.createCell(2);
        cell.setCellValue(checkIfNull(user.get("AHI_ACC_DES")));
        cell = row.createCell(3);
        cell.setCellValue(checkIfNull(user.get("AHI_FOLIO_NUMBER")));
        cell = row.createCell(4);
        cell.setCellValue(checkIfNull(user.get("AHI_NAME")));
        cell = row.createCell(5);
        cell.setCellValue(checkIfNull(user.get("AHI_FNAME")));
        cell = row.createCell(6);
        cell.setCellValue(checkIfNull(user.get("AHI_TYPE")));
        cell = row.createCell(7);
        cell.setCellValue(checkIfNull(user.get("AHI_ACC_TYPE")));
        cell = row.createCell(8);
        cell.setCellValue(checkIfNull(user.get("AHI_CNIC_REGISTRATION_NO")));
        cell = row.createCell(9);
        cell.setCellValue(checkIfNull(user.get("AHI_CNIC_EXP_DATE_DATE")));
        cell = row.createCell(10);
        cell.setCellValue(checkIfNull(user.get("AHI_ADDRESS")));
        cell = row.createCell(11);
        cell.setCellValue(checkIfNull(user.get("AHI_CITY")));
        cell = row.createCell(12);
        cell.setCellValue(checkIfNull(user.get("AHI_REGION")));
        cell = row.createCell(13);
        cell.setCellValue(checkIfNull(user.get("AHI_PHONE")));
        cell = row.createCell(14);
        cell.setCellValue(checkIfNull(user.get("AHI_MOBILE")));
        cell = row.createCell(15);
        cell.setCellValue(checkIfNull(user.get("AHI_EMAIL")));
        cell = row.createCell(16);
        cell.setCellValue(checkIfNull(user.get("AHI_DOB")));
        cell = row.createCell(17);
        cell.setCellValue(checkIfNull(user.get("AHI_RELIGION")));
        cell = row.createCell(18);
        cell.setCellValue(checkIfNull(user.get("AHI_GENDER")));
        cell = row.createCell(19);
        cell.setCellValue(checkIfNull(user.get("AHI_MARITAL_STATUS")));
        cell = row.createCell(20);
        cell.setCellValue(checkIfNull(user.get("AHI_NTN_NO")));
        cell = row.createCell(21);
        cell.setCellValue(checkIfNull(user.get("AHI_MOTHER_NAME")));
        cell = row.createCell(22);
        cell.setCellValue(checkIfNull(user.get("AHI_BUSINESS_CATEGORY")));
        cell = row.createCell(23);
        cell.setCellValue(checkIfNull(user.get("AHI_TRANS_AUTH")));
        cell = row.createCell(24);
        cell.setCellValue(checkIfNull(user.get("AHI_ZAKAT")));
        cell = row.createCell(25);
        cell.setCellValue(checkIfNull(user.get("AHI_DIVIDEND")));
        cell = row.createCell(26);
        cell.setCellValue(checkIfNull(user.get("AHI_REINVEST")));
        cell = row.createCell(27);
        cell.setCellValue(checkIfNull(user.get("AHI_BONUS_CASH")));
        cell = row.createCell(28);
        cell.setCellValue(checkIfNull(user.get("AHI_PAYMENT_MODE")));
        cell = row.createCell(29);
        cell.setCellValue(checkIfNull(user.get("AHI_BANK_NAME")));
        cell = row.createCell(30);
        cell.setCellValue(checkIfNull(user.get("AHI_ACC_NO")));
        cell = row.createCell(31);
        cell.setCellValue(checkIfNull(user.get("AHI_BRANCH_CODE")));
        cell = row.createCell(32);
        cell.setCellValue(checkIfNull(user.get("AHI_BANK_ADDRESS")));
        cell = row.createCell(33);
        cell.setCellValue(checkIfNull(user.get("AHI_BRANCH_CITY")));
        cell = row.createCell(34);
        cell.setCellValue(checkIfNull(user.get("AHI_EMAIL_HOLDER")));
        cell = row.createCell(35);
        cell.setCellValue(checkIfNull(user.get("AHI_PHY_ACC_STAT")));
        cell = row.createCell(36);
        cell.setCellValue(checkIfNull(user.get("AHI_ELEC_ACC_STAT")));
        cell = row.createCell(37);
        cell.setCellValue(checkIfNull(user.get("AHI_WHT")));
        cell = row.createCell(38);
        cell.setCellValue(checkIfNull(user.get("NM_NAME1")));
        cell = row.createCell(39);
        cell.setCellValue(checkIfNull(user.get("NM_RELATION1")));
        cell = row.createCell(40);
        cell.setCellValue(checkIfNull(user.get("NM_SHARE1")));
        cell = row.createCell(41);
        cell.setCellValue(checkIfNull(user.get("NM_NAME2")));
        cell = row.createCell(42);
        cell.setCellValue(checkIfNull(user.get("NM_RELATION2")));
        cell = row.createCell(43);
        cell.setCellValue(checkIfNull(user.get("NM_SHARE2")));
        cell = row.createCell(44);
        cell.setCellValue(checkIfNull(user.get("NM_NAME3")));
        cell = row.createCell(45);
        cell.setCellValue(checkIfNull(user.get("NM_RELATION3")));
        cell = row.createCell(46);
        cell.setCellValue(checkIfNull(user.get("NM_SHARE3")));
        cell = row.createCell(47);
        cell.setCellValue(checkIfNull(user.get("NM_NAME4")));
        cell = row.createCell(48);
        cell.setCellValue(checkIfNull(user.get("NM_RELATION4")));
        cell = row.createCell(49);
        cell.setCellValue(checkIfNull(user.get("NM_SHARE4")));
        cell = row.createCell(50);
        cell.setCellValue(checkIfNull(user.get("JNA_CCODE1")));
        cell = row.createCell(51);
        cell.setCellValue(checkIfNull(user.get("JNA_CNAME1")));
        cell = row.createCell(52);
        cell.setCellValue(checkIfNull(user.get("JNA_CFNAME1")));
        cell = row.createCell(53);
        cell.setCellValue(checkIfNull(user.get("JNA_CNIC1")));
        cell = row.createCell(54);
        cell.setCellValue(checkIfNull(user.get("JNA_CCODE2")));
        cell = row.createCell(55);
        cell.setCellValue(checkIfNull(user.get("JNA_CNAME2")));
        cell = row.createCell(56);
        cell.setCellValue(checkIfNull(user.get("JNA_CFNAME2")));
        cell = row.createCell(57);
        cell.setCellValue(checkIfNull(user.get("JNA_CNIC2")));
        cell = row.createCell(58);
        cell.setCellValue(checkIfNull(user.get("JNA_CCODE3")));
        cell = row.createCell(59);
        cell.setCellValue(checkIfNull(user.get("JNA_CNAME3")));
        cell = row.createCell(60);
        cell.setCellValue(checkIfNull(user.get("JNA_CFNAME3")));
        cell = row.createCell(61);
        cell.setCellValue(checkIfNull(user.get("JNA_CNIC3")));
        cell = row.createCell(62);
        cell.setCellValue(checkIfNull(user.get("AHI_IF")));
        cell = row.createCell(63);
        cell.setCellValue(checkIfNull(user.get("AHI_SF")));
        cell = row.createCell(64);
        cell.setCellValue(checkIfNull(user.get("AHI_CF")));
        cell = row.createCell(65);
        cell.setCellValue(checkIfNull(user.get("AHI_IIF")));
        cell = row.createCell(66);
        cell.setCellValue(checkIfNull(user.get("AHI_GSF")));
        cell = row.createCell(67);
        cell.setCellValue(checkIfNull(user.get("AHI_ISF")));
        cell = row.createCell(68);
        cell.setCellValue(checkIfNull(user.get("AHI_IPPF_2")));
        cell = row.createCell(69);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_CONSERVATIVE")));
        cell = row.createCell(70);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_AGGRESSIVE")));
        cell = row.createCell(71);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_ACTIVE")));
        cell = row.createCell(72);
        cell.setCellValue(checkIfNull(user.get("AHI_FPF_CONSERVATIVE")));
        cell = row.createCell(73);
        cell.setCellValue(checkIfNull(user.get("AHI_FPF_ACTIVEPLAN")));
        cell = row.createCell(74);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPI")));
        cell = row.createCell(75);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPII")));
        cell = row.createCell(76);
        cell.setCellValue(checkIfNull(user.get("AHI_FPF_SAP")));
        cell = row.createCell(77);
        cell.setCellValue(checkIfNull(user.get("AHI_IDSF")));
        cell = row.createCell(78);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPIII")));
        cell = row.createCell(79);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPIV")));
        cell = row.createCell(80);
        cell.setCellValue(checkIfNull(user.get("AHI_ALLIED_CPF")));
        cell = row.createCell(81);
        cell.setCellValue(checkIfNull(user.get("AHI_IAAF")));
        cell = row.createCell(82);
        cell.setCellValue(checkIfNull(user.get("AHI_AFF")));
        cell = row.createCell(83);
        cell.setCellValue(checkIfNull(user.get("AHI_IFPF")));
        cell = row.createCell(84);
        cell.setCellValue(checkIfNull(user.get("AHI_SSPI")));
        cell = row.createCell(85);
        cell.setCellValue(checkIfNull(user.get("AHI_SSPII")));
        cell = row.createCell(86);
        cell.setCellValue(checkIfNull(user.get("AHI_SSPIII")));
        cell = row.createCell(87);
        cell.setCellValue(checkIfNull(user.get("AHI_SSPIV")));
        cell = row.createCell(88);
        cell.setCellValue(checkIfNull(user.get("AHI_INV_PLAN")));
        cell = row.createCell(89);
        cell.setCellValue(checkIfNull(user.get("AHI_PAYMENT_INTER")));
        cell = row.createCell(90);
        cell.setCellValue(checkIfNull(user.get("AHI_FIXED_AMT")));
    }

    private static String checkIfNull(Object o) {
        try {
            if (o != null && !o.toString().equals("")) {
                return o.toString();
            }

        } catch (Exception exception) {
        }

        return "";
    }

    @PostMapping(path = {"/cnicExpiryReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> cnicExpiryReport(@RequestBody MainModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy && model != null) {
                busy = true;
                String qry = " select c.client_code customer_id,\n       ua.folio_number account_number,\n       decode(ua.joint_account, '1', 'Joint', 'Single') account_type,\n       c.client_name,\n       c.nic_passport cnic,\n       to_char(c.cnic_expiry_date, 'dd/MM/yyyy') cnic_expiry_date,\n       c.address1 || ' ' || c.address2 address,\n       ct.city,\n       c.phone_one,\n       c.phone_two,\n       c.phone_mobile,\n       c.e_mail,\n       decode(ua.hold_mail, '1', 'Yes', 'No') account_type\n  from client c, unit_account ua, city ct\n where c.client_code = ua.primary_client\n   and c.city_code = ct.city_code\n";
                if (model.getAsOnDate() != null && !model.getAsOnDate().equals("") && model.getAsOnDate().equals("true")) {
                    qry = qry + " and c.cnic_expiry_date <= sysdate ";
                } else {
                    qry = qry + " and c.cnic_expiry_date >= to_date('" + model.getFromDate() + "' , 'dd/MM/yyyy') and c.cnic_expiry_date <= to_date('" + model.getToDate() + "' , 'dd/MM/yyyy') ";
                }
                data = this.jdbcTemplate.queryForList(qry);
            } else {
                return null;
            }

        } catch (Exception e) {
            busy = false;
            return data;
        }
        busy = false;
        return data;
    }

    @GetMapping({"/{id}"})
    public Object get(@PathVariable String id) {
        return null;
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Object input) {
        return null;
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }

    private static boolean busy = false;
    @Autowired
    private JdbcTemplate jdbcTemplate;
}