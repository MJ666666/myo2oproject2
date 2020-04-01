package com.lmj.o2o.controller;

import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: DispatchController
 * Description:
 * date: 2020/3/17 17:56
 *
 * @author MJ
 */
@Controller
public class DispatchController {

    @RequestMapping("/showShopInfo")
    public ModelAndView skipToShowShopInfo(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/showShopInfo");
        return modelAndView;

    }
    @RequestMapping("/productCategoryList")
    public ModelAndView skipToProductCategoryList(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/productCategoryList");
        return modelAndView;

    }
    @RequestMapping("/productList")
    public ModelAndView skipToProductList(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/productList");
        return modelAndView;

    }


    @RequestMapping("/productPage")
    public ModelAndView skipToProductInfo(String productId, HttpServletRequest request,ModelAndView modelAndView) {
        System.out.println("productId:"+productId);
        Long pId=Long.parseLong(productId);
        request.getSession().setAttribute("currentProductId",pId);
        modelAndView.setViewName("owner/showProductInfo");
        return modelAndView;

    }
    @RequestMapping("/welcome")
    public ModelAndView skipToWelcome(ModelAndView modelAndView) {
        modelAndView.setViewName("welcome");
        return modelAndView;

    }

    @RequestMapping("/login")
    public ModelAndView skipToLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;

    }
    @RequestMapping("/register")
    public ModelAndView skipToRegister(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;

    }
    @RequestMapping("/awards")
    public ModelAndView skipToAwards(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/awards");
        return modelAndView;

    }
    @RequestMapping("/awardInfo")
    public ModelAndView skipToAwardInfo(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/awardInfo");
        return modelAndView;

    }

    @RequestMapping("/addAward")
    public ModelAndView skipToAddAward(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/addAward");
        return modelAndView;

    }

    @RequestMapping("/statistic")
    public ModelAndView skipToStatistic(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/statistic");
        return modelAndView;

    }

    @RequestMapping("/records")
    public ModelAndView skipToRecords(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/records");
        return modelAndView;

    }

    @RequestMapping("/awardRecords")
    public ModelAndView skipToAwardRecords(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/awardRecords");
        return modelAndView;

    }

    @RequestMapping("/userInShop")
    public ModelAndView skipToUserInShop(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/userInShop");
        return modelAndView;

    }





    @GetMapping("/shopIndex")
    public String index(Shop shop, HttpServletRequest request) {


        request.getSession().setAttribute("currentShopId",shop.getShopId());

        return "owner/shopIndex";
    }


    @GetMapping("/shopList")
    public String index2() {
        return "owner/owner_shopList";
    }



    @GetMapping("/productCateList")
    public String index3(HttpServletRequest request)
    {
        request.getSession().setAttribute("currentShopId",15L);
        return "owner/productCategoryList";
    }

    @GetMapping("/addProductPage")
    public String index4(HttpServletRequest request)
    {
        request.getSession().setAttribute("currentShopId",15L);
        return "owner/addProduct";
    }



    @GetMapping("/productImgList")
    public String index6(HttpServletRequest request, String productId) {
        long pid = Long.parseLong(productId);
        request.getSession().setAttribute("currentProductId",pid);
        return "owner/productImgList";
    }

    @GetMapping("/editProductPage")
    public String index7(HttpServletRequest request, Product productId) {
        request.getSession().setAttribute("currentProductId",productId.getProductId());
        return "owner/editProduct";
    }

    @GetMapping("/editProductPageFromInfoPage")
    public String index8(HttpServletRequest request) {
        Long currentProductId = (Long)request.getSession().getAttribute("currentProductId");
        request.getSession().setAttribute("currentProductId",currentProductId);
        return "owner/editProduct";
    }


    @GetMapping("/echart")
    public String index9() {
        return "statistic";
    }

    @RequestMapping("/404")
    public ModelAndView skipTo404(ModelAndView modelAndView) {
        modelAndView.setViewName("404");
        return modelAndView;

    }
    @RequestMapping("/nullparam")
    public ModelAndView skipToNullParam(ModelAndView modelAndView) {
        modelAndView.setViewName("nullparam");
        return modelAndView;

    }
    @RequestMapping("/validateNotMatch")
    public ModelAndView skipToValidateNotMatch(ModelAndView modelAndView) {
        modelAndView.setViewName("validateNotMatch");
        return modelAndView;

    }
    @RequestMapping("/shopAuthManage")
    public ModelAndView skipToShopAuthManage(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/shopAuthManage");
        return modelAndView;

    }

    @RequestMapping("/editShopAuth")
    public ModelAndView skipToEditShopAuth(ModelAndView modelAndView) {
        modelAndView.setViewName("owner/editShopAuth");
        return modelAndView;

    }

    @RequestMapping("/adminArea")
    public ModelAndView skipToAdminAreaList(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/areaList");
        return modelAndView;

    }

    @RequestMapping("/adminIndex")
    public ModelAndView skipToAdminIndex(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/adminIndex");
        return modelAndView;

    }


    @RequestMapping("/headLine")
    public ModelAndView skipToHeadLine(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/headLineList");
        return modelAndView;

    }

    @RequestMapping("/addHeadLine")
    public ModelAndView skipToAddHeadLine(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/addHeadLine");
        return modelAndView;

    }
    @RequestMapping("/shopCates")
    public ModelAndView skipToShopCates(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/shopCateList");
        return modelAndView;

    }

    @RequestMapping("/addShopCategory")
    public ModelAndView skipToAddShopCate(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/addShopCate");
        return modelAndView;
    }

    @RequestMapping("/shops")
    public ModelAndView skipToShopList(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/shopList");
        return modelAndView;

    }
    @RequestMapping("/accounts")
    public ModelAndView skipToAccountList(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/accountList");
        return modelAndView;

    }
    @RequestMapping("/adminPage")
    public ModelAndView skipToAdminLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("adminLogin");
        return modelAndView;

    }

}
