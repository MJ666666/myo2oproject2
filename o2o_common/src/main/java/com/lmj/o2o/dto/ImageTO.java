package com.lmj.o2o.dto;


import com.lmj.o2o.entity.ProductImg;
import com.lmj.o2o.enums.OperationEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ImageTO
 * Description:
 * date: 2020/3/11 15:53
 *
 * @author MJ
 */
@Data
public class ImageTO extends BaseTO {
    private List<ProductImg> imgList;
    private List<Map<String,Object>> dataList;
    private ProductImg productImg;

    public ImageTO(OperationEnum operationEnum) {
        this.state=operationEnum.getState();
        this.stateInfo=operationEnum.getStateInfo();
    }




}
