/*     */ package WEB-INF.classes.com.ams.controller;
/*     */ 
/*     */ import com.ams.model.FmrPdfReportModal;
/*     */ import com.ams.model.FmrPlanPdfReportModal;
/*     */ import com.ams.utility.MediaReplacedElementFactory;
/*     */ import com.ams.utility.ReportDbUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.thymeleaf.TemplateEngine;
/*     */ import org.thymeleaf.context.Context;
/*     */ import org.thymeleaf.context.IContext;
/*     */ import org.thymeleaf.templatemode.TemplateMode;
/*     */ import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
/*     */ import org.thymeleaf.templateresolver.ITemplateResolver;
/*     */ import org.xhtmlrenderer.extend.ReplacedElementFactory;
/*     */ import org.xhtmlrenderer.pdf.ITextRenderer;
/*     */ 
/*     */ @Controller
/*     */ public class MainController
/*     */ {
/*     */   private static final String OUTPUT_FILE = "FMR_REPORT.pdf";
/*     */   @Autowired
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   
/*     */   @RequestMapping(value = {"/", "/welcome"}, method = {RequestMethod.GET})
/*     */   public String welcomePage(Model model) throws IOException {
/*  43 */     model.addAttribute("title", "Welcome");
/*  44 */     model.addAttribute("message", "This is welcome page!");
/*  45 */     return "welcomePage";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/fmr"}, method = {RequestMethod.GET})
/*     */   public String fmrPage(Model model) throws IOException {
/*  50 */     model.addAttribute("title", "Welcome");
/*  51 */     model.addAttribute("message", "This is welcome page!");
/*  52 */     return "fmrSection";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fmrExportPdf"}, method = {RequestMethod.GET})
/*     */   public String fmrExportPdf(ModelMap model, HttpServletRequest request, @RequestParam(value = "transDate", required = false) String transDate) {
/*  60 */     String contextPath = request.getRealPath("/");
/*  61 */     String jasperFileName = contextPath + "/Content/" + "FMR_REPORT.pdf";
/*  62 */     if ((new File(jasperFileName)).exists()) {
/*  63 */       (new File(jasperFileName)).delete();
/*     */     }
/*     */     try {
/*  66 */       ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
/*  67 */       emailTemplateResolver.setPrefix("/templates/");
/*  68 */       emailTemplateResolver.setSuffix(".html");
/*  69 */       emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
/*  70 */       emailTemplateResolver.setCacheable(false);
/*  71 */       emailTemplateResolver.setCharacterEncoding("UTF-8");
/*  72 */       TemplateEngine templateEngine = new TemplateEngine();
/*  73 */       templateEngine.setTemplateResolver((ITemplateResolver)emailTemplateResolver);
/*  74 */       List<FmrPdfReportModal> fif_report_data = new ArrayList<>(), eqf_report_data = new ArrayList<>(), cpf_report_data = new ArrayList<>();
/*  75 */       List<FmrPlanPdfReportModal> plan_report_data = new ArrayList<>(), pf_report_data = new ArrayList<>();
/*  76 */       Context context = new Context();
/*     */       
/*  78 */       ReportDbUtil rpt = new ReportDbUtil();
/*  79 */       rpt.FixedIncomeModelMap(fif_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
/*  80 */       rpt.EqfModalMap(eqf_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
/*  81 */       rpt.FundPlanModalMap(plan_report_data, null, transDate, contextPath, context, this.jdbcTemplate);
/*     */       
/*  83 */       File ff = new File(jasperFileName);
/*  84 */       ff.createNewFile();
/*  85 */       String html = templateEngine.process("fmrPdfTemplate", (IContext)context);
/*  86 */       OutputStream outputStream = new FileOutputStream(jasperFileName);
/*  87 */       ITextRenderer renderer = new ITextRenderer();
/*  88 */       renderer.getSharedContext().setReplacedElementFactory((ReplacedElementFactory)new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), contextPath));
/*  89 */       renderer.setDocumentFromString(html);
/*  90 */       renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\impact.TTF", true);
/*  91 */       renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibri.TTF", true);
/*  92 */       renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\arial.TTF", true);
/*     */       
/*  94 */       renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibri.ttf", true);
/*  95 */       renderer.getFontResolver().addFont("C:\\WINDOWS\\FONTS\\calibrib.ttf", true);
/*  96 */       renderer.layout();
/*  97 */       renderer.createPDF(outputStream);
/*  98 */       outputStream.close();
/*  99 */       return "fmrPdfTemplate";
/* 100 */     } catch (Exception ex) {
/* 101 */       ex.printStackTrace();
/* 102 */       Logger.getLogger(com.ams.controller.MainController.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       
/* 104 */       return "fmrPdfTemplate";
/*     */     } 
/*     */   }
/*     */   private String getFmrDate(String transDate) {
/* 108 */     String dt = "";
/* 109 */     String fperformance_sql = "select to_char(to_date('" + transDate + "' , 'dd/MM/yyyy') , 'MONTHYYYY') fmr_trans_date from dual ";
/* 110 */     List<Map<String, Object>> lst = this.jdbcTemplate.queryForList(fperformance_sql);
/* 111 */     for (Map<String, Object> f : lst) {
/* 112 */       dt = f.get("fmr_trans_date".toUpperCase()).toString();
/*     */     }
/* 114 */     return dt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
/*     */   public String loginPage(Model model) {
/* 277 */     return "loginPage";
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/logoutSuccessful"}, method = {RequestMethod.GET})
/*     */   public String logoutSuccessfulPage(Model model) {
/* 282 */     model.addAttribute("title", "Logout");
/* 283 */     return "welcomePage";
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\controller\MainController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */