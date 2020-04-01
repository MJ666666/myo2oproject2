package com.lmj.o2o.dto;


import com.lmj.o2o.entity.HeadLine;
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
public class HeadLineTO extends BaseTO {



    private List<HeadLine> headLineList;

    private List<Map<String,Object>> dataMap;

    public HeadLineTO(OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
    }

    public HeadLineTO(List<Map<String,Object>> dataMap,OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
        this.dataMap=dataMap;
    }
}
