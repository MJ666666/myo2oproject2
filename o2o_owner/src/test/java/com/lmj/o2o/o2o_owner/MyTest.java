package com.lmj.o2o.o2o_owner;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.*;
import com.lmj.o2o.entity.*;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.HttpRequestUtils;
import com.lmj.o2o.utils.MD5Utils;
import com.lmj.o2o.utils.UuidUtils;
import com.lmj.o2o.vo.PageVO;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLEncoder;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = O2oOwnerApplication.class)
public class MyTest {
    @Autowired
    private AwardDao awardDao;

    @Test
    public void testAwardSearch() {
        Award award = new Award();
        award.setShopId(20L);
        award.setAwardDesc("冲锋战神");
        award.setAwardImg("/mj888/666.jpg");
        award.setAwardName("我们走，冲锋战神");
        award.setCreateTime(new Date());
        award.setLastEditTime(new Date());
        award.setEnableStatus(1);
        award.setPriority(80);
        award.setPoint(66);
        award.setShopId(20L);
        awardDao.addAward(award);
        System.out.println(award.getAwardId());


    }

    @Test
    public void test2() {
        Award award = new Award();
        award.setAwardId(6L);
        Award awardByAwardId = awardDao.getAwardByAwardId(award);
        System.out.println(awardByAwardId);

    }

    @Autowired
    private UserProductDao userProductDao;

    @Test
    public void test3() {

    }

    @Autowired
    private UserAwardDao userAwardDao;

    @Test
    public void test92() {
        UserAwardMap userAwardMap = new UserAwardMap();
        userAwardMap.setShopId(20L);
        userAwardMap.setAwardId(3L);
        userAwardMap.setUsedStatus(0);
        userAwardMap.setUserId(8L);
        userAwardMap.setUserName("李翔");
        userAwardMap.setAwardName("怀旧智能机");
        userAwardMap.setCreateTime(new Date());
        userAwardMap.setExpireTime(new Date());
        userAwardMap.setPoint(66);
        userAwardDao.addNewUserAwardMap(userAwardMap);
        System.out.println(userAwardMap.getUserAwardId());
    }

    @Test
    public void test91() {
        UserAwardMap userAwardMap = new UserAwardMap();

        userAwardMap.setUsedStatus(1);
        userAwardMap.setUserAwardId(6L);

        int i = userAwardDao.updateUserAwardMap(userAwardMap);
        System.out.println(i);

    }

    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void test90() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("xiangze1");
        localAuth.setPassword("25q2y22l06q99569529l52q26qqs2bq6");
    }

    @Test
    public void test89() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("testAccount2");
        localAuth.setPassword(MD5Utils.md5("mj666"));
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");
        localAuth.setUuid(s);
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int i = localAuthDao.addNewAccount(localAuth);
        System.out.println(i);
    }

    @Test
    public void test88() {
        LocalAuth localAuth = new LocalAuth();
        //  localAuth.setPassword(MD5Utils.md5("MJ666"));
        localAuth.setUserId(8L);
        localAuth.setLastEditTime(new Date());
        localAuth.setLocalAuthId(9L);
        int i = localAuthDao.updateAccount(localAuth);
        System.out.println(i);
    }

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void test87() {
        LocalAuth localAuth = new LocalAuth();
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");
        localAuth.setUuid(s);
        int i = personInfoDao.addUserViaWeb(localAuth);
        System.out.println(i);
        System.out.println(localAuth);
    }

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void test86() {
        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setUuid(UuidUtils.getUUID());
        wechatAuth.setCreateTime(new Date());
        wechatAuth.setOpenId("1234567sda");
        wechatAuth.setUserId(8L);
        int i = wechatAuthDao.addNewWechatUser(wechatAuth);
        System.out.println(i);
    }

    @Test
    public void test85() {
        PageVO pageVO = new PageVO();
        pageVO.setUserId(14L);
        pageVO.setCurrentPage(3);
        pageVO.setPageSize(5);
        pageVO.setTotals(15);
        List<UserAwardMap> userAwardMaps = userAwardDao.queryAllUserAward(pageVO);
        System.out.println(userAwardMaps.size());
        for (UserAwardMap awardMap : userAwardMaps) {

            System.out.println(awardMap);
        }
    }

    @Test
    public void test84() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("test1");
        int i = localAuthDao.queryExistAccount(localAuth);
        System.out.println(i);
    }

    @Autowired
    private ProductDao productDao;


    @Test
    public void test82() {
        Shop shop = new Shop();
        shop.setShopId(20L);
        List<Product> products = productDao.queryProductCateByShopId(shop);
        int size = products.size();

        List<List<UserProductCount>> countPerWeek = new ArrayList<>();

        for (int j = 0; j < 7; j++) {
            List<UserProductCount> countPerDay = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UserProductCount userProductCount = new UserProductCount();
                userProductCount.setBeforeDay(j + 1);
                userProductCount.setProductId(products.get(i).getProductId());
                userProductCount.setProductName(products.get(i).getProductName());
                int countConsumeRecords = userProductDao.countConsumeRecords(userProductCount);
                userProductCount.setCount(countConsumeRecords);
                countPerDay.add(userProductCount);
            }
            countPerWeek.add(countPerDay);

        }

        for (List<UserProductCount> userProductCounts : countPerWeek) {
            System.out.println(userProductCounts);
        }

    }


    @Test
    public void test81() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        pageVO.setStartIndex(0);
        pageVO.setPageSize(10);
        List<UserProductMap> userProductMaps = userProductDao.geConsumeProductList(pageVO);
        for (UserProductMap userProductMap : userProductMaps) {
            System.out.println(userProductMap);
        }
    }

    @Test
    public void test80() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        int userProductMaps = userProductDao.countRecords(pageVO);
        System.out.println(userProductMaps);
    }

    @Test
    public void test79() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        pageVO.setStartIndex(0);
        pageVO.setPageSize(10);
        List<UserAwardMap> userAwardMaps = userAwardDao.queryAllUserAwardByShopId(pageVO);
        for (UserAwardMap userAwardMap : userAwardMaps) {
            System.out.println(userAwardMap);
        }
    }

    @Test
    public void test78() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        int userProductMaps = userAwardDao.countTotalRecords(pageVO);
        System.out.println(userProductMaps);
    }

    @Autowired
    private UserShopDao userShopDao;

    @Test
    public void test77() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        int i = userShopDao.countAllCustomers(pageVO);
        System.out.println(i);
    }

    @Test
    public void test76() {
        PageVO pageVO = new PageVO();
        pageVO.setShopId(20L);
        pageVO.setStartIndex(0);
        pageVO.setPageSize(5);
        List<UserShopMap> userShopMaps = userShopDao.queryAllCustomerByShopID(pageVO);
        for (UserShopMap userShopMap : userShopMaps) {
            System.out.println(userShopMap);
        }
    }

    @Test
    public void test75() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("mj666666");
        localAuth.setPassword(MD5Utils.md5("12345678"));
        LocalAuth localAuth1 = localAuthDao.verifyAccount(localAuth);
        System.out.println(localAuth1);
    }


    @Test
    public void test74() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setLastEditTime(new Date());
        personInfo.setCreateTime(new Date());
        personInfo.setName("mjtest2");
        personInfo.setAdminFlag(0);
        personInfo.setShopOwnerFlag(1);
        personInfo.setUserUuid(UuidUtils.getUUID());
        personInfo.setBirthday(new Date());
        personInfo.setProfileImg("/mypics/mj666.jpg");
        personInfo.setCustomerFlag(0);
        personInfo.setEmail("12345756@qq.com");
        personInfo.setEnableStatus(1);
        personInfo.setGender("1");
        personInfo.setPhone("18876787654");
        int i = personInfoDao.addPersonInfo(personInfo);
        System.out.println(i);
    }

    @Autowired
    private ShopAuthManageDao shopAuthManageDao;

    @Test
    public void test73() {
        Shop shop = new Shop();
        shop.setShopId(20L);
        List<ShopAuthMap> shopAuthMaps = shopAuthManageDao.queryAllShopAuth(shop);
        for (ShopAuthMap shopAuthMap : shopAuthMaps) {
            System.out.println(shopAuthMap);
        }
    }

    @Test
    public void test72() {
        ShopAuthMap shopAuthMap1 = new ShopAuthMap();
        shopAuthMap1.setEmployeeId(9L);
        shopAuthMap1.setShopId(20L);
        int i = shopAuthManageDao.deleteShopAuth(shopAuthMap1);
        System.out.println(i);
    }

    @Test
    public void test71() {
        ShopAuthMap shopAuthMap1 = new ShopAuthMap();
        shopAuthMap1.setEmployeeId(9L);
        shopAuthMap1.setShopId(20L);
        shopAuthMap1.setCreateTime(new Date());
        shopAuthMap1.setLastEditTime(new Date());
        shopAuthMap1.setName("王小五");
        shopAuthMap1.setTitle("店小五");
        shopAuthMap1.setEnableStatus(1);
        shopAuthMap1.setTitleFlag(1);
        int i = shopAuthManageDao.addShopAuth(shopAuthMap1);
        System.out.println(i);
    }

    @Test
    public void test70() {
        String url = "http://192.168.0.104:8080/shopAuth/addShopAuth?shopId=20";
        String encode = URLEncoder.encode(url);
        String param = Consts.SHORT_LINK_INTERFACE_PRIVATE_KEY + "&url=" + url;

        String s = HttpRequestUtils.sendPostRequest("https://ctsign.cn/Web/open/generateShortUrl", param);
        System.out.println(s);
    }

    @Test
    public void test69() {
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        shopAuthMap.setShopId(20L);
        shopAuthMap.setEmployeeId(14L);
        shopAuthMap.setTitle("店员");
        shopAuthMap.setLastEditTime(new Date());
        int i = shopAuthManageDao.updateShopAuth(shopAuthMap);
        System.out.println(i);
    }
    @Test
    public void test68() {
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        shopAuthMap.setShopId(19L);
        shopAuthMap.setEmployeeId(14L);
        ShopAuthMap shopAuth = shopAuthManageDao.queryExistAuth(shopAuthMap);
        System.out.println(shopAuth);
    }

    @Autowired
    private AreaDao areaDao;

    @Test
    public void test67() {
        Area area = new Area();
        area.setAreaId(7L);
        int i = areaDao.deleteArea(area);
        System.out.println(i);
    }

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void test66() {
        HeadLine headLine = new HeadLine();
        headLine.setLineId(16L);
        int i = headLineDao.deleteHeadLine(headLine);
        System.out.println(i);
    }

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void test65() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryName("精神食粮3");

        shopCategory.setShopCategoryId(34L);
        int i = shopCategoryDao.deleteShopCategory(shopCategory);
        System.out.println(i);

    }


    @Autowired
    private ShopDao shopDao;
    @Test
    public void test64() {
        Shop shop = new Shop();
        shop.setShopId(45L);
        shop.setEnableStatus(1);
        shop.setOwnerId(14L);
        shop.setAdvice("要好好经营哦");
        shop.setLongitude(100.2);
        shop.setLatitude(120.9);
        shop.setLastEditTime(new Date());
        shop.setPriority(20);
        shop.setParentShopCategoryId(32L);
        shop.setAreaId(3L);
        int i = shopDao.updateShopInfo(shop);
        System.out.println(i);

    }

    @Test
    public void test62() {

    }
    @Test
    public void test61() {
        List<ShopCategory> shopCategories = shopCategoryDao.queryAllShopCategory();
        for (ShopCategory shopCategory : shopCategories) {
            System.out.println(shopCategory);
        }

    }

    @Test
    public void test60() {
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(10);
        pageVO.setStartIndex(0);
        List<Shop> shops = shopDao.queryAllShops(pageVO);
        for (Shop shop : shops) {
            System.out.println(shop);
        }


    }

    @Test
    public void test59() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("mj666666");
        localAuth.setPassword("25d55ad283aa400af464c76d713c07ad");
        LocalAuth localAuth1 = localAuthDao.adminLogin(localAuth);
        System.out.println(localAuth1);

    }
    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @Test
    public void test58() {
        Shop shop = new Shop();
        shop.setShopId(10L);
        shop.setShopName("mysqhop");
        String s = redisService.storeValue(Consts.SHOP_KEY + "_" + 12345, GsonUtils.toGsonString(shop));
        System.out.println(s);
    }
}