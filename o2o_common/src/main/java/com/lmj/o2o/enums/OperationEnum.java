package com.lmj.o2o.enums;

/**
 * ClassName: OperationEnum
 * Description:
 * date: 2020/3/7 16:39
 *
 * @author MJ
 */
public enum OperationEnum {




    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_RESULT(-1002, "查询结果为空"),
    NULL_PARAM(-1003, "传入了空的信息"),
    TOO_MUCH_FILES(-1004,"传入文件数超过限制"),
    WRONG_PASSWORD(-1005,"密码错误"),
    USER_NOT_EXISTS(-2001,"用户不存在"),
    ACCOUNT_ALREADY_BIND(1001,"该微信已绑定"),
    USERNAME_EXISTS(1002,"用户已存在"),
    QRCODE_EXPIRE(-1010,"二维码已过期"),
    UNGRANTED_OPERATION(-1011,"没权限的操作"),
    USER_EXISTS(1000,"用户已存在");
    private int state;

    private String stateInfo;

    private OperationEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OperationEnum stateOf(int index) {
        for (OperationEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
