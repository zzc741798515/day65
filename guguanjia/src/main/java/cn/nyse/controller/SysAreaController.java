package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysArea;
import cn.nyse.service.SysAreaService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/area")
public class SysAreaController {

    @Autowired
    SysAreaService service;

    @RequestMapping("")
    public List<SysArea> selectAll(){
        return  service.selectAll();
    }


    @RequestMapping("selectPage")
    public PageInfo<SysArea> selectPage(@RequestBody Map<String,Object> params){
        return  service.selectByPage(params);
    }

    @RequestMapping("toUpdate")

    public SysArea toUpdate(Long id){
        return service.selectByAid(id);
    }


    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        //在response输出之前,设置输出的格式
        //默认不支持中文,new String(fname.getBytes(),"ISO-8859-1"),转义中文编码
        response.addHeader("Content-Disposition", "attachment;filename="+new String("area.xlsx".getBytes(),"ISO-8859-1"));
        //将文件写入到response的输出流
       service.getListOutputStream(response.getOutputStream());//写出
    }

    @RequestMapping("importExcel")
    public Result importExcel(MultipartFile file) throws IOException {
        int i = service.importExcel(file.getInputStream());
        Result result = new Result();
        if(i>0){
            result.setMsg("操作成功");
            result.setSuccess(true);
        }
        return result;
    }


    @RequestMapping("update")
    public Result update(@RequestBody SysArea sysArea){
        int i = service.updateArea(sysArea);

        Result result = new Result();
        if(i>0){
            result.setSuccess(true);
            result.setMsg("操作成功");
        }
        return result;

    }




}
