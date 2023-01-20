package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectConfigG extends WebSecurityConfigurerAdapter {
    /**
     * formLogin() 양식 기반 로그인
     * 로그인 했음? 응답 ㄱㄱ
     * 로그인 안함? 로그인 양식으로 리디렉션 ㄱㄱ
     * -> 로그인 하면 내가 요청했던 페이지로 리디렉션 ㄱㄱ
     * 근데 로그인 하고보니 해당 경로가 존재하지 않아?
     * -> 기본 오류 페이지
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
//        http.authorizeRequests().anyRequest().authenticated();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 인증 성공하면 /home 으로 가주쇼~~!
                .defaultSuccessUrl("/home", true);
        http.authorizeRequests().anyRequest().authenticated();
    }
}
