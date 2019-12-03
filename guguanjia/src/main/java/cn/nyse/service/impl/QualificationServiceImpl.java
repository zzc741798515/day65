package cn.nyse.service.impl;

import cn.nyse.dao.QualificationMapper;
import cn.nyse.dao.SysUserMapper;
import cn.nyse.entity.Qualification;
import cn.nyse.entity.SysUser;
import cn.nyse.service.QualificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QualificationServiceImpl extends ServiceImpl<Qualification> implements QualificationService {

    @Autowired
    SysUserMapper userMapper;

    @Value("${imgPath}")
    String imgPath;
    /**
     * 根据条件判断，进行动态sql
     *
     * params：
     * pageNum、pageSize、type、check、begin、end
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<Qualification> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));

        //拼接sql  调用 mapper方法
        Example example = new Example(Qualification.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("delFlag","0");
        if(!StringUtils.isEmpty(params.get("type"))){
            criteria.andEqualTo("type",params.get("type"));
        }
        if (!StringUtils.isEmpty(params.get("check"))){
            criteria.andEqualTo("check",params.get("check"));
        }

        if (!StringUtils.isEmpty(params.get("begin"))){
            criteria.andGreaterThan("createDate",params.get("begin"));
        }

        if (!StringUtils.isEmpty(params.get("end"))){
            criteria.andLessThan("createDate",params.get("end"));
        }
        List<Qualification> qualifications = mapper.selectByExample(example);

        QualificationMapper qualificationMapper = (QualificationMapper) mapper;

        //根据qualifications中的uploadUserId和checkUserId查询数据
        for (Qualification qualification : qualifications) {
            Map<String, Object> names = qualificationMapper.selectNames(qualification.getId());
            qualification.setCheckUserName((String) names.get("checkUserName"));
            qualification.setUploadUserName((String)names.get("uploadUserName"));
        }
        return new PageInfo<Qualification>(qualifications);
    }

    /**
     * 根据资质id  查询  企业id   和资质信息   处理资质图片地址
     * @param key
     * @return
     */
    @Override
    public Qualification selectByPrimaryKey(Object key) {
        //根据上传用户id关联用户表查询office_id
        Qualification qualification = mapper.selectByPrimaryKey(key);
        SysUser sysUser = userMapper.selectByPrimaryKey(qualification.getUploadUserId());
        // uploads/企业id/用户图片名
        qualification.setAddress(imgPath+sysUser.getOfficeId()+ File.separator+qualification.getAddress());
        return qualification;
    }
}
