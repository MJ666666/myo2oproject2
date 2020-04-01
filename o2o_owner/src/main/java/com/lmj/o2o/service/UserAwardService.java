package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: UserAwardService
 * Description:
 * date: 2020/3/21 13:37
 *
 * @author MJ
 */
public interface UserAwardService {

    UserAwardTO addNewUserAwardRecord(UserAwardMap userAwardMap);

    UserAwardTO updateUserAwardRecord(UserAwardMap userAwardMap, ShopAuthMap shopAuthMap);

    String getRandomCode();

    /**
     * Description: web端
     *
     * @author: MJ
     * @date: 2020/3/24 16:40
     * @param:
     * @return:
     */
    UserAwardTO getCustomersAwardRecords(PageVO pageVO);

    /**
     * Description: 小程序端
     * @author: MJ
     * @date: 2020/3/24 16:40
     * @param:
     * @return:
     */
    UserAwardTO getHistoryAwardRecords(PageVO pageVO);
}
