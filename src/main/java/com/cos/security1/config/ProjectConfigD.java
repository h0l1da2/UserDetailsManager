package com.cos.security1.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableAsync // 보통은 책임을 분리하기때문에, 절대 주 클래스를 구성 클래스로 이용하지 않음
@Configuration
public class ProjectConfigD {

    /**
     * 프레임워크가 자체적으로 스레드를 만들 때만 작동함(ex: @Async 메서드 이용)
     * 코드로 스레드를 만들면 이 설정가지고는 안 됨
     */
//    @Bean
//    public InitializingBean initializingBean() {
//        return () -> SecurityContextHolder.setStrategyName(
//                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
//        );
//    }

    /**
     * 모든 쓰레드가 같은 보안 컨텍스트에 접근
     * 웹서버는 안 쓰고 독립된 애플리케이션에선 쓸수있음
     */
    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_GLOBAL
        );
    }

}
