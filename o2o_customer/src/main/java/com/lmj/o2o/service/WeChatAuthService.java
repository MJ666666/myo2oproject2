package com.lmj.o2o.service;

/**
 * ClassName: WeChatAuthService
 * Description:
 * date: 2020/3/20 9:39
 *
 * @author MJ
 */
public interface WeChatAuthService {


    String getOpenIdAndSessionKey(String resCode);
}
