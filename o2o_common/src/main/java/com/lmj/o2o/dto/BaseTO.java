package com.lmj.o2o.dto;

import lombok.Data;

/**
 * ClassName: BaseTO
 * Description:
 * date: 2020/3/8 10:58
 *
 * @author MJ
 */
@Data
public abstract class BaseTO {
    protected int state;
    protected String stateInfo;
}
