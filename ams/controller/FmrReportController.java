package WEB

-INF.classes.com.ams.controller;

import com.ams.common.ResponseCodes;
import com.ams.controller.MainController;
import com.ams.dao.FmrDao;
import com.ams.dao.FmrFundBasicInfoMapper;
import com.ams.dao.FundDefinitionRowMapper;
import com.ams.model.FmrFundBasicInfo;
import com.ams.model.FundDefinition;
import com.ams.model.fmr_asset_allocation_model;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


@Controller
public class FmrReportController {
    private static final String OUTPUT_FILE = "C:\\Users\\mohammad.bassam\\Desktop/message.pdf";
    private static final String UTF_8 = "UTF-8";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = {"/fmr/fmrExportPdf"}, method = {RequestMethod.GET})
    public String fmrExportPdf(ModelMap model) {
        if ((new File("C:\\Users\\mohammad.bassam\\Desktop/message.pdf")).exists()) {
            (new File("C:\\Users\\mohammad.bassam\\Desktop/message.pdf")).delete();
        }

        try {
            ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
            emailTemplateResolver.setPrefix("/templates/");
            emailTemplateResolver.setTemplateMode("HTML5");
            emailTemplateResolver.setSuffix(".html");
            emailTemplateResolver.setTemplateMode("XHTML");
            emailTemplateResolver.setCharacterEncoding("UTF-8");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver((ITemplateResolver) emailTemplateResolver);

            Context context = fillModelMap(model);


            String html = templateEngine.process("fmrPdfTemplate", (IContext) context);


            return "";
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, (String) null, ex);

            return "";
        }
    }

    public Context fillModelMap(ModelMap model) {
        Context context = new Context();
        FmrDao dao = new FmrDao();
        FundDefinition input = new FundDefinition();
        input.setFundCode("00001");
        input.setTransDate("31/10/2018");
        FundDefinition fif_def = new FundDefinition();
        FmrFundBasicInfo fif_basicInfo = new FmrFundBasicInfo();


        try {
            String sql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, fmr.members, f.fund_name, commentary, objective from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code = ? and fmr.trans_date = to_date(?, 'dd/MM/yyyy')";
            fif_def = (FundDefinition) this.jdbcTemplate.queryForObject(sql, new Object[]{input.fundCode, input.transDate}, (RowMapper) new FundDefinitionRowMapper());


            String fif_basicInfo_sql = "select TRANSDATE, ID,fund_code, FUND_TYPE,CATEGORY,LAUNCHDATE,NETASSETS,NETASSETEXFOF,NAV,BENCHMARK,DEALING_DAYS,CUTT_OFF_TIME,PRICING_MECHANISM,\nMANAGEMENT_FEE,FRONT_END_LOAD,TRUSTEE,AUDITOR,ASSET_MANAGER_RATING,RISK_PROF_FUND,perform_ranking,FUND_MANGER,LISTING, COMMENTS from fmr_fund_basic_info t\nwhere fund_code = 00001 and transdate = '31-oct-2018'";


            fif_basicInfo = (FmrFundBasicInfo) this.jdbcTemplate.queryForObject(fif_basicInfo_sql, new Object[0], (RowMapper) new FmrFundBasicInfoMapper());

            String fperformance_sql = "select descp, nvl(B_MONTH1, 0) B_MONTH1, nvl(BYTD, 0) BYTD, nvl(ST_DEV, 0) ST_DEV, nvl(SHARP_RATIO, 0) SHARP_RATIO, nvl(ALPHA, 0) ALPHA\n  from fund_performence\n where FNDID = 00001\n   and trans_date = '31-OCT-2018'";


            List<Map<String, Object>> fif_perform_data = this.jdbcTemplate.queryForList(fperformance_sql);

            List<Map<String, Object>> fif_asset_header = getAssetAllocHeader("00001");
            List<fmr_asset_allocation_model> fif_prev_asset_alloc = getAssetAllocBody("00001");

            String fperformance_sql1 = "select descp, nvl(B_MONTH3, 0) B_MONTH3, nvl(B_MONTH6, 0) B_MONTH6, nvl(B_YEAR1, 0) B_YEAR1, nvl(B_YEAR3, 0) B_YEAR3, nvl(B_YEAR5, 0) B_YEAR5, nvl(B_SINCE, 0) B_SINCE\n  from fund_performence\n where FNDID = 00001\n   and trans_date = '31-OCT-2018'";


            List<Map<String, Object>> fif_perform_data1 = this.jdbcTemplate.queryForList(fperformance_sql1);

            String fmr_tech_info_sql = "select transdate, description, value\n  from fmr_tech_info\n where FUND_CODE = 00001\n   and TRANSDATE = '31-OCT-2018'";


            List<Map<String, Object>> fmr_tech_info_sql_data = this.jdbcTemplate.queryForList(fmr_tech_info_sql);


            barJfree();


            context.setVariable("fif_def", fif_def);
            context.setVariable("fif_basicInfo", fif_basicInfo);
            context.setVariable("fif_perform_data", fif_perform_data);
            context.setVariable("fif_perform_data1", fif_perform_data1);
            context.setVariable("fif_asset_header", fif_asset_header);
            context.setVariable("fassetallocation_data", fif_prev_asset_alloc);
            context.setVariable("fmr_tech_info_sql_data", fmr_tech_info_sql_data);


        } catch (Exception ex) {
            fif_def.setResponse_code(ResponseCodes.FAILURE);
        }

        return context;
    }


    private List<fmr_asset_allocation_model> FillAssetAllocation(List<Map<String, Object>> rows) {
        List<fmr_asset_allocation_model> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            fmr_asset_allocation_model customer = new fmr_asset_allocation_model();
            customer.setCode(row.get("CODE").toString());
            list.add(customer);
        }
        return list;
    }


    private List<Map<String, Object>> getAssetAllocHeader(String fundcode) {
        String fassetallocation_sql = "select to_char(p.aa_trans_date , 'Monthdd,yyyy') prev, to_char(c.aa_trans_date , 'Monthdd,yyyy') curr \n  from fmr_prev_asset_alloocation p, fmr_current_asset_alloocation c\n where p.fund_code = 00001\n   and p.aa_trans_date =\n       last_day(to_date(to_char(add_months(get_system_date, -2), 'YYYYMM'),\n                        'YYYYMM'))\n   and c.aa_trans_date =\n       last_day(to_date(to_char(add_months(get_system_date, -1), 'YYYYMM'),\n                        'YYYYMM'))\n   and rownum = '" + fundcode + "'";


        List<Map<String, Object>> fif_perform_data1 = this.jdbcTemplate.queryForList(fassetallocation_sql);
        return fif_perform_data1;
    }


    private List<fmr_asset_allocation_model> getAssetAllocBody(String fundcode) {
        List<fmr_asset_allocation_model> currlist = new ArrayList<>();
        String currsql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth \n  from fmr_current_asset_alloocation\n where fund_code = '" + fundcode + "'\n   and aa_trans_date =\n       last_day(to_date(to_char(add_months(get_system_date, -1), 'YYYYMM'),\n                        'YYYYMM'))";


        List<Map<String, Object>> currdata = this.jdbcTemplate.queryForList(currsql);
        for (Map<String, Object> row : currdata) {
            fmr_asset_allocation_model customer = new fmr_asset_allocation_model();
            customer.setCode(row.get("CODE").toString());
            customer.setSecName(row.get("AA_ASSET_DESCRIPTION").toString());
            if (row.get("AA_PREVMTH") != null) {
                customer.setPrev_month(row.get("AA_PREVMTH").toString());
            }
            if (row.get("AA_CURRMTH") != null) {
                customer.setCurr_month(row.get("AA_CURRMTH").toString());
            }
            currlist.add(customer);
        }

        String prevsql = "select nvl(code, 0) code, aa_asset_description, nvl(aa_prevmth, 0) aa_prevmth, nvl(aa_currmth, 0) aa_currmth \n  from fmr_prev_asset_alloocation\n where fund_code = 00001\n   and aa_trans_date =\n       last_day(to_date(to_char(add_months(get_system_date, -2), 'YYYYMM'),\n                        'YYYYMM'))";


        List<Map<String, Object>> prevdata = this.jdbcTemplate.queryForList(prevsql);
        for (fmr_asset_allocation_model crow : currlist) {
            for (Map<String, Object> prow : prevdata) {
                String pr = prow.get("CODE").toString();
                String cr = crow.getCode();
                if (cr.equals(pr)) {
                    crow.setPrev_month(prow.get("AA_PREVMTH").toString());
                }
            }
        }
        return currlist;
    }


    public void barJfree() throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.setValue(0.0D, "Fund", "January");
        dataset.setValue(0.0D, "Benchmark", "January");

        dataset.setValue(4.0D, "Fund", "February");
        dataset.setValue(5.0D, "Benchmark", "February");

        dataset.setValue(3.0D, "Fund", "March");
        dataset.setValue(6.0D, "Benchmark", "March");

        dataset.setValue(5.0D, "Fund", "April");
        dataset.setValue(6.0D, "Benchmark", "April");

        dataset.setValue(4.0D, "Fund", "May");
        dataset.setValue(6.0D, "Benchmark", "May");

        dataset.setValue(3.0D, "Fund", "June");
        dataset.setValue(6.0D, "Benchmark", "June");

        dataset.setValue(3.0D, "Fund", "July");
        dataset.setValue(5.0D, "Benchmark", "July");

        JFreeChart chart = ChartFactory.createBarChart("ABL - IF Vs Benchmark (MOM Returns)", "", "", (CategoryDataset) dataset, PlotOrientation.VERTICAL, true, true, false);


        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0.0D);
        br.setSeriesPaint(0, Color.blue.darker());
        br.setSeriesPaint(1, Color.yellow.darker());

        Font font3 = new Font("Dialog", 0, 15);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        CategoryAxis domain = plot.getDomainAxis();
        domain.setLowerMargin(0.25D);
        domain.setUpperMargin(0.25D);
        int width = 640;
        int height = 480;
        File barChart3D = new File("D:\\Users\\mohammad.bassam\\Documents\\OnlinePortal\\src\\main\\resources\\public/barChart3D.jpeg");
        ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
    }


    @GetMapping({"/getReport/{fundcode}"})
    public void getReport(@PathVariable String fundcode, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("----------- getReport ---------------");
    }
}