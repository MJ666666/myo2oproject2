package com.lmj.o2o.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalAuth {
	private String uuid;
	private Long localAuthId;
	private String userName;
	private String password;
	private Long userId;
	private Date createTime;
	private Date lastEditTime;
	private PersonInfo personInfo;

	//用于修改密码
	private String oldPassword;



}
