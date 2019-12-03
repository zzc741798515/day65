package cn.nyse.service.impl;

import cn.nyse.dao.SysAreaMapper;
import cn.nyse.entity.SysArea;
import cn.nyse.listener.SysAreaListener;
import cn.nyse.service.SysAreaService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysAreaServiceImpl extends ServiceImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper areaMapper;

    /**
     * 根据父区域id或者区域名或者不带条件查找所有区域
     * @return
     *
     *
     */
//    @Cacheable(value = "areaCache",key = "'SysAreaServiceImpl:selectByPage:'+#params['pageNum']+':'+#params['pageSize']")
    @Override
    public PageInfo<SysArea> selectByPage(Map<String,Object> params) {
        //{"aid":''}        {}
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageInfo<SysArea> pageInfo = null;
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        //根据父区域id的查询
        if(params.containsKey("aid")&&!StringUtils.isEmpty(params.get("aid"))){
            int id =  (Integer) params.get("aid");
            List<SysArea> list = areaMapper.selectByPid((long) id);
            pageInfo = new PageInfo<>(list);
        }else if(params.containsKey("areaName")&&!StringUtils.isEmpty(params.get("areaName"))){
//TODO   根据区域名字查询    关联  父区域
//            SysArea sysArea = new SysArea();
//            sysArea.setName((String) params.get("areaName"));
//            List<SysArea> list = areaMapper.select(sysArea);
//            pageInfo = new PageInfo<>(list);
        }else {
            //TODO  查询所有数据    关联父区域
//            List<SysArea> list = areaMapper.selectAll();
//            pageInfo = new PageInfo<>(list);
        }
        return pageInfo;
    }

    /*
    根据数据库查询所有SysArea
    根据excel工具转变成输出流
     */
    @Override
    public OutputStream getListOutputStream(OutputStream outputStream){
        List<SysArea> sysAreas = areaMapper.selectAll();


        /**
         * 1.构建写excel对象，传入写入文件和每行记录对应的java类字节文件对象   如果需要自定义设置写入excel中的表头数据或数据格式，需要通过easyExcel的注解在实体类上添加设置
         */
        ExcelWriter excelWriter = EasyExcel.write(outputStream, SysArea.class).build();
        //2.操作excel对象，用于设置excel的配置
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        //3.写出
        excelWriter.write(sysAreas,writeSheet);

        //4.关闭流
        excelWriter.finish();
        return outputStream;
    }

    @Override
    public int importExcel(InputStream inputStream) {
        int result = 0;
        ExcelReader excelReader = EasyExcel.read(inputStream, SysArea.class, new SysAreaListener(areaMapper)).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        result ++;

        return result;
    }

    @Override
    public SysArea selectByAid(long aid){
        SysArea sysArea = areaMapper.selectByAid(aid);
        sysArea.setOldParentIds(sysArea.getParentIds());//给区域绑定旧parentIds
        return areaMapper.selectByAid(aid);
    }

    /**
     * 1.更新当前区域数据
     * 2.根据oldParentIds和parentIds判断是否有更新该parentIds字段，如果有则更新其所有的子级区域的parentIds字段
     *
     * @param sysArea
     * @return
     */
    @Override
    public int updateArea(SysArea sysArea){
        //1.更新当前区域数据
        int result = areaMapper.updateByPrimaryKey(sysArea);

        //2.根据oldParentIds和parentIds判断是否有更新该parentIds字段，如果有则更新其所有的子级区域的parentIds字段
        if(!sysArea.getOldParentIds().equals(sysArea.getParentIds())){
            areaMapper.updateParentIds(sysArea);
            result++;
        }
        return result;
    }
}
