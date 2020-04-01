package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: UserShopService
 * Description:
 * date: 2020/3/24 17:43
 *
 * @author MJ
 */
public interface UserShopService {


    UserShopTO getAllCustomers(PageVO pageVO);
}
