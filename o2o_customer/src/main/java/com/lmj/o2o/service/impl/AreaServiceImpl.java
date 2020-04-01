package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AreaDao;
import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.entity.Area;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AreaService;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: AreaServiceImpl
 * Description:
 * date: 2020/3/19 15:52
 *
 * @author MJ
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public AreaTO getAreas() {
        AreaTO areaTO;
        List<Map<String, Object>> areaList = areaDao.getAreaList();
        if (areaList!=null && areaList.size()>0) {
          areaTO=new AreaTO(areaList,OperationEnum.SUCCESS);
          return areaTO;
        }
        areaTO=new AreaTO(OperationEnum.INNER_ERROR);

        return areaTO;
    }
}
