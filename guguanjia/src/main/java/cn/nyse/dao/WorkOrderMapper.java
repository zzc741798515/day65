package cn.nyse.dao;

import cn.nyse.entity.WorkOrder;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {

    @SelectProvider(type = WorkOrderProvider.class,method = "selectByCondition")
    List<WorkOrder> selectByCondition(Map<String, Object> params);

    @Select("select wo.* ,cu.name createName,cu.phone createPhone,tu.name transportName," +
            "tu.phone transportPhone,ru.name recipientName,ru.phone recipientPhone ," +
            "co.name createOfficeName,toffice.name transportOfficeName,ro.name recipientOfficeName " +
            "from " +
            " work_order wo " +
            "left join " +
            " sys_user cu " +
            "on " +
            " wo.create_user_id=cu.id " +
            "left join " +
            " sys_user tu        " +
            "on " +
            " wo.transport_user_id=tu.id " +
            "left join " +
            " sys_user ru " +
            "on " +
            " wo.recipient_user_id=ru.id " +
            "left join " +
            " sys_office co " +
            "on " +
            " cu.office_id=co.id " +
            "left join " +
            " sys_office toffice " +
            "on " +
            " tu.office_id=toffice.id " +
            "left join " +
            " sys_office ro " +
            "on " +
            " ru.office_id=ro.id " +
            "where " +
            " wo.del_flag=0 " +
            "and " +
            " wo.id=#{id}")
    WorkOrder selectById(Long id);
}