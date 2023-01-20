//package com.cos.security1.config;
//
//import com.cos.security1.handler.CustomEntryPoint;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class ProjectConfigF extends WebSecurityConfigurerAdapter {
//
//    /**
//     * httpBasic 에 매개변수를 통해서
//     * 인증 방식과 관련된 일부 구성
//     * (여기서는 영역 이름)
//     * 바꿀 수 있다는데
//     * 이해못하겠엉...
//     * - 영역 이란?
//     * 특정 인정 방식을 이용하는 보호 공간이래요(잘모르겠음)
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        /**
//         * 람다 뜻
//         * Customizer<HttpBasicConfigurer<HttpSecurity>> 형식의 객체임
//         * HttpBasicConfigurer<HttpSecurity> 형식의 매개 변수로
//         * realName()을 호출해
//         * 영역 이름을 변경할 수 있게 함
//         * -
//         * HTTP 응답에 영역 이름이 변경됐는지 나옴
//         * 근데 www-authenticate 헤더에는
//         * 401 예외일때만 있고 200일때는 걍 cURL 호출함
//         */
//        http.httpBasic(c -> {
//            c.realmName("OTHER");
//            // 권한 없어서 응답 실패가 뜨면 이 응답 맞춤 구성해주세용
//            c.authenticationEntryPoint(new CustomEntryPoint());
//        });
//
//        http.authorizeRequests().anyRequest().authenticated();
//    }
//}
