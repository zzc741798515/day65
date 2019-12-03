package cn.nyse.service.impl;

import cn.nyse.dao.DetailMapper;
import cn.nyse.dao.TransferMapper;
import cn.nyse.dao.WorkOrderMapper;
import cn.nyse.entity.Detail;
import cn.nyse.entity.Transfer;
import cn.nyse.entity.WorkOrder;
import cn.nyse.service.WorkOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrder> implements WorkOrderService {


    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;


    @Override
    public PageInfo<WorkOrder> selectByCondition(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        WorkOrderMapper workOrderMapper= (WorkOrderMapper) mapper;
        List<WorkOrder> workOrders = workOrderMapper.selectByCondition(params);
        PageInfo<WorkOrder> pageInfo = new PageInfo<>(workOrders);//生成分页对象

        return pageInfo;
    }

    /**
     * 根据联单的id查询联单详情：
     * 1.worder信息
     * 2.根据order的id查询Detail
     * 3.根据order的id查询转运记录Transfer
     * @param id
     * @return
     */
    @Override
    public WorkOrder selectById(Long id) {
        WorkOrderMapper workOrderMapper= (WorkOrderMapper) mapper;
        WorkOrder workOrder = workOrderMapper.selectById(id);
        List<Detail> details = detailMapper.selectByOrderId(id);
        workOrder.setDetails(details);
        List<Transfer> transfers = transferMapper.selectByOrderId(id);
        workOrder.setTransfers(transfers);

        return workOrder;
    }

}
