package cn.nyse.controller;

import cn.nyse.entity.SysUser;
import cn.nyse.service.SysUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//替代Controller   自动添加@ResponseBody转换
@RestController
@RequestMapping("manager/sysuser")
public class SysUserController {

    @Autowired
    SysUserService service;

    @RequestMapping("")
//    @ResponseBody
    public PageInfo<SysUser> index(@RequestBody Map<String,Object> params){
        return service.selectByCondition(params);
    }


//

    @RequestMapping("detail")
    public SysUser detail(long id){
        return service.selectOneByCondition(id);
    }


    @RequestMapping("selectByRid")
    public List<SysUser> selectByRid(long rid){
        return service.selectByRid(rid);
    }

    @RequestMapping("selectNoRole")
    public List<SysUser> selectNoRole(long rid,long oid){
        return service.selectNoRole(rid,oid);
    }
}
