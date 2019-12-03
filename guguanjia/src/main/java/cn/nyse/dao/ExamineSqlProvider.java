package cn.nyse.dao;

import org.springframework.util.StringUtils;

import java.util.Map;

public class ExamineSqlProvider {

//map:[params:map,param1:.....]
//    public String selectByCondition(@Param("params") Map<String,Object> map){
//        Map <String,Object> condition = (Map<String, Object>) map.get("params");
//        StringBuilder sb = new StringBuilder();
//        sb.append("select ex.*,su.name userName,so.name officeName from  " +
//                " examine ex , sys_user su , sys_office so  " +
//                "where " +
//                " ex.del_flag=0" +
//                " and " +
//                " ex.examine_user_id=su.id " +
//                "and " +
//                " su.office_id=so.id ");
//        if(!StringUtils.isEmpty(condition.get("officeId"))){
//            sb.append(" and so.id=#{params.officeId} ");
//        }
//        if(!StringUtils.isEmpty(condition.get("userName"))){
//            sb.append(" and su.name like concat('%',#{params.userName},'%') ");
//        }
//        if(!StringUtils.isEmpty(condition.get("type"))){
//            sb.append(" and ex.type=#{params.type} ");
//        }
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//  condition:    {"officeId":....}
     public String selectByCondition(Map<String,Object> condition){
        StringBuilder sb = new StringBuilder();
        sb.append("select ex.*,su.name userName,so.name officeName from  " +
                " examine ex , sys_user su , sys_office so  " +
                "where " +
                " ex.del_flag=0" +
                " and " +
                " ex.examine_user_id=su.id " +
                "and " +
                " su.office_id=so.id ");
        if(!StringUtils.isEmpty(condition.get("officeId"))){
            sb.append(" and so.id=#{officeId} ");
        }
        if(!StringUtils.isEmpty(condition.get("userName"))){
            sb.append(" and su.name like concat('%',#{userName},'%') ");
        }
        if(!StringUtils.isEmpty(condition.get("type"))){
            sb.append(" and ex.type=#{type} ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
