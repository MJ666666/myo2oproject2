package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.entity.UserShopMap;
import com.lmj.o2o.vo.PageVO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: UserShopDao
 * Description:
 * date: 2020/3/21 21:25
 *
 * @author MJ
 */
@Repository
public interface UserShopDao {

    List<UserShopMap> queryAllCustomerByShopID(PageVO pageVO);

    int countAllCustomers(PageVO pageVO);

    int updateUserPoint(UserAwardMap userAwardMap);

    int addNewCustomer(UserProductMap userProductMap);

    int queryExistCustomer(UserProductMap userProductMap);

    int addUserPoint(UserProductMap userProductMap);
}
