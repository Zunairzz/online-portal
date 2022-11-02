/*    */ package WEB-INF.classes.com.ams.dao;
/*    */ 
/*    */ import com.ams.common.ResponseCodes;
/*    */ import com.ams.dao.FundDefinitionRowMapper;
/*    */ import com.ams.model.FundDefinition;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ import org.springframework.stereotype.Component;
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
/*    */ 
/*    */ @Component
/*    */ public class FmrDao
/*    */ {
/*    */   @Autowired
/*    */   private JdbcTemplate jdbcTemplate;
/*    */   
/*    */   public FundDefinition getFrmDefinitionImpl(FundDefinition input) {
/* 28 */     FundDefinition fmr = new FundDefinition();
/*    */     try {
/* 30 */       String sql = "select id, to_char(fmr.trans_date, 'dd/MM/yyyy') trans_date, fmr.fund_code, fmr.members, f.fund_name, commentary, objective from fmr_def fmr, fund f where fmr.fund_code = f.fund_code and fmr.fund_code = ? and fmr.trans_date = to_date(?, 'dd/MM/yyyy')";
/* 31 */       fmr = (FundDefinition)this.jdbcTemplate.queryForObject(sql, new Object[] { input.fundCode, input.transDate }, (RowMapper)new FundDefinitionRowMapper());
/*    */       
/* 33 */       fmr.setResponse_code(ResponseCodes.SUCCESS);
/* 34 */     } catch (Exception ex) {
/* 35 */       fmr.setResponse_code(ResponseCodes.FAILURE);
/*    */     } 
/* 37 */     return fmr;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\dao\FmrDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */