package cn.nyse.service;

import cn.nyse.entity.Qualification;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface QualificationService extends IService<Qualification> {


    PageInfo<Qualification> selectByCondition(Map<String, Object> params);
}
