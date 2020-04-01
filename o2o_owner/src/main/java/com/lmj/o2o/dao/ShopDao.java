package com.lmj.o2o.dao;


import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ShopDao
 * Description:
 * date: 2020/3/7 10:47
 *
 * @author MJ
 */
@Repository
public interface ShopDao {

    /**
     * Description:
     *      * @author: MJ
     *      * @date: 2020/3/7 11:07
     *      * @param:店主的id
     *      * @return:店主名下所有店铺
     */
    List<Shop> queryOwnerShops(PageVO shopPage);

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/7 11:09
     * @param:商店要修改的信息
     * @return:影响行数
     */
    int updateShopInfo(Shop shop);

    /**
     * Description: 新增商店
     * @author: MJ
     * @date: 2020/3/11 12:52
     * @param:
     * @return:
     */
    int addNewShop(Shop shop);

    Shop queryShopByShopId(Shop shop);


    int countTotalShops(Shop shop);


    /**
     * Description: 管理员
     * @author: MJ
     * @date: 2020/3/27 13:11
     * @param:
     * @return:
     */
    List<Shop> queryAllShops(PageVO pageVO);


}
