package com.ams.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class AppRoleDAO {
    public List<String> getRoleNames(Long userId) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");

        return roles;
    }
}