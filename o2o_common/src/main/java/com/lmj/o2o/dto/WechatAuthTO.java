package com.lmj.o2o.dto;

import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.enums.OperationEnum;
import lombok.Data;

/**
 * ClassName: AwardTO
 * Description:
 * date: 2020/3/19 10:08
 *
 * @author MJ
 */
@Data
public class WechatAuthTO extends BaseTO{

    private WechatAuth wechatAuth;


    public WechatAuthTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
    public WechatAuthTO(WechatAuth localAuth, OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
        this.wechatAuth=localAuth;
    }
}
