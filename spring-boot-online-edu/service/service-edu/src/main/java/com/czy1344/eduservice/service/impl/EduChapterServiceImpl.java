package com.czy1344.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.eduservice.entity.EduChapter;
import com.czy1344.eduservice.entity.EduVideo;
import com.czy1344.eduservice.entity.vo.ChapterInfoVo;
import com.czy1344.eduservice.entity.vo.VideoInfoVo;
import com.czy1344.eduservice.mapper.EduChapterMapper;
import com.czy1344.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterInfoVo> selectAllChapterInfo(String courseId) {
        List<ChapterInfoVo> target = new ArrayList<>();
        // 先拿到所有的chapter,并按照sort排序
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");
        wrapper.orderByAsc("gmt_create");
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        // 再拿到所有的Video并按照sort排序
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper();
        wrapper2.eq("course_id", courseId);
        wrapper2.orderByAsc("sort");
        wrapper2.orderByAsc("gmt_create");
        List<EduVideo> eduVideos = eduVideoService.list(wrapper2);

        // 遍历拿到对应chapter的小节
        for (EduChapter chapter : eduChapters) {
            ChapterInfoVo chapterInfoVo = new ChapterInfoVo();

            BeanUtils.copyProperties(chapter, chapterInfoVo);
            //小节的存储list
            List<VideoInfoVo> target2 = new ArrayList<>();
            for (EduVideo video : eduVideos) {

                if (video.getChapterId().equals(chapterInfoVo.getId())){
                    VideoInfoVo videoInfoVo = new VideoInfoVo();
                    BeanUtils.copyProperties(video,videoInfoVo);

                    target2.add(videoInfoVo);
                }
            }
            chapterInfoVo.setChildren(target2);

            target.add(chapterInfoVo);
        }
        return target;
    }

    @Override
    public boolean deleteChapterInfo(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = eduVideoService.count(wrapper);

        // count == 1 false
        // count == 0 true
        if (count <= 0){
            baseMapper.deleteById(id);
            return true;
        }
        return false;
    }
}
