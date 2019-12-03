import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.SysOfficeMapper;
import cn.nyse.entity.SysOffice;
import cn.nyse.service.SysOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestOffice {


    @Autowired
    SysOfficeMapper mapper;
//
    @Autowired
    SysOfficeService service ;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;//注入spring容器管理的redisTemplate对象


    /**
     * 将查询所有信息放入redis缓存
     * 放入redis的对象类型需要实现序列化接口，否则会SerializationException: Cannot serialize
     */
    @Test
    public void testSelectAll(){
        List<SysOffice> sysOffices = service.selectAll();

        //数据放入redis缓存起来
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //将集合放入value为string结构的redis数据中，template会根据序列化策略，将sysOffices转换成string类型
        opsForValue.set("sysOffices",sysOffices);
        System.out.println("--------------------------");
        System.out.println(opsForValue.get("sysOffices"));
    }

    /**
     * 测试redis作为缓存
     */
    @Test
    public void testServiceSelectAll(){
        List<SysOffice> sysOffices = service.selectAll();
        System.out.println(sysOffices);
        List<SysOffice> sysOffices2 = service.selectAll();
        System.out.println(sysOffices2);
        List<SysOffice> sysOffices3 = service.selectAll();
        System.out.println(sysOffices3);
    }

    /**
     * 测试按照查询条件设置key
     */
    @Test
    public void testSelectByOid(){
        SysOffice sysOffice = service.selectByOid(2);
        System.out.println(sysOffice);

        SysOffice sysOffice2 = service.selectByOid(2);
        System.out.println(sysOffice2);

        SysOffice sysOffice3 = service.selectByOid(5);
        System.out.println(sysOffice3);
    }


    /**
     * 测试设置全部清除的操作
     */
    @Test
    public void testUpdate(){
        SysOffice sysOffice = service.selectByOid(2);
        sysOffice.setEmail("2@qq.com");
        service.updateByPrimaryKey(sysOffice);
    }


}
