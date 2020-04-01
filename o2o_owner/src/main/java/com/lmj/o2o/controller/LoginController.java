package com.lmj.o2o.controller;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dto.LocalAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.LocalAuthService;
import com.lmj.o2o.utils.ImageUtil;
import com.lmj.o2o.vo.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ClassName: LoginController
 * Description:
 * date: 2020/3/25 9:56
 *
 * @author MJ
 */
@Controller
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private LocalAuthService localAuthService;

    @PostMapping("/login")
    public ModelAndView login(LocalAuth localAuth, HttpServletResponse response, HttpSession session,String validateCode,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(localAuth.getUserName()) || StringUtils.isEmpty(localAuth.getPassword()) || StringUtils.isEmpty(validateCode)) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        if (!session.getAttribute("validateCode").equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }

        LocalAuthTO loginTo = localAuthService.login(localAuth);
        int state = loginTo.getState();
        if (state == OperationEnum.USER_NOT_EXISTS.getState()) {
            modelAndView.setViewName("register");

            return modelAndView;
        }
        if (state == OperationEnum.WRONG_PASSWORD.getState()) {
            modelAndView.addObject("errorCode", loginTo.getState());
            modelAndView.setViewName("error");
            return modelAndView;
        }
        if (state == OperationEnum.SUCCESS.getState()) {
            Cookie userToken = new Cookie(Consts.USER_TOKEN, loginTo.getToken());
            userToken.setMaxAge(-1);
            userToken.setPath("/myo2oproject/");
            response.addCookie(userToken);
            //往session添加userid
            session.setAttribute("currentUserId",loginTo.getLocalAuth().getUserId());
            modelAndView.setViewName("redirect:/shopList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }



    @PostMapping("/register")
    public ModelAndView registerOwner(ModelAndView modelAndView, RegisterEntity registerEntity, String validateCode, HttpSession session, HttpServletResponse response, MultipartFile thumbnail) {
        System.out.println(registerEntity);
        if (StringUtils.isEmpty(registerEntity.getUserName())|| StringUtils.isEmpty(registerEntity.getPassword()) || StringUtils.isEmpty(validateCode) || StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            modelAndView.setViewName("nullparam");
           return modelAndView;
        }
        String storeCode =(String) session.getAttribute("validateCode");
        if (!validateCode.equals(storeCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
        String profileImg = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
        registerEntity.setProfileImg(profileImg);
        LocalAuthTO localAuthTO = localAuthService.registerOwnerAccount(registerEntity);
        if (localAuthTO.getState()==OperationEnum.USER_EXISTS.getState()) {
            modelAndView.addObject("errorCode", OperationEnum.USER_EXISTS.getState());
            modelAndView.addObject("errMsg", OperationEnum.USER_EXISTS.getStateInfo());
            modelAndView.setViewName("error");
            return modelAndView;
        }
        if (localAuthTO.getState()==1) {
            Cookie cookie = new Cookie(Consts.USER_TOKEN,localAuthTO.getToken());
            cookie.setPath("/myo2oproject/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            session.setAttribute("currentUserId",localAuthTO.getLocalAuth().getUserId());
            modelAndView.setViewName("redirect:/shopList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
