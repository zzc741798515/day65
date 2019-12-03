package cn.nyse.service;

import cn.nyse.entity.SysArea;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface SysAreaService extends  IService<SysArea>{
    PageInfo<SysArea> selectByPage(Map<String, Object> params);

    /*
        根据数据库查询所有SysArea
        根据excel工具转变成输出流
         */
    OutputStream getListOutputStream(OutputStream outputStream);

    /**
     * 根据输入流数据解析每一行数据进行批量插入
     * @param inputStream
     * @return
     */
    int importExcel(InputStream inputStream);

    SysArea selectByAid(long aid);

    int updateArea(SysArea sysArea);
}
