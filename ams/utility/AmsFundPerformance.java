package com.ams.utility;


import com.ams.model.FmrPdfReportModal;
import com.ams.utility.ChartUtility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.jdbc.core.JdbcTemplate;


public class AmsFundPerformance {

    public static String fundBenchMarkData(FmrPdfReportModal fpModal, String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
        String title = "";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String fmr_barchart_sql = "select to_char(g.transdate , 'MON-YY') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.transdate between add_months(to_date('" + transDate + "' , 'dd/MM/yyyy'), '-12') and\n       to_date('" + transDate + "' , 'dd/MM/yyyy') and bm_return is not null and fund_return is not null \n order by g.transdate";


        System.out.println("fmr_barchart_sql : " + fmr_barchart_sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
        if (list.size() > 12) list.remove(0);
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map<String, Object> dt : list) {

            dataset.setValue(Double.parseDouble(df.format(Float.valueOf(dt.get("FUND_RETURN").toString()))), dt.get("TITLE").toString(), dt.get("MONTH").toString());
            dataset.setValue(Double.parseDouble(df.format(Float.valueOf(dt.get("BM_RETURN").toString()))), fpModal.getBenchMarkDesc(), dt.get("MONTH").toString());
            title = dt.get("TITLE").toString();

        }

        JFreeChart chart = ChartFactory.createBarChart(title + " Vs Benchmark (MOM Returns %)", "", "", (CategoryDataset) dataset, PlotOrientation.VERTICAL, true, true, false);


        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0.0D);
        br.setSeriesPaint(0, new Color(31, 74, 126));
        br.setSeriesPaint(1, new Color(227, 110, 37));
        Font font3 = new Font("Dialog", 1, 15);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);
        CategoryAxis domain = plot.getDomainAxis();
        domain.setLowerMargin(0.25D);
        domain.setUpperMargin(0.25D);
        domain.setLabelFont(font3);
        SubCategoryAxis domainAxis = new SubCategoryAxis(" ");
        domainAxis.setCategoryMargin(0.15D);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(font3);
        plot.setDomainAxis((CategoryAxis) domainAxis);
        int width = 780;
        int height = 480;
        String fileName = "barChart3D_" + fundCode + ".jpeg";
        File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
        ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
        return fileName;

    }


    public static String fundSectorAllocationData(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate, int upperBound) throws Exception {

        String fmr_barchart_sql;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (fundCode.equals("00002") || fundCode.equals("00008") || fundCode.equals("00020") || fundCode.equals("00025")) {
            fmr_barchart_sql = "select * from ( select to_char(g.trans_date, 'MM-dd-yyyy') month,\n       g.sector_name ,\n       g.sector_percentage ,\n       decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n  from fmr_sector_allocation g\n where g.fund_code = '" + fundCode + "' and upper(g.sector_name) != upper('Bank Balance & Others') \n   and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ) where rownum <= 5 union all select to_char(fsa.trans_date, 'MM-dd-yyyy') as month, 'Other Sectors' as sector_name, sum(fsa.sector_percentage)  as sector_percentage,  decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n from fmr_sector_allocation fsa where fsa.sector_name not in ( select a.* from (select  g.sector_name  from fmr_sector_allocation g  where g.fund_code = '" + fundCode + "' and upper(g.sector_name) != upper('Bank Balance & Others') and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ) a where rownum <=5) and fsa.fund_code = '" + fundCode + "' and upper(fsa.sector_name) != upper('Bank Balance & Others') and fsa.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage > 0  group by to_char(fsa.trans_date, 'MM-dd-yyyy') ";


        } else {


            fmr_barchart_sql = "select to_char(g.trans_date, 'MM-dd-yyyy') month,\n       g.sector_name ,\n       g.sector_percentage ,\n       decode('" + fundCode + "', '10003' , 'EQUITY SUB-FUND', '20003' , 'EQUITY SUB-FUND' ,(select f.fund_short_name from fund f where f.fund_code = '" + fundCode + "')) as title\n  from fmr_sector_allocation g\n where g.fund_code = '" + fundCode + "'\n   and g.trans_date = to_date('" + transDate + "', 'dd/MM/yyyy') and sector_percentage>0  order by g.sector_percentage desc ";

        }


        System.out.println("fmr_barchart_sql Fund Code " + fundCode + ": " + fmr_barchart_sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
        for (Map<String, Object> dt : list) {
            float vv = Float.valueOf(dt.get("SECTOR_PERCENTAGE").toString()).floatValue();
            dataset.setValue(vv, dt.get("TITLE").toString(), dt.get("SECTOR_NAME").toString());

        }

        JFreeChart chart = ChartFactory.createBarChart("", "", "", (CategoryDataset) dataset, PlotOrientation.HORIZONTAL, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);

        chart.getTitle().setPaint(Color.BLACK);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(Color.WHITE);


        BarRenderer br = (BarRenderer) plot.getRenderer();

        br.setDrawBarOutline(false);
        br.setItemMargin(20.0D);
        br.setSeriesPaint(0, new Color(227, 110, 37));
        br.setSeriesItemLabelGenerator(0, (CategoryItemLabelGenerator) new StandardCategoryItemLabelGenerator("  {2}", NumberFormat.getInstance()));
        br.setSeriesCreateEntities(100, Boolean.valueOf(true));
        br.setSeriesItemLabelsVisible(0, true);
        chart.getCategoryPlot().setRenderer((CategoryItemRenderer) br);
        plot.getRangeAxis().setRange(0.0D, upperBound);

        Font font3 = new Font("Dialog", 1, 15);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);

        CategoryAxis domain = plot.getDomainAxis();
        domain.setLowerMargin(0.25D);
        domain.setUpperMargin(0.25D);
        domain.setLabelFont(font3);

        SubCategoryAxis domainAxis = new SubCategoryAxis(" ");
        domainAxis.setCategoryMargin(0.15D);
        domainAxis.setTickLabelFont(font3);
        plot.setDomainAxis((CategoryAxis) domainAxis);

        int width = 820;
        int height = 430;
        String fileName = "barChart3D_" + fundCode + ".jpeg";
        File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
        ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
        return fileName;

    }


    public static String fundBenchMarkTimeSeries(FmrPdfReportModal fpModal, String fundCode, String fundShortName, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws ParseException {
        String title = "";
        String fmr_barchart_sql = "select to_char(add_months(to_date(g.transdate, 'dd-MM-yy'), '-1'), 'dd/MM/yyyy') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name\n          from fund f\n         where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.bm_return is not null and g.fund_return is not null and g.transdate between\n       add_months(to_date('" + transDate + "', 'dd/MM/yyyy'), '-11') and\n       to_date('" + transDate + "', 'dd/MM/yyyy') group by g.transdate, g.bm_return, g.fund_return  order by g.transdate";


        if (fundCode.equals("000255")) {
            fmr_barchart_sql = "select to_char(g.transdate,'dd/MM/yyyy') month,\n       g.bm_return,\n       g.fund_return,\n       (select f.fund_short_name\n          from fund f\n         where f.fund_code = '" + fundCode + "') as title\n  from FMR_FUND_PERFORMENCE_GRAPH g\n where g.fund_code = '" + fundCode + "'\n   and g.bm_return is not null and g.fund_return is not null  group by g.transdate, g.bm_return, g.fund_return  order by g.transdate";

        }


        List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_barchart_sql);
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries series1 = new TimeSeries(fpModal.getBenchMarkDesc());
        TimeSeries series2 = new TimeSeries(fundShortName);
        for (Map<String, Object> dt : list) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            title = dt.get("TITLE").toString();
            Date date = format.parse(dt.get("MONTH").toString());
            series1.addOrUpdate((RegularTimePeriod) new Day(date), Math.round(Float.valueOf(dt.get("BM_RETURN").toString()).floatValue()));
            series2.addOrUpdate((RegularTimePeriod) new Day(date), Math.round(Float.valueOf(dt.get("FUND_RETURN").toString()).floatValue()));

        }
        tsc.addSeries(series1);
        tsc.addSeries(series2);
        TimeSeriesCollection timeSeriesCollection1 = tsc;
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title + " vs BENCHMARK (12m ROLLING RETURNS)", "", "", (XYDataset) timeSeriesCollection1, true, true, false);
        ValueMarker marker = new ValueMarker(0.0D);
        marker.setPaint(Color.black);
        XYPlot plot = chart.getXYPlot();
        plot.addRangeMarker((Marker) marker);
        plot.setBackgroundPaint(Color.WHITE);
        Font font3 = new Font("Dialog", 1, 15);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(31, 74, 126));
        renderer.setSeriesPaint(1, new Color(227, 110, 37));
        renderer.setSeriesStroke(1, new BasicStroke(3.0F));
        renderer.setSeriesStroke(0, new BasicStroke(3.0F));
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        DecimalFormat pctFormat = new DecimalFormat(" #,##0.00 '%'");
        rangeAxis.setNumberFormatOverride(pctFormat);
        if (fundCode.equals("00024") || fundCode.equals("00023") || fundCode.equals("00025")) {
            rangeAxis.setRange(-8.0D, 8.0D);

        } else {
            rangeAxis.setRange(-20.0D, 20.0D);

        }
        rangeAxis.setLabelFont(font3);
        int width = 840;
        int height = 480;
        String fileName = "timeseries_" + fundCode + ".jpeg";

        try {
            File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
            ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
        } catch (Exception exception) {
        }

        return fileName;

    }


    private static float lastValue = 100.0F;


    public static void adddata(DynamicTimeSeriesCollection dataset) {
        double factor = 0.9D + 0.2D * Math.random();
        lastValue *= (float) factor;
        float[] float_array = new float[1];
        float_array[0] = lastValue;
        System.out.println("lastValue is " + lastValue);
        dataset.advanceTime();
        dataset.appendData(float_array);

    }


    public static String fundPortfolioQualityData(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
        DefaultPieDataset dataset = new DefaultPieDataset();
        JFreeChart chart = ChartFactory.createPieChart3D("", (PieDataset) dataset, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(null);
        plot.setStartAngle(270.0D);
        plot.setInteriorGap(0.02D);
        plot.setDrawingSupplier((DrawingSupplier) new ChartUtility());
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(null);
        plot.setSectionOutlinesVisible(false);

        String fmr_piechart_sql = "select rating, round(sum(perofnav),2) vv\n  from FMR_CreditQuality\n where fund_code = '" + fundCode + "'\n   and transdate = to_date('" + transDate + "' , 'dd/MM/yyyy') \n   and perofnav > 0 and rating is not null \n group by rating order by vv desc";


        List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_piechart_sql);
        for (Map<String, Object> dt : list) {
            339
            dataset.setValue(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", new Double(dt.get("VV").toString()));

        }
        int width = 500;
        int height = 200;
        String fileName = "pie_Chart3D_" + fundCode + ".jpeg";
        File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
        ChartUtilities.saveChartAsJPEG(barChart3D, chart, width, height);
        return fileName;

    }


    public static String fundPortfolioQualityData1(String fundCode, String transDate, String contextPath, JdbcTemplate jdbcTemplate) throws Exception {
        DefaultPieDataset myExplodedPieChartData = new DefaultPieDataset();
        JFreeChart myExplodedPieChart = ChartFactory.createPieChart("", (PieDataset) myExplodedPieChartData, false, true, false);
        PiePlot ColorConfigurator = (PiePlot) myExplodedPieChart.getPlot();
        ColorConfigurator.setDrawingSupplier((DrawingSupplier) new ChartUtility());
        myExplodedPieChart.setBackgroundPaint(Color.WHITE);
        myExplodedPieChart.setBorderVisible(false);
        ColorConfigurator.setBackgroundPaint(Color.WHITE);
        ColorConfigurator.setOutlinePaint(null);
        String fmr_piechart_sql = "select rating, round(sum(perofnav),2) vv\n  from FMR_CreditQuality\n where fund_code = '" + fundCode + "'\n   and transdate = to_date('" + transDate + "' , 'dd/MM/yyyy') \n   and perofnav > 0 and rating is not null \n group by rating order by vv desc";


        List<Map<String, Object>> list = jdbcTemplate.queryForList(fmr_piechart_sql);
        double dd = 0.05D;
        for (Map<String, Object> dt : list) {
            double vv = (new Double(dt.get("VV").toString())).doubleValue();
            if (vv < 5.0D) {
                vv += 3.0D;

            }
            myExplodedPieChartData.setValue(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", vv);
            ColorConfigurator.setExplodePercent(dt.get("RATING").toString() + " " + dt.get("VV").toString() + "%", dd);
            dd += 0.05D;

        }
        ColorConfigurator.setStartAngle(270.0D);
        ColorConfigurator.setInteriorGap(0.02D);
        ColorConfigurator.setDrawingSupplier((DrawingSupplier) new ChartUtility());
        ColorConfigurator.setOutlineVisible(false);
        ColorConfigurator.setShadowPaint(null);
        ColorConfigurator.setLabelOutlinePaint(null);
        ColorConfigurator.setLabelShadowPaint(null);
        ColorConfigurator.setLabelBackgroundPaint(null);
        ColorConfigurator.setSectionOutlinesVisible(false);

        int width = 700;
        int height = 400;
        String fileName = "pie_Chart3D_" + fundCode + ".jpeg";
        File barChart3D = new File(contextPath + "WEB-INF\\classes\\public/" + fileName);
        ChartUtilities.saveChartAsJPEG(barChart3D, myExplodedPieChart, width, height);
        return fileName;

    }

}