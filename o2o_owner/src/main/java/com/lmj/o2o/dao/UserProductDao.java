package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserProductCount;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: UserProductDao
 * Description:
 * date: 2020/3/20 16:48
 *
 * @author MJ
 */
@Repository
public interface UserProductDao {
    //查询共有几条数据用于分页
    int countRecords(PageVO recordsPage);


    List<UserProductMap> geConsumeProductList(PageVO recordsPage);

    int addConsumeRecord(UserProductMap userProductMap);


    int countConsumeRecords(UserProductCount userProductCount);
}
