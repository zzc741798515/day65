import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.TransferMapper;
import cn.nyse.entity.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestTransfer {


    @Autowired
    TransferMapper mapper;
//



    @Test
    public void testSelectById(){
        List<Transfer> transfers = mapper.selectByOrderId((long) 11);
        System.out.println(transfers);

    }


}
