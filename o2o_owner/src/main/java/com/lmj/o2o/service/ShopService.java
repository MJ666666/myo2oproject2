package com.lmj.o2o.service;


import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: ShopService
 * Description:
 * date: 2020/3/7 11:37
 *
 * @author MJ
 */
public interface ShopService {

    ShopTO getShopList(PageVO shopPage);

    ShopTO modifyShopInfo(Shop shop);

    ShopTO addNewShop(Shop shop);

    ShopTO getShopInfo(Shop shop);


    ShopTO getAllShops(PageVO adminPageVO);


    ShopTO adminShopInfo(Shop shop);



}
