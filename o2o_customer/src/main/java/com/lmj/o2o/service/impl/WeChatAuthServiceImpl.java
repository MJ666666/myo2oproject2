package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.service.WeChatAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ClassName: WeChatAuthServiceImpl
 * Description:
 * date: 2020/3/20 9:39
 *
 * @author MJ
 */
@Service
@Slf4j
public class WeChatAuthServiceImpl implements WeChatAuthService {

    @Value("${appID}")
    private String appID;
    @Value("${secret}")
    private String secret;


    @Override
    public String getOpenIdAndSessionKey(String resCode) {
        String msg = "";
        try {
            // 1. 得到访问地址的URL
            URL url = new URL(
                    Consts.WX_OPENID_REQUEST_ADDR+"?appid="+appID+"&secret="+secret+"&js_code="+resCode+"&grant_type=authorization_code");
            // 2. 得到网络访问对象java.net.HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("GET");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);
            // 连接
            connection.connect();
            // 4. 得到响应状态码的返回值 responseCode
            int code2 = connection.getResponseCode();
            // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据

            if (code2 == 200) { // 正常响应
                // 从流中读取响应信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = null;

                while ((line = reader.readLine()) != null) { // 循环从流中读取
                    msg += line + "\n";
                }
                reader.close(); // 关闭流
            }
            // 6. 断开连接，释放资源
            connection.disconnect();

            // 显示响应结果
            System.out.println(msg);

        } catch (IOException e) {
            e.printStackTrace();
            log.debug(e.getMessage());
        }
        return msg;
    }


}
