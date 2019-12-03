package cn.nyse.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class SysRoleProvider {


    /**
     * 根据条件，动态生成sql语句
     * @param params
     * @return
     */
    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select " +
                " sr.*,so.name officeName " +
                "from " +
                " sys_role sr,sys_office so " +
                "where " +
                " sr.office_id=so.id " +
                "and" +
                " sr.del_flag=0 "
        );

        if(params.containsKey("dataScope")&&!StringUtils.isEmpty(params.get("dataScope"))){

            sb.append(" and sr.data_score=#{dataScope}  ");
        }
        if(params.containsKey("remarks")&&!StringUtils.isEmpty(params.get("remarks"))){
            sb.append(" and sr.remarks=#{remarks} ");
        }

        if(params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
            sb.append(" and so.id=#{oid} ");
        }

        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append(" AND sr.name like CONCAT('%',#{name},'%') ");
        }
        return sb.toString();
    }


    public String updateByUids(@Param("rid") long rid, @Param("uids")long... uids){
        StringBuilder sb = new StringBuilder();
        sb.append("update " +
                " sys_user_role " +
                "set " +
                " del_flag=1 " +
                "where " +
                " role_id=#{rid} " +
                "and " +
                " user_id " +
                "in ");
        sb.append("(");
        for (int i = 0; i < uids.length; i++) {
            sb.append("#{uids["+i+"]},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }



    public String insertBatch(@Param("cids") List<Long> cids, @Param("rid")long rid){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_user_role`( `role_id`, `user_id`, `create_by`, `create_date`, " +
                "`update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < cids.size(); i++) {
            sb.append("(#{rid},#{cids["+i+"]},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
