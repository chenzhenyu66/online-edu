package com.czy1344.springboot.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * 2020/7/29 9:39
 *
 * @author czy1344
 * 说明：
 */
public class EasyExcelListener extends AnalysisEventListener<DataDemo> {

    //一行一行读取
    @Override
    public void invoke(DataDemo dataDemo, AnalysisContext analysisContext) {
        System.out.println("=========="+dataDemo);
    }

    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
