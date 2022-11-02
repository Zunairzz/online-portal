/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.FmrModel;
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
/*    */ public class FmrRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 20 */     FmrModel customer = new FmrModel();
/* 21 */     customer.setMembers(rs.getString("MEMBERS"));
/* 22 */     customer.setComments(rs.getString("COMMENTS"));
/* 23 */     customer.setObjective(rs.getString("OBJECTIVES"));
/* 24 */     customer.setLaunchDate(rs.getString("LAUNCH_DATE"));
/* 25 */     customer.setFundTitle(rs.getString("FUND_NAME"));
/* 26 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */