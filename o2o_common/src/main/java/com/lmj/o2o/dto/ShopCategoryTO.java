package com.lmj.o2o.dto;

import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.enums.OperationEnum;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AreaTO
 * Description:
 * date: 2020/3/12 16:49
 *
 * @author MJ
 */
@Data
public class ShopCategoryTO extends BaseTO {
    private List<ShopCategory> shopCategoryList;
    private List<Map<String,Object>> dataMap;

    public ShopCategoryTO(OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
    }
    public ShopCategoryTO(List<Map<String,Object>> dataMap,OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
        this.dataMap=dataMap;
    }
}
