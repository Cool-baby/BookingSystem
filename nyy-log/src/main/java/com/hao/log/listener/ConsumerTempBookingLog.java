package com.hao.log.listener;

import cn.hutool.core.util.StrUtil;
import com.hao.log.domain.po.TempBookingLog;
import com.hao.log.service.ITempBookingLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 消费临时预约日志
 * @date 2023-11-11 12:07:47
 */
@Service
@RocketMQMessageListener(consumerGroup = "group1", topic = "addTempBookingLog1")
@RequiredArgsConstructor
@Slf4j
public class ConsumerTempBookingLog implements RocketMQListener<TempBookingLog> {

    private final ITempBookingLogService tempBookingLogService;

    @Override
    public void onMessage(TempBookingLog tempBookingLog) {
        if (StrUtil.isNotBlank(tempBookingLog.getManeuverId()) && StrUtil.isNotBlank(tempBookingLog.getUserId())){
            if(tempBookingLog.getState() == 0){ // 如果状态为0，执行插入操作
                tempBookingLogService.saveTempBookingLog(tempBookingLog);
            }else if(tempBookingLog.getState() == 1){ // 如果状态为1，执行删除操作
                tempBookingLogService.deleteTempBookingLog(tempBookingLog.getUserId(), tempBookingLog.getManeuverId());
            }else {
                log.error("状态错误：" + tempBookingLog);
            }
        }else {
            log.error("数据错误：" + tempBookingLog);
        }
    }
}
