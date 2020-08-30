package com.czy1344.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduTeacher;
import com.czy1344.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 2020/8/4 13:46
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduservice/frontteacher")
public class EduTeacherFrontController {

    @Autowired
    EduTeacherService eduTeacherService;
    /**
     * 显示最火的4位教师的信息
     */

    @GetMapping("/teacher")
    @Cacheable(key = "'teacher'",value = "getTeacherToList")
    public Result getTeacherToList(){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> list = eduTeacherService.list(wrapper);

        return Result.success().data("items",list);
    }

    /**
     * 分页查询返回给用户界面
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("/teachers/{current}/{limit}")
//    @Cacheable(key = "'teachers'",value = "getAllTeacherToPage")  后面用SpEL来实现
    public Result getAllTeacherToPage(@PathVariable("current") long current,
                                      @PathVariable("limit") long limit){
        Page<EduTeacher> params = new Page<>(current,limit);

        Map<String,Object> map = eduTeacherService.getAllTeacherToPage(params);

        return Result.success().data("items",map);
    }

    /**
     * 用于显示教师详细信息，+ 教师所交的课程
     */
    @GetMapping("/teacher/{id}")
    public Result getTeacherAndCourseInfo(@PathVariable("id") String id){
        Map<String,Object> map = eduTeacherService.getTeacherAndCourseInfo(id);
        return Result.success().data("items",map);
    }

}
