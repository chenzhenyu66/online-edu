package com.czy1344.eduservice.entity.vo;

import lombok.Data;

/**
 * 2020/8/7 10:49
 *
 * @author czy1344
 * 说明：
 */
@Data
public class VideoInfoVo {
    private String id;
    private String title;
    private Integer sort;
    private Boolean isFree;
    private String videoSourceId;
}
