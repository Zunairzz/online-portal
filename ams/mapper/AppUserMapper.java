/*    */ package WEB-INF.classes.com.ams.mapper;
/*    */ 
/*    */ import com.ams.model.AppUser;
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
/*    */ 
/*    */ 
/*    */ public class AppUserMapper
/*    */   implements RowMapper<AppUser>
/*    */ {
/*    */   public static final String BASE_SQL = "Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u ";
/*    */   
/*    */   public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 25 */     Long userId = Long.valueOf("1");
/* 26 */     String userName = "admin";
/* 27 */     String encrytedPassword = "admin";
/*    */     
/* 29 */     return new AppUser(userId, userName, encrytedPassword);
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\mapper\AppUserMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */