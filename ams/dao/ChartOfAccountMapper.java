/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.ChartOfAccountModel;
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
/*    */ public class ChartOfAccountMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 20 */     ChartOfAccountModel customer = new ChartOfAccountModel();
/* 21 */     customer.setFundCode(rs.getString("FUNDCODE"));
/* 22 */     customer.setGlCode(rs.getString("GL_CODE"));
/* 23 */     customer.setDescription(rs.getString("DESCRIPTION"));
/* 24 */     customer.setTransDate(rs.getString("TRANS_DATE"));
/* 25 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\ChartOfAccountMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */