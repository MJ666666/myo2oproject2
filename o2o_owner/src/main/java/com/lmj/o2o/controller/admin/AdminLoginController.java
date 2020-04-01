package com.lmj.o2o.controller.admin;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dto.LocalAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.service.LocalAuthService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ClassName: AdminLoginController
 * Description:
 * date: 2020/3/28 13:24
 *
 * @author MJ
 */
@Controller
@RequestMapping("/adminLogin")
public class AdminLoginController {

    @Autowired
    private LocalAuthService localAuthService;

    @PostMapping("/login")
    public ModelAndView login(LocalAuth localAuth, ModelAndView modelAndView, String validateCode, HttpSession session, HttpServletResponse response) {
        if (StringUtils.isEmpty(localAuth.getUserName()) || StringUtils.isEmpty(localAuth.getPassword()) || StringUtils.isEmpty(validateCode)) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        String storeCode =(String) session.getAttribute("validateCode");
        if (!storeCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        LocalAuthTO localAuthTO = localAuthService.adminLogin(localAuth);
        if (localAuthTO.getState() == 1) {
            session.setAttribute("adminFlag","true");
            Cookie userToken = new Cookie(Consts.USER_TOKEN, localAuthTO.getToken());
            userToken.setMaxAge(-1);
            userToken.setPath("/myo2oproject/");
            response.addCookie(userToken);
            //往session添加userid
            session.setAttribute("currentUserId",localAuthTO.getLocalAuth().getUserId());
            modelAndView.setViewName("redirect:/adminIndex");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }



}
