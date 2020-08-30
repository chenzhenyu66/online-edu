package com.czy1344.ossservice.controller;

import com.czy1344.commonutils.Result;
import com.czy1344.ossservice.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 2020/7/27 20:40
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduoss/file")
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);

        return Result.success().data("url",url);
    }

    @DeleteMapping("/delete")
    public Result deleteOssFile(String url){
        boolean flag = ossService.deleteOssFile(url);
        return Result.success();
    }

}
