package com.lmj.o2o.dao;

import com.lmj.o2o.entity.ShopCategory;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCategoryDao
 * Description:
 * date: 2020/3/20 13:32
 *
 * @author MJ
 */
public interface ShopCategoryDao {

    List<Map<String,Object>> getShopCategoryParentList();

    List<Map<String,Object>>  getShopCategoryListByParentId(ShopCategory shopCategory);

}
