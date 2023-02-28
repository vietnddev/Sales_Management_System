package com.flowiee.app.authentication;

import com.flowiee.app.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/build/**", "/dist/**", "/plugins/**", "/upload/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/login?success=true")
                .failureUrl("/login?success=fail")
                .loginProcessingUrl("/j_spring_security_check");

        System.out.println(customUserDetailService.loadUserByUsername("koma"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Xác thực bằng tài khoản động dưới database
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());

        /*
        * Xác thực với tài khoản tỉnh được lưu ở RAM
        * String username = "";
        * String password = "";
        * auth.inMemoryAuthentication().withUser(username).password("password");
        * */
    }
}
