package com.lmj.o2o.enums;

/**
 * ClassName: OperationEnum
 * Description:
 * date: 2020/3/7 16:39
 *
 * @author MJ
 */
public enum UserEnum {




    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_RESULT(-1002, "查询结果为空"),
    NULL_USER_ID(-1003, "userID为空");

    private int state;

    private String stateInfo;

    private UserEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static UserEnum stateOf(int index) {
        for (UserEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
