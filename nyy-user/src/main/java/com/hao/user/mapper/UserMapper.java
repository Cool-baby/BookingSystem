package com.hao.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: UserMapper
 * @date 2023-11-09 16:12:41
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
