package com.lmj.o2o.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.QRCodeUtils;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
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
