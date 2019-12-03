import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.SysRoleMapper;
import cn.nyse.entity.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestSysRole {


    @Autowired
    SysRoleMapper mapper;




    @Test
    public void testSelectOneByCondition(){


        SysRole sysRole = mapper.selectOneByCondition(27);

            System.out.println(sysRole);
//            System.out.println(sysUser.getOfficeName());



    }


    @Test
    public void testUpdateRole(){
        long[] uids = {67L, 68L};
        int i = mapper.updateByUids(2, uids);
    }


    @Test
    public void testInsertBatch(){
        ArrayList<Long> cids = new ArrayList<>();
        cids.add(64L);
        cids.add(69L);
        int i = mapper.insertBatch(cids, 2);
    }
}
