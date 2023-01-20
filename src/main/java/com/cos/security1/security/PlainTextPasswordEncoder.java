package com.cos.security1.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 암호를 일반 텍스트로 취급해서 비교하기
 */
public class PlainTextPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString(); // 변환 X 그대로 반환
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword); // equals 로 문자열 같은지 확인만 !
    }
}
