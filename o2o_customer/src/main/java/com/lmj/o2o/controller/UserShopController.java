package com.lmj.o2o.controller;

import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.UserShopMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserShopController
 * Description:
 * date: 2020/3/20 14:04
 *
 * @author MJ
 */
@Controller
@RequestMapping("/userShop")
public class UserShopController {

    @Autowired
    private UserShopService userShopService;

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/20 14:11
     * @param: shopId
     * @return:
     */
    @PostMapping("/getUserInfo")
    @ResponseBody
    public Map<String,Object> getUserInfo(@RequestBody UserShopMap userShopMap, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userShopMap.getShopId())) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
            return map;
        }
//        Long currentUser = (Long)request.getSession().getAttribute("currentUserId");
//        userShopMap.setUserId(currentUser);
        UserShopTO userShopTO = userShopService.getUserShopInfo(userShopMap);
        if (userShopTO.getState() == 1) {
            map.put("userInfo", userShopTO.getDataList());
            map.put("result",new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(OperationEnum.NULL_RESULT));
        return map;
    }

    @PostMapping("/getAllUserInfo")
    @ResponseBody
    public Map<String,Object> getAllUserInfo(@RequestBody UserShopMap userShopMap) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userShopMap.getUserId())) {
            map.put("result",new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        UserShopTO userShopTO = userShopService.getUserShopInfo(userShopMap);
        if (userShopTO.getState() == 1) {
            map.put("pointRecords", userShopTO.getDataList());
            map.put("result",new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(OperationEnum.NULL_RESULT));
        return map;
    }
}
