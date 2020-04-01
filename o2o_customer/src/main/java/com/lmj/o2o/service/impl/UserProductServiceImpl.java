package com.lmj.o2o.service.impl;

import com.lmj.o2o.dao.UserProductDao;
import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserProductServiceImpl
 * Description:
 * date: 2020/3/20 14:34
 *
 * @author MJ
 */
@Service
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private UserProductDao userProductDao;

    @Override
    public UserProductTO getRecords(UserProductMap userProductMap) {
        UserProductTO userProductTO;
        List<Map<String, Object>> dataList = userProductDao.getUserProdcutList(userProductMap);
        if (dataList.size()>0) {
            userProductTO=new UserProductTO(OperationEnum.SUCCESS);
            userProductTO.setDataList(dataList);
            return userProductTO;
        }
        userProductTO=new UserProductTO(OperationEnum.NULL_RESULT);
        return userProductTO;
    }

}
