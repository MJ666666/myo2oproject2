package com.lmj.o2o.dao;

import com.lmj.o2o.entity.PersonInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * ClassName: UserDao
 * Description:
 * date: 2020/4/4 11:26
 *
 * @author MJ
 */
@Repository
public interface UserDao {


    @Select("select * from tb_person_info where name=#{name}")
    PersonInfo selectUserByUserName(PersonInfo personInfo);
}
