package com.potato.base.business.demo.controller.demo;

import com.potato.base.plugin.ratelimiter.RateLimiterExe;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import com.potato.base.plugin.ratelimiter.enums.AlgorithmType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * RateLimiterExe
 *
 * @author lizhifu
 * @date 2021/12/13
 */
@RestController
@RequestMapping("rateLimiterExe")
public class RateLimiterExeController {
    @Resource
    private RateLimiterExe rateLimiterExe;
    @RequestMapping("concurrent")
    public void rateLimiterExe(){
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.CONCURRENT,"demo").build();
        rateLimiterExe.exe(rateLimiterExeRequest);
    }
}
