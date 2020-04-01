package com.lmj.o2o.service.impl;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ImageDao;
import com.lmj.o2o.dao.ProductDao;
import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Area;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.ProductImg;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.ProductExecuteException;
import com.lmj.o2o.service.ProductService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductCategoryServiceImpl
 * Description:
 * date: 2020/3/7 16:33
 *
 * @author MJ
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProductDao productDao;

    @Override
    public ProductTO getProductByShopId(PageVO productPage) {
        String productListKey= Consts.PRODUCT_LIST_KEY+"_"+productPage.getShopId().toString()+"_page_"+productPage.getCurrentPage();
        Map<String, Object> map = new HashMap<>();
        ProductTO productTO;
        List<Product> products;
        if (!redisService.existKey(productListKey)) {
            products=productDao.queryProductByShopId(productPage);
            redisService.storeValue(productListKey, GsonUtils.toGsonString(products).replaceAll("/","aaaa"));
            redisService.expireKey(productListKey,Consts.PAGE_EXPIRE_TIME);
        }else {
            String productJsons = redisService.get(productListKey).replaceAll("aaaa","/");
            products = GsonUtils.GsonToList(productJsons, Product.class);
        }
        map.put("productList",products);

        String recordNumsKey=Consts.PRODUCT_LIST_KEY+"_"+productPage.getShopId().toString()+"_totalRecords";
        int totalRecords;
        if (!redisService.existKey(recordNumsKey)) {
            totalRecords = productDao.countTotalRecordsByShopId(productPage);
            redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
            redisService.expireKey(recordNumsKey,Consts.PAGE_EXPIRE_TIME);
        }else {
            totalRecords=Integer.valueOf(redisService.get(recordNumsKey));
        }
        PageUtil.fillParamsIntoMap(map, productPage, totalRecords);

        if (products==null) {
            productTO=new ProductTO(OperationEnum.INNER_ERROR);
        }
        else if (products.size()==0) {
            productTO=new ProductTO(OperationEnum.NULL_RESULT);
        }else {
            productTO=new ProductTO(map, OperationEnum.SUCCESS);
        }

        return productTO;
    }

    @Autowired
    private ImageDao imageDao;

    @Transactional
    @Override
    public ProductTO addNewProduct(Product product,List<ProductImg> imgs) {
        String productListKey=Consts.PRODUCT_LIST_KEY+"_"+product.getShopId().toString();
        Date date = new Date();
        product.setLastEditTime(date);
        product.setCreateTime(date);
        product.setEnableStatus(1);
        int effectRow;
        try {
            effectRow=productDao.addNewProduct(product);
        } catch (Exception e) {
            throw new ProductExecuteException("添加商品失败");
        }
        try {
            Long productId = product.getProductId();
            if (effectRow > 0) {
                for (int i = 0; i < imgs.size(); i++) {
                    imgs.get(i).setProductId(productId);
                }
                int i = imageDao.addImages(imgs);
            }else {
                return new ProductTO(OperationEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new ProductExecuteException("添加商品图片失败");
        }
        redisService.deleteKey(productListKey);
        return  new ProductTO(OperationEnum.SUCCESS);
    }

    @Transactional
    @Override
    public ProductTO updateProduct(Product product, List<ProductImg> imgPaths) {
        String productListKey=Consts.PRODUCT_LIST_KEY+"_"+product.getShopId().toString();
        ProductTO productTO ;
        int i=0;
        if (imgPaths!=null && imgPaths.size()!=0) {
                i = imageDao.deleteImages(product);
        }
        if (i>=0) {
            int imgInsertResult = imageDao.addImages(imgPaths);
            if (imgInsertResult<0 ) {
                throw new ProductExecuteException("图片插入失败");
            }
        }

            int productUpdateResult = productDao.updateProduct(product);
            if (productUpdateResult == 1) {
                productTO = new ProductTO(OperationEnum.SUCCESS);
                redisService.deleteKey(productListKey);
            } else {
                throw new ProductExecuteException("商品信息更新失败");
            }

        return productTO;
    }

    @Override
    public ProductTO updateProductStatus(Product product) {
        ProductTO productTO;
        int i = productDao.updateProduct(product);
        String productListKey=Consts.PRODUCT_LIST_KEY+"_"+product.getShopId().toString();
        if (i==1) {
            productTO=new ProductTO(OperationEnum.SUCCESS);
            redisService.deleteKey(productListKey);
            return productTO;
        }
        productTO=new ProductTO(OperationEnum.INNER_ERROR);
        return productTO;
    }

    @Override
    public ProductTO getProductInfoById(Product product) {
        ProductTO productTO;
        String productKey=Consts.PRODUCT_KEY+"_"+product.getProductId().toString();
        Product productInfo;
        if (!redisService.existKey(productKey)) {
            productInfo=productDao.getProductInfo(product);
            redisService.storeValue(productKey,GsonUtils.toGsonString(productInfo).replaceAll("/","aaaa"));
            redisService.expireKey(productKey,Consts.EXPIRE_TIME);
        }else {
            String productInfoJson = redisService.get(productKey).replaceAll("aaaa","/");
            productInfo=GsonUtils.GsonToBean(productInfoJson,Product.class);
        }
        if (productInfo != null) {
            productTO=new ProductTO(OperationEnum.SUCCESS);
            productTO.setProduct(productInfo);
            return productTO;
        }
        productTO=new ProductTO(OperationEnum.INNER_ERROR);
        return productTO;
    }

}
