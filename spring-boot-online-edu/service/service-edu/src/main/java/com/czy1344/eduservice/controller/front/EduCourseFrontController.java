package com.czy1344.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.Result;
import com.czy1344.commonutils.vo.CourseInfoVo;
import com.czy1344.eduservice.entity.EduCourse;
import com.czy1344.eduservice.entity.frontvo.CourseConditionVo;
import com.czy1344.eduservice.entity.frontvo.CourseWebInfoVo;
import com.czy1344.eduservice.entity.vo.ChapterInfoVo;
import com.czy1344.eduservice.entity.vo.SubjectFrom;
import com.czy1344.eduservice.service.EduChapterService;
import com.czy1344.eduservice.service.EduCourseService;
import com.czy1344.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 2020/8/4 13:42
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduservice/frontcourse")
public class EduCourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Autowired
    private EduChapterService eduChapterService;
    /**
     * 在前台显示观看量前8的课程信息
     */

    @GetMapping("/course")
    @Cacheable(key = "'course'",value = "getCourseToList")
    public Result getCourseToList(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> list = eduCourseService.list(wrapper);
        return Result.success().data("items",list);
    }


    @PostMapping("/courses/{current}/{limit}")
    public Result getAllCourseToPage(@PathVariable("current") long current,
                                     @PathVariable("limit") long limit,
                                     @RequestBody CourseConditionVo courseConditionVo){
        Page<EduCourse> page = new Page<>(current,limit);

        Map<String,Object> map = eduCourseService.getAllCourseToPage(page,courseConditionVo);

        return Result.success().data("items",map);
    }

    /**
     *
     * @return
     */
    @GetMapping("/subject")
    @Cacheable(key = "'subject'",value = "getSubjectAsList")
    public Result getSubjectAsList(){
        List<SubjectFrom> subjects = eduSubjectService.selectFromAsList();

        return Result.success().data("items",subjects);
    }

    /**
     * 用户前台 课程详情界面显示
     */
    @GetMapping("/course/{id}")
    public Result selectInfoWebById(@PathVariable("id") String id){
        CourseWebInfoVo courseWebInfoVo = eduCourseService.selectInfoWebById(id);
        return Result.success().data("item",courseWebInfoVo);
    }

    /**
     * 用户前台 课程详情界面显示 章节信息
     * @param id
     * @return
     */
    @GetMapping("/chapter/{id}")
    public Result selectChapterInfoById(@PathVariable("id") String id){

        List<ChapterInfoVo> chapters = eduChapterService.selectAllChapterInfo(id);
        return Result.success().data("items",chapters);
    }

    /**
     * 订单课程信息，用于usr模块调用
     */
    @GetMapping("/coursetoorder/{courseId}")
    public CourseInfoVo selectCourseInfoToUsr(@PathVariable("courseId") String courseId){

        CourseInfoVo info = eduCourseService.selectCourseInfoToUsr(courseId);

        return info;
    }
}
