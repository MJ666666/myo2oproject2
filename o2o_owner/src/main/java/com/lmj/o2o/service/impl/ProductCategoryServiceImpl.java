package com.lmj.o2o.service.impl;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ProductCategoryDao;
import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.ProductCategory;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ProductCategoryService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ProductCategoryServiceImpl
 * Description:
 * date: 2020/3/7 16:33
 *
 * @author MJ
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;


    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategoryTO getProductCategoryByShopId(Shop shop) {
        ProductCategoryTO productCategoryTO;
        List<ProductCategory> productCategories;
        String productCategoryListKey= Consts.PRODUCT_CATEGORY_LIST_KEY+"_"+shop.getShopId().toString();
        if (!redisService.existKey(productCategoryListKey)) {
            productCategories=productCategoryDao.queryProductCategory(shop);
            redisService.storeValue(productCategoryListKey, GsonUtils.toGsonString(productCategories).replaceAll("/","aaaa"));
            redisService.expireKey(productCategoryListKey,Consts.EXPIRE_TIME);
        }else{
            String productCateJsons = redisService.get(productCategoryListKey).replaceAll("aaaa","/");
            productCategories = GsonUtils.GsonToList(productCateJsons, ProductCategory.class);
        }

        if (productCategories==null) {
            productCategoryTO=new ProductCategoryTO(OperationEnum.INNER_ERROR);
        }
        else if (productCategories.size()==0) {
            productCategoryTO=new ProductCategoryTO(OperationEnum.NULL_RESULT);
        }else {
            productCategoryTO=new ProductCategoryTO(productCategories,OperationEnum.SUCCESS);
        }

        return productCategoryTO;
    }

    @Override
    public ProductCategoryTO addNewProductCategory(ProductCategory productCategory) {
        ProductCategoryTO productCategoryTO;
        String productCatesKey=Consts.PRODUCT_CATEGORY_LIST_KEY+"_"+productCategory.getShopId().toString();
        int i = productCategoryDao.addProductCategory(productCategory);
        if (i>0) {
            productCategoryTO=new ProductCategoryTO(OperationEnum.SUCCESS);
            productCategoryTO.setProductCategory(productCategory);
            redisService.delKey(productCatesKey);
        }else {
            productCategoryTO=new ProductCategoryTO(OperationEnum.INNER_ERROR);
        }

        return productCategoryTO;
    }

    @Override
    public ProductCategoryTO deleteProductCategory(ProductCategory productCategory) {
        ProductCategoryTO productCategoryTO;
        String productCatesKey=Consts.PRODUCT_CATEGORY_LIST_KEY+"_"+productCategory.getShopId().toString();
        int i = productCategoryDao.deleteProductCategory(productCategory);
        if (i==1) {
            productCategoryTO=new ProductCategoryTO(OperationEnum.SUCCESS);
            redisService.delKey(productCatesKey);
        }else {
            productCategoryTO=new ProductCategoryTO(OperationEnum.INNER_ERROR);
        }

        return productCategoryTO;
    }
}
