package com.lmj.o2o.entity;


import com.lmj.o2o.dto.BaseTO;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.enums.ShopStateEnum;
import com.lmj.o2o.enums.UserEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Result
 * Description:
 * date: 2020/3/7 15:32
 *
 * @author MJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int statusCode;
    private String msg;

    public Result(OperationEnum operationEnum) {
        this.statusCode=operationEnum.getState();
        this.msg=operationEnum.getStateInfo();
    }

    public Result(BaseTO transferObject) {
        this.statusCode=transferObject.getState();
        this.msg=transferObject.getStateInfo();

    }
    public Result(ShopStateEnum shopStateEnum) {
        this.statusCode=shopStateEnum.getState();
        this.msg=shopStateEnum.getStateInfo();
    }
    public Result(UserEnum userEnum) {
        this.statusCode=userEnum.getState();
        this.msg=userEnum.getStateInfo();
    }
}
