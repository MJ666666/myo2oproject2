package com.lmj.o2o.controller;

import com.google.zxing.WriterException;
import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ClassName: QRCodeController
 * Description:
 * date: 2020/3/26 11:11
 *
 * @author MJ
 */
@Slf4j
@Controller
public class QRCodeController {

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @GetMapping("/qrcode")
    public void getCode(HttpServletResponse response, HttpSession session) throws WriterException, IOException {
        Long currentShopId =(Long) session.getAttribute("currentShopId");
        String url="";
        try {
             url = Consts.SHOP_AUTH_REQUEST_ADDRESS + "?shopId=" + currentShopId.toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            log.debug("currentShopId为空,生成二维码失败",e);
        }
        String codeStoreKey=Consts.SHOP_AUTH_CODE_PREFIX+currentShopId.toString();
        redisService.storeValue(codeStoreKey,"unused");
        redisService.expireKey(codeStoreKey,Consts.PAGE_EXPIRE_TIME);
        QRCodeUtils.getQRcode(response.getOutputStream(), url);
    }
}
