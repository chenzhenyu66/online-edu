package com.czy1344.eduservice.service;

import com.czy1344.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.eduservice.entity.vo.ChapterInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterInfoVo> selectAllChapterInfo(String courseId);

    boolean deleteChapterInfo(String id);
}
