package com.czy1344.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.entity.EduBanner;
import com.czy1344.eduservice.service.EduBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 2020/8/4 13:40
 *
 * @author czy1344
 * 说明：前台显示界面
 *
 */
@RestController
@RequestMapping("/eduservice/frontbanner")
public class EduBannerFrontController {
    @Autowired
    EduBannerService eduBannerService;
    /**
     * 按照时间查询数据库中最新的3条banner信息
     */
    @GetMapping("/banner")
    @Cacheable(key = "'banner'",value = "getBannerToList")
    public Result getBannerToList(){
        QueryWrapper<EduBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");
        wrapper.last("limit 3");
        List<EduBanner> list = eduBannerService.list(wrapper);

        return Result.success().data("items",list);
    }
}
