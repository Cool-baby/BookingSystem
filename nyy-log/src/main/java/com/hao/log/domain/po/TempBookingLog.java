package com.hao.log.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 临时预约日志
 * @date 2023-11-09 17:36:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("temp_booking_log")
public class TempBookingLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户ID
    private String userId;

    // 活动ID
    private String maneuverId;

    // 分段ID
    private Long segmentId;

    // 预约时间
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime bookingTime;

    // 状态（1正常、0取消）
    private int state;
}
