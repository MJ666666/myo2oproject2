package com.lmj.o2o.controller.admin;

import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.service.ShopService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminShopCaontroller
 * Description:
 * date: 2020/3/28 9:19
 *
 * @author MJ
 */
@Controller
@RequestMapping("/adminShop")
public class AdminShopController {

    @Autowired
    private ShopService shopService;


    @PostMapping("/getShopList")
    @ResponseBody
    public Map<String,Object> getAllShops(@RequestBody PageVO pageVO) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(pageVO.getCurrentPage())) {
            pageVO.setStartIndex(0);
            pageVO.setPageSize(5);
        }else {
            PageUtil.convertPage(pageVO);
        }
        ShopTO allShops = shopService.getAllShops(pageVO);
        if (allShops.getState() == 1) {
            map = allShops.getMap();
            map.put("result", new Result(allShops));
            return map;
        }
        map.put("result", new Result(allShops));
        return map;
    }

    @PostMapping("/updateShop")
    public ModelAndView updateShop(Shop shop,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(shop.getShopId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        shop.setLastEditTime(new Date());
        ShopTO shopTO = shopService.adminShopInfo(shop);
        if (shopTO.getState() == 1) {
            modelAndView.setViewName("redirect:/shops");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errCode", shopTO.getState());
        modelAndView.addObject("errMsg", shopTO.getStateInfo());
        return modelAndView;
    }

    @GetMapping("/updateShopStatus")
    public ModelAndView updateShopInfo(Shop shop,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(shop.getShopId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        shop.setLastEditTime(new Date());
        ShopTO shopTO = shopService.adminShopInfo(shop);
        if (shopTO.getState() == 1) {
            modelAndView.setViewName("redirect:/shops");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errCode", shopTO.getState());
        modelAndView.addObject("errMsg", shopTO.getStateInfo());
        return modelAndView;
    }


    @GetMapping("/editShop")
    public ModelAndView skipToEditShop(Shop shop,ModelAndView modelAndView) {
        modelAndView.addObject("shop", shop);
        modelAndView.setViewName("admin/editShop");
        return modelAndView;

    }
}
