package com.lmj.o2o.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RegisterEntity
 * Description:  网页注册owner的实体
 * date: 2020/3/25 17:12
 *
 * @author MJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEntity {
    private String userName;
    private String password;
    private String rePassword;
    private String phone;
    private String email;
    private String nickName;
    private String profileImg;
}
