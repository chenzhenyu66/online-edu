package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.vo.SubjectFrom;
import com.czy1344.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/eduservice")
public class EduSubjectController {

    private EduSubjectService eduSubjectService;
    @Autowired
    public void setEduSubjectService(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @PostMapping("/uploadSubject")
    public Result uploadFile(MultipartFile file){
        eduSubjectService.saveSubject(file);
        return Result.success();
    }

    @GetMapping("/subjects")
    public Result selectAll(){
        List<SubjectFrom> target = eduSubjectService.selectFromAsList();
        return Result.success().data("items",target);
    }
    @DeleteMapping("/subjects")
    public Result deleteAll(){
        eduSubjectService.remove(null);
        return Result.success();
    }
}

