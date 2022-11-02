/*     */ package WEB-INF.classes.com.ams.utility;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.time.LocalDate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FMRChartOfAccountServiceHandler
/*     */   implements Runnable
/*     */ {
/*     */   @Autowired
/*     */   private JdbcTemplate jdbcTemplate;
/*     */   
/*     */   public FMRChartOfAccountServiceHandler() {
/*  28 */     System.out.println("======= FMRChartOfAccountServiceHandler =======");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdbcTemplate getJdbcTemplate() {
/*  35 */     return this.jdbcTemplate;
/*     */   }
/*     */   
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/*  39 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  45 */       System.out.println("=== start search list of previous month records for updation ===");
/*     */       
/*  47 */       fmrChartOfAccount();
/*  48 */     } catch (Exception e) {
/*  49 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fmrChartOfAccount() {
/*  55 */     System.out.println("=== fmrChartOfAccount ===");
/*     */ 
/*     */     
/*     */     try {
/*  59 */       Connection connection = getCleanDBConnection();
/*  60 */       String query = "select fca.fundcode, fca.gl_code, fca.description, fca.trans_date, fca.id from fmr_chart_of_account fca where Trans_date = (select last_day(to_date((ADD_MONTHS(sysdate, -2)), 'dd/MM/yy')) from dual)";
/*  61 */       Statement cmd = connection.createStatement();
/*  62 */       ResultSet rs = cmd.executeQuery(query);
/*  63 */       String sql = "insert into fmr_chart_of_account (fundcode, gl_code, description, trans_date, id) values (?, ?, ?,?, ?)";
/*  64 */       PreparedStatement ps = connection.prepareStatement(sql);
/*     */       
/*  66 */       String maxIDQuery = "select max(ID) as ID from fmr_chart_of_account t";
/*  67 */       Statement cmdd = connection.createStatement();
/*  68 */       ResultSet rsMax = cmdd.executeQuery(maxIDQuery);
/*  69 */       int maxID = 0;
/*  70 */       while (rsMax.next()) {
/*  71 */         maxID = rsMax.getInt("ID");
/*     */       }
/*  73 */       System.out.println("maxID : " + maxID);
/*  74 */       int index = 1;
/*  75 */       while (rs.next()) {
/*  76 */         System.out.println("index : " + index);
/*  77 */         ps.setString(1, rs.getString("fundcode"));
/*  78 */         ps.setString(2, rs.getString("gl_code"));
/*  79 */         ps.setString(3, rs.getString("description"));
/*     */         
/*  81 */         LocalDate dateTime = LocalDate.now();
/*  82 */         System.out.println("date time" + dateTime);
/*  83 */         LocalDate newDateTime = dateTime.minusDays(1L);
/*  84 */         System.out.println("upate date time" + newDateTime);
/*  85 */         Date mySqlDate = Date.valueOf(newDateTime);
/*     */         
/*  87 */         ps.setDate(4, mySqlDate);
/*  88 */         ps.setInt(5, maxID + index);
/*  89 */         ps.addBatch();
/*  90 */         index++;
/*     */       } 
/*  92 */       int[] result = ps.executeBatch();
/*  93 */       System.out.println("inserted batch rows size iss : " + result.length);
/*  94 */       connection.commit();
/*     */     }
/*  96 */     catch (Exception e) {
/*  97 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Connection getCleanDBConnection() throws SQLException {
/*     */     try {
/* 107 */       Class.forName("oracle.jdbc.driver.OracleDriver");
/* 108 */       Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db.ablamc.com:1521:ORCL", "clean", "clean");
/*     */       
/* 110 */       Logger.getLogger(com.ams.utility.FMRChartOfAccountServiceHandler.class.getName()).log(Level.SEVERE, (String)null, "Fetching clean db connection...");
/* 111 */       return con;
/* 112 */     } catch (Exception e) {
/* 113 */       e.printStackTrace();
/*     */       
/* 115 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\FMRChartOfAccountServiceHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */