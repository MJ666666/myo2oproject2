package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AwardDao
 * Description:
 * date: 2020/3/20 13:04
 *
 * @author MJ
 */
public interface AwardDao {


    List<Map<String, Object>> getAwardListByShopId(PageVO awardPage);

    List<Map<String, Object>> getAwardByAwardId(Award award);
}
