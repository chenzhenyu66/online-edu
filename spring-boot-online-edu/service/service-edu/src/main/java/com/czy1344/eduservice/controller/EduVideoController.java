package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduVideo;
import com.czy1344.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/eduservice")
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;
    /**
     * 更新视频信息
     *
     */
    @PutMapping("/video")
    public Result updateVideoInfo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.success();
    }

    /**
     * 添加视频信息
     */
    @PostMapping("/video")
    public Result addVideoInfo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.success();
    }

    /**
     * 删除课时视频信息
     * todo 需要同时将视频删除
     */
    @DeleteMapping("/video/{id}")
    public Result deleteVideoInfo(@PathVariable String id){
        eduVideoService.removeVideoInfoById(id);
        return Result.success();
    }


    @GetMapping("/video/{id}")
    public Result getVideoInfo(@PathVariable("id") String id){
        EduVideo video = eduVideoService.getById(id);
        return Result.success().data("item",video);
    }
}

