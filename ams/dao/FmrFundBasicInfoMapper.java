/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.FmrFundBasicInfo;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmrFundBasicInfoMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FmrFundBasicInfo bi = new FmrFundBasicInfo();
/* 22 */     bi.id = rs.getString("ID");
/* 23 */     bi.transDate = rs.getString("TRANSDATE");
/* 24 */     bi.fundCode = rs.getString("FUND_CODE");
/* 25 */     bi.fund_type = rs.getString("FUND_TYPE");
/* 26 */     bi.category = rs.getString("CATEGORY");
/* 27 */     bi.launchdate = rs.getString("LAUNCHDATE");
/* 28 */     bi.setNetassets(rs.getString("NETASSETS"));
/* 29 */     if (rs.getString("NETASSETEXFOF") != null) {
/* 30 */       bi.setNetassetexfof(rs.getString("NETASSETEXFOF"));
/*    */     }
/*    */     try {
/* 33 */       if (rs.getString("NAV") != null && !rs.getString("NAV").equals("") && !rs.getString("NAV").equals("null")) {
/* 34 */         bi.setNav(rs.getString("NAV"));
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 39 */       bi.fundTer = format(Double.valueOf(rs.getString("TER")).doubleValue());
/* 40 */       bi.setTer_gov_levy(format(Double.valueOf(rs.getString("TER_GOV_LEVY")).doubleValue()));
/* 41 */       bi.setSwwf(Double.valueOf(rs.getString("SWWF")).doubleValue());
/* 42 */       bi.setSwwf_nav_unit(format(Double.valueOf(rs.getString("SWWF_NAV")).doubleValue()));
/* 43 */       bi.setSmexp(Double.valueOf(rs.getString("SMEXP")).doubleValue());
/* 44 */     } catch (Exception e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/* 47 */     bi.benchmark = rs.getString("BENCHMARK");
/* 48 */     bi.dealing_days = rs.getString("DEALING_DAYS");
/* 49 */     bi.cutt_off_time = rs.getString("CUTT_OFF_TIME");
/* 50 */     bi.pricing_mechanism = rs.getString("PRICING_MECHANISM");
/* 51 */     bi.management_fee = rs.getString("MANAGEMENT_FEE");
/* 52 */     bi.front_end_load = rs.getString("FRONT_END_LOAD");
/* 53 */     bi.trustee = rs.getString("TRUSTEE");
/* 54 */     bi.auditor = rs.getString("AUDITOR");
/* 55 */     bi.asset_manager_rating = rs.getString("ASSET_MANAGER_RATING");
/* 56 */     bi.risk_prof_fund = rs.getString("RISK_PROF_FUND");
/* 57 */     bi.perform_ranking = rs.getString("PERFORM_RANKING");
/* 58 */     bi.fund_manger = rs.getString("FUND_MANGER");
/* 59 */     bi.listing = rs.getString("LISTING");
/* 60 */     bi.comments = rs.getString("COMMENTS");
/* 61 */     return bi;
/*    */   }
/*    */   
/*    */   public String format(double d) {
/* 65 */     if (d == (long)d) {
/* 66 */       return String.format("%d", new Object[] { Long.valueOf((long)d) });
/*    */     }
/* 68 */     return String.format("%s", new Object[] { Double.valueOf(d) });
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrFundBasicInfoMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */