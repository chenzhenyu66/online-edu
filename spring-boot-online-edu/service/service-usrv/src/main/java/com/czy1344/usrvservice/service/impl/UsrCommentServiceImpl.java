package com.czy1344.usrvservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.usrvservice.entity.UsrComment;
import com.czy1344.usrvservice.entity.vo.CommentVo;
import com.czy1344.usrvservice.mapper.UsrCommentMapper;
import com.czy1344.usrvservice.service.UsrCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@Service
public class UsrCommentServiceImpl extends ServiceImpl<UsrCommentMapper, UsrComment> implements UsrCommentService {


    @Override
    public List<CommentVo> listComment(int current, int limit, String videoId) {
        int index = (current-1) * limit;
        List<CommentVo> list = baseMapper.listComment(index,limit,videoId);
        return list;
    }
}
