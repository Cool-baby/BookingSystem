package com.hao.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.hao.common.domain.other.TokenInfo;
import com.hao.common.util.JWTUtil;
import com.hao.gateway.config.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 网关登录过滤器
 * @date 2023-11-25 20:49:18
 */
@Component
@RequiredArgsConstructor
public class LoginGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // 过滤器
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
         * 自定义登录拦截器
         *   1、获取request
         *   2、判断当前请求是否要拦截
         *   3、获取token
         *   4、需要拦截，解析token
         *   5、传递用户信息到下游服务
         *   6、放行
         * */
        // 1、获取request
        ServerHttpRequest request = exchange.getRequest();

        // 2、判断当前请求是否要拦截，如果不需要拦截，直接放过
        if (isAllowPath(request)) {
            return chain.filter(exchange);
        }

        // 3、获取token
        String token = null;
        List<String> headers = request.getHeaders().get(TokenInfo.USER_INFO_OUTSIDE);
        if (headers != null) token = headers.get(0);

        // 4、解析token
        String userId;
        userId = JWTUtil.getUserIDByToken(token);
        if (StrUtil.isBlank(userId)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 返回401错误
            return response.setComplete();
        }

        // 5、将用户ID传递到下游服务
        String userInfo = userId;
        exchange.mutate()
                .request(builder -> builder.header(TokenInfo.USER_INFO_INSIDE, userInfo))
                .build();

        return chain.filter(exchange);
    }

    // 优先级
    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isAllowPath(ServerHttpRequest request) {
        boolean flag = false;
        // 1、获取当前路径
        String path = request.getPath().toString();
        String method = request.getMethodValue();

        // 2、要放行的路径
        for (String excludePath : authProperties.getExcludePaths()) {
            boolean isMatch = pathMatcher.match(excludePath, method + ":"+path);
            if (isMatch) {
                flag = true;
                break;
            }
        }

        return flag;
    }
}
