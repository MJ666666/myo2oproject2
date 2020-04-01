package com.lmj.o2o.controller;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dto.ShopAuthManageTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.ShopAuthManageService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ShopAuthManageController
 * Description:
 * date: 2020/3/26 13:18
 *
 * @author MJ
 */

@Controller
@RequestMapping("/shopAuth")
public class ShopAuthManageController {

    @Autowired
    private ShopAuthManageService shopAuthManageService;


    @Autowired
    private RedisService redisService;

    @GetMapping("/getShopAuthList")
    @ResponseBody
    public Map<String,Object> getShopAuthList(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Long currentShopId = (Long)session.getAttribute("currentShopId");
        if (currentShopId==null) {
            map.put("result", new Result(OperationEnum.NULL_RESULT));
            return map;
        }
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        ShopAuthManageTO shopAuthManageTO = shopAuthManageService.getShopAuthListByShopId(shop);
        if (shopAuthManageTO.getState() == 1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            map.put("authList", shopAuthManageTO.getList());
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }


    @GetMapping("/deleteShopAuth")
    public ModelAndView deleteShopAuth(ShopAuthMap shopAuthMap, ModelAndView modelAndView,HttpSession session) {
        if (StringUtils.isEmpty(shopAuthMap.getEmployeeId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        Long currentShopId = (Long)session.getAttribute("currentShopId");
        if (currentShopId == null) {
            modelAndView.setViewName("error");
            return modelAndView;
        }
        shopAuthMap.setShopId(currentShopId);
        ShopAuthManageTO shopAuthManageTO = shopAuthManageService.deleteShopAuth(shopAuthMap);
        if (shopAuthManageTO.getState() == 1) {
            modelAndView.setViewName("redirect:/shopAuthManage");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @GetMapping("/addShopAuth")
    public ModelAndView addShopAuth(ShopAuthMap shopAuthMap, ModelAndView modelAndView,HttpSession session) {
        if (StringUtils.isEmpty(shopAuthMap.getShopId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        String codeKey= Consts.SHOP_AUTH_CODE_PREFIX+shopAuthMap.getShopId().toString();
        if (!redisService.existKey(codeKey)) {
            modelAndView.addObject("errorCode", OperationEnum.QRCODE_EXPIRE.getState());
            modelAndView.addObject("errMsg", OperationEnum.QRCODE_EXPIRE.getStateInfo());
            modelAndView.setViewName("error");
            return modelAndView;
        }
        LocalAuth currentUser =(LocalAuth) session.getAttribute("currentUser");
        shopAuthMap.setName(currentUser.getUserName());
        shopAuthMap.setEmployeeId(currentUser.getUserId());
        ShopAuthManageTO shopAuthManageTO = shopAuthManageService.addShopAuth(shopAuthMap);
        if (shopAuthManageTO.getState()==1) {
            modelAndView.setViewName("redirect:/shopList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @PostMapping("/updateShopAuth")
    public ModelAndView updateShopAuth(ShopAuthMap shopAuthMap, ModelAndView modelAndView,String validateCode,HttpSession session) {
        if (StringUtils.isEmpty(shopAuthMap.getEmployeeId()) || StringUtils.isEmpty(validateCode)) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        String storeCode = (String)session.getAttribute("validateCode");
        if (!storeCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        Long currentShopId =(Long) session.getAttribute("currentShopId");
        shopAuthMap.setShopId(currentShopId);
        shopAuthMap.setLastEditTime(new Date());
        ShopAuthManageTO shopAuthManageTO = shopAuthManageService.updateShopAuth(shopAuthMap);
        if (shopAuthManageTO.getState()==1) {
            modelAndView.setViewName("redirect:/shopAuthManage");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @GetMapping("/getShopAuth")
    public ModelAndView getShopAuthInfo(ShopAuthMap shopAuthMap,ModelAndView modelAndView,HttpSession session) {
        if (StringUtils.isEmpty(shopAuthMap.getEmployeeId()) ) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        Long currentShopId =(Long) session.getAttribute("currentShopId");
        shopAuthMap.setShopId(currentShopId);
        ShopAuthManageTO shopAuthManageTO = shopAuthManageService.queryShopAuth(shopAuthMap);
        if (shopAuthManageTO.getState()==1) {
            modelAndView.addObject("shopAuth", shopAuthManageTO.getShopAuthMap());
            modelAndView.setViewName("owner/editShopAuth");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;

    }
}
