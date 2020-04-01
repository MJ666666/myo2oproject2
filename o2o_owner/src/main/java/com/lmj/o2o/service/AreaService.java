package com.lmj.o2o.service;


import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.entity.Area;

/**
 * ClassName: AreaService
 * Description:
 * date: 2020/3/12 16:48
 *
 * @author MJ
 */
public interface AreaService {

    AreaTO getAreaLists();

    AreaTO addArea(Area area);

    AreaTO modifyArea(Area area);


}
