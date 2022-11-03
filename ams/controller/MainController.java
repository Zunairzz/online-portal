package com.ams.controller;

import com.ams.model.FmrPdfReportModal;
import com.ams.model.FmrPlanPdfReportModal;
import com.ams.utility.MediaReplacedElementFactory;
import com.ams.utility.ReportDbUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Controller
public class MainController {
    private static final String OUTPUT_FILE = "FMR_REPORT.pdf";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = {"/", "/welcome"}, method = {RequestMethod.GET})
    public String welcomePage(Model model) throws IOException {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = {"/fmr"}, method = {RequestMethod.GET})
    public String fmrPage(Model model) throws IOException {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "fmrSection";
    }

    @RequestMapping(value = {"/fmrExportPdf"}, method = {RequestMethod.GET})
    public String fmrExportPdf(ModelMap model, HttpServletRequest request, @RequestParam(value = "transDate", required = false) String transDate) {
        String contextPath = request.getRealPath("/");
        String jasperFileName = contextPath + "/Content/" + "FMR_REPORT.pdf";
        if ((new File(jasperFileName)).exists()) {
            (new File(jasperFileName)).delete();
        }
        try {
            ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
            emailTemplateResolver.setSuffix(".html");
            emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
            emailTemplateResolver.setCacheable(false);
            emailTemplateResolver.setCharacterEncoding("UTF-8");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver((ITemplateResolver) emailTemplateResolver);
            List<FmrPdfReportModal> fif_report_data = new ArrayList<>(), eqf_report_data = new ArrayList<>(), cpf_report_data = new ArrayList<>();
            List<FmrPlanPdfReportModal> plan_report_data = new ArrayList<>(), pf_report_data = new ArrayList<>();
            Context context = new Context();
            ReportDbUtil rpt = new ReportDbUtil();
            rpt.FixedIncomeModelMap(fif_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
            rpt.EqfModalMap(eqf_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
            rpt.FundPlanModalMap(plan_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
            File ff = new File(jasperFileName);
            ff.createNewFile();
            String html = templateEngine.process("fmrPdfTemplate", (IContext) context);
            OutputStream outputStream = new FileOutputStream(jasperFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.getSharedContext().setReplacedElementFactory((ReplacedElementFactory) new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), contextPath));
            renderer.setDocumentFromString(html);
            renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\impact.TTF", true);
            renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibri.TTF", true);
            renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\arial.TTF", true);
            renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibri.ttf", true);
            renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibrib.ttf", true);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();
            return "fmrPdfTemplate";
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(com.ams.controller.MainController.class.getName()).log(Level.SEVERE, (String) null, ex);
            return "fmrPdfTemplate";
        }
    }

    private String getFmrDate(String transDate) {
        String dt = "";
        String fperformance_sql = "select to_char(to_date('" + transDate + "' , 'dd/MM/yyyy') , 'MONTHYYYY') fmr_trans_date from dual ";
        List<Map<String, Object>> lst = this.jdbcTemplate.queryForList(fperformance_sql);
        for (Map<String, Object> f : lst) {
            dt = f.get("fmr_trans_date".toUpperCase()).toString();
        }
        return dt;
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String loginPage(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = {"/logoutSuccessful"}, method = {RequestMethod.GET})
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "welcomePage";
    }
}