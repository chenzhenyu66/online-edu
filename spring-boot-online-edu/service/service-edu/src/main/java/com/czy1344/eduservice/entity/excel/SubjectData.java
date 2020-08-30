package com.czy1344.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 2020/7/29 10:04
 *
 * @author czy1344
 * 说明：
 */
@Data
public class SubjectData {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;
}
