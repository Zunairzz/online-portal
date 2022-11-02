/*    */ package WEB-INF.classes.com.ams.controller;
/*    */ 
/*    */ import com.ams.common.ResponseCodes;
/*    */ import com.ams.model.UserModel;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*    */ import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/*    */ import org.springframework.jdbc.core.simple.SimpleJdbcCall;
/*    */ import org.springframework.web.bind.annotation.DeleteMapping;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.PostMapping;
/*    */ import org.springframework.web.bind.annotation.PutMapping;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping({"/user"})
/*    */ public class UserController
/*    */ {
/*    */   @Autowired
/*    */   private JdbcTemplate jdbcTemplate;
/*    */   
/*    */   @GetMapping({"/validate"})
/*    */   public UserModel validate() {
/* 37 */     UserModel userModel = new UserModel();
/* 38 */     Object[] res1 = { "" };
/*    */     try {
/* 40 */       userModel = validateUser("ADMIN", "ADMIN123*+", "localhost", "123123123123");
/* 41 */     } catch (Exception ex) {
/* 42 */       System.out.println(ex.toString());
/*    */     } 
/* 44 */     return userModel;
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/validate/{id}/{pwd}"})
/*    */   public UserModel validate(@PathVariable String id, @PathVariable String pwd) {
/* 50 */     UserModel userModel = new UserModel();
/* 51 */     Object[] res1 = { "" };
/*    */     try {
/* 53 */       userModel = validateUser(id, pwd, "localhost", "123123123123");
/* 54 */     } catch (Exception ex) {
/* 55 */       System.out.println(ex.toString());
/*    */     } 
/* 57 */     return userModel;
/*    */   }
/*    */   
/*    */   @PutMapping({"/{id}"})
/*    */   public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
/* 62 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @PostMapping
/*    */   public Object post(@RequestBody Object input) {
/* 69 */     return input;
/*    */   }
/*    */   
/*    */   @DeleteMapping({"/{id}"})
/*    */   public ResponseEntity<?> delete(@PathVariable String id) {
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public UserModel validateUser(String user, String password, String ip, String sessionId) {
/* 79 */     SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(this.jdbcTemplate)).withProcedureName("validateUser");
/* 80 */     MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("USER", user).addValue("PASS", password).addValue("REMOTEADD", ip).addValue("SESSIONID", sessionId);
/* 81 */     Map<String, Object> out = jdbcCall.execute((SqlParameterSource)mapSqlParameterSource);
/* 82 */     UserModel userModel = new UserModel();
/* 83 */     if (out.get("USERTYPE") != null && out.get("ROLECODE") != null) {
/* 84 */       userModel.Name = user;
/* 85 */       userModel.UserId = user;
/* 86 */       userModel.ROLE = out.get("ROLECODE").toString();
/* 87 */       userModel.setResponse_code(ResponseCodes.VALIDUSER);
/*    */     } else {
/* 89 */       userModel.setResponse_code(ResponseCodes.INVALIDUSER);
/*    */     } 
/*    */ 
/*    */     
/* 93 */     return userModel;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\controller\UserController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */