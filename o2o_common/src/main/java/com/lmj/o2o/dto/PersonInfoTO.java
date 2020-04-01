package com.lmj.o2o.dto;

import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.WechatAuth;
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
public class PersonInfoTO extends BaseTO{

    private Map<String,Object> map;



    public PersonInfoTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

}
