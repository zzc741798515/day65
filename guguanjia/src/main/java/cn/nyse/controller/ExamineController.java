package cn.nyse.controller;

import cn.nyse.entity.Examine;
import cn.nyse.service.ExamineService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/examine")
public class ExamineController {

    @Autowired
    ExamineService service;

    @RequestMapping("index")
//    @ResponseBody
    public PageInfo<Examine> index(@RequestBody Map<String,Object> params){
        return service.selectAll(params);
    }


    @RequestMapping("toUpdate")
    @ResponseBody
    public Examine toUpdate(Long id){
        return service.selectByPrimaryKey(id);
    }


}
