package com.ams.controller;

import com.ams.model.FundManageModel;

import java.util.List;
import java.util.Map;

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
@RequestMapping({"/fundmanager"})
public class FundManageController {
    @GetMapping
    public List<Object> list() {
        return null;
    }


    @PostMapping(path = {"/generateRelatedPartyReport"}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Map<String, Object>> generateRelatedPartyReport(@RequestBody FundManageModel model) {
        List<Map<String, Object>> data = null;
        try {
            if (!busy) {
                busy = true;
                data = this.jdbcTemplate.queryForList("select ua.title,\nfr.folio_number,\nto_char(get_fund_folio_balance(to_date('" + model

                        .getpFromDate() + "','dd/MM/yyyy')-1, fr.fund_code, ua.folio_number), '9999999999.99') as op_units,\nto_char(get_fund_folio_balance(to_date('" + model
                        .getpFromDate() + "','dd/MM/yyyy')-1, fr.fund_code, ua.folio_number)*un.redemption_price, '9999999999.99') as op_amount,\n(select to_char(sum(pd.units_issued), '9999999999.99') from unit_sale us, payment_detail pd\nwhere us.payment_id = pd.payment_id\nand us.folio_number = fr.folio_number and us.fund_code = fr.fund_code\nand us.sale_date>=to_date('" + model


                        .getpFromDate() + "','dd/MM/yyyy') and us.sale_date<=to_date('" + model.getpToDate() + "','dd/MM/yyyy')) as sl_units,\n(select to_char(sum(pd.amount), '9999999999.99') from unit_sale us, payment_detail pd\nwhere us.payment_id = pd.payment_id\nand us.folio_number = fr.folio_number and us.fund_code = fr.fund_code\nand us.sale_date>=to_date('" + model


                        .getpFromDate() + "','dd/MM/yyyy') and us.sale_date<=to_date('" + model.getpToDate() + "','dd/MM/yyyy')) as sl_amount,\n(select to_char(sum(ur.uncertified_quantity), '9999999999.99') from unit_redemption ur\nwhere ur.folio_number = fr.folio_number and ur.fund_code = fr.fund_code\nand ur.redemption_date>=to_date('" + model


                        .getpFromDate() + "','dd/MM/yyyy') and ur.redemption_date<=to_date('" + model.getpToDate() + "','dd/MM/yyyy')) as rd_units,\n(select to_char(sum(ur.uncertified_quantity)*un.redemption_price, '9999999999.99') from unit_redemption ur\nwhere ur.folio_number = fr.folio_number and ur.fund_code = fr.fund_code\nand ur.redemption_date>=to_date('" + model


                        .getpFromDate() + "','dd/MM/yyyy') and ur.redemption_date<=to_date('" + model.getpToDate() + "','dd/MM/yyyy')) as rd_amount,\nto_char(get_fund_folio_balance(to_date('" + model
                        .getpToDate() + "','dd/MM/yyyy'),fr.fund_code, ua.folio_number), '9999999999.99') as cl_units,\nto_char(get_fund_folio_balance(to_date('" + model
                        .getpToDate() + "','dd/MM/yyyy'),fr.fund_code, ua.folio_number)*un.redemption_price, '9999999999.99') as cl_amount , un.redemption_price as price \nfrom op_fund_related_parties fr, unit_account ua, unit_nav un\nwhere fr.fund_code = '" + model

                        .getpFundCode() + "'\nand fr.folio_number = ua.folio_number\nand fr.fund_code= un.fund_code\nand un.price_date = (\n    select max(unn.price_date) from unit_nav unn where unn.fund_code = un.fund_code\n    and unn.price_date<= to_date('" + model


                        .getpFromDate() + "','dd/MM/yyyy') \n) ");
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