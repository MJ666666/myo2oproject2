package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.vo.ExchangeRecordPage;
import com.lmj.o2o.vo.PageVO;

import java.util.Map;

/**
 * ClassName: UserAwardService
 * Description:
 * date: 2020/3/20 15:05
 *
 * @author MJ
 */
public interface UserAwardService {

    UserAwardTO getExchangeRecords(ExchangeRecordPage pageVO);

    Map<String,Object> postToAddRecord(UserAwardMap userAwardMap);
}
