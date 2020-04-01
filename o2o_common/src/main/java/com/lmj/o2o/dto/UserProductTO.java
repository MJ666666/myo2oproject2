package com.lmj.o2o.dto;

import com.lmj.o2o.entity.UserProductCount;
import com.lmj.o2o.entity.UserProductMap;
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
public class UserProductTO extends BaseTO{
    private Map<String,Object> map;
    private List<UserProductMap> list;
    private List<Map<String,Object>> dataList;

    //商品消费情况统计
    private List<List<UserProductCount>> countList;

    public UserProductTO(List<UserProductMap> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public UserProductTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
}
