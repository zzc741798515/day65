package cn.nyse.service.impl;

import cn.nyse.dao.ExamineMapper;
import cn.nyse.entity.Examine;
import cn.nyse.service.ExamineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExamineServiceImpl extends ServiceImpl<Examine> implements ExamineService {



    @Override
    public PageInfo<Examine> selectAll(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        ExamineMapper examineMapper= (ExamineMapper) mapper;
        List<Examine> examines = examineMapper.selectByCondition(params);

        PageInfo<Examine> pageInfo = new PageInfo<>(examines);//生成分页对象

        return pageInfo;
    }



}
