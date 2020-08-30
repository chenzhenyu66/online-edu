package com.czy1344.springboot.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 2020/7/29 9:26
 *
 * @author czy1344
 * 说明：excel一行一行的读取
 * 测试easyExcel的读写操作
 */
@Data
public class DataDemo {
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer id;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}
