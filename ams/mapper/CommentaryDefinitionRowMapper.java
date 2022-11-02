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
/*    */ 
/*    */ public class CommentaryDefinitionRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 21 */     FmrEconomyDto customer = new FmrEconomyDto();
/* 22 */     if (rs.getString("TYPE").equals("PARA_CONTENT")) {
/* 23 */       customer.setComm1(rs.getString("COMM1"));
/* 24 */       customer.setComm2(rs.getString("COMM2"));
/* 25 */       customer.setComm3(rs.getString("COMM3"));
/* 26 */     } else if (rs.getString("TYPE").equals("PARA_HEADING")) {
/* 27 */       customer.setHead1(rs.getString("COMM1"));
/* 28 */       customer.setHead2(rs.getString("COMM2"));
/* 29 */       customer.setHead3(rs.getString("COMM3"));
/* 30 */     } else if (rs.getString("TYPE").equals("PARA_CONTENT_ISL")) {
/* 31 */       customer.setComm1(rs.getString("COMM1"));
/* 32 */       customer.setComm2(rs.getString("COMM2"));
/* 33 */       customer.setComm3(rs.getString("COMM3"));
/* 34 */     } else if (rs.getString("TYPE").equals("PARA_HEADING_ISL")) {
/* 35 */       customer.setHead1(rs.getString("COMM1"));
/* 36 */       customer.setHead2(rs.getString("COMM2"));
/* 37 */       customer.setHead3(rs.getString("COMM3"));
/*    */     } 
/*    */     
/* 40 */     return customer;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\mapper\CommentaryDefinitionRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */