package com.lmj.o2o.controller;


import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ShopSearchService;
import com.lmj.o2o.vo.ShopSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ShopSearchController
 * Description:
 * date: 2020/3/14 21:24
 *
 * @author MJ
 */
@Controller
@RequestMapping("/shopSearch")
public class ShopSearchController {

    @Autowired
    private ShopSearchService shopSearchService;

    @PostMapping("/bindSearch")
    @ResponseBody
    public Map<String,Object> bindSearch(@RequestBody ShopSearchVO shopSearchVO) {
        Map<String,Object> map=new HashMap<>();
        ShopTO shopTO = shopSearchService.searchShopViaElasticSearch(shopSearchVO);
        if (shopTO.getState()==1) {
            map.put("shopMapList",shopTO.getList());
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;
    }

}
