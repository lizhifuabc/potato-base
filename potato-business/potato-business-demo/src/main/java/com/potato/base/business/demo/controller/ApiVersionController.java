package com.potato.base.business.demo.controller;

import com.potato.base.plugin.common.annotation.ApiVersion;
import com.potato.base.plugin.common.dto.Response;
import com.potato.base.plugin.framework.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo controller
 *
 * @author lizhifu
 * @date 2021/12/9
 */
@RestController
@RequestMapping("{version}/demo")
public class ApiVersionController extends BaseController {
    @RequestMapping("demo")
    public Response demo(){
        return Response.buildSuccess();
    }

    @ApiVersion(2)
    @RequestMapping("demo")
    public Response demo2(){
        return Response.buildSuccess();
    }
}
