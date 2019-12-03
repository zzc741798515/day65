package cn.nyse.service.impl;

import cn.nyse.dao.SysOfficeMapper;
import cn.nyse.entity.SysOffice;
import cn.nyse.service.SysOfficeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@CacheConfig(cacheNames = "officeCache")//设置全局缓存配置
public class SysOfficeServiceImpl extends ServiceImpl<SysOffice> implements SysOfficeService {

//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;


    @Override
    public PageInfo<SysOffice> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        SysOfficeMapper sysOfficeMapper= (SysOfficeMapper) mapper;
        List<SysOffice> sysOffices = sysOfficeMapper.selectByCondition(params);

        PageInfo<SysOffice> pageInfo = new PageInfo<>(sysOffices);//生成分页对象

        return pageInfo;
    }

    @Cacheable(/*value = "officeCache",*/key = "'SysOfficeServiceImpl:selectByOid:'+#oid")
    @Override
    public SysOffice selectByOid(long oid){
        SysOfficeMapper sysOfficeMapper= (SysOfficeMapper) mapper;
        return sysOfficeMapper.selectByOid(oid);
    }


    /**
     * 使用redis作为缓存，提升热点查询方法的性能和体验
     * 1.从redis中查询是否存在该数据，redis中存在则直接从redis获取
     * 2.redis中不存在，则需要从数据库查询数据，并且返回的数据需要放入redis中
     *
     * key:   SysOfficeServiceImpl:selectAll    类名:方法名:参数列表
     * @return
     */
//    @Override
//    public List<SysOffice> selectAll() {
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        if(redisTemplate.hasKey("SysOfficeServiceImpl:selectAll")) {
//            return (List<SysOffice>) opsForValue.get("SysOfficeServiceImpl:selectAll");
//        }
//        List<SysOffice> sysOffices = super.selectAll();
//        opsForValue.set("SysOfficeServiceImpl:selectAll",sysOffices);//放入redis
//        return sysOffices;
//    }

//默认是设置缓存名，会通过名叫officeCache的缓存对象来管理selectAll方法的查询数据
    //如果缓存中没有数据才会查询数据库，如果有，则直接返回缓存中的数据
    //key是使用springEL表达式语法的:
    //1.如果是字符串，则需要通过''包括住
    //2.可以获取方法中的参数值等信息   #方法参数名
    @Cacheable(/*value="officeCache",*/key ="'SysOfficeServiceImpl:selectAll'" )
    @Override
    public List<SysOffice> selectAll() {
        return super.selectAll();
    }

    /*
    清空缓存officeCache里面的数据数据
     */
    @CacheEvict(/*value = "officeCache",*/allEntries = true)
    @Override
    public int updateByPrimaryKey(SysOffice sysOffice) {
        return super.updateByPrimaryKey(sysOffice);
    }
}
