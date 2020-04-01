package com.lmj.o2o.controller;

import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ProductCategoryController
 * Description:
 * date: 2020/3/19 16:56
 *
 * @author MJ
 */
@Controller
@RequestMapping("/productCates")
public class ProductCategoryController {



    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping("/getProductCates")
    @ResponseBody
    public Map<String,Object> getProductCatesByShopId(@RequestBody Shop shop) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(shop.getShopId())) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        ProductCategoryTO categoryTO = productCategoryService.getProductCatesListByShopId(shop);
        if (categoryTO.getState()==1) {
            map.put("productCategoryList",categoryTO.getDataList());
            map.put("result",new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;
    }
}
