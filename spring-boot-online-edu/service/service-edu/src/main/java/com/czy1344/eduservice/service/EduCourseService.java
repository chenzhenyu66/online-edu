package com.czy1344.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.vo.CourseInfoVo;
import com.czy1344.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.eduservice.entity.frontvo.CourseConditionVo;
import com.czy1344.eduservice.entity.frontvo.CourseWebInfoVo;
import com.czy1344.eduservice.entity.vo.CourseForPublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfoVo(com.czy1344.eduservice.entity.vo.CourseInfoVo courseInfoVo);

    com.czy1344.eduservice.entity.vo.CourseInfoVo findCourseInfoById(String id);

    String updateCourseInfoById(com.czy1344.eduservice.entity.vo.CourseInfoVo courseInfoVo);

    CourseForPublishVo findCourseForPublishById(String courseId);

    boolean publishCourseById(String courseId);

    List<CourseForPublishVo> findCoursesForList(Integer offset,Integer size);

    void deleteCourseInfoInList(String courseId);

    /**
     * 用于 用户界面 课程列表的显示
     * @param page
     * @param courseConditionVo
     * @return
     */
    Map<String,Object> getAllCourseToPage(Page<EduCourse> page, CourseConditionVo courseConditionVo);


    /**
     * 用于用户界面  显示课程详情
     * @param id
     * @return
     */
    CourseWebInfoVo selectInfoWebById(String id);

    /**
     * 订单课程信息，用于usr模块调用
     */
    CourseInfoVo selectCourseInfoToUsr(String courseId);
}
