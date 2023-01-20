package com.cos.security1.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class HelloController {
    /**
     * MODE_THREADLOCAL 전략(기본)
     * 각 스레드는 컬렉션에 저장한 데이터만 볼 수 있고
     * 다른 스레드에게는 접근이 불가함
     * - 그냥 다 자기꺼 가지고 있고
     * - 내꺼는 나만 볼 수 있다는 뜻
     * - 다른 스레드가 만들어져도 걔가 내 세부 정보를 복사하지 않음
     * - 자기 껄 따로 가지고 있음
     * /
     * /*
     * 인증이 완료된 사용자 정보를
     * 시큐리티 컨텍스트에 있는
     * 어센티케이션에서 가져올 수 있어요
     */
    // SecurityContext 를 가져와서 직접 어센티케이션을 가져와서 정보 보기
    @GetMapping("/hello1")
    public String hello1() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "hello! " + authentication.getName();
    }

    // ** 추천
    // 스프링 인증은 곧바로 매개변수에 주입이 가능!
    // 그냥 매개변수로 받은 Authentication 을 바로 참조해도 됩니다
    @GetMapping("/hello2")
    public String hello2(Authentication authentication) {
        return "hello! " + authentication.getName();
    }

    /**
     * 요청 당 여러 스레드가 사용될 땐 ?
     * MODE_INHERITABLETHREADLOCAL
     * -> 요청이 오면 요청의 원래 스레드에 있는 세부 정보를
     * 비동기에서 새로 생성 된 스레드로 복사한다 ! !
     */
    @GetMapping("/bye")
    @Async // 다른 스레드에서 동작해줘 (비동기)
    public void goodBye() {
        SecurityContext context = SecurityContextHolder.getContext();
        /**
         * 아무 설정 없잉 실행하면 ? NullPointerException !!
         * 해당 메서드는 보안 컨텍스트를 상속하지 않는 다른 클래스에서 실행되기 때문에
         * Authorization 객체는 Null 이다
         */
        String username = context.getAuthentication().getName(); // NullPointerException
    }

}
