package com.czy1344.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.vo.CourseInfoVo;
import com.czy1344.eduservice.client.VodClient;
import com.czy1344.eduservice.entity.EduChapter;
import com.czy1344.eduservice.entity.EduCourse;
import com.czy1344.eduservice.entity.EduCourseDescription;
import com.czy1344.eduservice.entity.EduVideo;
import com.czy1344.eduservice.entity.frontvo.CourseConditionVo;
import com.czy1344.eduservice.entity.frontvo.CourseWebInfoVo;
import com.czy1344.eduservice.entity.vo.CourseForPublishVo;
import com.czy1344.eduservice.mapper.EduCourseMapper;
import com.czy1344.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.servicebase.exception.OnlineEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    public void setEduCourseDescriptionService(EduCourseDescriptionService eduCourseDescriptionService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
    }

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodClient vodClient;





    //添加课程基本信息的方法
    @Override
    public String saveCourseInfoVo(com.czy1344.eduservice.entity.vo.CourseInfoVo courseInfoVo) {
        //1.向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);
        if (insert == 0)
            throw new OnlineEduException(400, "添加课程信息失败");
        String id = eduCourse.getId();

        //2.向info表添加信息
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);

        description.setId(id);
        eduCourseDescriptionService.save(description);

        return id;
    }

    @Override
    public com.czy1344.eduservice.entity.vo.CourseInfoVo findCourseInfoById(String id) {
        com.czy1344.eduservice.entity.vo.CourseInfoVo infoVo = new com.czy1344.eduservice.entity.vo.CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(id);
        System.out.println(eduCourse);
        BeanUtils.copyProperties(eduCourse, infoVo);

        EduCourseDescription description = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(description, infoVo);

        return infoVo;
    }

    @Override
    public String updateCourseInfoById(com.czy1344.eduservice.entity.vo.CourseInfoVo courseInfoVo) {
        //1.向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int i = baseMapper.updateById(eduCourse);
        if (i == 0)
            throw new OnlineEduException(400, "添加课程信息失败");

        //2.说明表中添加
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);

        eduCourseDescriptionService.updateById(description);

        return courseInfoVo.getId();
    }

    @Override
    public CourseForPublishVo findCourseForPublishById(String courseId) {
        CourseForPublishVo course = baseMapper.findCourseForPublishById(courseId);

        return course;
    }

    @Override
    public boolean publishCourseById(String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        int i = baseMapper.updateById(course);

        return i > 0;
    }

    /**
     * 查到所有的课程信息，返回一个list
     *
     * @return
     */
    @Override
    public List<CourseForPublishVo> findCoursesForList(Integer offset, Integer size) {
        List<CourseForPublishVo> coursesForList = baseMapper.findCoursesForList(offset, size);
        return coursesForList;
    }

    /**
     * 删除课程下所有信息
     *
     * @param courseId
     */

    //事务
    //@Transactional
    @Override
    public void deleteCourseInfoInList(String courseId) {
        // 1.删除课时表的所有对应视频
        List<String> list = eduVideoService.selectVideoSourceIdToList(courseId);
        if (list != null)
            vodClient.deleteVideosInfo(list);

        // 2.删除课时表的所有对应小节
        //System.out.println(list);
        QueryWrapper<EduVideo> wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);
        eduVideoService.remove(wrapper);

        // 3.删除章节表中所有对应章节
        QueryWrapper<EduChapter> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        eduChapterService.remove(wrapper2);

        // 4.删除描述表中对应的描述
        QueryWrapper<EduCourseDescription> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("id", courseId);
        eduCourseDescriptionService.remove(wrapper3);


        // 5.删除课程表中对应的课程
        QueryWrapper<EduCourse> wrapper4 = new QueryWrapper<>();
        wrapper4.eq("id", courseId);
        baseMapper.delete(wrapper4);
    }

    /**
     * 用于 用户界面 课程列表的显示
     *
     * @param page
     * @param courseConditionVo
     * @return
     */
    @Override
    public Map<String,Object> getAllCourseToPage(Page<EduCourse> page,
                                                  CourseConditionVo courseConditionVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断有无一级分类
        if (!StringUtils.isEmpty(courseConditionVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseConditionVo.getSubjectParentId());
        }
        // 判断有无二级分类
        if (!StringUtils.isEmpty(courseConditionVo.getSubjectId())) {
            wrapper.eq("subject_id", courseConditionVo.getSubjectId());
        }
        // 判断价格升序，降序
        if (courseConditionVo.getPriceSort() != null && courseConditionVo.getPriceSort() == 1) {
            wrapper.orderByAsc("price");
        }else if(courseConditionVo.getPriceSort() != null && courseConditionVo.getPriceSort() == 2){
            wrapper.orderByDesc("price");
        }
        // 判断时间
        if (courseConditionVo.getGmtModifiedSort() != null && courseConditionVo.getGmtModifiedSort()  == 1) {
            wrapper.orderByAsc("gmt_modified");
        }else if(courseConditionVo.getGmtModifiedSort() != null && courseConditionVo.getGmtModifiedSort() == 2){
            wrapper.orderByDesc("gmt_modified");
        }

        // 判断购买量
        if (courseConditionVo.getBuyCountSort() != null && courseConditionVo.getBuyCountSort()  == 1) {
            wrapper.orderByAsc("gmt_modified");
        }else if(courseConditionVo.getBuyCountSort() != null && courseConditionVo.getBuyCountSort() == 2){
            wrapper.orderByDesc("gmt_modified");
        }

        // 判断课程是否发布

        wrapper.eq("status","Normal");
        baseMapper.selectPage(page,wrapper);

        long total = page.getTotal();
        List<EduCourse> courses = page.getRecords();
        long current = page.getCurrent();
        long size = page.getSize();
        long pages = page.getPages();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String,Object> map = new HashMap<>();

        map.put("total",total);
        map.put("courses",courses);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    /**
     * 用于用户界面  显示课程详情
     * @param id
     * @return
     */
    @Override
    public CourseWebInfoVo selectInfoWebById(String id) {

        CourseWebInfoVo courseInfo = baseMapper.selectInfoWebById(id);

        return courseInfo;
    }

    /**
     * 订单课程信息，用于usr模块调用
     */
    @Override
    public CourseInfoVo selectCourseInfoToUsr(String courseId) {
        CourseInfoVo order = baseMapper.selectCourseInfoToUsr(courseId);

        return order;
    }

}
