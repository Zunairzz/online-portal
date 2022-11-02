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
/*    */ public class FmrPlanBasicInfoMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FmrFundBasicInfo bi = new FmrFundBasicInfo();
/* 22 */     bi.id = rs.getString("ID");
/* 23 */     bi.transDate = rs.getString("TRANSDATE");
/* 24 */     bi.fundCode = rs.getString("PLAN_CODE");
/* 25 */     bi.fund_type = rs.getString("PLAN_TYPE");
/* 26 */     bi.category = rs.getString("CATEGORY");
/*    */     
/* 28 */     bi.launchdate = rs.getString("LAUNCHDATE");
/*    */     
/* 30 */     bi.benchmark = rs.getString("BENCHMARK");
/* 31 */     bi.dealing_days = rs.getString("DEALING_DAYS");
/* 32 */     bi.cutt_off_time = rs.getString("CUTT_OFF_TIME");
/* 33 */     bi.pricing_mechanism = rs.getString("PRICING_MECHANISM");
/* 34 */     bi.management_fee = rs.getString("MANAGEMENT_FEE");
/* 35 */     bi.front_end_load = rs.getString("FRONT_END_LOAD");
/* 36 */     bi.trustee = rs.getString("TRUSTEE");
/* 37 */     bi.auditor = rs.getString("AUDITOR");
/*    */     
/* 39 */     bi.asset_manager_rating = rs.getString("ASSET_MANAGER_RATING");
/* 40 */     bi.risk_prof_fund = rs.getString("RISK_PROF_FUND");
/* 41 */     bi.perform_ranking = rs.getString("PERFORM_RANKING");
/*    */     
/* 43 */     bi.fund_manger = rs.getString("PLAN_MANGER");
/* 44 */     bi.listing = rs.getString("LISTING");
/* 45 */     bi.comments = rs.getString("COMMENTS");
/* 46 */     return bi;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrPlanBasicInfoMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */