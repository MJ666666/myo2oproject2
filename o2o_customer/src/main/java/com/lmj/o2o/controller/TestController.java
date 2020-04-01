package com.lmj.o2o.controller;

import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.utils.QRCodeUtils;
import org.apache.http.HttpConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: TestController
 * Description:
 * date: 2020/3/17 22:03
 *
 * @author MJ
 */
@Controller
public class TestController {

    @GetMapping("/test1")
    public void test1(String uuid,String openid,String testid,HttpServletResponse response,HttpServletRequest request) throws IOException {
        String id = request.getSession().getId();
        System.out.println("sessionId"+id);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println(s);
        }
        System.out.println("----------------");
        System.out.println("uuid:"+uuid);
        System.out.println("testid"+testid);
        System.out.println("openid:"+openid);
        response.getWriter().write("hello,ok");


    }


    @PostMapping("/getCodeData")
    @ResponseBody
    public  Map<String, Object> test3(HttpServletRequest request,@RequestBody String userInfo) throws IOException {
        System.out.println(userInfo);
        String uuid = UUID.randomUUID().toString();
        uuid=uuid.replaceAll("-","");
        System.out.println(uuid);
        request.getSession().setAttribute("uuid",uuid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uuid",uuid);
        return  map;
    }





}
