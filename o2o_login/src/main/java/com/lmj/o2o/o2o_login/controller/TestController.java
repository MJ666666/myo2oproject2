package com.lmj.o2o.o2o_login.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: TestController
 * Description:
 * date: 2020/4/2 14:56
 *
 * @author MJ
 */
@RestController
public class TestController {


    @RequestMapping(value = "/echo/{str}",method = RequestMethod.GET)
    public void test1(@PathVariable("str") String str, HttpServletResponse response) throws IOException {
            response.getOutputStream().write(("welcome to get"+str).getBytes());
    }
}
