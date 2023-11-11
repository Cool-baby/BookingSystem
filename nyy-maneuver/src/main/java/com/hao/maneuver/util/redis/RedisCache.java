package com.hao.maneuver.util.redis;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Redis工具类
 * @date 2023-11-10 10:25:43
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisCache {

    private final StringRedisTemplate redisTemplate;

    /**
     * 存入Redis
     * @param key 键
     * @param value 值
     * @param time TTL
     * @param unit 时间单位
     */
    public void setString(String key, Object value, Long time, TimeUnit unit){
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    /**
     * 获取Redis的key
     * @param key 键
     * @return jsonString
     */
    public String getString(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除指定的Key
     * @param key 键
     * @return boolean
     */
    public boolean removeString(String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 往Hash添加key-value
     * @param key 键（Redis查询键）
     * @param mapKey mapKey（键值对的键）
     * @param value 数据
     */
    public void putToHash(String key, String mapKey, Object value){
        redisTemplate.opsForHash().put(key, mapKey, JSONUtil.toJsonStr(value));
    }

    /**
     * 往Hash添加key-value
     * @param key 键（Redis查询键）
     * @param mapKey mapKey（键值对的键）
     * @param value 数据
     */
    public void putToHash(String key, String mapKey, String value){
        redisTemplate.opsForHash().put(key, mapKey, value);
    }

    /**
     * 获取Hash的指定mapKey的值
     * @param key 键
     * @param mapKey Hash Key
     * @return String
     */
    public Object getHashValue(String key, String mapKey){
        return redisTemplate.opsForHash().get(key, mapKey);
    }

    /**
     * 删除Hash中的key-value
     * @param key 键（Redis查询键）
     * @param mapKey mapKey（键值对的键）
     */
    public Long removeValueInHash(String key, String mapKey){
        return redisTemplate.opsForHash().delete(key, mapKey);
    }

    /**
     * 获取Hash长度
     * @param key 键
     * @return Long
     */
    public Long getHashSize(String key){
        return redisTemplate.opsForHash().size(key);
    }

    public Set<Object> getHashKeys(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 增加或减少指定mapKey的值
     * @param key 键
     * @param mapKey Hash Key
     * @param size 步长
     * @return Long
     */
    public Long incrementOrDecrementHash(String key, String mapKey, Long size) {
        return redisTemplate.opsForHash().increment(key, mapKey, size);
    }

    /**
     * 往Redis的Set中添加
     * @param key 键
     * @param value 值
     * @return Long
     */
    public Long addSet(String key, String value){
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 判断Set中是否有此值
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public boolean hasValueInSet(String key, String value){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 删除Set中的某个value，返回Set中剩余键的数量
     * @param key 键
     * @param value 值
     * @return Long Set中剩余键的数量
     */
    public Long removeValueInSet(String key, String value){
        return redisTemplate.opsForSet().remove(key, value);
    }
}
