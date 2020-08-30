package com.czy1344.usrvservice.mapper;

import com.czy1344.usrvservice.entity.UsrComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czy1344.usrvservice.entity.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
public interface UsrCommentMapper extends BaseMapper<UsrComment> {

    List<CommentVo> listComment(int index, int limit,String videoId);
}
