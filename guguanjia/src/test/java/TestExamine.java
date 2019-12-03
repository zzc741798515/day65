import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.ExamineMapper;
import cn.nyse.entity.Examine;
import cn.nyse.service.ExamineService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestExamine {


    @Autowired
    ExamineMapper mapper;
//
    @Autowired
    ExamineService service;


    @Test
    public void testSelectCondition(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", "工作");

        List<Examine> examines = mapper.selectByCondition(map);
        System.out.println(examines);
    }

    @Test
    public void testServiceselectAll(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", "工作");

        PageInfo<Examine> pageInfo = service.selectAll(map);
        System.out.println(pageInfo);
    }

}
