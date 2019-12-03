package cn.nyse.service.impl;

import cn.nyse.dao.StatuteMapper;
import cn.nyse.entity.Statute;
import cn.nyse.service.StatuteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatuteServiceImpl extends ServiceImpl<Statute> implements StatuteService {


    @Override
    public PageInfo<Statute> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        StatuteMapper statuteMapper= (StatuteMapper) mapper;
        List<Statute> statutes = statuteMapper.selectByCondition(params);

        PageInfo<Statute> pageInfo = new PageInfo<>(statutes);//生成分页对象

        return pageInfo;
    }

}
