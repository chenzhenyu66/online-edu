package com.czy1344.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 2020/8/2 14:31
 *
 * @author czy1344
 * 说明：
 */
public interface VoDService {
    String uploadVideoToVoD(MultipartFile file);

    String getVideoPlayUrl(String videoId);

    boolean deleteVideo(String videoId);

    void deleteVideosOnAliyun(List<String> list);
}
