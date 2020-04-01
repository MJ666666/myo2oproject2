package com.lmj.o2o.dto;

import com.lmj.o2o.entity.Area;
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
public class AreaTO extends BaseTO {
    private List<Area> areaList;
   private List<Map<String,Object>> dataList;
    public AreaTO(OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
    }

    public AreaTO(List<Map<String,Object>> dataList,OperationEnum operationEnum) {
        this.dataList=dataList;
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
    }
}
