package com.cos.security1.config;

import com.cos.security1.handler.CustomAuthenticationFailureHandler;
import com.cos.security1.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
public class ProjectConfigH extends WebSecurityConfigurerAdapter {

    /**
     * 응답 성공 핸들러
     * 응답 실패 핸들러
     */
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * Http Basic 방식이라면
     * 사용자가 올바른 아이디 비번을 통해 요청했어도
     * 핸들러 요청에 따라 302 리디렉션 응답이 반환되어
     * 200 아니라 302 코드로 http 헤더에 뜸
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                // httpBasic 은 선택임
                .and()
                .httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
