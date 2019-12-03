package cn.nyse.dao;

import cn.nyse.entity.SysUser;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser> {


    @SelectProvider(type = SysUserMapperProvider.class,method = "selectByCondition")
    //设置关联查询，将用户id对应的所有的roles查询出来
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "roles",column = "id",many = @Many(select = "cn.nyse.dao.SysRoleMapper.selectRoleByUid"))
    })
    List<SysUser> selectByCondition(Map<String, Object> params);


    @Select("SELECT " +
            " su.*,so.name officeName " +
            "FROM " +
            " sys_user su " +
            "LEFT JOIN " +
            " sys_office so " +
            "ON " +
            " su.office_id=so.id " +
            "WHERE " +
            " su.id=#{uid}")
    //设置关联查询，将用户id对应的所有的roles查询出来
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "roles",column = "id",many = @Many(select = "cn.nyse.dao.SysRoleMapper.selectRoleByUid"))
    })
    SysUser selectOneByCondition(long uid);


@Select("select " +
        " su.* " +
        "from " +
        " sys_role sr,sys_user_role sur,sys_user su " +
        "where " +
        " sr.id=#{rid} " +
        "and " +
        " sr.id=sur.role_id " +
        "and " +
        " su.id=sur.user_id " +
        "and" +
        "  sur.del_flag=0 ")
    List<SysUser> selectByRid(long rid);


    /*

     */
    @Select("select " +
            " * " +
            "from " +
            " sys_user " +
            "where " +
            " office_id=#{oid} " +
            "and " +
            " id  " +
            "not in " +
            "( " +
            "select " +
            " sur.user_id " +
            "from " +
            " sys_role sr,sys_user_role sur " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.id=sur.role_id " +
            ")")
    List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid") long oid);
}