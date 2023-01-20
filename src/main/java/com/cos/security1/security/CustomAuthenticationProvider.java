package com.cos.security1.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * AuthenticationProvider 를 재정의한 클래스
 * 내가 지정한 특정한 키를 (인증하도록)지원하도록 해보자!
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * username 과 password 를 가져와서
     * 해당 유저의 정보를(UserDetails) 가져온다음
     * 암호가 일치하면
     * 필요한 세부 정보가 포함된 authentication 을 반환하고
     * 암호가 틀리면
     * 예외를 반환한다
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    userDetails.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("password wrong");
        }
    }

    // 해당 Authentication 타입의 객체를 인증할 수 이쓰면 true 를 반환한다
    @Override
    public boolean supports(Class<?> authentication) {
        // 필터에서 아무 구성도 하지 않으면 기본으로 UsernamePasswordAuthenticationToken 타입을 이용함
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
