package com.hao.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.log.domain.po.TempBookingLog;
import com.hao.log.mapper.TempBookingLogMapper;
import com.hao.log.service.ITempBookingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: TempBookingLogService实现类
 * @date 2023-11-09 17:43:55
 */
@Service
@RequiredArgsConstructor
public class TempBookingLogServiceImpl extends ServiceImpl<TempBookingLogMapper, TempBookingLog> implements ITempBookingLogService {


    // 存储临时日志
    @Override
    public void saveTempBookingLog(TempBookingLog tempBookingLog) {
        this.save(tempBookingLog);
    }

    // 删除日志
    @Override
    public boolean deleteTempBookingLog(String userId, String maneuverId) {
        return this.lambdaUpdate()
                .eq(TempBookingLog::getUserId, userId)
                .eq(TempBookingLog::getManeuverId, maneuverId)
                .remove();
    }

    // 获取TempBookingLog
    @Override
    public TempBookingLog getTempBookingLog(String userId, String maneuverId) {
        return this.lambdaQuery()
                .eq(TempBookingLog::getUserId, userId)
                .eq(TempBookingLog::getManeuverId, maneuverId)
                .one();
    }
}
