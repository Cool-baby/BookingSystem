package com.hao.common.domain.other;

/**
 * @author Hao
 * @program: HD08FileManageSystemServer
 * @description: 各操作成功与否代码
 * @date 2022-09-04 08:09:50
 */
public class Code {

    // 失败
    public static final Integer FAILED = 20000;
    // 成功
    public static final Integer SUCCESS = 20001;
    // 错误
    public static final Integer ERROR = 20002;
    // token失效
    public static final Integer TOKEN_INVALID = 40001;
    // 无权限
    public static final Integer NO_PERMISSION = 40002;
    // 服务器错误
    public static final Integer SYSTEM_ERROR = 50000;
}
