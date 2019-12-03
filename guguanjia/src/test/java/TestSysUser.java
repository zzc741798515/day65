import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.SysUserMapper;
import cn.nyse.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestSysUser {


    @Autowired
    SysUserMapper mapper;

//    @Autowired
//    SysUserService service;

    @Test
    public void testSelectByCondition(){
        HashMap<String, Object> map = new HashMap<>();
//        map.put("uid",26);
//        map.put("rid",1);
        map.put("oid","47");

        List<SysUser> list = mapper.selectByCondition(map);
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
//            System.out.println(sysUser.getOfficeName());
            System.out.println(sysUser.getRoles());
        }

    }

}
