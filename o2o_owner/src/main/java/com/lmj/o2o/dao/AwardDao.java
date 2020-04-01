package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AwardDao
 * Description:
 * date: 2020/3/19 8:52
 *
 * @author MJ
 */
@Repository
public interface AwardDao {


    List<Award> getAwardListByShopId(Shop shop);

    int updateAward(Award award);


    int deleteAward(Award award);

    int addAward(Award award);


    Award getAwardByAwardId(Award award);
}
