package com.czy1344.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 2020/7/31 10:37
 *
 * @author czy1344
 * 说明：
 */
@Data
public class CourseForPublishVo {
    private String id;//id

    private String title;//课程名称

    private String description; //课程描述

    private String name; // 讲师名称

    private String subjectTitle; //二级分类

    private String subjectParentTitle; //一级分类

    private String cover; //封面

    private Integer lessonNum; //课时

    private BigDecimal price; //价钱

    private String status; //发布状态

    private String gmtCreate; //发布时间

}
