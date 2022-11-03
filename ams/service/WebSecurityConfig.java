package com.ams.service;

import com.ams.service.UserDetailsServiceImpl;
import com.ams.utility.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration

@EnableWebSecurity
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Bean
    public EncrytedPasswordUtils passwordEncoder() {

        EncrytedPasswordUtils bCryptPasswordEncoder = new EncrytedPasswordUtils();

        return bCryptPasswordEncoder;

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService((UserDetailsService) this.userDetailsService).passwordEncoder((PasswordEncoder) passwordEncoder());

    }


    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(new String[]{"/login", "/logout", "/Content*"})).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(new String[]{"*"})).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(new String[]{"/admin"})).access("hasRole('ROLE_ADMIN')");
        ((HttpSecurity) http.authorizeRequests().and()).exceptionHandling().accessDeniedPage("/403");
        ((HttpSecurity) ((FormLoginConfigurer) ((FormLoginConfigurer) ((HttpSecurity) http.authorizeRequests().and()).formLogin()
                .loginProcessingUrl("/j_spring_security_check"))
                .loginPage("/login")
                .failureUrl("/login?error=true"))
                .usernameParameter("username")
                .passwordParameter("password")
                .and()).logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }

}