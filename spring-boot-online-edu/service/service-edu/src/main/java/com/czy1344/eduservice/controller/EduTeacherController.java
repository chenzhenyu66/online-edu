package com.czy1344.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduTeacher;
import com.czy1344.eduservice.entity.vo.TeacherQuery;
import com.czy1344.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/eduservice")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有
     *
     * @return
     */
    @ApiOperation("查询所有")
    @GetMapping("/teachers")
    public Result listTeacher() {
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return Result.success().data("total", eduTeachers.size()).data("items", eduTeachers);
    }

    /**
     * 按照id删除
     *
     * @param id
     * @return
     */
    @ApiOperation("按照id删除")
    @DeleteMapping("/teacher/{id}")
    public Result deleteTeacher(@PathVariable("id") String id) {
        if (eduTeacherService.removeById(id)) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 按照id查询
     *
     * @param id
     * @return
     */
    @ApiOperation("按照id查询")
    @GetMapping("/teacher/{id}")
    public Result selectOne(@PathVariable("id") String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.success().data("item", teacher);
    }

    /**
     * 分页查询
     */
    @ApiOperation("分页查询")
    @GetMapping("/teacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable("current") int current, @PathVariable("limit") int limit) {
        Page<EduTeacher> page = new Page<>(current, limit);

        eduTeacherService.pageMaps(page, null);
        List<EduTeacher> records = page.getRecords();

        return Result.success().data("total", page.getTotal()).data("items", records);
    }

    /**
     * 分页条件查询
     *
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation("分页条件查询")
    @PostMapping("/teacherCondition/{current}/{limit}")
    public Result pageQueryCondition(@PathVariable("current") int current, @PathVariable("limit") int limit,
                                     @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(current, limit);

        eduTeacherService.pageQuery(pageParam, teacherQuery);

        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return Result.success().data("total", total).data("rows", records);
    }

    @ApiOperation("插入数据")
    @PostMapping("/teacher")
    public Result insertOne(@RequestBody EduTeacher eduTeacher) {
        if ("".equals(eduTeacher.getAvatar()) || eduTeacher.getAvatar() == null)
            eduTeacher.setAvatar("https://edu-online1024.oss-cn-beijing.aliyuncs.com/2020/07/28/9ef479c8-c11e-4498-9fea-5a0fe98b80b83.jpg");
        if (eduTeacherService.save(eduTeacher))
            return Result.success();
        return Result.error();
    }

    @ApiOperation("更新数据")
    @PutMapping("/teacher")
    public Result updateOne(@RequestBody EduTeacher eduTeacher) {
        if (eduTeacherService.updateById(eduTeacher)) {
            return Result.success();
        }
        return Result.error();
    }
}

