package com.lmj.o2o.o2o_login.controller;

import cn.dsna.util.images.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: VerifyController
 * Description:
 * date: 2020/3/7 21:18
 *
 * @author MJ
 */
@Controller
public class VerifyController {


    /**
     * Description: 请求验证码后将验证码放到session上
     * @author: MJ
     * @date: 2020/3/7 22:03
     * @param:
     * @return:
     */
    @RequestMapping("/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCode validateCode = new ValidateCode(150, 30, 5, 20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("validateCode",code);

        validateCode.write(response.getOutputStream());
    }
}
