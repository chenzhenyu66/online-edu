package com.czy1344.springboot.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * 2020/7/29 9:27
 *
 * @author czy1344
 * 说明：
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        //写操作
        /*String fileName  = "E:\\testEasyExcel.xls";

        List<DataDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataDemo dataDemo = new DataDemo();
            dataDemo.setId(i+1);
            dataDemo.setName("小红帽"+i);
            list.add(dataDemo);
        }

        EasyExcel.write(fileName,DataDemo.class).sheet("学生列表").doWrite(list);*/


        /*=========================================================================*/

        //读操作
        EasyExcel.read("E:\\testEasyExcel.xls",DataDemo.class,new EasyExcelListener()).sheet().doRead();

    }
}
