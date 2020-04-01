package com.lmj.o2o.consts;

/**
 * ClassName: Consts
 * Description:
 * date: 2020/3/18 11:01
 *
 * @author MJ
 */
public class Consts {

    //单点登录key
    public static final String REDIS_SSO_KEY="o2o_user_";

    public static final String USER_TOKEN="o2o_user_token";

    public static final String AREA_LIST_KEY="arealist";
    public static final String HEAD_LINE_LIST_KEY="headlinelist";
    public static final String SHOP_LIST_KEY="shoplist";
    public static final String SHOP_CATEGORY_ALL_LIST_KEY="shopcategoryALLlist";
    public static final String SHOP_CATEGORY_PARENT_LIST_KEY="shopcategoryPARENTlist";
    public static final String SHOP_CATEGORY_SON_LIST_KEY="shopcategorySONlist";

    public static final String AWARD_LIST_KEY="awardlist";
    public static final String AWARD_KEY="award";
    public static final String PRODUCT_LIST_KEY="productlist";
    public static final String PRODUCT_CATEGORY_LIST_KEY="productcategorylist";

    public static final String USER_PRODUCT_RECORD_LIST_KEY="userproductrecords_";


    public static final String USER_SHOP_LIST_KEY="usershoplist_";

    //web端owner查看积分兑换记录
    public static final String USER_AWARD_RECORD_LIST_OWNER_KEY="userawardrecords_";

//小程序端顾客查看积分兑换记录
    public static final String USER_AWARD_HIISTORY_LIST_KEY="userawardlist_";

    public static final String WECHAT_USER="wechatuser_";

    //防止es数据还没刷新，重复扫码领奖
    public static final String EXCHANGE_TEMP_KEY="exchange_award_";

    public static final String PRODUCT_KEY="product";
    public static final String SHOP_KEY="shop";

    //储存在redis里的用户的key前缀
    public static final String USER_PREFIX="o2o_";

    //过期时间
    public static final int EXPIRE_TIME=1800;

    public static final int PAGE_EXPIRE_TIME=180;

    public static final String ES_INDEX="o2o";

    public static final String ES_HEAD_LINE_TYPE="headLine";

    public static final String ES_SHOP_CATEGORY_TYPE="shopCategory";

    public static final String ES_AREA_TYPE="area";
    public static final String ES_AWARD_TYPE="award";
    public static final String ES_USER_PRODUCT_MAP_TYPE="userProduct";
    public static final String ES_USER_SHOP_MAP_TYPE="userShop";
    public static final String ES_USER_AWARD_MAP_TYPE="userAward";
    public static final String ES_PRODUCT_CATEGORY_TYPE="productCategory";
    public static final String ES_PRODUCT_IMAGE_TYPE="productImg";
    public static final String FDFS_SERVER="http://192.168.174.128";


    //扫码授权码的存储key
    public static final String SHOP_AUTH_CODE_PREFIX="shopauthcode_";


    public static final String WX_OPENID_REQUEST_ADDR="https://api.weixin.qq.com/sns/jscode2session";

    public static final String USER_AWARD_SERVICE_URL="http://localhost:8080/myo2oproject/userAward/exchangeAward";

    public static final String WECHAT_USER_INFO_UPDATE="http://localhost:8080/myo2oproject/personInfo/updatePersonInfo";
    public static final String WECHAT_AWARD_QRCODE_EXCHANGE="http://localhost:8080/myo2oproject/userAward/updateRecord";

    //短链接生成接口
    public static final String SHORT_LINK_INTERFACE="https://ctsign.cn/Web/open/generateShortUrl";
    //短链接生成接口的私钥
    public static final String SHORT_LINK_INTERFACE_PRIVATE_KEY="secretKey=7554nbyd864tb4a26vaaf34cba9cqe28e";


    public static final String SHOP_AUTH_REQUEST_ADDRESS="http://192.168.0.104:8080/myo2oproject/shopAuth/addShopAuth";
}
