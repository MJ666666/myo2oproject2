package com.lmj.o2o.o2o_login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: DispatchController
 * Description:
 * date: 2020/4/2 15:28
 *
 * @author MJ
 */
@RestController
public class DispatchController {



    @GetMapping("/login")
    public ModelAndView skipToLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
