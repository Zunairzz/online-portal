/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.FundCommentary;
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
/*    */ public class FundCommentaryMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 20 */     FundCommentary customer = new FundCommentary();
/* 21 */     customer.setFundCode(rs.getString("FUND_CODE"));
/* 22 */     customer.setCommentary(rs.getString("DESCRIPTION"));
/* 23 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FundCommentaryMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */