package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ImageDao;
import com.lmj.o2o.dto.ImageTO;
import com.lmj.o2o.entity.HeadLine;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.ProductImg;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ImageService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * ClassName: ImageServiceImpl
 * Description:
 * date: 2020/3/11 15:51
 *
 * @author MJ
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ImageDao imageDao;

    @Override
    public ImageTO deleteImagesByProductId(Product product) {
        int i = imageDao.deleteImages(product);
        ImageTO imageTO;
        if (i>0) {
            imageTO=new ImageTO(OperationEnum.SUCCESS);
            return imageTO;
        }else {
            return new ImageTO(OperationEnum.INNER_ERROR);
        }
    }

    @Override
    public ImageTO getImgListByProductId(Product product) {
        ImageTO imageTO;
        List<ProductImg> imgList;
        String productListKey= Consts.PRODUCT_LIST_KEY+"_"+product.getProductId().toString();
        if (!redisService.existKey(productListKey)) {
            imgList = imageDao.getImgList(product);
            redisService.storeValue(productListKey, GsonUtils.toGsonString(imgList).replaceAll("/","aaaa"));
            redisService.expireKey(productListKey,Consts.EXPIRE_TIME);
        }else{
            String imgsJosns = redisService.get(productListKey).replaceAll("aaaa","/");
            imgList = GsonUtils.GsonToList(imgsJosns, ProductImg.class);
        }
        if (imgList!=null || imgList.size()!=0) {
            imageTO=new ImageTO(OperationEnum.SUCCESS);
            imageTO.setImgList(imgList);
            return imageTO;
        }
        imageTO=new ImageTO(OperationEnum.INNER_ERROR);
        return imageTO;
    }
}
