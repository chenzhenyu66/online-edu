package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduBanner;
import com.czy1344.eduservice.service.EduBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * 用于后台添加
 * </p>
 *
 * @author czy1344
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/eduservice/banner")
public class EduBannerController {
    @Autowired
    EduBannerService eduBannerService;

    /**
     * 增
     */
    @PostMapping("/banner")
    public Result saveBanner(@RequestBody EduBanner eduBanner){
        eduBannerService.save(eduBanner);
        return Result.success();
    }

    /**
     * 删
     */
    @DeleteMapping("/banner/{id}")
    public Result deleteBanner(@PathVariable("id") String id){
        eduBannerService.removeById(id);
        return Result.success();
    }

    /**
     * 改
     */
    @PutMapping("/banner")
    public Result updateBanner(@RequestBody EduBanner eduBanner){
        eduBannerService.updateById(eduBanner);
        return Result.success();
    }

    /**
     * 查
     */
    @GetMapping("/banner/{id}")
    public Result getBanner(@PathVariable String id){
        eduBannerService.getById(id);
        return Result.success();
    }
}

