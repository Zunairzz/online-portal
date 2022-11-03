package com.ams.utility;


import com.ams.common.ResponseCodes;
import com.ams.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class EncrytedPasswordUtils
        implements PasswordEncoder {
    private static final Logger log = LoggerFactory.getLogger(com.ams.utility.EncrytedPasswordUtils.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");


    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);

    }


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String encode(CharSequence cs) {
        System.out.println("password: " + cs);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(cs);

    }


    public boolean matches(CharSequence cs, String encrypted) {
        UserModel user = checkUserValidity(encrypted, cs.toString(), "127.0.0.1", "1");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return (user != null && user.ResponseCode.equals(ResponseCodes.VALIDUSER));

    }


    public UserModel checkUserValidity(String user, String password, String ip, String sessionId) {
        SimpleJdbcCall jdbcCall = (new SimpleJdbcCall(this.jdbcTemplate)).withProcedureName("validateUser");
        MapSqlParameterSource mapSqlParameterSource = (new MapSqlParameterSource()).addValue("USER", user).addValue("PASS", password).addValue("REMOTEADD", ip).addValue("SESSIONID", sessionId);
        Map<String, Object> out = jdbcCall.execute((SqlParameterSource) mapSqlParameterSource);
        UserModel userModel = new UserModel();
        if (out.get("USERTYPE") != null && out.get("ROLECODE") != null && !out.get("ROLECODE").toString().equals("BUN")) {
            userModel.Name = user;
            userModel.UserId = user;
            userModel.ROLE = out.get("ROLECODE").toString();
            userModel.setResponse_code(ResponseCodes.VALIDUSER);
            log.info("User : " + user + " -- login -- {}", dateFormat.format(new Date()));

        } else {
            userModel.setResponse_code(ResponseCodes.INVALIDUSER);

        }
        return userModel;

    }

}