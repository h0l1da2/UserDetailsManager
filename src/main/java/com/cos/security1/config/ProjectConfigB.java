package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfigB {

    /**
     * 쿼리 재정의도 가능 !
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery =
                "select username, password, enabled from users where username = ?";
        String authsByUserQuery =
                "select username, authority from authorities where username = ?";

        /**
         * UserDetilsManager 구현에 이용되는 모든 쿼리 재정의 가능 !
         */
        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
        return userDetailsManager;
    }

    // 유저디테일서비스를구현할땐항상 패스워드 인코더도 구현해준다(세트)

    /**
     * 비밀번호 자격 증명 알고리즘을 변경하고 싶은데
     * 기존 사용자의 자격 증명을 변경하기 어려울때...
     * DelegatingPasswordEncoder 를 사용한다
     * 얘는 자기가 직접 인코딩을 하는 게 아니라 다른 인코더들에게 위임한다
     * 그래서 A 인코더를 쓰다가 B 인코딩으로 변경하면 키만 바꿔서 돌려쓰면 된다
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
