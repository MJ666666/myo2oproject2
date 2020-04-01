package com.lmj.o2o.dto;


import com.lmj.o2o.entity.Product;
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
public class ProductTO extends BaseTO{
    private Map<String,Object> map;
    private List<Map<String,Object>> list;
    private Product product;


    public ProductTO(List<Map<String,Object>> list, OperationEnum operationEnum) {
        this.list = list;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }
    public ProductTO( Map<String,Object> map, OperationEnum operationEnum) {
        this.map = map;
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }

    public ProductTO(OperationEnum operationEnum) {
        this.state = operationEnum.getState();
        this.stateInfo = operationEnum.getStateInfo();
    }


}
