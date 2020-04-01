package com.lmj.o2o.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: UserProductCount
 * Description:
 * date: 2020/3/24 10:10
 *
 * @author MJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductCount {
    private Long productId;
    private String productName;
    private int count;

    private int beforeDay;

}
