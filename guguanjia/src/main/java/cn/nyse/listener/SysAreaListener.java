package cn.nyse.listener;

import cn.nyse.dao.SysAreaMapper;
import cn.nyse.entity.SysArea;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * AnalysisEventListener:抽象监听器
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    private SysAreaMapper mapper;
    private List<SysArea> sysAreas=new ArrayList<>();//用于存放读取到的excel数据,用于批量插入，插入完成后应该清除数据

    public SysAreaListener() {
    }

    public SysAreaListener(SysAreaMapper mapper) {
        this.mapper=mapper;
    }

    /**
     * 每次读取一行会自动调用该方法
//     * @param o
     * @param analysisContext
     */
    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        System.out.println("读取到数据"+sysArea);
        sysAreas.add(sysArea);
        if(sysAreas.size()>=5){//10条进行批处理
            mapper.insertBatch(sysAreas);
            sysAreas.clear();//清空集合
        }
    }

    /**
     * 读取所有数据完成后会自动调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成");
        if(sysAreas.size()>0) {
            mapper.insertBatch(sysAreas);
        }
    }


}
