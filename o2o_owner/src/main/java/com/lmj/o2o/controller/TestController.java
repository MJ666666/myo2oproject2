package com.lmj.o2o.controller;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.ImageUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;

/**
 * ClassName: TestController
 * Description:
 * date: 2020/3/7 14:58
 *
 * @author MJ
 */
@Controller
public class TestController {

    @GetMapping("/test2222")
    public void test1(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id = request.getSession().getId();
        Cookie cookie = new Cookie("mjcode", "myspecCode");
        response.addCookie(cookie);
        System.out.println(id);
        response.getWriter().write("helloworld");
    }

    @GetMapping("/test11")
    public void test1(String uuid,String openid,String testid,HttpServletResponse response,HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("cookieName:"+cookies[i].getName());
                System.out.println("cookieValue:"+cookies[i].getName());
            }
        }
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



  @GetMapping("/test111")
  public void test(String productId) {
      System.out.println(productId);

  }

    @GetMapping("/test222")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("aaa", "123");
        modelAndView.setViewName("test");
        return modelAndView;
    }


    @GetMapping("/test1")
    public String test2() {
        return "owner/register";
    }

    @PostMapping("/test2")
    public void test3(MultipartFile file) throws IOException {
        BufferedImage image = Thumbnails.of(file.getInputStream()).size(200, 200).outputQuality(0.25f).asBufferedImage();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", out);
        ImageUtil.uploadImage(file.getName(),file.getOriginalFilename(),out.toByteArray());
         out.close();

    }

    @Autowired
    private RedisService redisService;

    @GetMapping("/test/{key}/{value}")
    public void setKey(@PathVariable("key") String key,@PathVariable("value") String value,HttpServletResponse response ) throws IOException {

        Shop shop = new Shop();
        shop.setShopId(2222L);
        shop.setShopName(key+"你好");
        shop.setShopDesc(value);
        String s = redisService.storeValue(Consts.SHOP_KEY + "_" + 10086, GsonUtils.toGsonString(shop));
        response.getOutputStream().write(s.getBytes());

    }



}
