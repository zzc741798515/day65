package cn.nyse.dao;

import cn.nyse.entity.Detail;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DetailMapper extends Mapper<Detail> {

    @Select("select  " +
            " de.*,wa.code wasteCode,wt.name,wt.code " +
            "from " +
            " detail de,waste wa,waste_type wt " +
            "where " +
            " de.work_order_id=#{id} " +
            "and " +
            " de.waste_type_id=wt.id " +
            "and " +
            " de.waste_id=wa.id")
    List<Detail> selectByOrderId(Long id);
}