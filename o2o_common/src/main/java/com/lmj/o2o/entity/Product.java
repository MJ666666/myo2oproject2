package com.lmj.o2o.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -349433539553804024L;
	private Long productId;
	private String productName;
	private String productDesc;
	private String imgAddr;// 简略图
	private String normalPrice;
	private String promotionPrice;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;
	private Integer point;

	private List<ProductImg> productImgList;
	private ProductCategory productCategory;
	private Shop shop;
	private Long productCategoryId;
	private Long shopId;

}
