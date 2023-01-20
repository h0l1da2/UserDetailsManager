package com.cos.security1.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 패스워드를 SHA-512로 해시하는 인코더
 */
public class Sha512PasswordEncoder implements PasswordEncoder {

    // 입력을 SHA-512 로 해시하는 메서드
    private String hashWithSHA512(String input) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digested = md.digest(input.getBytes());
            for (byte digest : digested) {
                result.append(Integer.toHexString(0xFF & digest));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("bad 알고리즘");
        }
        return result.toString();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSHA512(rawPassword.toString());
    }

    // 해싱한 값이랑 맞나 비교
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }
}
