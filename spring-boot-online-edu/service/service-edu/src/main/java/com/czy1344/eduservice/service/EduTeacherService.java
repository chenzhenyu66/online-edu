package com.czy1344.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.eduservice.entity.vo.TeacherQuery;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-25
 */
@Service
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    /**
     * 前端界面显示
     */
    Map<String, Object> getAllTeacherToPage(Page<EduTeacher> params);

    /**
     * 用户界面显示教师信息
     * @param id
     * @return
     */
    Map<String, Object> getTeacherAndCourseInfo(String id);
}
