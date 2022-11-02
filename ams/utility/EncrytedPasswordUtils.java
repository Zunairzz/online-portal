/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import com.ams.common.ResponseCodes;
/*    */ import com.ams.model.UserModel;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*    */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*    */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*    */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*    */ import org.springframework.security.crypto.password.PasswordEncoder;
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
/*    */ public class EncrytedPasswordUtils
/*    */   implements PasswordEncoder
/*    */ {
/* 30 */   private static final Logger log = LoggerFactory.getLogger(com.ams.utility.EncrytedPasswordUtils.class);
/* 31 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
/*    */ 
/*    */   
/*    */   public static String encrytePassword(String password) {
/* 35 */     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
/* 36 */     return encoder.encode(password);
/*    */   }
/*    */   @Autowired
/*    */   private JdbcTemplate jdbcTemplate;
/*    */   public String encode(CharSequence cs) {
/* 41 */     System.out.println("password: " + cs);
/* 42 */     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
/* 43 */     return encoder.encode(cs);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CharSequence cs, String encrypted) {
/* 48 */     UserModel user = checkUserValidity(encrypted, cs.toString(), "127.0.0.1", "1");
/* 49 */     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
/* 50 */     return (user != null && user.ResponseCode.equals(ResponseCodes.VALIDUSER));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UserModel checkUserValidity(String user, String password, String ip, String sessionId) {
/* 56 */     SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(this.jdbcTemplate)).withProcedureName("validateUser");
/* 57 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("USER", user).addValue("PASS", password).addValue("REMOTEADD", ip).addValue("SESSIONID", sessionId);
/* 58 */     Map<String, Object> out = jdbcCall.execute((SqlParameterSource)mapSqlParameterSource);
/* 59 */     UserModel userModel = new UserModel();
/* 60 */     if (out.get("USERTYPE") != null && out.get("ROLECODE") != null && !out.get("ROLECODE").toString().equals("BUN")) {
/* 61 */       userModel.Name = user;
/* 62 */       userModel.UserId = user;
/* 63 */       userModel.ROLE = out.get("ROLECODE").toString();
/* 64 */       userModel.setResponse_code(ResponseCodes.VALIDUSER);
/* 65 */       log.info("User : " + user + " -- login -- {}", dateFormat.format(new Date()));
/*    */     } else {
/* 67 */       userModel.setResponse_code(ResponseCodes.INVALIDUSER);
/*    */     } 
/* 69 */     return userModel;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\EncrytedPasswordUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */