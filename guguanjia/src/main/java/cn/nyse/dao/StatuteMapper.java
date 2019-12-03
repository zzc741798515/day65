package cn.nyse.dao;

import cn.nyse.entity.Statute;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface StatuteMapper extends Mapper<Statute> {

    /**
     * 动态sql实现  连表查询
     * @param params   {officeId：,      userName: ,     type:}
     * @return
     */
    @SelectProvider(type = StatuteSqlProvider.class,method = "selectByCondition")
    List<Statute> selectByCondition(/*@Param("params")*/ Map<String, Object> condition);
}