/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.model.CreditRatingOfBanks;
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
/*    */ public class CreditRattingOfBanksRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 20 */     CreditRatingOfBanks cr = new CreditRatingOfBanks();
/* 21 */     cr.setGlBankCode(rs.getString("gl_bank_code"));
/* 22 */     cr.setGlBankName(rs.getString("gl_bank_name"));
/* 23 */     cr.setRating(rs.getString("rating"));
/* 24 */     return cr;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\CreditRattingOfBanksRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */