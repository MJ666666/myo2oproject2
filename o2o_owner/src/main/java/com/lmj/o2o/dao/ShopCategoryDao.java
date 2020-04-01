package com.lmj.o2o.dao;


import com.lmj.o2o.entity.ShopCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ShopCategoryDao
 * Description:
 * date: 2020/3/12 17:17
 *
 * @author MJ
 */
@Repository
public interface ShopCategoryDao {
    List<ShopCategory> getShopCategoryByParentId(ShopCategory shopCategory);

    List<ShopCategory> getShopCategorys();

    List<ShopCategory> getShopParentCategorys();

    int updateShopCategory(ShopCategory shopCategory);

    int addShopCategory(ShopCategory shopCategory);

    int deleteShopCategory(ShopCategory shopCategory);

    List<ShopCategory> queryAllShopCategory();
}
