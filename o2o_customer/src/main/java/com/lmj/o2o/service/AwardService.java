package com.lmj.o2o.service;

import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: AwardService
 * Description:
 * date: 2020/3/20 10:43
 *
 * @author MJ
 */
public interface AwardService {



    AwardTO getAwardListByShopId(PageVO awardPage);

    AwardTO getAwardByAwardId(Award award);
}
