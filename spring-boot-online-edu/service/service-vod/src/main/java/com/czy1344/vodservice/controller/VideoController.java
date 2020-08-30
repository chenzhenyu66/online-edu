package com.czy1344.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.czy1344.commonutils.Result;
import com.czy1344.vodservice.util.AliyunVodSDKUtils;
import com.czy1344.vodservice.util.ConstantPropertiesUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 2020/8/7 9:54
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduvod/video")
public class VideoController {

    @GetMapping("get-play-auth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;


        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return Result.success().message("获取凭证成功").data("playAuth", playAuth);
    }
}
