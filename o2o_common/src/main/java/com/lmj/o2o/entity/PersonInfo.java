package com.lmj.o2o.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfo {

	private String userUuid;

	private Long userId;
	private String name;
	private Date birthday;
	private String gender;
	private String phone;
	private String email;
	private String profileImg;
	private Integer customerFlag;
	private Integer shopOwnerFlag;
	private Integer adminFlag;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;


}
