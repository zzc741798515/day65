package cn.nyse.service;

import cn.nyse.entity.AppVersion;
import com.github.pagehelper.PageInfo;

public interface AppVersionService extends IService<AppVersion> {

//    List<AppVersion> selectAll();

    PageInfo<AppVersion> selectAll(Integer pageNum, Integer pageSize);
}
