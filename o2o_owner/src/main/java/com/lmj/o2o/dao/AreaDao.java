package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AreaDao
 * Description:
 * date: 2020/3/5 14:16
 *
 * @author MJ
 */
@Repository
public interface AreaDao {

     List<Area> getAreas();

     int addArea(Area area);

     int updateArea(Area area);

     int deleteArea(Area area);
}
