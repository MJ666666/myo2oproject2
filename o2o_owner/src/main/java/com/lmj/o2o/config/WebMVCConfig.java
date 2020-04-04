package com.lmj.o2o.config;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.filter.RequestFilter;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by paul on 2017-06-14.
 * 该文件用于配置Spring的视图解析器,为了解析jsp采用ViewResolver对象
 */
 //该注解表示这是一个配置类
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.lmj.o2o.controller")
public class WebMVCConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ViewResolver viewResolver() {
        //配置jsp视图解析器
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        //这里是jsp文件的路径映射,我们把jsp文件放在WEB-INF下的views文件夹中
        resolver.setPrefix("/WEB-INF/html/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/**")
                .addResourceLocations("resources/", "static/", "public/",
                        "META-INF/resources/")
                .addResourceLocations("classpath:resources/", "classpath:static/",
                        "classpath:public/", "classpath:META-INF/resources/")
                .addResourceLocations("file:///tmp/webapps/");
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //配置静态资源处理
        configurer.enable();
    }


    @Bean
    public FilterRegistrationBean addFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RequestFilter());
        filterFilterRegistrationBean.addUrlPatterns("/shopIndex");
        return filterFilterRegistrationBean;
    }
//
//    @Autowired
//    private RedisService redisService;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//
//        //登录拦截
//        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
//
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                Cookie[] cookies = request.getCookies();
//                if (cookies!=null && cookies.length!=0) {
//
//                    String userToken="";
//                    for (Cookie cookie : cookies) {
//                        if (Consts.USER_TOKEN.equals(cookie.getName())) {
//                            userToken = cookie.getValue();
//                            break;
//                        }
//                    }
//                    if (!redisService.existKey(userToken)) {
//                        response.sendRedirect("login");
//                        return false;
//                    }
//                    String localAuthJson = redisService.get(userToken).replaceAll("aaaa","/");
//                    LocalAuth localAuth = GsonUtils.GsonToBean(localAuthJson, LocalAuth.class);
//                    request.getSession().setAttribute("currentUser",localAuth);
//                    request.getSession().setAttribute("currentUserId",localAuth.getUserId());
//
//                    return true;
//
//
//                }
//
//
//                response.sendRedirect("login");
//                return false;
//
//            }
//
//            @Override
//            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//
//            }
//
//            @Override
//            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//            }
//        };
//        registry.addInterceptor(handlerInterceptor).excludePathPatterns("/login").excludePathPatterns("/validateCode").excludePathPatterns("/user/login")
//                .excludePathPatterns("/register").excludePathPatterns("/user/register")
//                .excludePathPatterns("/error").excludePathPatterns("/404").excludePathPatterns("/validateNotMatch")
//                .excludePathPatterns("/nullparam").excludePathPatterns("/wechat/*").excludePathPatterns("/userAward/exchangeAward")
//                .excludePathPatterns("/personInfo/updatePersonInfo").excludePathPatterns("/userAward/updateRecord").excludePathPatterns("/userAward/getAwardCode")
//                .excludePathPatterns("/adminPage").excludePathPatterns("/adminLogin/login");
//
//
//        HandlerInterceptor adminInteceptor=new HandlerInterceptor(){
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//                String adminFlag = (String)request.getSession().getAttribute("adminFlag");
//                if (adminFlag != null && "true".equals(adminFlag)) {
//                    return true;
//                }
//
//                return false;
//            }
//        };
//        registry.addInterceptor(adminInteceptor).addPathPatterns("/adminIndex").addPathPatterns("/admin/*").addPathPatterns("/adminHeadLine")
//                .addPathPatterns("/adminUser").addPathPatterns("/adminCates").addPathPatterns("/adminShop");
//
//    }
}
