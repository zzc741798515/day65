package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.Statute;
import cn.nyse.service.StatuteService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/statute")
public class StatuteController {

    @Autowired
    StatuteService service;

    @RequestMapping("index")
//    @ResponseBody
    public PageInfo<Statute> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


    @RequestMapping("toUpdate")
    @ResponseBody
    public Statute toUpdate(Long id){
        return service.selectByPrimaryKey(id);
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Statute statute){


        int i = service.updateByPrimaryKeySelective(statute);
        Result result = new Result();
        if(i>0){
            result.setMsg("操作成功");
            result.setSuccess(true);
        }
        return result;

    }
}
