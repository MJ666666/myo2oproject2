package com.lmj.o2o.dto;

import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.entity.UserProductMap;
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
public class UserAwardTO extends BaseTO{
    private Map<String,Object> map;

    private List<UserAwardMap> list;
    private List<Map<String,Object>> dataList;

    public UserAwardTO(List<UserAwardMap> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public UserAwardTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
}
