package com.czy1344.eduservice.mapper;

import com.czy1344.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {

    List<String> selectVideoSourceIdToList(String courseId);
}
