package com.lmj.o2o.service;

import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;

/**
 * ClassName: AwardSerice
 * Description:
 * date: 2020/3/19 10:09
 *
 * @author MJ
 */
public interface AwardSerice {

    AwardTO addNewAward(Award award);

    AwardTO deleteAward(Award award);

    AwardTO updateAward(Award award);

    AwardTO getAwardListByShopId(Shop shop);

    AwardTO getAwardByAwardId(Award award);
}
