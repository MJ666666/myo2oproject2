package com.lmj.o2o.controller;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.WeChatAuthService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ClassName: WXAuthController
 * Description:
 * date: 2020/3/20 9:47
 *
 * @author MJ
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WXAuthController {


    @Autowired
    private WeChatAuthService weChatAuthService;

    @GetMapping("/getOpenId")
    public void getWeChatOpenId(String code, HttpServletResponse response) {
        if (StringUtils.isEmpty(code)) {
            try {
                response.getOutputStream().write(GsonUtils.toGsonString(new Result(OperationEnum.NULL_PARAM)).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                log.debug("[wechat]"+e.getMessage());
            }
        }

        String openIdAndSessionKey = weChatAuthService.getOpenIdAndSessionKey(code);
        if (!"".equals(openIdAndSessionKey)) {
            try {
                response.getOutputStream().write(openIdAndSessionKey.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                log.debug("[wechat]"+e.getMessage());
            }
        }

    }

    @PostMapping("/updatePersonInfo")
    public void updatePersonInfo(@RequestBody PersonInfo personInfo, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(personInfo.getUserId())) {
            response.getOutputStream().write("userId is null".getBytes());
        }
        System.out.println(personInfo);
        StringBuilder sb = new StringBuilder();
        sb.append("gender="+personInfo.getGender()).append("&profileImg="+personInfo.getProfileImg()).append("&userId="+personInfo.getUserId());
        String responseResult = HttpRequestUtils.sendPostRequest(Consts.WECHAT_USER_INFO_UPDATE, sb.toString());

        response.getOutputStream().write(responseResult.getBytes());

    }

}
