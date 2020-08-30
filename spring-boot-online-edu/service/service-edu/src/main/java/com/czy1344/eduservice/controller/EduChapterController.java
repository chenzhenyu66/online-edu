package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduChapter;
import com.czy1344.eduservice.entity.vo.ChapterInfoVo;
import com.czy1344.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    /**
     * 增
     */
    @PostMapping("/chapter")
    public Result addChapterInfo(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return Result.success();
    }

    /**
     * 查询，要查出章节和小结
     */
    @GetMapping("/chapters/{courseId}")
    public Result selectAllChapterInfo(@PathVariable String courseId) {
        List<ChapterInfoVo> target = eduChapterService.selectAllChapterInfo(courseId);
        return Result.success().data("items", target);
    }

    /**
     *查询，根据id查询
     */
    @GetMapping("/chapter/{id}")
    public Result selectOne(@PathVariable String id) {
        EduChapter chapter = eduChapterService.getById(id);
        return Result.success().data("item", chapter);
    }

    /**
     * 删除，2种方法，
     * 1.要求删除章节后也删除其他小结
     * 2.要求删除其他小结后才允许删除章节(这里用这种方法)
     * @param id
     * @return
     */

    @DeleteMapping("/chapter/{id}")
    public Result deleteChapter(@PathVariable String id) {
        boolean flag = eduChapterService.deleteChapterInfo(id);

        return flag ? Result.success() : Result.error().message("该章节还有其他内容，请检查");
    }

    //编辑，更新
    @PutMapping("/chapter")
    public Result updateChapterInfo(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return Result.success();
    }


}

