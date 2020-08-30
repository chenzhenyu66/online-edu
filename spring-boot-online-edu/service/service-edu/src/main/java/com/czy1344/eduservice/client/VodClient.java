package com.czy1344.eduservice.client;

import com.czy1344.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 2020/8/3 13:35
 *
 * @author czy1344
 * 说明：
 */
@FeignClient(name = "service-vod",fallback = VodFileFuseClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/{videoId}")
    public Result deleteVideoInfo(@PathVariable("videoId") String videoId);


    @DeleteMapping("/eduvod/videos")
    public Result deleteVideosInfo(@RequestParam List<String> list);
}
