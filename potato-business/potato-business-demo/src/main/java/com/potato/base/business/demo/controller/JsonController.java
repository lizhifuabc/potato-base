package com.potato.base.business.demo.controller;

import com.potato.base.business.demo.entity.Demo;
import com.potato.base.plugin.common.dto.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * application/json 请求头
 * 当为GET请求时,?传参方式可传值,参数名为实体内的属性值,用@RequestBody注解修饰,可直接获取到参数名对应的入参
 * 当为POST请求时候,body中,用@RequestBody注解修饰接收实体.
 * @author lizhifu
 * @date 2021/9/16
 */
@RestController
@Slf4j
@RequestMapping(value = "json", headers = "content-type=application/json")
public class JsonController {
    /**
     * GET请求 传参没有注解修饰,实体接收,?传参方式可传值,参数名为实体内的属性值
     * 传参用@RequestBody注解修饰,可在body中获取到值,参数名为入参的属性名称
     * @param demo
     * @return
     */
    @GetMapping(value = "demo1")
    public SingleResponse<Demo> demo1(@RequestBody Demo demo) {
        return SingleResponse.of(demo);
    }
    /**
     * GET请求 传参没有注解修饰,?传参方式可传值,参数名为入参的属性名称
     * 也可用 @RequestParam 取别名
     * @param demo
     * @return
     */
    @GetMapping(value = "demo2")
    public SingleResponse<String> demo2(@RequestParam(value = "demo", required = false) String demo) {
        return SingleResponse.of(demo);
    }
    /**
     * POST请求,实体必须用@RequestBody注解修饰
     * 传入Body里,参数名为入参的属性名称
     * @param demo
     * @return
     */
    @PostMapping(value = "demo3")
    public SingleResponse<Demo> demo3(@RequestBody Demo demo) {
        return SingleResponse.of(demo);
    }
}
