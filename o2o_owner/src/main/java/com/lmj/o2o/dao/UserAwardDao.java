package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: UserAwardDao.xml
 * Description:
 * date: 2020/3/21 13:01
 *
 * @author MJ
 */
@Repository
public interface UserAwardDao {

    int addNewUserAwardMap(UserAwardMap userAwardMap);

    int updateUserAwardMap(UserAwardMap userAwardMap);


    List<UserAwardMap> queryAllUserAwardByShopId(PageVO userAwardPage);


    int countTotalRecords(PageVO pageVO);




    /**
     * Description: 小程序端查询兑换记录
     * @author: MJ
     * @date: 2020/3/24 16:28
     * @param:
     * @return:
     */
    List<UserAwardMap> queryAllUserAward(PageVO userAwardPage);
}
