package com.czy1344.eduservice.service;

import com.czy1344.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.eduservice.entity.vo.SubjectFrom;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-07-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file);

    List<SubjectFrom> selectFromAsList();
}
