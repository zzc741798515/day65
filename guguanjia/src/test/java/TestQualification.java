import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.QualificationMapper;
import cn.nyse.entity.Qualification;
import cn.nyse.service.QualificationService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestQualification {


    @Autowired
    QualificationMapper mapper;
//
    @Autowired
    QualificationService service;


    /**
     * 根据传入类型、状态、时间生成动态sql
     */
    @Test
    public void testQueryExample(){
        //根据Qualification字节码文件对象创建一个动态sql对象
        Example example = new Example(Qualification.class);// select * from qualification
        //创建一个动态sql  构建对象  andEqualTo(属性名,属性值)
        //select * from qualification where type = 1
        Example.Criteria criteria = example.createCriteria().andEqualTo("type", 1);
        criteria.andEqualTo("check",0).
                andGreaterThan("updateDate","2018-01-11").
                andLessThan("updateDate","2019-11-14");
        List<Qualification> qualifications = mapper.selectByExample(example);
        for (Qualification qualification : qualifications) {
            System.out.println(qualification);
        }
    }

    @Test
    public void testQualicationService(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type","1");
        map.put("begin","2018-01-11");
        PageInfo<Qualification> qualificationPageInfo = service.selectByCondition(map);
        System.out.println(qualificationPageInfo);
    }

    @Test
    public void testSelect(){
//        Qualification qualification = mapper.selectByPrimaryKey(1);
//        System.out.println(qualification);
        Map<String, Object> map = mapper.selectNames(10L);
        System.out.println(map);
    }
}
