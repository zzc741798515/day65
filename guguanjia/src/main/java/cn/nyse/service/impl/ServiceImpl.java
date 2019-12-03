package cn.nyse.service.impl;

import cn.nyse.service.IService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用实现类    实现通用接口方法
 */
public class ServiceImpl<T> implements IService<T> {
    //AppVersionMapper<AppVersion>       UserMapper<User>
    @Autowired
    Mapper<T> mapper;  //泛型注入  会自动根据泛型类型查找Mapper接口的具体子类


    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    //删除是逻辑删除    ：
    //根据传入的t的id值，生成动态更新  更新  字段del_flag 为1
    @Override
    public int delete(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return mapper.existsWithPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public T selectOneByExample(Object example) {
        return mapper.selectOneByExample(example);
    }

    @Override
    public int updateByExample(T t, Object example) {
        return mapper.updateByExample(t,example);
    }

    @Override
    public int updateByExampleSelective(T t, Object example) {
        return mapper.updateByExampleSelective(t,example);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(example,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return mapper.selectByRowBounds(t,rowBounds);
    }
}
