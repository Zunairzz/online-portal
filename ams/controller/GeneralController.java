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
                data = this.jdbcTemplate.queryForList(" select f.fund_code,\n       decode(f.fund_code,\n              '00001',\n              'ABL Income Fund',\n              '00002',\n              'ABL Stock Fund',\n              '00003',\n              'ABL Cash Fund',\n              '00004',\n              'ABL Islamic Income Fund',\n              '00005',\n              'ABL Government Securities Fund',\n              '00008',\n              'ABL Islamic Stock Fund',\n              '00011',\n              'ABL IFPF-Conservative Allocation Plan',\n              '00012',\n              'ABL IFPF-Aggressive Allocation Plan',\n              '00013',\n              'ABL IFPF-Active Allocation Plan',\n              '00014',\n              'ABL FPF-Conservative Allocation Plan',\n              '00016',\n              'ABL FPF-Active Allocation Plan',\n              '00017',\n              'ABL-IFPF-Strategic Allocation Plan- I',\n              '00018',\n              'ABL-IFPF-Strategic Allocation Plan- II',\n              '00019',\n              'ABL FPF-Strategic Allocation Plan',\n              '00020',\n              'ABL Islamic Dedicated Stock Fund',\n              '00021',\n              'ABL-IFPF-Strategic Allocation Plan- III',\n              '00022',\n              'ABL-IFPF-Strategic Allocation Plan- IV',\n              '00023',\n              'Allied Capital Protected Fund',\n              '00024',\n              'ABL Islamic Asset Allocation Fund',\n              '00025',\n              'Allied Finergy Fund',\n              '00026',\n              'ABL IFPF Capital Preservation Plan- I',\n              '00027',\n              'ABL Special Savings Plan-I',\n              '00028',\n              'ABL Special Savings Plan-II',\n              '00029',\n              'ABL Special Savings Plan-III\t',\n              '00030',\n              'ABL Special Savings Plan-IV\t',\n              F.fund_name) Fund,\n       to_char(decode(f.fund_code,\n                      '00017',\n                      null,\n                      '00018',\n                      null,\n                      '00021',\n                      null,\n                      '00022',\n                      null,\n                      '00023',\n                      null,\n                      '00026',\n                      null,\n                      u.sale_price),\n               '9999.9999') Offer,\n       to_char(u.redemption_price, '9999.9999') Redemption,\n       to_char(u.nav, '9999.9999') Nav,\n       to_char(decode(f.fund_code, '00003', u.price_date + 1, u.price_date),\n               'dd-MON-yy') price_date\n  from unit_nav u, fund f\n where u.price_date = to_date('" + model


                        164 .getTransDate() + "','dd/MM/yyyy') \n   and f.fund_code = u.fund_code\n order by u.fund_code ");
            } else {

                168 return null;
            }
            170
        } catch (Exception e) {
            171 busy = false;
            172 return data;
        }
        174 busy = false;
        175 return data;
    }

    @PostMapping(path = {"/netAssetsReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> netAssetsReport(@RequestBody MainModel model) {
        180 List<Map<String, Object>> data = null;


        try {
            200
            String qry = "SELECT PRICE_DATE, NETASSET, (select nav_report_util.CALC_TOTAL_UNITS(to_date(price_date,'dd/MM/yyyy')-1,\n                                                                    '" + model.getFundCode() + "') +\n                                   nav_report_util.SOLD_UNITS_FOR_THE_DAY(to_date(price_date,'dd/MM/yyyy'),\n                                                                          '" + model.getFundCode() + "') -\n                                   nav_report_util.REDEMED_UNITS_FOR_THE_DAY(to_date(price_date,'dd/MM/yyyy'),\n                                                                             '" + model.getFundCode() + "')\n                              FROM dual) as UNITS FROM (SELECT to_char(price_date, 'dd/MM/yyyy') price_date, net_assets netAsset \n                      FROM TABLE(net_assets_for_date_range(to_date('" + model.getFromDate() + "', 'dd/MM/yyyy'),\n                                                           to_date('" + model.getToDate() + "', 'dd/MM/yyyy'),\n                                                           '" + model.getFundCode() + "'))\n                     )  ORDER BY to_date(price_date,'dd/MM/yyyy') ";

            202 data = this.jdbcTemplate.queryForList(qry);
        }
        204      catch(Exception e){
            205 System.out.println(e.toString());
            206 return data;
        }
        208 return data;
    }

    @PostMapping(path = {"/netAssetsMVReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> netAssetsMVReport(@RequestBody MainModel model) {
        213 List<Map<String, Object>> data = null;


        try {
            221
            String qry = " select to_char(t.Portfolio_Date , 'dd/MM/yyyy') Portfolio_Date,\n       t.Net_Assets,\n       t.market_value,\n       round(t.market_value / t.Net_Assets, 4) * 100 as percentage\n  from avg_protfolio t\n where t.Portfolio_Date between to_date('" + model.getFromDate() + "', 'dd/MM/yyyy') and to_date('" + model.getToDate() + "', 'dd/MM/yyyy')\n   and t.fund_code = '" + model.getFundCode() + "'\n   order by t.Portfolio_Date,t.fund_code ";

            223 data = this.jdbcTemplate.queryForList(qry);
        }
        225      catch(Exception e){
            226 return data;
        }
        228 return data;
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
        }
              catch(Exception e){
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
        }
              catch(Exception e){
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
        }
              catch(Exception e){
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
        647 cell = row.createCell(40);
        648 cell.setCellValue("NM_SHARE1");
        649 cell = row.createCell(41);
        650 cell.setCellValue("NM_NAME2");
        651 cell = row.createCell(42);
        652 cell.setCellValue("NM_RELATION2");
        653 cell = row.createCell(43);
        654 cell.setCellValue("NM_SHARE2");
        655 cell = row.createCell(44);
        656 cell.setCellValue("NM_NAME3");
        657 cell = row.createCell(45);
        658 cell.setCellValue("NM_RELATION3");
        659 cell = row.createCell(46);
        660 cell.setCellValue("NM_SHARE3");
        661 cell = row.createCell(47);
        662 cell.setCellValue("NM_NAME4");
        663 cell = row.createCell(48);
        664 cell.setCellValue("NM_RELATION4");
        665 cell = row.createCell(49);
        666 cell.setCellValue("NM_SHARE4");
        667 cell = row.createCell(50);
        668 cell.setCellValue("JNA_CCODE1");
        669 cell = row.createCell(51);
        670 cell.setCellValue("JNA_CNAME1");
        671 cell = row.createCell(52);
        672 cell.setCellValue("JNA_CFNAME1");
        673 cell = row.createCell(53);
        674 cell.setCellValue("JNA_CNIC1");
        675 cell = row.createCell(54);
        676 cell.setCellValue("JNA_CCODE2");
        677 cell = row.createCell(55);
        678 cell.setCellValue("JNA_CNAME2");
        679 cell = row.createCell(56);
        680 cell.setCellValue("JNA_CFNAME2");
        681 cell = row.createCell(57);
        682 cell.setCellValue("JNA_CNIC2");
        683 cell = row.createCell(58);
        684 cell.setCellValue("JNA_CCODE3");
        685 cell = row.createCell(59);
        686 cell.setCellValue("JNA_CNAME3");
        687 cell = row.createCell(60);
        688 cell.setCellValue("JNA_CFNAME3");
        689 cell = row.createCell(61);
        690 cell.setCellValue("JNA_CNIC3");
        691 cell = row.createCell(62);
        692 cell.setCellValue("AHI_IF");
        693 cell = row.createCell(63);
        694 cell.setCellValue("AHI_SF");
        695 cell = row.createCell(64);
        696 cell.setCellValue("AHI_CF");
        697 cell = row.createCell(65);
        698 cell.setCellValue("AHI_IIF");
        699 cell = row.createCell(66);
        700 cell.setCellValue("AHI_GSF");
        701 cell = row.createCell(67);
        702 cell.setCellValue("AHI_ISF");
        703 cell = row.createCell(68);
        704 cell.setCellValue("AHI_IPPF_2");
        705 cell = row.createCell(69);
        706 cell.setCellValue("AHI_IFPF_CONSERVATIVE");
        707 cell = row.createCell(70);
        708 cell.setCellValue("AHI_IFPF_AGGRESSIVE");
        709 cell = row.createCell(71);
        710 cell.setCellValue("AHI_IFPF_ACTIVE");
        711 cell = row.createCell(72);
        712 cell.setCellValue("AHI_FPF_CONSERVATIVE");
        713 cell = row.createCell(73);
        714 cell.setCellValue("AHI_FPF_ACTIVEPLAN");
        715 cell = row.createCell(74);
        716 cell.setCellValue("AHI_IFPF_SAPI");
        717 cell = row.createCell(75);
        718 cell.setCellValue("AHI_IFPF_SAPII");
        719 cell = row.createCell(76);
        720 cell.setCellValue("AHI_FPF_SAP");
        721 cell = row.createCell(77);
        722 cell.setCellValue("AHI_IDSF");
        723 cell = row.createCell(78);
        724 cell.setCellValue("AHI_IFPF_SAPIII");
        725 cell = row.createCell(79);
        726 cell.setCellValue("AHI_IFPF_SAPIV");
        727 cell = row.createCell(80);
        728 cell.setCellValue("AHI_ALLIED_CPF");
        729 cell = row.createCell(81);
        730 cell.setCellValue("AHI_IAAF");
        731 cell = row.createCell(82);
        732 cell.setCellValue("AHI_AFF");
        733 cell = row.createCell(83);
        734 cell.setCellValue("AHI_IFPF");
        735 cell = row.createCell(84);
        736 cell.setCellValue("AHI_SSPI");
        737 cell = row.createCell(85);
        738 cell.setCellValue("AHI_SSPII");
        739 cell = row.createCell(86);
        740 cell.setCellValue("AHI_SSPIII");
        741 cell = row.createCell(87);
        742 cell.setCellValue("AHI_SSPIV");
        743 cell = row.createCell(88);
        744 cell.setCellValue("AHI_INV_PLAN");
        745 cell = row.createCell(89);
        746 cell.setCellValue("AHI_PAYMENT_INTER");
        747 cell = row.createCell(90);
        748 cell.setCellValue("AHI_FIXED_AMT");
    }

    private static void createList(Map<String, Object> user, Row row) {
        752 Cell cell = row.createCell(0);
        753 cell.setCellValue(checkIfNull(user.get("SRNO")));
        754 cell = row.createCell(1);
        755 cell.setCellValue(checkIfNull(user.get("AHI_ACC_DATE")));
        756 cell = row.createCell(2);
        757 cell.setCellValue(checkIfNull(user.get("AHI_ACC_DES")));
        758 cell = row.createCell(3);
        759 cell.setCellValue(checkIfNull(user.get("AHI_FOLIO_NUMBER")));
        760 cell = row.createCell(4);
        761 cell.setCellValue(checkIfNull(user.get("AHI_NAME")));
        762 cell = row.createCell(5);
        763 cell.setCellValue(checkIfNull(user.get("AHI_FNAME")));
        764 cell = row.createCell(6);
        765 cell.setCellValue(checkIfNull(user.get("AHI_TYPE")));
        766 cell = row.createCell(7);
        767 cell.setCellValue(checkIfNull(user.get("AHI_ACC_TYPE")));
        768 cell = row.createCell(8);
        769 cell.setCellValue(checkIfNull(user.get("AHI_CNIC_REGISTRATION_NO")));
        770 cell = row.createCell(9);
        771 cell.setCellValue(checkIfNull(user.get("AHI_CNIC_EXP_DATE_DATE")));
        772 cell = row.createCell(10);
        773 cell.setCellValue(checkIfNull(user.get("AHI_ADDRESS")));
        774 cell = row.createCell(11);
        775 cell.setCellValue(checkIfNull(user.get("AHI_CITY")));
        776 cell = row.createCell(12);
        777 cell.setCellValue(checkIfNull(user.get("AHI_REGION")));
        778 cell = row.createCell(13);
        779 cell.setCellValue(checkIfNull(user.get("AHI_PHONE")));
        780 cell = row.createCell(14);
        781 cell.setCellValue(checkIfNull(user.get("AHI_MOBILE")));
        782 cell = row.createCell(15);
        783 cell.setCellValue(checkIfNull(user.get("AHI_EMAIL")));
        784 cell = row.createCell(16);
        785 cell.setCellValue(checkIfNull(user.get("AHI_DOB")));
        786 cell = row.createCell(17);
        787 cell.setCellValue(checkIfNull(user.get("AHI_RELIGION")));
        788 cell = row.createCell(18);
        789 cell.setCellValue(checkIfNull(user.get("AHI_GENDER")));
        790 cell = row.createCell(19);
        791 cell.setCellValue(checkIfNull(user.get("AHI_MARITAL_STATUS")));
        792 cell = row.createCell(20);
        793 cell.setCellValue(checkIfNull(user.get("AHI_NTN_NO")));
        794 cell = row.createCell(21);
        795 cell.setCellValue(checkIfNull(user.get("AHI_MOTHER_NAME")));
        796 cell = row.createCell(22);
        797 cell.setCellValue(checkIfNull(user.get("AHI_BUSINESS_CATEGORY")));
        798 cell = row.createCell(23);
        799 cell.setCellValue(checkIfNull(user.get("AHI_TRANS_AUTH")));
        800 cell = row.createCell(24);
        801 cell.setCellValue(checkIfNull(user.get("AHI_ZAKAT")));
        802 cell = row.createCell(25);
        803 cell.setCellValue(checkIfNull(user.get("AHI_DIVIDEND")));
        804 cell = row.createCell(26);
        805 cell.setCellValue(checkIfNull(user.get("AHI_REINVEST")));
        806 cell = row.createCell(27);
        807 cell.setCellValue(checkIfNull(user.get("AHI_BONUS_CASH")));
        808 cell = row.createCell(28);
        809 cell.setCellValue(checkIfNull(user.get("AHI_PAYMENT_MODE")));
        810 cell = row.createCell(29);
        811 cell.setCellValue(checkIfNull(user.get("AHI_BANK_NAME")));
        812 cell = row.createCell(30);
        813 cell.setCellValue(checkIfNull(user.get("AHI_ACC_NO")));
        814 cell = row.createCell(31);
        815 cell.setCellValue(checkIfNull(user.get("AHI_BRANCH_CODE")));
        816 cell = row.createCell(32);
        817 cell.setCellValue(checkIfNull(user.get("AHI_BANK_ADDRESS")));
        818 cell = row.createCell(33);
        819 cell.setCellValue(checkIfNull(user.get("AHI_BRANCH_CITY")));
        820 cell = row.createCell(34);
        821 cell.setCellValue(checkIfNull(user.get("AHI_EMAIL_HOLDER")));
        822 cell = row.createCell(35);
        823 cell.setCellValue(checkIfNull(user.get("AHI_PHY_ACC_STAT")));
        824 cell = row.createCell(36);
        825 cell.setCellValue(checkIfNull(user.get("AHI_ELEC_ACC_STAT")));
        826 cell = row.createCell(37);
        827 cell.setCellValue(checkIfNull(user.get("AHI_WHT")));
        828 cell = row.createCell(38);
        829 cell.setCellValue(checkIfNull(user.get("NM_NAME1")));
        830 cell = row.createCell(39);
        831 cell.setCellValue(checkIfNull(user.get("NM_RELATION1")));
        832 cell = row.createCell(40);
        833 cell.setCellValue(checkIfNull(user.get("NM_SHARE1")));
        834 cell = row.createCell(41);
        835 cell.setCellValue(checkIfNull(user.get("NM_NAME2")));
        836 cell = row.createCell(42);
        837 cell.setCellValue(checkIfNull(user.get("NM_RELATION2")));
        838 cell = row.createCell(43);
        839 cell.setCellValue(checkIfNull(user.get("NM_SHARE2")));
        840 cell = row.createCell(44);
        841 cell.setCellValue(checkIfNull(user.get("NM_NAME3")));
        842 cell = row.createCell(45);
        843 cell.setCellValue(checkIfNull(user.get("NM_RELATION3")));
        844 cell = row.createCell(46);
        845 cell.setCellValue(checkIfNull(user.get("NM_SHARE3")));
        846 cell = row.createCell(47);
        847 cell.setCellValue(checkIfNull(user.get("NM_NAME4")));
        848 cell = row.createCell(48);
        849 cell.setCellValue(checkIfNull(user.get("NM_RELATION4")));
        850 cell = row.createCell(49);
        851 cell.setCellValue(checkIfNull(user.get("NM_SHARE4")));
        852 cell = row.createCell(50);
        853 cell.setCellValue(checkIfNull(user.get("JNA_CCODE1")));
        854 cell = row.createCell(51);
        855 cell.setCellValue(checkIfNull(user.get("JNA_CNAME1")));
        856 cell = row.createCell(52);
        857 cell.setCellValue(checkIfNull(user.get("JNA_CFNAME1")));
        858 cell = row.createCell(53);
        859 cell.setCellValue(checkIfNull(user.get("JNA_CNIC1")));
        860 cell = row.createCell(54);
        861 cell.setCellValue(checkIfNull(user.get("JNA_CCODE2")));
        862 cell = row.createCell(55);
        863 cell.setCellValue(checkIfNull(user.get("JNA_CNAME2")));
        864 cell = row.createCell(56);
        865 cell.setCellValue(checkIfNull(user.get("JNA_CFNAME2")));
        866 cell = row.createCell(57);
        867 cell.setCellValue(checkIfNull(user.get("JNA_CNIC2")));
        868 cell = row.createCell(58);
        869 cell.setCellValue(checkIfNull(user.get("JNA_CCODE3")));
        870 cell = row.createCell(59);
        871 cell.setCellValue(checkIfNull(user.get("JNA_CNAME3")));
        872 cell = row.createCell(60);
        873 cell.setCellValue(checkIfNull(user.get("JNA_CFNAME3")));
        874 cell = row.createCell(61);
        875 cell.setCellValue(checkIfNull(user.get("JNA_CNIC3")));
        876 cell = row.createCell(62);
        877 cell.setCellValue(checkIfNull(user.get("AHI_IF")));
        878 cell = row.createCell(63);
        879 cell.setCellValue(checkIfNull(user.get("AHI_SF")));
        880 cell = row.createCell(64);
        881 cell.setCellValue(checkIfNull(user.get("AHI_CF")));
        882 cell = row.createCell(65);
        883 cell.setCellValue(checkIfNull(user.get("AHI_IIF")));
        884 cell = row.createCell(66);
        885 cell.setCellValue(checkIfNull(user.get("AHI_GSF")));
        886 cell = row.createCell(67);
        887 cell.setCellValue(checkIfNull(user.get("AHI_ISF")));
        888 cell = row.createCell(68);
        889 cell.setCellValue(checkIfNull(user.get("AHI_IPPF_2")));
        890 cell = row.createCell(69);
        891 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_CONSERVATIVE")));
        892 cell = row.createCell(70);
        893 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_AGGRESSIVE")));
        894 cell = row.createCell(71);
        895 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_ACTIVE")));
        896 cell = row.createCell(72);
        897 cell.setCellValue(checkIfNull(user.get("AHI_FPF_CONSERVATIVE")));
        898 cell = row.createCell(73);
        899 cell.setCellValue(checkIfNull(user.get("AHI_FPF_ACTIVEPLAN")));
        900 cell = row.createCell(74);
        901 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPI")));
        902 cell = row.createCell(75);
        903 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPII")));
        904 cell = row.createCell(76);
        905 cell.setCellValue(checkIfNull(user.get("AHI_FPF_SAP")));
        906 cell = row.createCell(77);
        907 cell.setCellValue(checkIfNull(user.get("AHI_IDSF")));
        908 cell = row.createCell(78);
        909 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPIII")));
        910 cell = row.createCell(79);
        911 cell.setCellValue(checkIfNull(user.get("AHI_IFPF_SAPIV")));
        912 cell = row.createCell(80);
        913 cell.setCellValue(checkIfNull(user.get("AHI_ALLIED_CPF")));
        914 cell = row.createCell(81);
        915 cell.setCellValue(checkIfNull(user.get("AHI_IAAF")));
        916 cell = row.createCell(82);
        917 cell.setCellValue(checkIfNull(user.get("AHI_AFF")));
        918 cell = row.createCell(83);
        919 cell.setCellValue(checkIfNull(user.get("AHI_IFPF")));
        920 cell = row.createCell(84);
        921 cell.setCellValue(checkIfNull(user.get("AHI_SSPI")));
        922 cell = row.createCell(85);
        923 cell.setCellValue(checkIfNull(user.get("AHI_SSPII")));
        924 cell = row.createCell(86);
        925 cell.setCellValue(checkIfNull(user.get("AHI_SSPIII")));
        926 cell = row.createCell(87);
        927 cell.setCellValue(checkIfNull(user.get("AHI_SSPIV")));
        928 cell = row.createCell(88);
        929 cell.setCellValue(checkIfNull(user.get("AHI_INV_PLAN")));
        930 cell = row.createCell(89);
        931 cell.setCellValue(checkIfNull(user.get("AHI_PAYMENT_INTER")));
        932 cell = row.createCell(90);
        933 cell.setCellValue(checkIfNull(user.get("AHI_FIXED_AMT")));
    }

    private static String checkIfNull(Object o) {
        try {
            938 if (o != null && !o.toString().equals("")) {
                939 return o.toString();
            }
            941
        } catch (Exception exception) {
        }

        943 return "";
    }

    @PostMapping(path = {"/cnicExpiryReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> cnicExpiryReport(@RequestBody MainModel model) {
        948 List<Map<String, Object>> data = null;
        try {
            950 if (!busy && model != null) {
                951 busy = true;
                952
                String qry = " select c.client_code customer_id,\n       ua.folio_number account_number,\n       decode(ua.joint_account, '1', 'Joint', 'Single') account_type,\n       c.client_name,\n       c.nic_passport cnic,\n       to_char(c.cnic_expiry_date, 'dd/MM/yyyy') cnic_expiry_date,\n       c.address1 || ' ' || c.address2 address,\n       ct.city,\n       c.phone_one,\n       c.phone_two,\n       c.phone_mobile,\n       c.e_mail,\n       decode(ua.hold_mail, '1', 'Yes', 'No') account_type\n  from client c, unit_account ua, city ct\n where c.client_code = ua.primary_client\n   and c.city_code = ct.city_code\n";


                968
                if (model.getAsOnDate() != null && !model.getAsOnDate().equals("") && model.getAsOnDate().equals("true")) {
                    969 qry = qry + " and c.cnic_expiry_date <= sysdate ";
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
        987 return null;
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        992 return null;
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Object input) {
        997 return null;
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> delete(@PathVariable String id) {
        1002 return null;
    }

    private static boolean busy = false;
    @Autowired
    private JdbcTemplate jdbcTemplate;
}