package com.lmj.o2o.service.impl;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AreaDao;
import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.entity.Area;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AreaService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * ClassName: AreaServiceImpl
 * Description:
 * date: 2020/3/12 16:50
 *
 * @author MJ
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AreaDao areaDao;

    @Override
    public AreaTO getAreaLists() {
        AreaTO areaTO;
        List<Area> areas;
        if (!redisService.existKey(Consts.AREA_LIST_KEY)) {
            areas = areaDao.getAreas();
            redisService.storeValue(Consts.AREA_LIST_KEY, GsonUtils.toGsonString(areas).replaceAll("/","aaaa"));
            redisService.expireKey(Consts.AREA_LIST_KEY,Consts.EXPIRE_TIME);
        }else {
            String areaJsons = redisService.get(Consts.AREA_LIST_KEY).replaceAll("aaaa","/");
            areas = GsonUtils.GsonToList(areaJsons, Area.class);
        }
        if (areas==null || areas.size()==0) {
            areaTO=new AreaTO(OperationEnum.NULL_RESULT);
            return areaTO;
        }
        areaTO=new AreaTO(OperationEnum.SUCCESS);
        areaTO.setAreaList(areas);
        return areaTO;
    }

    @Override
    public AreaTO addArea(Area area) {
        AreaTO areaTO;
        int i = areaDao.addArea(area);
        if (i==1) {
            areaTO = new AreaTO(OperationEnum.SUCCESS);
            redisService.deleteKey(Consts.AREA_LIST_KEY);
            return areaTO;
        }
        areaTO = new AreaTO(OperationEnum.INNER_ERROR);
        return areaTO;
    }

    @Override
    public AreaTO modifyArea(Area area) {
        AreaTO areaTO;
        int i = areaDao.updateArea(area);
        if (i==1) {
            areaTO = new AreaTO(OperationEnum.SUCCESS);
            redisService.deleteKey(Consts.AREA_LIST_KEY);
            return areaTO;
        }
        areaTO = new AreaTO(OperationEnum.INNER_ERROR);
        return areaTO;
    }
}
