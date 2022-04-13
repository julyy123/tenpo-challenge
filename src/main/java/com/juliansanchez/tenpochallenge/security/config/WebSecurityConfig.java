package com.juliansanchez.tenpochallenge.security.config;

import com.juliansanchez.tenpochallenge.security.authentication.JwtAuthenticationEntryPoint;
import com.juliansanchez.tenpochallenge.security.jwt.JwtTokenFilter;
import com.juliansanchez.tenpochallenge.security.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Web security configuration class
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService customUserDetailsService;
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    public WebSecurityConfig(final CustomUserDetailsService customUserDetailsService,
                             final JwtTokenFilter jwtTokenFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated().and();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
