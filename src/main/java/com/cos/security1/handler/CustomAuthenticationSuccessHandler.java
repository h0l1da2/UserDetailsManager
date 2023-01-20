package com.cos.security1.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * 인증이 성공했을 경우 이렇게 응답 해주세요!!
 * AuthenticationSuccessHandler 구현!
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // authorities 안에서 getAuthority가 read인 객체 젤 첫번째로 찾아지는거 주세요
        Optional<? extends GrantedAuthority> auth = authorities
                .stream()
                .filter(a -> a.getAuthority().equals("read"))
                .findFirst();// -> read 권한 없으면 빈 optional 객체 반환

        if(auth.isPresent()) {
            // read 권한 있으면 home
            response.sendRedirect("/home");
        } else {
            // read 권한 없으면 /
            response.sendRedirect("/");
        }
    }
}
