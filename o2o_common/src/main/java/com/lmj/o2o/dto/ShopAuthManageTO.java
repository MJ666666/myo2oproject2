package com.lmj.o2o.dto;

import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.enums.OperationEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AwardTO
 * Description:
 * date: 2020/3/19 10:08
 *
 * @author MJ
 */
@Data
public class ShopAuthManageTO extends BaseTO{
    private Map<String,Object> map;

    private ShopAuthMap shopAuthMap;

    private List<ShopAuthMap> list;

    public ShopAuthManageTO(List<ShopAuthMap> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public ShopAuthManageTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
}
