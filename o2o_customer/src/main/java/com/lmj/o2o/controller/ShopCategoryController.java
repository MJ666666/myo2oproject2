package com.lmj.o2o.controller;

import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCategoryController
 * Description:
 * date: 2020/3/18 16:21
 *
 * @author MJ
 */
@Controller
@RequestMapping("/shopCategory")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;


    @GetMapping("/getShopParentCategoryList")
    @ResponseBody
    public Map<String,Object> getShopParentCategoriesList() {
        Map<String,Object> map=new HashMap<>();
        ShopCategoryTO shopCategoryTO = shopCategoryService.getShopCategoryParentList();
        return getStringObjectMap(map, shopCategoryTO);
    }

    @GetMapping("/getShopCategoryListByParentId")
    @ResponseBody
    public Map<String,Object> getSubList(ShopCategory shopCategory) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(shopCategory.getParentId())) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        ShopCategoryTO shopCategoryTO = shopCategoryService.getShopCategoryListByParentId(shopCategory);
        return getStringObjectMap(map, shopCategoryTO);
    }


    private Map<String, Object> getStringObjectMap(Map<String, Object> map, ShopCategoryTO shopCategoryTO) {
        if (shopCategoryTO.getState() == 1) {
            map.put("shopCategoryList", shopCategoryTO.getDataMap());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.INNER_ERROR));
        return map;
    }


}

