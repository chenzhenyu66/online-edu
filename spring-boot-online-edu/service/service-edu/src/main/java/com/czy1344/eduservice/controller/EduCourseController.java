package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.vo.CourseForPublishVo;
import com.czy1344.eduservice.entity.vo.CourseInfoVo;
import com.czy1344.eduservice.service.EduChapterService;
import com.czy1344.eduservice.service.EduCourseService;
import com.czy1344.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/eduservice")
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;


    @PostMapping("/course")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfoVo(courseInfoVo);

        return Result.success().data("id", id);
    }

    @GetMapping("/course/{id}")
    public Result findCourseById(@PathVariable("id") String id) {
        CourseInfoVo courseInfo = eduCourseService.findCourseInfoById(id);
        return Result.success().data("item", courseInfo);
    }

    @PutMapping("/course")
    public Result updateCourseById(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.updateCourseInfoById(courseInfoVo);
        return Result.success().data("id", id);
    }

    /**
     * 拿到需要发布的课程信息,作为发布前界面显示使用
     */
    @GetMapping("/courseInfo/{courseId}")
    public Result findCourseForPublishById(@PathVariable String courseId) {
        CourseForPublishVo courseForPublishVo = eduCourseService.findCourseForPublishById(courseId);
        return Result.success().data("item", courseForPublishVo);
    }

    /**
     * 拿到所有课程信息，作为list界面显示
     */
    @GetMapping("/courseInfos/{page}/{size}")
    public Result findCoursesForList(@PathVariable("page") Integer page,
                                     @PathVariable("size") Integer size) {
        Integer offset = page * size - size;
        List<CourseForPublishVo> coursesForList = eduCourseService.findCoursesForList(offset,size);
        // 注意，这里逻辑删除了
        int total = eduCourseService.count(null);
        return Result.success().data("items", coursesForList).data("total",total);
    }

    /**
     * 发布课程,将课程状态Draft改为Normal
     */
    @PutMapping("/coursePublish/{courseId}")
    public Result publishCourseById(@PathVariable String courseId) {
        boolean flag = eduCourseService.publishCourseById(courseId);

        return flag ? Result.success() : Result.error();
    }

    /**
     * 删除列表信息
     */
    @DeleteMapping("/courseInfo/{courseId}")
    @Transactional
    public Result deleteCourseInfoInList(@PathVariable("courseId") String courseId){
        eduCourseService.deleteCourseInfoInList(courseId);
        return Result.success();
    }



}

