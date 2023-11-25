package com.hao.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hao.common.domain.other.TokenInfo;
import com.hao.common.util.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hao
 * @program: hmall
 * @description: 将用户信息保存到Thread Local
 * @date 2023-11-24 15:26:19
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取请求头中的用户
        String userId = request.getHeader(TokenInfo.USER_INFO_INSIDE);

        // 2、判断是否为空，如果不为空，存入UserContext
        if (StrUtil.isNotBlank(userId)) {
            UserContext.setUser(userId);
        }

        // 3、放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserContext.removeUser();
    }
}
