package com.svetikov.ecommerceshop.config;

import com.svetikov.ecommerceshop.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    private static final String RESOURCES_LOCATION = "/resources/**";
    private static final String MANAGE_ENDPOINT = "/manage/**";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String LOGOUT_ENDPOINT = "/logout";
    private static final String REGISTER_ENDPOINT = "/register";
    private static final String SHOP_ENDPOINT = "/shop/**";
    private static final String CART_ENDPOINT = "/cart/**";
    private static final String CONTACT_ENDPOINT = "/contact";
    private static final String BLOG_ENDPOINT = "/blog";
    private static final String[] HOME_ENDPOINTS = {"/", "/home", "/index"};

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(RESOURCES_LOCATION).permitAll()
                .antMatchers(HOME_ENDPOINTS).permitAll()
                .antMatchers(SHOP_ENDPOINT).permitAll()
                .antMatchers(BLOG_ENDPOINT).permitAll()
                .antMatchers(CONTACT_ENDPOINT).permitAll()
                .antMatchers(REGISTER_ENDPOINT).permitAll()
                .antMatchers(MANAGE_ENDPOINT).hasRole("ADMIN")
                .antMatchers(CART_ENDPOINT).hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_ENDPOINT).defaultSuccessUrl(HOME_ENDPOINTS[0]).permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENDPOINT))
                .logoutSuccessUrl(HOME_ENDPOINTS[0]).permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    }

}
