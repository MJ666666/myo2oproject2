package com.lmj.o2o.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: HttpSeesionAttributeUtil
 * Description:
 * date: 2020/3/13 10:47
 *
 * @author MJ
 */
public class HttpSessionAttributeUtil {

    public static Object getAttribute(HttpServletRequest request, String attributeName) {
        Object attr = request.getSession().getAttribute(attributeName);
        return attr;
    }
}
