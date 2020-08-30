package com.czy1344.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 2020/7/27 20:38
 *
 * @author czy1344
 * 说明：
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);

    boolean deleteOssFile(String url);
}
