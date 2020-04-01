package com.lmj.o2o.controller;


import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ShopCategoryController
 * Description:
 * date: 2020/3/12 17:53
 *
 * @author MJ
 */
@Controller
@RequestMapping("/shopCategory")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @GetMapping("/getShopCategoryList")
    @ResponseBody
    public Map<String,Object> getShopCategoriesList() {
        Map<String,Object> map=new HashMap<>();
        ShopCategoryTO shopCategoryTO = shopCategoryService.getShopCategoryList();
        return getStringObjectMap(map, shopCategoryTO);
    }

    @GetMapping("/getShopParentCategoryList")
    @ResponseBody
    public Map<String,Object> getShopParentCategoriesList() {
        Map<String,Object> map=new HashMap<>();
        ShopCategoryTO shopCategoryTO = shopCategoryService.getShopParentCategoryList();
        return getStringObjectMap(map, shopCategoryTO);
    }

    @GetMapping("/getShopCategoryListByParentId")
    @ResponseBody
    public Map<String,Object> getShopCategoriesListByParentId(ShopCategory shopCategory) {
        Map<String,Object> map=new HashMap<>();
        ShopCategoryTO shopCategoryTO = shopCategoryService.getShopCategoryListByParentId(shopCategory);
        return getStringObjectMap(map, shopCategoryTO);
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> map, ShopCategoryTO shopCategoryTO) {
        if (shopCategoryTO.getState() == 1) {
            map.put("shopCategoryList", shopCategoryTO.getShopCategoryList());
        }
        map.put("result", new Result(shopCategoryTO));
        return map;
    }
}
