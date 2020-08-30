package com.czy1344.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 2020/8/8 9:19
 *
 * @author czy1344
 * 说明：
 */
@Data
public class CourseInfoVo {

    private String courseId;

    private String courseTitle;

    private String courseCover;

    private String teacherName;

    private BigDecimal totalFee;

}
