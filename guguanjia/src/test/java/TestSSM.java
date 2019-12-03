import cn.nyse.config.SpringMybatis;
import cn.nyse.dao.AppVersionMapper;
import cn.nyse.entity.AppVersion;
import cn.nyse.service.AppVersionService;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestSSM {

    @Autowired
    DruidDataSource druidDataSource;

    @Autowired
    SqlSessionFactory factory;

    @Autowired
    AppVersionMapper mapper;
//
    @Autowired
    AppVersionService service;

    @Test
    public void testConn() throws SQLException {
        System.out.println(druidDataSource.getConnection());

    }

    @Test
    public void testFactory() throws Exception {
        System.out.println(factory.openSession().getConnection());

    }

   @Test
    public void testSelectOne() throws Exception {
        AppVersion user = mapper.selectByPrimaryKey((long) 1);
        System.out.println(user);

    }

    @Test
    public void testService(){
        List<AppVersion> appVersions = service.selectAll();
        for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }

    }

    @Test
    public void testUpdateSeletive(){
        AppVersion appVersion = new AppVersion();
        appVersion.setId(7L);
        appVersion.setUpdateDate(new Date());
        appVersion.setSize(200.0F);
        int i = mapper.updateByPrimaryKeySelective(appVersion);
    }

    /***
     * 插入一条记录并返回自增主键
     */
    @Test
    public void testInsert(){
        AppVersion appVersion = new AppVersion();

        appVersion.setUpdateDate(new Date());
        appVersion.setSize(200.0F);
        appVersion.setPlatform(0);
        appVersion.setVersionNo("1.8.0");
        appVersion.setDownPath("http://127.0.0.1:8080/guguanjia/manager/#/ajax/manager/app/index");
        appVersion.setCreateDate(new Date());
        appVersion.setCreateBy("admin");
        appVersion.setDelFlag("0");
        int i = mapper.insertSelective(appVersion);
        System.out.println(appVersion);
    }

    //模拟事务异常回滚
    /* @Test
    public void testInsertBatch(){
        User user = new User();
        user.setUsername("鸠摩智");
        user.setPassword("123");
        int i = service.insertBatch(user);
        System.out.println(i);
    }
*/


    @Test
    public  void testPageHelper(){
        PageHelper.startPage(2,3);
        List<AppVersion> appVersions = mapper.selectAll();
        PageInfo<AppVersion> pageInfo = new PageInfo<>(appVersions);
        System.out.println(pageInfo.getList());//获取集合
    }

    //测试逻辑删除功能
    @Test
    public void testDelete(){
//        int i = mapper.deleteByPrimaryKey(8);
        AppVersion appVersion = new AppVersion();
        appVersion.setId(1L);
        appVersion.setUpdateDate(new Date());
        appVersion.setDelFlag("1");

        int delete = service.delete(appVersion);
    }

    @Test
    public void testSelectByCondition(){
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> list = mapper.select(appVersion);
        for (AppVersion version : list) {
            System.out.println(version);
        }
    }

}
