package com.lmj.o2o.service;


import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.ShopCategory;

/**
 * ClassName: ShopCategoryService
 * Description:
 * date: 2020/3/12 17:49
 *
 * @author MJ
 */
public interface ShopCategoryService {

    ShopCategoryTO getShopCategoryList();

    ShopCategoryTO getShopParentCategoryList();

    ShopCategoryTO getShopCategoryListByParentId(ShopCategory shopCategory);

    ShopCategoryTO getAllShopCategory();

    ShopCategoryTO addShopCategory(ShopCategory shopCategory);

    ShopCategoryTO modifyShopCategory(ShopCategory shopCategory);
}
