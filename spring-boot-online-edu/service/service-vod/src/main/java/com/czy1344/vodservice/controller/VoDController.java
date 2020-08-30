package com.czy1344.vodservice.controller;

import com.czy1344.commonutils.Result;
import com.czy1344.vodservice.service.VoDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 2020/8/2 14:31
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduvod")
public class VoDController {

    VoDService voDService;
    @Autowired
    public void setVoDService(VoDService voDService) {
        this.voDService = voDService;
    }

    /**
     * 上传视频
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result uploadVideoToVoD(MultipartFile file) {
        String videoId = voDService.uploadVideoToVoD(file);
        if (videoId.contains("error"))
            return Result.error().data("error", videoId);
        return Result.success().data("videoId", videoId);
    }

    /**
     * 获取播放地址
     * @param videoId
     * @return
     */
    @GetMapping("/video/{videoId}")
    public Result getVideoUrl(@PathVariable String videoId) {
        String url = voDService.getVideoPlayUrl(videoId);

        return Result.success().data("url", url);
    }

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/video/{videoId}")
    public Result deleteVideoInfo(@PathVariable String videoId) {
        boolean b = voDService.deleteVideo(videoId);

        return Result.success() ;
    }

    /**
     * 删除一堆视频
     */
    @DeleteMapping("/videos")
    public Result deleteVideosInfo(@RequestParam List<String> list){
        voDService.deleteVideosOnAliyun(list);
        return Result.success();
    }

}
