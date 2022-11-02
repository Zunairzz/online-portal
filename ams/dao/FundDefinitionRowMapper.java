/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.FundDefinition;
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
/*    */ public class FundDefinitionRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FundDefinition customer = new FundDefinition();
/* 22 */     customer.setId(rs.getString("ID"));
/* 23 */     customer.setFundCode(rs.getString("FUND_CODE"));
/*    */     
/* 25 */     customer.setCommentary(rs.getString("COMMENTARY"));
/* 26 */     customer.setObjective(rs.getString("OBJECTIVE"));
/* 27 */     customer.setTransDate(rs.getString("TRANS_DATE"));
/* 28 */     customer.setMemberList(rs.getString("MEMBERS"));
/* 29 */     customer.setMemberString(rs.getString("MEMBER"));
/* 30 */     customer.setWeightAvg(rs.getString("WEIGHTED_AVG"));
/* 31 */     customer.setLeverage(rs.getString("LEVERAGE"));
/* 32 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FundDefinitionRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */