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
 * @description: 预约活动实体类
 * @date 2023-11-09 11:47:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("maneuver_info")
public class Maneuver {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 活动ID
    private String maneuverId;

    // 活动名称
    private String maneuverName;

    // 活动说明
    private String maneuverNote;

    // 开始时间
    private LocalDateTime startTime;

    // 结束时间
    private LocalDateTime endTime;

    // 活动总容量
    private Long capacity;

    // 预约规则限制
    private String rules;
}
