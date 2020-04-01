package com.lmj.o2o.controller;

import com.lmj.o2o.dto.ImageTO;
import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ImageService;
import com.lmj.o2o.service.ProductSearchService;
import com.lmj.o2o.vo.ProductSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ProductSearchController
 * Description:
 * date: 2020/3/16 14:19
 *
 * @author MJ
 */
@Controller
@RequestMapping("/productSearch")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @PostMapping("/searchProduct")
    @ResponseBody
    public Map<String,Object> productSearch(@RequestBody ProductSearchVO productSearchVO) {
        Map<String, Object> map = new HashMap<>();
        ProductTO productTO = productSearchService.searchProduct(productSearchVO);
        if (productTO.getState()==1) {
            map.put("productList",productTO.getList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.INNER_ERROR));
        return map;

    }


    @Autowired
    private ImageService imageService;

    @GetMapping("/getProduct")
    @ResponseBody
    public Map<String, Object> getProductByProductId(Product product) {
        System.out.println(product.getProductId());
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(product.getProductId())) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
        }
        ProductTO productTO = productSearchService.searchProductById(product);
        ImageTO imageTO = imageService.getProductImgs(product);
        if (productTO.getState()==1 && imageTO.getState()==1) {
            map.put("result",new Result(OperationEnum.SUCCESS));
            map.put("product",productTO.getList());
            map.put("imgList",imageTO.getDataList());
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;

    }



}
