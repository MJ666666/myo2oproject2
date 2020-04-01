package com.lmj.o2o.enums;

import lombok.Data;

/**
 * ClassName: RedisEnum
 * Description:
 * date: 2020/3/18 10:50
 *
 * @author MJ
 */

public enum RedisEnum {
    SUCCESS(1,"操作成功"),
    FAIL(-1,"操作失败");



    private int status;
    private String msg;
    private RedisEnum(int status,String msg){
        this.status=status;
        this.msg=msg;

    }

    public int getState() {
        return status;
    }

    public String getStateInfo() {
        return msg;
    }


}
