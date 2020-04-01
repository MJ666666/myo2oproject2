package com.lmj.o2o.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ShopSearchVO
 * Description:
 * date: 2020/3/14 21:39
 *
 * @author MJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopSearchVO {
   public static final String INDEX="o2o";
    public static final String TYPE="shop";



    private String area_id;
    private String shop_category_id;
    private String keyword;
    private String parent_category_id;



    private int currentPage;

    private int pageSize;
}
