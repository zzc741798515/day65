package cn.nyse.controller;

import cn.nyse.entity.SysOffice;
import cn.nyse.service.SysOfficeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService service;


    @RequestMapping("list")
    public List<SysOffice> list (){
        return service.selectAll();
    }

    @RequestMapping("")
    public PageInfo<SysOffice> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


    @RequestMapping("toUpdate")
    public SysOffice toUpdate(long id){
        //TODO 需要关联区域查询上级区域名
//        return service.selectByPrimaryKey(id);
        return service.selectByOid(id);
    }
//
//    @RequestMapping("update")
//    @ResponseBody
//    public Result update(@RequestBody SysOffice sysOffice){
//
//
//        int i = service.updateByPrimaryKeySelective(sysOffice);
//        Result result = new Result();
//        if(i>0){
//            result.setMsg("操作成功");
//            result.setSuccess(true);
//        }
//        return result;
//
//    }
}
