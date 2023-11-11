package com.hao.maneuver.util.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Redisson工具
 * @date 2023-11-10 18:28:52
 */
public class RedissonUtil {

    // 创建Redisson客户端
    private static final RedissonClient redissonClient = Redisson.create();

    /**
     * 获取分布式锁
     * @param lockName 锁的名称
     * @param maxWaitTime 最大等待时间
     * @param maxHoldTime 自动释放时间
     * @param timeUnit 时间单位
     * @return 是否上锁成功
     */
    public static boolean tryLock(String lockName, Long maxWaitTime, Long maxHoldTime, TimeUnit timeUnit){
        RLock myLock = redissonClient.getLock(lockName);
        try {
            return myLock.tryLock(maxWaitTime, maxHoldTime, timeUnit);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放分布式锁
     * @param lockName 锁的名称
     */
    public static void unLock(String lockName){
        RLock myLock = redissonClient.getLock(lockName);
        if(myLock.isLocked()){
            myLock.unlock();
        }
    }
}
