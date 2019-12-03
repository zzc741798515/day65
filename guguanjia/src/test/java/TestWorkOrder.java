import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.WorkOrderMapper;
import cn.nyse.entity.WorkOrder;
import cn.nyse.service.WorkOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestWorkOrder {


    @Autowired
    WorkOrderMapper mapper;
//
    @Autowired
    WorkOrderService service;

    @Test
    public void testSelectCondition(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",2);
//        map.put("officeName","重庆市利特环保工程有限公司（黑石子）");
        map.put("begin","2016-09-20");
        List<WorkOrder> workOrders = mapper.selectByCondition(map);
        for (WorkOrder workOrder : workOrders) {
            System.out.println(workOrder);
        }
    }

    @Test
    public void testSelectById(){
        WorkOrder workOrder = mapper.selectById((long) 11);
        System.out.println(workOrder);
    }

    @Test
    public void serviceTestSelectById(){
        WorkOrder workOrder = service.selectById((long) 11);
        System.out.println(workOrder);
    }


}
