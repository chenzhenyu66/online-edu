package com.czy1344.eduservice.service;

import com.czy1344.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoInfoById(String id);

    List<String> selectVideoSourceIdToList(String courseId);
}
