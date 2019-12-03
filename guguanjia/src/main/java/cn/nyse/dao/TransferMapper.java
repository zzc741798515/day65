package cn.nyse.dao;

import cn.nyse.entity.Transfer;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransferMapper extends Mapper<Transfer> {

    @Select("select " +
            " tr.*,su.name ,su.phone " +
            "from " +
            " transfer tr,sys_user su " +
            "where " +
            " tr.work_order_id=#{id} " +
            "and " +
            " tr.oprate_user_id=su.id " +
            "order by " +
            " tr.create_date desc")
    List<Transfer> selectByOrderId(Long id);
}