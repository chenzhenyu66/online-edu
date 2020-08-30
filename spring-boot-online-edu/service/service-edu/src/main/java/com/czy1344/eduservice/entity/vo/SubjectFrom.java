package com.czy1344.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 2020/7/29 14:51
 *
 * @author czy1344
 * 说明：
 */
@Data
public class SubjectFrom {
    private String id;
    private String title;
    private List<SubjectFrom> children;
}
