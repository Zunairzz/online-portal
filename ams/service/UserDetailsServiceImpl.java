package com.ams.service;


import com.ams.dao.AppRoleDAO;
import com.ams.dao.AppUserDAO;
import com.ams.model.AppUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;


    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        AppUser appUser = this.appUserDAO.findUserAccount(userName);

        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<>();

        if (roleNames != null) {

            for (String role : roleNames) {

                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);

                grantList.add(simpleGrantedAuthority);

            }

        }

        return (UserDetails) new User(appUser.getUserName(), appUser
                .getEncrytedPassword(), grantList);

    }

}