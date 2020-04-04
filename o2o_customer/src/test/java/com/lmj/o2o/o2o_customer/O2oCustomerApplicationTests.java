package com.lmj.o2o.o2o_customer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AreaDao;
import com.lmj.o2o.dao.UserAwardDao;
import com.lmj.o2o.dao.UserDao;
import com.lmj.o2o.dto.*;
import com.lmj.o2o.entity.*;
import com.lmj.o2o.service.*;
import com.lmj.o2o.utils.HttpRequestUtils;
import com.lmj.o2o.vo.PageVO;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class O2oCustomerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void test1() {
        ShopCategoryTO shopCategoryParentList = shopCategoryService.getShopCategoryParentList();
        System.out.println(shopCategoryParentList);
    }

    @Test
    public void test4() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setParentId(10L);
        ShopCategoryTO shopCategoryParentList = shopCategoryService.getShopCategoryListByParentId(shopCategory);
        System.out.println(shopCategoryParentList.getDataMap());
    }

    @Autowired
    private HeadLineService headLineService;

    @Test
    public void test97() {
        HeadLineTO headLinesList = headLineService.getHeadLinesList();
        System.out.println(headLinesList);
    }

    @Test
    public void test2() throws WriterException, IOException {
        Map<EncodeHintType, Object> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix matrix = new QRCodeWriter().encode("http://www.baidu.com", BarcodeFormat.QR_CODE, 200, 200, map);
        MatrixToImageWriter.writeToStream(matrix, "png", new FileOutputStream("D:\\acgpics\\test.png"),
                new MatrixToImageConfig(Color.RED.getRGB(), Color.WHITE.getRGB()));
    }

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaDao areaDao;

    @Test
    public void test99() {
        List<Map<String, Object>> areaList = areaDao.getAreaList();
        System.out.println(areaList);
    }

    @Test
    public void test3() {
        AreaTO areas = areaService.getAreas();
        List<Map<String, Object>> dataList = areas.getDataList();
        System.out.println(dataList);
    }

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void test7() {
        Shop shop = new Shop();
        shop.setShopId(15L);
        ProductCategoryTO productCatesListByShopId =
                productCategoryService.getProductCatesListByShopId(shop);
        System.out.println(productCatesListByShopId.getDataList());
    }

    @Autowired
    private AwardService awardService;

    @Test
    public void test8() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        AwardTO awardListByShopId = awardService.getAwardListByShopId(pageVO);
        System.out.println(awardListByShopId.getDataList());
    }

    @Autowired
    private UserShopService userShopService;

    @Test
    public void test96() {
        UserShopMap userShopMap = new UserShopMap();
        userShopMap.setShopId(20L);
        userShopMap.setUserId(8L);
        UserShopTO userShopInfo = userShopService.getUserShopInfo(userShopMap);
        System.out.println(userShopInfo);
    }

    @Autowired
    private UserProductService userProductService;

    @Test
    public void test95() {
        UserProductMap userProductMap = new UserProductMap();
        userProductMap.setUserId(8L);
        userProductMap.setShopId(20L);
        UserProductTO records = userProductService.getRecords(userProductMap);
        System.out.println(records.getDataList());
    }

    @Autowired
    private UserAwardService userAwardService;

    @Test
    public void test94() {

    }


    @Test
    public void test93() throws IOException {
        UserAwardMap userAwardMap = new UserAwardMap();
        userAwardMap.setShopId(20L);
        userAwardMap.setAwardId(2L);
        userAwardMap.setUsedStatus(0);
        userAwardMap.setUserId(8L);
        userAwardMap.setUserName("李翔");
        userAwardMap.setAwardName("不求人2.0");
       // userAwardMap.setCreateTime(new Date());
      //  userAwardMap.setExpireTime(new Date());
        userAwardMap.setPoint(33);
        String msg = "";
        StringBuilder sb = new StringBuilder();
        sb.append("http://localhost:8080/myo2oproject/userAward/exchangeAward");
        sb.append("?shopId=" + userAwardMap.getShopId().toString());
        sb.append("&awardId=" + userAwardMap.getAwardId().toString());
        sb.append("&usedStatus=" + userAwardMap.getUsedStatus());
        sb.append("&userId=" + userAwardMap.getUserId().toString());
        sb.append("&userName=" + userAwardMap.getUserName());
        sb.append("&awardName=" + userAwardMap.getAwardName());
        sb.append("&point=" + userAwardMap.getPoint());

        try {
            // 1. 得到访问地址的URL
            URL url = new URL(sb.toString());
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

        }
    }


    @Test
    public void test91() {
        UserAwardMap userAwardMap = new UserAwardMap();
        userAwardMap.setShopId(20L);
        userAwardMap.setAwardId(2L);
        userAwardMap.setUsedStatus(0);
        userAwardMap.setUserId(8L);
        userAwardMap.setUserName("李翔");
        userAwardMap.setAwardName("不求人2.0");
        // userAwardMap.setCreateTime(new Date());
        //  userAwardMap.setExpireTime(new Date());
        userAwardMap.setPoint(33);


    }

    @Test
    public void test73() throws Exception {
        String encode = URLEncoder.encode("http://www.baidu.com", "UTF-8");

        String url="https://ctsign.cn/Web/open/generateShortUrl";
        String pk="secretKey=7554nbyd864tb4a26vaaf34cba9cqe28e&url="+encode;
        String s = HttpRequestUtils.sendPostRequest(url, pk);
        System.out.println(s);

    }

    @Autowired
    private UserDao userDao;

    @Test
    public void test72() {

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("mj666666");
        PersonInfo personInfo1 = userDao.selectUserByUserName(personInfo);
        System.out.println(personInfo1);
    }

}
