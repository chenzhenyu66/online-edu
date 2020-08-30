package com.czy1344.usrvservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.commonutils.Result;
import com.czy1344.usrvservice.entity.UsrComment;
import com.czy1344.usrvservice.entity.UsrvMember;
import com.czy1344.usrvservice.entity.vo.CommentVo;
import com.czy1344.usrvservice.service.UsrCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@RestController
@RequestMapping("/eduusrv/comment")
public class UsrCommentController {
    @Autowired
    private UsrCommentService usrCommentService;
    /**
     * 添加评论
     */

    @PostMapping("/add/comment")
    public Result addCommentInfo(@RequestBody UsrComment usrComment){
        usrCommentService.save(usrComment);
        return Result.success();
    }

    /**
     * 分页显示评论
     */
    @GetMapping("comments/{current}/{limit}/{videoId}")
    public Result listComment(@PathVariable("current") int current,
                              @PathVariable("limit") int limit,
                              @PathVariable("videoId") String videoId){

        List<CommentVo> list = usrCommentService.listComment(current,limit,videoId);
        return Result.success().data("items",list);
    }
}

