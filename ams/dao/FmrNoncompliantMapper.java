/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.fmr.FmrNoncompliant;
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
/*    */ public class FmrNoncompliantMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FmrNoncompliant bi = new FmrNoncompliant();
/*    */ 
/*    */     
/* 24 */     bi.non_complaint_investment = rs.getString("non_complaint_investment");
/* 25 */     bi.type_of_investment = rs.getString("type_of_investment");
/* 26 */     bi.exposure_limit = rs.getString("exposure_limit");
/* 27 */     bi.percent_of_net_asset = rs.getString("percent_of_net_asset");
/* 28 */     bi.percent_of_total_asset = rs.getString("percent_of_total_asset");
/* 29 */     bi.excess_exposure_per_of_net = rs.getString("excess_exposure_per_of_net");
/* 30 */     bi.excess_exposure_per_of_total = rs.getString("excess_exposure_per_of_total");
/* 31 */     return bi;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrNoncompliantMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */