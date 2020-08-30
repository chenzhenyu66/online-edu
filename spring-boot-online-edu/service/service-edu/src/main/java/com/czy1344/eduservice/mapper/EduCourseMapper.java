package com.czy1344.eduservice.mapper;

import com.czy1344.commonutils.vo.CourseInfoVo;
import com.czy1344.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czy1344.eduservice.entity.frontvo.CourseWebInfoVo;
import com.czy1344.eduservice.entity.vo.CourseForPublishVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CourseForPublishVo findCourseForPublishById(String courseId);

    List<CourseForPublishVo> findCoursesForList(Integer offset,Integer size);

    CourseWebInfoVo selectInfoWebById(String id);

    /**
     * 订单课程信息，用于usr模块调用
     */
    CourseInfoVo selectCourseInfoToUsr(String courseId);
}
