package com.example.anonymous_board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j

public class TimerAop {

    @Pointcut(value = "within(com.example.anonymous_board.post.controller.PostApiController) || within(com.example.anonymous_board.reply.controller.ReplyApiController)")
    public void timerAopPointCut(){
    }

    @Around(value = "timerAopPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("어라운드 진입");
        var stopWatch = new StopWatch();
        stopWatch.start();

        Object var = joinPoint.proceed();

        log.info("어라운드 - 메서드 끝");
        stopWatch.stop();
        log.info("걸린 시간: {}",stopWatch.getTotalTimeMillis());

        return var;
    }
}
