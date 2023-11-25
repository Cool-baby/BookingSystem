package com.hao.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.common.domain.dto.Result;
import com.hao.common.domain.other.Code;
import com.hao.common.util.JWTUtil;
import com.hao.user.domain.po.User;
import com.hao.user.domain.vo.LoginVO;
import com.hao.user.mapper.UserMapper;
import com.hao.user.service.IUserService;
import com.hao.user.util.PasswordUtil;
import org.springframework.stereotype.Service;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: IUserService实现类
 * @date 2023-11-09 16:13:34
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Result login(LoginVO loginVO) {

        // 1、获取用户输入信息
        String userId = loginVO.getUserID();
        String password = loginVO.getUserPassword();

        // 2、根据用户ID查询用户
        User user = lambdaQuery().eq(User::getUserId, userId).one();
        if (user == null) return new Result(Code.ERROR, "用户名错误", null);

        // 3、校验密码
        if (!PasswordUtil.pwdEqual(password, user.getUserPassword())) {
            return new Result(Code.ERROR, "密码错误", null);
        }

        // 4、生成Token
        String token = JWTUtil.createToken(userId);
        return new Result(Code.SUCCESS, "登录成功！", token);
    }
}
