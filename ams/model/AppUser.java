package com.ams.model;


public class AppUser {
    private Long userId;
    private String userName;
    private String encrytedPassword;


    public AppUser() {
        this.userId = Long.valueOf("1");
        this.userName = "admin";
        this.encrytedPassword = "$2a$10$Oxao1Xm6JND7DxHp.OLVGOkT/NFeftthZsv1ub/Ux5LpOr6ZRIE86";

    }


    public AppUser(Long userId, String userName, String encrytedPassword) {
        this.userId = userId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;

    }


    public Long getUserId() {
        return this.userId;

    }


    public void setUserId(Long userId) {
        this.userId = userId;

    }


    public String getUserName() {
        return this.userName;

    }


    public void setUserName(String userName) {
        this.userName = userName;

    }


    public String getEncrytedPassword() {
        return this.encrytedPassword;

    }


    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;

    }


    public String toString() {
        return this.userName + "/" + this.encrytedPassword;

    }

}