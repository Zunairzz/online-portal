/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.FmrNonComplaintsInvest;
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
/*    */ public class FmrNonComplaintsInvestRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FmrNonComplaintsInvest bi = new FmrNonComplaintsInvest();
/* 22 */     bi.fundCode = rs.getString("fund_code");
/* 23 */     bi.transDate = rs.getString("transdate");
/* 24 */     bi.nonComplaintInvestment = rs.getString("non_complaint_investment");
/* 25 */     bi.typeOfInvestment = rs.getString("type_of_investment");
/* 26 */     bi.exposureLimit = rs.getString("exposure_limit");
/* 27 */     bi.percentOfNetAsset = rs.getString("percent_of_net_asset");
/* 28 */     bi.percentOfTotalAsset = rs.getString("percent_of_total_asset");
/* 29 */     bi.excessExposurePerOfNet = rs.getString("excess_exposure_per_of_net");
/* 30 */     bi.excessExposurePerOfTotal = rs.getString("excess_exposure_per_of_total");
/* 31 */     return bi;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrNonComplaintsInvestRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */