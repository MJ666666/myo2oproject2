package com.lmj.o2o.vo;


import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ClassName: PageVO
 * Description:
 * date: 2020/3/11 21:43
 *
 * @author MJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
    private int currentPage;
    private int pageSize;
    private int startIndex;
    private int totals;
    private Long ownerId;
    private Long shopId;
    private Shop shop;
    private Map<String,Object> map;
    private Product product;
    private Long userId;
}
