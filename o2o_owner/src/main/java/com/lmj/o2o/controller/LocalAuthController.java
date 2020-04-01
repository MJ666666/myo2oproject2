package com.lmj.o2o.controller;

import com.lmj.o2o.dto.LocalAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.LocalAuthService;
import com.lmj.o2o.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: LocalAuthController
 * Description:
 * date: 2020/3/22 10:03
 *
 * @author MJ
 */
@Controller
@RequestMapping("/localAuth")
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    @PostMapping("/login")
    public ModelAndView login(LocalAuth localAuth, HttpSession session,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(localAuth.getUserName()) || StringUtils.isEmpty(localAuth.getPassword())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        LocalAuthTO localAuthTO = localAuthService.verifyAccount(localAuth);
        if (localAuthTO.getState() == 1) {
            session.setAttribute("loginFlag","success");
            session.setAttribute("currentUserId",localAuthTO.getLocalAuth().getUserId());
            modelAndView.setViewName("owner/shopList");
            return modelAndView;
        }
      modelAndView.setViewName("error");
        modelAndView.addObject("errorCode", localAuthTO.getState());
        modelAndView.addObject("errorMsg", localAuthTO.getStateInfo());
        return modelAndView;
    }

    @PostMapping("/registerOwner")
    public ModelAndView register(LocalAuth localAuth, HttpSession session,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(localAuth.getUserName()) || StringUtils.isEmpty(localAuth.getPassword())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        LocalAuthTO localAuthTO = localAuthService.registerAccount(localAuth);
        if (localAuthTO.getState() == 1) {
            session.setAttribute("loginFlag","success");
            session.setAttribute("currentUserId",localAuthTO.getLocalAuth().getUserId());
            modelAndView.setViewName("redirect:shopList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errorCode", localAuthTO.getState());
        modelAndView.addObject("errorMsg", localAuthTO.getStateInfo());
        return modelAndView;
    }




}
