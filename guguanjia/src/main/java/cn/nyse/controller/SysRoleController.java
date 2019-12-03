package cn.nyse.controller;

import cn.nyse.entity.Result;
import cn.nyse.entity.SysRole;
import cn.nyse.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/role")
public class SysRoleController {

    @Autowired
    SysRoleService service;

    @RequestMapping("")
//    @ResponseBody
    public PageInfo<SysRole> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


//
@RequestMapping("detail")
public SysRole detail(long id){
    return service.selectOneByCondition(id);
}

    @RequestMapping("updateByUids")
    public Result updateByUids(@RequestBody Map<String,Object> params){
        List<Integer> list = (List<Integer>) params.get("uids");
        int rid = (int) params.get("rid");
        long[] uids = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            uids[i]= Integer.valueOf(list.get(i));
        }

        int i = service.updateByUids(rid, uids);

        Result result = new Result();
        if(i>0){
            result.setMsg("更新成功");
            result.setSuccess(true);
        }
        return result;
    }


    @RequestMapping("insertBatch")
    public Result insertBatch(@RequestBody Map<String,Object> params){
        int rid = (int) params.get("rid");
        ArrayList<Long> cids = new ArrayList<>();
        List<Integer> list = (List<Integer>) params.get("cids");
        for (Integer integer : list) {
            cids.add(Long.valueOf(integer));
        }
        int i = service.insertBatch(cids, rid);
        Result result = new Result();
        if(i>0){
            result.setMsg("更新成功");
            result.setSuccess(true);
        }
        return result;
    }

}
