package cn.nyse.controller;

import cn.nyse.entity.WorkOrder;
import cn.nyse.service.WorkOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/admin")
public class WorkOrderController {

    @Autowired
    WorkOrderService service;

    @RequestMapping("work")
//    @ResponseBody
    public PageInfo<WorkOrder> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


//    @RequestMapping("toUpdate")
//    @ResponseBody
//    public  WorkOrder toUpdate(Long id){
//        return service.selectByPrimaryKey(id);
//    }
//
//    @RequestMapping("update")
//    @ResponseBody
//    public Result update(@RequestBody  WorkOrder workOrder){
//
//
//        int i = service.updateByPrimaryKeySelective(workOrder);
//        Result result = new Result();
//        if(i>0){
//            result.setMsg("操作成功");
//            result.setSuccess(true);
//        }
//        return result;
//
//    }

    @RequestMapping("work/detail")
    public WorkOrder detail(Long id){
        return service.selectById(id);
    }
}
