package com.hao.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.common.domain.dto.Result;
import com.hao.user.domain.po.User;
import com.hao.user.domain.vo.LoginVO;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: User服务
 * @date 2023-11-09 16:13:12
 */
public interface IUserService extends IService<User> {

    /**
     * 登录方法
     * @param loginVO 前端传输的用户名密码
     * @return Result
     */
    Result login(LoginVO loginVO);
}
