package cn.nyse.controller;

import cn.nyse.entity.Qualification;
import cn.nyse.entity.Result;
import cn.nyse.service.QualificationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    @RequestMapping("index")
//    @ResponseBody
    public PageInfo<Qualification> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


    @RequestMapping("toUpdate")
    @ResponseBody
    public Qualification toUpdate(Long id){
        return service.selectByPrimaryKey(id);
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Qualification qualification){
        //不更新address、更新审核状态、审核人
        //TODO  更新审核人信息
        qualification.setAddress(null);
        int i = service.updateByPrimaryKeySelective(qualification);
        Result result = new Result();
        if(i>0){
            result.setMsg("操作成功");
            result.setSuccess(true);
        }
        return result;

    }
}
