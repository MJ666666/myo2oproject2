package com.lmj.o2o.oauth2server.dao;

import com.lmj.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * ClassName: TbUserDao
 * Description:
 * date: 2020/4/3 19:51
 *
 * @author MJ
 */
@Repository
public interface TbUserDao {


    @Select("select * from tb_local_auth where user_name=#{userName}")
    LocalAuth selectUserByUserName(LocalAuth user);
}
