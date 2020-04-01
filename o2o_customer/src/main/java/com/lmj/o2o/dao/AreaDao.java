package com.lmj.o2o.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AreaDao
 * Description:
 * date: 2020/3/20 11:26
 *
 * @author MJ
 */

public interface AreaDao {


    List<Map<String,Object>> getAreaList();


}
