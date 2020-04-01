package com.lmj.o2o.dto;

import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.UserShopMap;
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
public class UserShopTO extends BaseTO{
    private Map<String,Object> map;

    private List<UserShopMap> list;
    private List<Map<String,Object>> dataList;

    public UserShopTO(List<UserShopMap> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public UserShopTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
}
