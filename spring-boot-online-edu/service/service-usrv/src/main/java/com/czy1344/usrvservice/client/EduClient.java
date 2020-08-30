package com.czy1344.usrvservice.client;

import com.czy1344.commonutils.vo.CourseInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 2020/8/8 9:08
 *
 * @author czy1344
 * 说明：调用edu的feign
 */
@FeignClient(name = "service-edu")
@Component
public interface EduClient {
    /**
     * 订单课程信息，用于usr模块调用
     */
    @GetMapping("/eduservice/frontcourse/coursetoorder/{courseId}")
    public CourseInfoVo selectCourseInfoToUsr(@PathVariable("courseId") String courseId);
}
