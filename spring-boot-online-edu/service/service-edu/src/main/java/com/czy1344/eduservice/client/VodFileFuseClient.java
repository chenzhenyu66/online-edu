package com.czy1344.eduservice.client;

import com.czy1344.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 2020/8/3 20:25
 *
 * @author czy1344
 * 说明：触发熔断机制后执行的方法
 */
@Component
public class VodFileFuseClient implements VodClient{
    @Override
    public Result deleteVideoInfo(String videoId) {

        return Result.error().message("上传单个文件出错");
    }

    @Override
    public Result deleteVideosInfo(List<String> list) {
        return Result.error().message("上传多个文件错误");
    }
}
