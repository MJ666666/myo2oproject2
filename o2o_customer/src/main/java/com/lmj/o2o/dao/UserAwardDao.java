package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.vo.ExchangeRecordPage;
import com.lmj.o2o.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserAwardDao
 * Description:
 * date: 2020/3/20 15:00
 *
 * @author MJ
 */
public interface UserAwardDao {

    List<Map<String,Object>> getExchangeList(ExchangeRecordPage exchangeRecordPage);

}
