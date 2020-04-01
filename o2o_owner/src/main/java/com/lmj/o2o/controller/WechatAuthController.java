package com.lmj.o2o.controller;

import com.lmj.o2o.dto.WechatAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.LocalAuthService;
import com.lmj.o2o.service.WechatAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: WechatAuthController
 * Description:
 * date: 2020/3/22 13:47
 *
 * @author MJ
 */
@Controller
@RequestMapping("/wechat")
public class WechatAuthController {

    @Autowired
    private WechatAuthService wechatAuthService;

    @PostMapping("/registerWechatUser")
    @ResponseBody
    public Map<String,Object> register(@RequestBody WechatAuth wechatAuth) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(wechatAuth.getOpenId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        WechatAuthTO wechatAuthTO = wechatAuthService.addNewWechatUser(wechatAuth);
        if (wechatAuthTO.getState()==1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            map.put("currentUser",wechatAuthTO.getWechatAuth());
            return map;
        }
        if (wechatAuthTO.getState()==1000) {
            map.put("result", new Result(OperationEnum.USER_EXISTS));
            map.put("currentUser",wechatAuthTO.getWechatAuth());
            return map;
        }
        map.put("result", new Result(wechatAuthTO.getState(), wechatAuthTO.getStateInfo()));

        return map;
    }



    @PostMapping("/bindAccount")
    @ResponseBody
    public Map<String,Object> bindAccount(@RequestBody LocalAuth localAuth) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(localAuth.getUserName()) || StringUtils.isEmpty(localAuth.getPassword()) || StringUtils.isEmpty(localAuth.getUserId()) || StringUtils.isEmpty(localAuth.getUuid())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        WechatAuthTO wechatAuthTO = wechatAuthService.bindAccount(localAuth);
        if (wechatAuthTO.getState()==1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(wechatAuthTO));
        return map;

    }

    @PostMapping("/modifyPsw")
    @ResponseBody
    public Map<String,Object> modifyPsw(@RequestBody LocalAuth localAuth) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(localAuth.getPassword()) || StringUtils.isEmpty(localAuth.getUuid()) || StringUtils.isEmpty(localAuth.getOldPassword())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        WechatAuthTO wechatAuthTO = wechatAuthService.modifyPsw(localAuth);
        if (wechatAuthTO.getState()==1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", OperationEnum.INNER_ERROR);
        return map;

    }

}
