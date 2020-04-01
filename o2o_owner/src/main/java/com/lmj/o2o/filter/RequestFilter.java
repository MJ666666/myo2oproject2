package com.lmj.o2o.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: RequestFilter
 * Description:
 * date: 2020/3/26 17:39
 *
 * @author MJ
 */
//@Component
public class RequestFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Long currentShopId = (Long)request.getSession().getAttribute("currentShopId");
        String shopId = request.getParameter("shopId");
        if (currentShopId!=null || shopId!=null) {
            filterChain.doFilter(request,response);
        }else {
            response.sendRedirect("shopList");
            return;
        }

    }
}
