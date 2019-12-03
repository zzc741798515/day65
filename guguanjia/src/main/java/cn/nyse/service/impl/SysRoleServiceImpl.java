package cn.nyse.service.impl;

import cn.nyse.dao.SysRoleMapper;
import cn.nyse.entity.SysRole;
import cn.nyse.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper roleMapper;



    @Override
    public PageInfo<SysRole> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
//        SysRoleMapper sysRoleMapper= (SysRoleMapper) mapper;
        List<SysRole> sysRoles = roleMapper.selectByCondition(params);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);//生成分页对象

        return pageInfo;
    }

    @Override
    public SysRole selectOneByCondition(long rid){
//        SysRoleMapper sysRoleMapper= (SysRoleMapper) mapper;
        SysRole sysRole = roleMapper.selectOneByCondition(rid);
        return sysRole;
    }


    @Override
    public int updateByUids(long rid, long... uids){
        return roleMapper.updateByUids(rid,uids);
    }

    @Override
    public int insertBatch( List<Long> cids,long rid){
        return roleMapper.insertBatch(cids,rid);
    }

}
