
package com.ams.dao;

import com.ams.model.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository

@Transactional
public class AppUserDAO {

    public AppUser findUserAccount(String userName) {
        AppUser userInfo = new AppUser();
        userInfo.setUserId(Long.valueOf("1"));
        userInfo.setUserName(userName);
        userInfo.setEncrytedPassword(userName);
        return userInfo;

    }

}
