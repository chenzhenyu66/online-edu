package com.czy1344.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.eduservice.entity.EduCourse;
import com.czy1344.eduservice.entity.EduTeacher;
import com.czy1344.eduservice.entity.vo.TeacherQuery;
import com.czy1344.eduservice.mapper.EduTeacherMapper;
import com.czy1344.eduservice.service.EduCourseService;
import com.czy1344.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService eduCourseService;
    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            //大于等于
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            //小于等于
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 用于前台界面显示
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getAllTeacherToPage(Page<EduTeacher> params) {
        // 根据id排下序，也可根据其他排序
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        // 执行后会将所有数据封装到page中去
        baseMapper.selectPage(params,wrapper);

        List<EduTeacher> records = params.getRecords();
        long  total= params.getTotal();
        long current = params.getCurrent();
        boolean next = params.hasNext();
        boolean pre = params.hasPrevious();
        long pages = params.getPages();

        Map<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("total",total);
        map.put("current",current);
        map.put("next",next);
        map.put("pre",pre);
        map.put("pages",pages);

        return map;
    }

    /**
     * 用户界面显示教师信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTeacherAndCourseInfo(String id) {
        // 1.拿到教师信息
        EduTeacher teacher = baseMapper.selectById(id);

        // 2.拿到教师所交所有课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courses = eduCourseService.list(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("teacher",teacher);
        map.put("courses",courses);
        return map;
    }

}
