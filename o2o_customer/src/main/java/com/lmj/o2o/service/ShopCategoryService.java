package com.lmj.o2o.service;

import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.ShopCategory;

/**
 * ClassName: ShopCategoryService
 * Description:
 * date: 2020/3/18 15:29
 *
 * @author MJ
 */
public interface ShopCategoryService {


    ShopCategoryTO getShopCategoryParentList();

    ShopCategoryTO getShopCategoryListByParentId(ShopCategory shopCategory);
}
