package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ImageDao;
import com.lmj.o2o.dto.ImageTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ImageService;
import org.apache.tomcat.util.bcel.Const;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ImageServiceImpl
 * Description:
 * date: 2020/3/19 17:23
 *
 * @author MJ
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public ImageTO getProductImgs(Product product) {
        ImageTO imageTO;
        List<Map<String, Object>> productImgList = imageDao.getProductImgList(product);
        if (productImgList.size()!=0) {
            imageTO = new ImageTO(OperationEnum.SUCCESS);
            imageTO.setDataList(productImgList);
            return imageTO;
        }
        imageTO=new ImageTO(OperationEnum.INNER_ERROR);
        return imageTO;
    }
}
