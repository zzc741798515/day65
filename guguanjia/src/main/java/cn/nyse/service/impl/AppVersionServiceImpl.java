package cn.nyse.service.impl;

import cn.nyse.entity.AppVersion;
import cn.nyse.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppVersionServiceImpl extends ServiceImpl<AppVersion> implements AppVersionService {

    @Override
    public PageInfo<AppVersion> selectAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);//开启分页拦截功能

//        List<AppVersion> appVersions = mapper.selectAll();//自动分页
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> appVersions = mapper.select(appVersion);//根据删除标记为0来查询数据
        PageInfo<AppVersion> pageInfo = new PageInfo<>(appVersions);//生成分页对象
//        System.out.println(pageInfo.getList());//获取集合
        return pageInfo;
    }


//    @Autowired
//    AppVersionMapper appVersionMapper;
//
//    @Override
//    public List<AppVersion> selectAll() {
//        return appVersionMapper.selectAll();
//    }
}
