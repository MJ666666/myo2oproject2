package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.UserAwardDao;
import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserAwardService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.HttpRequestUtils;
import com.lmj.o2o.vo.ExchangeRecordPage;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserAwardServiceImpl
 * Description:
 * date: 2020/3/20 15:07
 *
 * @author MJ
 */
@Service
public class UserAwardServiceImpl implements UserAwardService {

    @Autowired
    private UserAwardDao userAwardDao;

    @Override
    public UserAwardTO getExchangeRecords(ExchangeRecordPage exchangeRecordPage) {
        UserAwardTO userAwardTO;
        List<Map<String, Object>> exchangeList = userAwardDao.getExchangeList(exchangeRecordPage);
        if (!exchangeList.isEmpty()) {
            userAwardTO = new UserAwardTO(OperationEnum.SUCCESS);
            userAwardTO.setDataList(exchangeList);
            return userAwardTO;
        }
        userAwardTO = new UserAwardTO(OperationEnum.NULL_RESULT);
        return userAwardTO;
    }

    @Override
    public Map<String,Object> postToAddRecord(UserAwardMap userAwardMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("shopId=" + userAwardMap.getShopId().toString());
        sb.append("&awardId=" + userAwardMap.getAwardId().toString());
        sb.append("&usedStatus=" + userAwardMap.getUsedStatus());
        sb.append("&userId=" + userAwardMap.getUserId().toString());
        sb.append("&userName=" + userAwardMap.getUserName());
        sb.append("&awardName=" + userAwardMap.getAwardName());
        sb.append("&point=" + userAwardMap.getPoint());
        String responseJson = HttpRequestUtils.sendPostRequest(Consts.USER_AWARD_SERVICE_URL, sb.toString());
        Map<String, Object> map = GsonUtils.GsonToMaps(responseJson);

        return map;
    }
}
