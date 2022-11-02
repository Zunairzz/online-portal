/*     */ package WEB-INF.classes.com.ams.utility;
/*     */ 
/*     */ import com.ams.model.FmrPdfReportModal;
/*     */ import com.ams.utility.ChartUtility;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.io.File;
/*     */ import java.text.DateFormat;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.CategoryLabelPositions;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.SubCategoryAxis;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.DrawingSupplier;
/*     */ import org.jfree.chart.plot.Marker;
/*     */ import org.jfree.chart.plot.PiePlot;
/*     */ import org.jfree.chart.plot.PiePlot3D;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.ValueMarker;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.category.BarRenderer;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ import org.jfree.data.time.Day;
/*     */ import org.jfree.data.time.DynamicTimeSeriesCollection;
/*     */ import org.jfree.data.time.RegularTimePeriod;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmsFundPerformance
/*     */ {
/*     */   public static String fundBenchMarkData(FmrPdfReportModal fpModal, String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
/*  55 */     String title = "";
/*  56 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*  57 */     String fmr_barchart_sql = "select to_char(g.transdate , 'MON-YY') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.transdate between add_months(to_date('" + transDate + "' , 'dd/MM/yyyy'), '-12') and\n       to_date('" + transDate + "' , 'dd/MM/yyyy') and bm_return is not null and fund_return is not null \n order by g.transdate";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     System.out.println("fmr_barchart_sql : " + fmr_barchart_sql);
/*  67 */     List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
/*  68 */     if (list.size() > 12) list.remove(0); 
/*  69 */     DecimalFormat df = new DecimalFormat("0.00");
/*  70 */     for (Map<String, Object> dt : list) {
/*     */       
/*  72 */       dataset.setValue(Double.parseDouble(df.format(Float.valueOf(dt.get("FUND_RETURN").toString()))), dt.get("TITLE").toString(), dt.get("MONTH").toString());
/*  73 */       dataset.setValue(Double.parseDouble(df.format(Float.valueOf(dt.get("BM_RETURN").toString()))), fpModal.getBenchMarkDesc(), dt.get("MONTH").toString());
/*  74 */       title = dt.get("TITLE").toString();
/*     */     } 
/*     */     
/*  77 */     JFreeChart chart = ChartFactory.createBarChart(title + " Vs Benchmark (MOM Returns %)", "", "", (CategoryDataset)dataset, PlotOrientation.VERTICAL, true, true, false);
/*     */ 
/*     */     
/*  80 */     chart.setBackgroundPaint(Color.WHITE);
/*  81 */     chart.getTitle().setPaint(Color.BLACK);
/*  82 */     CategoryPlot plot = chart.getCategoryPlot();
/*  83 */     plot.setBackgroundPaint(Color.WHITE);
/*  84 */     BarRenderer br = (BarRenderer)plot.getRenderer();
/*  85 */     br.setItemMargin(0.0D);
/*  86 */     br.setSeriesPaint(0, new Color(31, 74, 126));
/*  87 */     br.setSeriesPaint(1, new Color(227, 110, 37));
/*  88 */     Font font3 = new Font("Dialog", 1, 15);
/*  89 */     plot.getDomainAxis().setLabelFont(font3);
/*  90 */     plot.getRangeAxis().setLabelFont(font3);
/*  91 */     CategoryAxis domain = plot.getDomainAxis();
/*  92 */     domain.setLowerMargin(0.25D);
/*  93 */     domain.setUpperMargin(0.25D);
/*  94 */     domain.setLabelFont(font3);
/*  95 */     SubCategoryAxis domainAxis = new SubCategoryAxis(" ");
/*  96 */     domainAxis.setCategoryMargin(0.15D);
/*  97 */     domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
/*  98 */     domainAxis.setTickLabelFont(font3);
/*  99 */     plot.setDomainAxis((CategoryAxis)domainAxis);
/* 100 */     int width = 780;
/* 101 */     int height = 480;
/* 102 */     String fileName = "barChart3D_" + fundCode + ".jpeg";
/* 103 */     File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
/* 104 */     ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
/* 105 */     return fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fundSectorAllocationData(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate, int upperBound) throws Exception {
/*     */     String fmr_barchart_sql;
/* 111 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*     */     
/* 113 */     if (fundCode.equals("00002") || fundCode.equals("00008") || fundCode.equals("00020") || fundCode.equals("00025")) {
/* 114 */       fmr_barchart_sql = "select * from ( select to_char(g.trans_date, 'MM-dd-yyyy') month,\n       g.sector_name ,\n       g.sector_percentage ,\n       decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n  from fmr_sector_allocation g\n where g.fund_code = '" + fundCode + "' and upper(g.sector_name) != upper('Bank Balance & Others') \n   and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ) where rownum <= 5 union all select to_char(fsa.trans_date, 'MM-dd-yyyy') as month, 'Other Sectors' as sector_name, sum(fsa.sector_percentage)  as sector_percentage,  decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n from fmr_sector_allocation fsa where fsa.sector_name not in ( select a.* from (select  g.sector_name  from fmr_sector_allocation g  where g.fund_code = '" + fundCode + "' and upper(g.sector_name) != upper('Bank Balance & Others') and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ) a where rownum <=5) and fsa.fund_code = '" + fundCode + "' and upper(fsa.sector_name) != upper('Bank Balance & Others') and fsa.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage > 0  group by to_char(fsa.trans_date, 'MM-dd-yyyy') ";
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
/*     */     }
/*     */     else {
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
/* 169 */       fmr_barchart_sql = "select to_char(g.trans_date, 'MM-dd-yyyy') month,\n       g.sector_name ,\n       g.sector_percentage ,\n       decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n  from fmr_sector_allocation g\n where g.fund_code = '" + fundCode + "'\n   and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     System.out.println("fmr_barchart_sql Fund Code " + fundCode + ": " + fmr_barchart_sql);
/* 179 */     List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
/* 180 */     for (Map<String, Object> dt : list) {
/* 181 */       float vv = Float.valueOf(dt.get("SECTOR_PERCENTAGE").toString()).floatValue();
/* 182 */       dataset.setValue(vv, dt.get("TITLE").toString(), dt.get("SECTOR_NAME").toString());
/*     */     } 
/*     */     
/* 185 */     JFreeChart chart = ChartFactory.createBarChart("", "", "", (CategoryDataset)dataset, PlotOrientation.HORIZONTAL, false, true, false);
/* 186 */     chart.setBackgroundPaint(Color.WHITE);
/* 187 */     chart.getTitle().setPaint(Color.BLACK);
/* 188 */     CategoryPlot plot = chart.getCategoryPlot();
/* 189 */     plot.setBackgroundPaint(Color.WHITE);
/*     */     
/* 191 */     BarRenderer br = (BarRenderer)plot.getRenderer();
/* 192 */     br.setDrawBarOutline(false);
/* 193 */     br.setItemMargin(20.0D);
/* 194 */     br.setSeriesPaint(0, new Color(227, 110, 37));
/* 195 */     br.setSeriesItemLabelGenerator(0, (CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator("  {2}", NumberFormat.getInstance()));
/* 196 */     br.setSeriesCreateEntities(100, Boolean.valueOf(true));
/* 197 */     br.setSeriesItemLabelsVisible(0, true);
/* 198 */     chart.getCategoryPlot().setRenderer((CategoryItemRenderer)br);
/* 199 */     plot.getRangeAxis().setRange(0.0D, upperBound);
/*     */     
/* 201 */     Font font3 = new Font("Dialog", 1, 15);
/* 202 */     plot.getDomainAxis().setLabelFont(font3);
/* 203 */     plot.getRangeAxis().setLabelFont(font3);
/*     */     
/* 205 */     CategoryAxis domain = plot.getDomainAxis();
/* 206 */     domain.setLowerMargin(0.25D);
/* 207 */     domain.setUpperMargin(0.25D);
/* 208 */     domain.setLabelFont(font3);
/*     */     
/* 210 */     SubCategoryAxis domainAxis = new SubCategoryAxis(" ");
/* 211 */     domainAxis.setCategoryMargin(0.15D);
/* 212 */     domainAxis.setTickLabelFont(font3);
/* 213 */     plot.setDomainAxis((CategoryAxis)domainAxis);
/*     */     
/* 215 */     int width = 820;
/* 216 */     int height = 430;
/* 217 */     String fileName = "barChart3D_" + fundCode + ".jpeg";
/* 218 */     File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
/* 219 */     ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
/* 220 */     return fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fundBenchMarkTimeSeries(FmrPdfReportModal fpModal, String fundCode, String fundShortName, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws ParseException {
/* 225 */     String title = "";
/* 226 */     String fmr_barchart_sql = "select to_char(add_months(to_date(g.transdate, 'dd-MM-yy'), '-1'), 'dd/MM/yyyy') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name\n          from fund f\n         where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.bm_return is not null and g.fund_return is not null and g.transdate between\n       add_months(to_date('" + transDate + "', 'dd/MM/yyyy'), '-11') and\n       to_date('" + transDate + "', 'dd/MM/yyyy') group by g.transdate, g.bm_return, g.fund_return  order by g.transdate";
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
/* 239 */     if (fundCode.equals("000255")) {
/* 240 */       fmr_barchart_sql = "select to_char(g.transdate,'dd/MM/yyyy') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name\n          from fund f\n         where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.bm_return is not null and g.fund_return is not null  group by g.transdate, g.bm_return, g.fund_return  order by g.transdate";
/*     */     }
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
/* 252 */     List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
/* 253 */     TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 254 */     TimeSeries series1 = new TimeSeries(fpModal.getBenchMarkDesc());
/* 255 */     TimeSeries series2 = new TimeSeries(fundShortName);
/* 256 */     for (Map<String, Object> dt : list) {
/* 257 */       DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
/* 258 */       title = dt.get("TITLE").toString();
/* 259 */       Date date = format.parse(dt.get("MONTH").toString());
/* 260 */       series1.addOrUpdate((RegularTimePeriod)new Day(date), Math.round(Float.valueOf(dt.get("BM_RETURN").toString()).floatValue()));
/* 261 */       series2.addOrUpdate((RegularTimePeriod)new Day(date), Math.round(Float.valueOf(dt.get("FUND_RETURN").toString()).floatValue()));
/*     */     } 
/* 263 */     tsc.addSeries(series1);
/* 264 */     tsc.addSeries(series2);
/* 265 */     TimeSeriesCollection timeSeriesCollection1 = tsc;
/* 266 */     JFreeChart chart = ChartFactory.createTimeSeriesChart(title + " vs BENCHMARK (12m ROLLING RETURNS)", "", "", (XYDataset)timeSeriesCollection1, true, true, false);
/* 267 */     ValueMarker marker = new ValueMarker(0.0D);
/* 268 */     marker.setPaint(Color.black);
/* 269 */     XYPlot plot = chart.getXYPlot();
/* 270 */     plot.addRangeMarker((Marker)marker);
/* 271 */     plot.setBackgroundPaint(Color.WHITE);
/* 272 */     Font font3 = new Font("Dialog", 1, 15);
/* 273 */     plot.getDomainAxis().setLabelFont(font3);
/* 274 */     plot.getRangeAxis().setLabelFont(font3);
/* 275 */     XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
/* 276 */     renderer.setSeriesPaint(0, new Color(31, 74, 126));
/* 277 */     renderer.setSeriesPaint(1, new Color(227, 110, 37));
/* 278 */     renderer.setSeriesStroke(1, new BasicStroke(3.0F));
/* 279 */     renderer.setSeriesStroke(0, new BasicStroke(3.0F));
/* 280 */     NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
/* 281 */     DecimalFormat pctFormat = new DecimalFormat(" #,##0.00 '%'");
/* 282 */     rangeAxis.setNumberFormatOverride(pctFormat);
/* 283 */     if (fundCode.equals("00024") || fundCode.equals("00023") || fundCode.equals("00025")) {
/* 284 */       rangeAxis.setRange(-8.0D, 8.0D);
/*     */     } else {
/* 286 */       rangeAxis.setRange(-20.0D, 20.0D);
/*     */     } 
/* 288 */     rangeAxis.setLabelFont(font3);
/* 289 */     int width = 840;
/* 290 */     int height = 480;
/* 291 */     String fileName = "timeseries_" + fundCode + ".jpeg";
/*     */     try {
/* 293 */       File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
/* 294 */       ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
/* 295 */     } catch (Exception exception) {}
/*     */     
/* 297 */     return fileName;
/*     */   }
/*     */   
/* 300 */   private static float lastValue = 100.0F;
/*     */   
/*     */   public static void adddata(DynamicTimeSeriesCollection dataset) {
/* 303 */     double factor = 0.9D + 0.2D * Math.random();
/* 304 */     lastValue *= (float)factor;
/* 305 */     float[] float_array = new float[1];
/* 306 */     float_array[0] = lastValue;
/* 307 */     System.out.println("lastValue is " + lastValue);
/* 308 */     dataset.advanceTime();
/* 309 */     dataset.appendData(float_array);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fundPortfolioQualityData(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
/* 314 */     DefaultPieDataset dataset = new DefaultPieDataset();
/* 315 */     JFreeChart chart = ChartFactory.createPieChart3D("", (PieDataset)dataset, false, true, false);
/* 316 */     chart.setBackgroundPaint(Color.WHITE);
/* 317 */     chart.setBorderVisible(false);
/* 318 */     PiePlot3D plot = (PiePlot3D)chart.getPlot();
/* 319 */     plot.setBackgroundPaint(Color.WHITE);
/* 320 */     plot.setOutlinePaint(null);
/* 321 */     plot.setStartAngle(270.0D);
/* 322 */     plot.setInteriorGap(0.02D);
/* 323 */     plot.setDrawingSupplier((DrawingSupplier)new ChartUtility());
/* 324 */     plot.setOutlineVisible(false);
/* 325 */     plot.setShadowPaint(null);
/* 326 */     plot.setLabelOutlinePaint(null);
/* 327 */     plot.setLabelShadowPaint(null);
/* 328 */     plot.setLabelBackgroundPaint(null);
/* 329 */     plot.setSectionOutlinesVisible(false);
/*     */     
/* 331 */     String fmr_piechart_sql = "select rating, round(sum(perofnav),2) vv\n  from FMR_CreditQuality\n where fund_code = '" + fundCode + "'\n   and transdate = to_date('" + transDate + "' , 'dd/MM/yyyy') \n   and perofnav > 0 and rating is not null \n group by rating order by vv desc";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_piechart_sql);
/* 338 */     for (Map<String, Object> dt : list) {
/* 339 */       dataset.setValue(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", new Double(dt.get("VV").toString()));
/*     */     }
/* 341 */     int width = 500;
/* 342 */     int height = 200;
/* 343 */     String fileName = "pie_Chart3D_" + fundCode + ".jpeg";
/* 344 */     File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
/* 345 */     ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
/* 346 */     return fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fundPortfolioQualityData1(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
/* 351 */     DefaultPieDataset myExplodedPieChartData = new DefaultPieDataset();
/* 352 */     JFreeChart myExplodedPieChart = ChartFactory.createPieChart("", (PieDataset)myExplodedPieChartData, false, true, false);
/* 353 */     PiePlot ColorConfigurator = (PiePlot)myExplodedPieChart.getPlot();
/* 354 */     ColorConfigurator.setDrawingSupplier((DrawingSupplier)new ChartUtility());
/* 355 */     myExplodedPieChart.setBackgroundPaint(Color.WHITE);
/* 356 */     myExplodedPieChart.setBorderVisible(false);
/* 357 */     ColorConfigurator.setBackgroundPaint(Color.WHITE);
/* 358 */     ColorConfigurator.setOutlinePaint(null);
/* 359 */     String fmr_piechart_sql = "select rating, round(sum(perofnav),2) vv\n  from FMR_CreditQuality\n where fund_code = '" + fundCode + "'\n   and transdate = to_date('" + transDate + "' , 'dd/MM/yyyy') \n   and perofnav > 0 and rating is not null \n group by rating order by vv desc";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_piechart_sql);
/* 366 */     double dd = 0.05D;
/* 367 */     for (Map<String, Object> dt : list) {
/* 368 */       double vv = (new Double(dt.get("VV").toString())).doubleValue();
/* 369 */       if (vv < 5.0D) {
/* 370 */         vv += 3.0D;
/*     */       }
/* 372 */       myExplodedPieChartData.setValue(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", vv);
/* 373 */       ColorConfigurator.setExplodePercent(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", dd);
/* 374 */       dd += 0.05D;
/*     */     } 
/* 376 */     ColorConfigurator.setStartAngle(270.0D);
/* 377 */     ColorConfigurator.setInteriorGap(0.02D);
/* 378 */     ColorConfigurator.setDrawingSupplier((DrawingSupplier)new ChartUtility());
/* 379 */     ColorConfigurator.setOutlineVisible(false);
/* 380 */     ColorConfigurator.setShadowPaint(null);
/* 381 */     ColorConfigurator.setLabelOutlinePaint(null);
/* 382 */     ColorConfigurator.setLabelShadowPaint(null);
/* 383 */     ColorConfigurator.setLabelBackgroundPaint(null);
/* 384 */     ColorConfigurator.setSectionOutlinesVisible(false);
/*     */     
/* 386 */     int width = 700;
/* 387 */     int height = 400;
/* 388 */     String fileName = "pie_Chart3D_" + fundCode + ".jpeg";
/* 389 */     File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
/* 390 */     ChartUtilities.saveChartAsJPEG(barChart3D, myExplodedPieChart, width, height);
/* 391 */     return fileName;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\AmsFundPerformance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */