package com.cos.security1.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 인증이 실패하면 이렇게 동작해주세요 ㅠㅠ
 * AuthenticationFailureHandler 구현!
 * 여기서는 응답 헤더를 추가했다
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setHeader("failed", LocalDateTime.now().toString());
    }
}
