package com.cos.security1.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * 사용자 이름 호출을 위해 쓰레드를 만들었지만
     * 스프링이 생성한 게 아닌 내가 직접 만든 쓰레드여서
     * 인증이 없고 보안 컨텍스트도 비어있음 ㅠㅠ
     */
    //보안 컨텍스트에서 사용자 이름을 반환함
    @GetMapping("/ciao1")
    public String ciao1() {
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            return "Ciao, " + executorService.submit(task).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    //보안 컨텍스트에서 사용자 이름을 반환함
    @GetMapping("/ciao2")
    public String ciao2() {
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            /**
             * DelegatingSecurityContextCallable
             * 얘를 추가해주면,
             * 현재 보안컨텍스트를
             * 새 쓰레드에 복사하고
             * 내가 하고 싶은 작업도 실행해준다!
             */
            var callable = new DelegatingSecurityContextCallable<>(task);
            return "Ciao, " + executorService.submit(task).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    @GetMapping("/hola")
    public String hola() {
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        ExecutorService e = Executors.newCachedThreadPool();
        e = new DelegatingSecurityContextExecutorService(e);
        try {
            return "hola, "+e.submit(task).get();
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            e.shutdown();
        }
    }
}
