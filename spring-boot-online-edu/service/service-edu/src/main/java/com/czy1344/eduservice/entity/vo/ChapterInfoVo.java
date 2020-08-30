package com.czy1344.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 2020/7/31 9:20
 *
 * @author czy1344
 * 说明：
 */
@Data
public class ChapterInfoVo implements Serializable {

    private static final long serialVersionUID = 7716266142747524620L;

    private String id;
    private String title;
    private Integer sort;

    //用于显示小节，由于和ChapterInfoVo类似，所以直接使用，不再创建新类
    private List<VideoInfoVo>  children;
}
