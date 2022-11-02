/*    */ package WEB-INF.classes.com.ams.mapper;
/*    */ 
/*    */ import com.ams.model.fmr.FmrEconomyDto;
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
/*    */ public class EconomySummaryDefinitionRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 20 */     FmrEconomyDto customer = new FmrEconomyDto();
/* 21 */     customer.setId(rs.getString("ID"));
/* 22 */     customer.setDescription(rs.getString("DESCRIPTION"));
/* 23 */     customer.setField1(rs.getString("FIELD_1"));
/* 24 */     customer.setField2(rs.getString("FIELD_2"));
/* 25 */     customer.setField3(rs.getString("FIELD_3"));
/* 26 */     customer.setField4(rs.getString("FIELD_4"));
/* 27 */     customer.setField5(rs.getString("FIELD_5"));
/* 28 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\mapper\EconomySummaryDefinitionRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */