package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserProductMap;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserProductDao
 * Description:
 * date: 2020/3/20 14:23
 *
 * @author MJ
 */
public interface UserProductDao {


    List<Map<String,Object>> getUserProdcutList(UserProductMap userProductMap);
}
