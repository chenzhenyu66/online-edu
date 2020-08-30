package com.czy1344.eduservice.entity.frontvo;

import lombok.Data;

/**
 * 2020/8/6 9:29
 *
 * @author czy1344
 * 说明：用户用户界面的课程列表显示
 * 主要用于     课程列表页面的     条件查询
 */
@Data
public class CourseConditionVo {

    private String title;       // 标题

    private String subjectParentId;  // 一级分类

    private String subjectId;  // 二级分类

    private Integer priceSort;       // 价格

    private Integer gmtModifiedSort;  // 更新日期

    private String teacherId;   // 讲师id

    private Integer buyCountSort; // 销量

}
