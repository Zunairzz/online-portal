package com.ams.mapper;

import com.ams.model.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class AppUserMapper
        implements RowMapper<AppUser> {
    public static final String BASE_SQL = "Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u ";


    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long userId = Long.valueOf("1");
        String userName = "admin";
        String encrytedPassword = "admin";

        return new AppUser(userId, userName, encrytedPassword);

    }

}