package com.lmj.o2o.oauth2server.dao;


import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.TbPermission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: TbPermissionDao
 * Description:
 * date: 2020/4/3 20:22
 *
 * @author MJ
 */
@Repository
public interface TbPermissionDao {

    @Select("SELECT p.* FROM tb_local_auth AS u\n" +
            "LEFT JOIN tb_user_role AS ur\n" +
            "ON u.user_id = ur.user_id\n" +
            "LEFT JOIN tb_role AS r\n" +
            "ON r.id=ur.role_id\n" +
            "LEFT JOIN tb_role_permission AS\n" +
            "rp ON r.id = rp.role_id\n" +
            "LEFT JOIN tb_permission AS p\n" +
            "ON p.id = rp.permission_id\n" +
            "WHERE u.user_id=#{userId}")
    @Results(@Result(column = "id",property = "permissionId"))
    List<TbPermission> selectPermissionByUserId(LocalAuth tbUser);
}
