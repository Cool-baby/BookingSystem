package com.hao.user.controller;

import com.hao.common.domain.dto.Result;
import com.hao.user.domain.vo.LoginVO;
import com.hao.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 用户Controller
 * @date 2023-11-25 19:44:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginVO loginVO) {
        return userService.login(loginVO);
    }
}
