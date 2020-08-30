package com.czy1344.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.eduservice.entity.EduSubject;
import com.czy1344.eduservice.entity.excel.SubjectData;
import com.czy1344.eduservice.entity.vo.SubjectFrom;
import com.czy1344.eduservice.mapper.EduSubjectMapper;
import com.czy1344.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.servicebase.exception.OnlineEduException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    private EduSubjectService subjectService;

    @Autowired
    public void setSubjectService(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener()).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿到所有课程分类信息
     * @return
     */
    @Override
    public List<SubjectFrom> selectFromAsList() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> parent = subjectService.list(wrapper);

        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> children = subjectService.list(wrapper2);

        List<SubjectFrom> target = new ArrayList<>();
        for (EduSubject subject : parent) {
            SubjectFrom from = new SubjectFrom();

            from.setTitle(subject.getTitle());
            from.setId(subject.getId());

            List<SubjectFrom> target2 = new ArrayList<>();
            for (EduSubject s : children) {
                if (subject.getId().equals(s.getParentId())) {
                    SubjectFrom from1 = new SubjectFrom();
                    from1.setId(s.getId());
                    from1.setTitle(s.getTitle());
                    target2.add(from1);
                }
            }

            from.setChildren(target2);
            target.add(from);
        }

        return target;
    }

    /*@Override
    public List<SubjectFrom> selectFromAsList() {
        List<SubjectFrom> target = new ArrayList<>();
        List<EduSubject> list = subjectService.list(null);
        // 将父节点先加入
        for (EduSubject s : list) {
            if ("0".equals(s.getParentId())) {
                SubjectFrom from = new SubjectFrom();
                from.setId(s.getId());
                from.setTitle(s.getTitle());
                target.add(from);
            }
        }
        for (SubjectFrom f : target) {
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", f.getId());
            List<EduSubject> list1 = subjectService.list(wrapper);
            List<SubjectFrom> target2 = new ArrayList<>();

            for (EduSubject e : list1) {
                SubjectFrom subjectFrom = new SubjectFrom();
                subjectFrom.setId(e.getId());
                subjectFrom.setTitle(e.getTitle());
                target2.add(subjectFrom);
            }

            f.setChildren(target2);
        }

        return target;
    }*/

    private class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


        @Override
        public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
            if (subjectData == null) {
                throw new OnlineEduException(400, "文件数据为空");
            }
            EduSubject one = exitsOne(subjectData);

            if (one == null) {
                one = new EduSubject();
                one.setParentId("0");
                one.setTitle(subjectData.getOneSubjectName());
                subjectService.save(one);
            }

            EduSubject two = exitsTwo(subjectData, one.getId());

            if (two == null) {
                two = new EduSubject();
                two.setTitle(subjectData.getTwoSubjectName());
                two.setParentId(one.getId());
                subjectService.save(two);
            }
        }

        private EduSubject exitsOne(SubjectData subjectData) {
            //一级目录不能重复
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            String oneName = subjectData.getOneSubjectName();
            wrapper.eq("title", oneName);
            wrapper.eq("parent_id", "0");
            EduSubject one = subjectService.getOne(wrapper);
            return one;
        }

        private EduSubject exitsTwo(SubjectData subjectData, String parentId) {
            //二级目录不能重复
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            String twoName = subjectData.getTwoSubjectName();
            wrapper.eq("title", twoName);
            wrapper.eq("parent_id", parentId);
            EduSubject two = subjectService.getOne(wrapper);
            return two;
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }
    }
}
