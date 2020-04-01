package com.lmj.o2o.dto;

import com.lmj.o2o.entity.Award;
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
public class AwardTO extends BaseTO{

    private List<Award> list;
    private Award award;
    private List<Map<String,Object>> dataList;

    public AwardTO(List<Award> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public AwardTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
}
