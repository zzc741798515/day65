package cn.nyse.dao;

import org.springframework.util.StringUtils;

import java.util.Map;

public class WorkOrderProvider {



    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select wo.* ,cu.name createName,tu.name transportName,ru.name recipientName,co.name createOfficeName " +
                "from " +
                " work_order wo " +
                "left join " +
                " sys_user cu " +
                "on " +
                " wo.create_user_id=cu.id  " +
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
                " wo.del_flag=0 ");
        if(!StringUtils.isEmpty(params.get("status"))){
            sb.append(" and wo.status=#{status} ");
        }
        if(!StringUtils.isEmpty(params.get("begin"))){
            sb.append(" and wo.create_date>#{begin} ");
        }
        if(!StringUtils.isEmpty(params.get("end"))){
            sb.append(" and wo.create_date<#{end} ");
        }
        if(!StringUtils.isEmpty(params.get("officeName"))){
            sb.append(" and " +
                    " ( " +
                    " co.name=#{officeName} " +
                    " or " +
                    " toffice.name=#{officeName} " +
                    " or " +
                    " ro.name=#{officeName} " +
                    " )");
        }

        return sb.toString();
    }
}
