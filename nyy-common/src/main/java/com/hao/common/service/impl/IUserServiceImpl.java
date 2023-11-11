package com.hao.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.common.domain.po.User;
import com.hao.common.mapper.UserMapper;
import com.hao.common.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: IUserService实现类
 * @date 2023-11-09 16:13:34
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
