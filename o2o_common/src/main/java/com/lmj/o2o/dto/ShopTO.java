package com.lmj.o2o.dto;


import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.ShopStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopTO
 * Description:
 * date: 2020/3/7 11:35
 *
 * @author MJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopTO extends BaseTO{

    private Map<String,Object> map;
    private List<Map<String,Object>> list;
    private Shop shop;


    public ShopTO(Map<String,Object> map, ShopStateEnum shopStateEnum) {
        this.map=map;
        this.state=shopStateEnum.getState();
        this.stateInfo=shopStateEnum.getStateInfo();

    }

    public ShopTO(ShopStateEnum shopStateEnum) {
        this.state=shopStateEnum.getState();
        this.stateInfo=shopStateEnum.getStateInfo();
    }

    public ShopTO(List<Map<String,Object>> list, ShopStateEnum shopStateEnum) {
        this.list=list;
        this.state=shopStateEnum.getState();
        this.stateInfo=shopStateEnum.getStateInfo();

    }
}
