package com.lmj.o2o.dto;

import com.lmj.o2o.entity.ProductCategory;

import com.lmj.o2o.enums.OperationEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductCategoryTO
 * Description:
 * date: 2020/3/7 16:31
 *
 * @author MJ
 */
@Data
@NoArgsConstructor
public class ProductCategoryTO extends BaseTO{

    private List<ProductCategory> list;
    private List<Map<String,Object>> dataList;
    private ProductCategory productCategory;

    public ProductCategoryTO(List<ProductCategory> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public ProductCategoryTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }



}
