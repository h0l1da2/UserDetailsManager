package com.cos.security1.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 실패가 떴을 때
 * 응답을 맞춤 구성하쟈!
 * 실패한 시스템 정보를 외부로 노출시키는 건
 * 보안에 매우매우 좋지 않아요
 */
public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("message", "내가니애비다");
        // 권한 없음 401 응답 실패
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }
}
