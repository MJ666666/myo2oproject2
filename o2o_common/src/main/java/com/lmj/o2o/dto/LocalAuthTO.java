package com.lmj.o2o.dto;

import com.lmj.o2o.entity.LocalAuth;
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
public class LocalAuthTO extends BaseTO{

    private LocalAuth localAuth;

    private String token;

    public LocalAuthTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
    public LocalAuthTO(LocalAuth localAuth,OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
        this.localAuth=localAuth;
    }
}
