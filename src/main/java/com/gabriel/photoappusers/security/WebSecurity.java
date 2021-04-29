package com.gabriel.photoappusers.security;

import com.gabriel.photoappusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Value("${gateway.ip}")
    private String gatewayIp;

    @Value("${login.url.path}")
    private String loginPath;

   private final UserService userService;
   private final PasswordEncoder passwordEncoder;
   private final Environment environment;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/users/**").permitAll();
        //ip of API gateway
        http.authorizeRequests().antMatchers("/**").hasIpAddress(gatewayIp)
        .and()
        .addFilter(getAuthenticationFilter());
        http.csrf().disable();
        http.headers().frameOptions().disable();//to allow h2 working
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        //to change login path from default which is /login
        authenticationFilter.setFilterProcessesUrl(loginPath);
        return authenticationFilter;
    }

}
