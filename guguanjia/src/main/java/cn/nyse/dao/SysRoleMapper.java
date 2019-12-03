package cn.nyse.dao;

import cn.nyse.entity.SysRole;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {
    
    @Select("select   " +
            "  sr.*  " +
            "from  " +
            "  sys_user su  " +
            "LEFT JOIN  " +
            "  sys_user_role sur  " +
            "ON  " +
            "  su.id=sur.user_id  " +
            "LEFT JOIN  " +
            "  sys_role sr  " +
            "ON  " +
            "  sr.id=sur.role_id  " +
            "where  " +
            "  su.id=#{uid}  ")
    List<SysRole> selectRoleByUid(long uid);

    @SelectProvider(type = SysRoleProvider.class,method = "selectByCondition")
    List<SysRole> selectByCondition(Map<String, Object> params);

    @Select("select " +
            " sr.*,so.name officeName " +
            "from " +
            " sys_role sr,sys_office so " +
            "where " +
            " sr.id=#{rid} " +
            "and " +
            " sr.office_id=so.id " )
    SysRole selectOneByCondition(long rid);

    /**
     * 根据用户id和角色id动态更新del_flag
     * @param uids
     * @return
     */
    @UpdateProvider(type = SysRoleProvider.class,method = "updateByUids")
    int updateByUids(@Param("rid") long rid, @Param("uids") long... uids);

    @InsertProvider(type = SysRoleProvider.class,method = "insertBatch")
    int insertBatch(@Param("cids") List<Long> cids, @Param("rid") long rid);

}