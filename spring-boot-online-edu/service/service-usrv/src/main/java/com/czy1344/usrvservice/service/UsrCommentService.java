package com.czy1344.usrvservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.usrvservice.entity.UsrComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.usrvservice.entity.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
public interface UsrCommentService extends IService<UsrComment> {


    List<CommentVo> listComment(int current, int limit, String videoId);
}
