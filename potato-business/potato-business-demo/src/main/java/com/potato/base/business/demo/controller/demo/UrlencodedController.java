package com.potato.base.business.demo.controller.demo;

import com.potato.base.business.demo.entity.Demo;
import com.potato.base.plugin.common.dto.Response;
import com.potato.base.plugin.common.dto.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * application/x-www-form-urlencoded 请求头
 * 当为GET请求时,浏览器用x-www-form-urlencoded的编码方式把form数据转换成一个字串（name1=value1&name2=value2...),
 * 然后把这个字串append到url后面，用?分割，加载这个新的url。需要对参数进行 urlencode 编码和序列化
 *
 * 当为POST请求时,浏览器把form数据封装到http body中,不可用@RequestBody注解修饰接收实体.
 * @author lizhifu
 * @date 2021/9/16
 */
@RestController
@Slf4j
@RequestMapping(value = "urlencoded", headers = "content-type=application/x-www-form-urlencoded")
public class UrlencodedController {
    /**
     * GET请求 传参没有注解修饰,实体接收,?传参方式可传值,参数名为实体内的属性值
     * @param demo
     * @return
     */
    @GetMapping(value = "demo1")
    public SingleResponse<Demo> demo1(Demo demo) {
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
     * POST请求 传参没有注解修饰
     * 传入Body里,参数名为入参的属性名称
     * @param demo
     * @return
     */
    @PostMapping(value = "demo3")
    public SingleResponse<Demo> demo3(Demo demo) {
        return SingleResponse.of(demo);
    }
}
