package com.czy1344.usrvservice.entity.vo;

import lombok.Data;

/**
 * 2020/8/7 17:07
 *
 * @author czy1344
 * 说明：
 */
@Data
public class CommentVo {
    private String id;
    private String nickname;
    private String avatar;
    private String gmtCreate;
    private String content;
}
