package com.hao.maneuver.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 预约活动分段信息实体类
 * @date 2023-11-09 11:51:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("maneuver_segment_info")
public class ManeuverSegment {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 活动ID
    private String maneuverId;

    // 分段ID
    private Long segmentId;

    // 分段开始时间
    private LocalDateTime startTime;

    // 分段结束时间
    private LocalDateTime endTime;

    // 分段容量
    private Long capacity;

    // 剩余容量
    private Long freeCapacity;
}
