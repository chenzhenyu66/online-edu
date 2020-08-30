package com.czy1344.eduservice.service.impl;

import com.czy1344.eduservice.client.VodClient;
import com.czy1344.eduservice.entity.EduVideo;
import com.czy1344.eduservice.mapper.EduVideoMapper;
import com.czy1344.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    /**
     * 删除视频信息之前，先删除视频
     * @param id
     */
    @Override
    public void removeVideoInfoById(String id) {
        EduVideo video = baseMapper.selectById(id);
        String videoId = video.getVideoSourceId();
        // 删除阿里云中的视频
        System.out.println(videoId);
        if (!StringUtils.isEmpty(videoId))
            vodClient.deleteVideoInfo(videoId);

        // 删除视频信息
        baseMapper.deleteById(id);
    }

    /**
     * 拿到
     * @return
     */
    @Override
    public List<String> selectVideoSourceIdToList(String courseId) {
        List<String> list = baseMapper.selectVideoSourceIdToList(courseId);
        return list;
    }
}
