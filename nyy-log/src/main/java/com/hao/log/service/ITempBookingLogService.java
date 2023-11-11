package com.hao.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.log.domain.po.TempBookingLog;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: TempBookingLogService
 * @date 2023-11-09 17:43:32
 */
public interface ITempBookingLogService extends IService<TempBookingLog> {

    /**
     * 存储临时日志
     * @param tempBookingLog tempBookingLog
     */
    void saveTempBookingLog(TempBookingLog tempBookingLog);

    /**
     * 删除日志
     * @param userId 用户ID
     * @param maneuverId 活动ID
     * @return boolean
     */
    boolean deleteTempBookingLog(String userId, String maneuverId);

    /**
     * 获取TempBookingLog
     * @param userId 用户ID
     * @param maneuverId 活动ID
     * @return TempBookingLog
     */
    TempBookingLog getTempBookingLog(String userId, String maneuverId);
}
