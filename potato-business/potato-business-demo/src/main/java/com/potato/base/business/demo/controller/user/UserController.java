package com.potato.base.business.demo.controller.user;

import com.potato.base.business.demo.model.User;
import com.potato.base.business.demo.repository.UserRepository;
import com.potato.base.business.demo.service.UserService;
import com.potato.base.plugin.common.dto.SingleResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * user
 *
 * @author lizhifu
 * @date 2021/12/14
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRepository userRepository;
    @RequestMapping("get")
    @ResponseBody
    public SingleResponse<User> get(){
        User user = userRepository.findById(200L).get();
        return SingleResponse.of(user);
    }
}
