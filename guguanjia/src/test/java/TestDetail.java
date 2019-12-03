import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.DetailMapper;
import cn.nyse.entity.Detail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestDetail {


    @Autowired
    DetailMapper mapper;
//


    @Test
    public void testSelectById(){
        List<Detail> details = mapper.selectByOrderId((long) 11);
        for (Detail detail : details) {
            System.out.println(detail);
        }


    }


}
