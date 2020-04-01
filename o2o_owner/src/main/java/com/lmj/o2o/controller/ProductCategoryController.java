package com.lmj.o2o.controller;


import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.ProductCategory;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.enums.ShopStateEnum;
import com.lmj.o2o.service.ProductCategoryService;
import com.lmj.o2o.utils.HttpSessionAttributeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ProductCategoryController
 * Description:
 * date: 2020/3/7 16:48
 *
 * @author MJ
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/7 17:04
     * @param: 从session中获取当前商铺id
     * @return:
     */
    @GetMapping("/getList")
    @ResponseBody
    public Map<String,Object> getProductCategoryByShopId(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
        Result result;
        Long currentShopId =(Long) request.getSession().getAttribute("currentShopId");
        if (currentShopId==null) {
             result = new Result(ShopStateEnum.NULL_SHOPID);
            map.put("result",result);
            return map;
        }
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        ProductCategoryTO productCategoryTO = productCategoryService.getProductCategoryByShopId(shop);
        if (productCategoryTO.getList() == null) {
           result=new Result(OperationEnum.NULL_RESULT);
           map.put("result",result);
           return map;
        }
        map.put("productCategoryList",productCategoryTO.getList());
        result=new Result(OperationEnum.SUCCESS);
        map.put("result",result);
        return map;
    }

    @PostMapping("/addNewProductCategory")
    public ModelAndView addNewProductCategory(ProductCategory productCategory, HttpServletRequest request) {
        Long currentShopId =(Long) HttpSessionAttributeUtil.getAttribute(request, "currentShopId");
        productCategory.setShopId(currentShopId);
        ModelAndView modelAndView = new ModelAndView();
        if (productCategory.getShopId()==null || productCategory.getPriority()==null) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        ProductCategoryTO productCategoryTO = productCategoryService.addNewProductCategory(productCategory);
        if (productCategoryTO.getProductCategory().getProductCategoryId()==null) {
            modelAndView.setViewName("error");
            return  modelAndView;
        }
        modelAndView.setViewName("redirect:/productCategoryList");
        return modelAndView;

    }

    @GetMapping("/deleteProductCategory")
    public ModelAndView deleteProductCategory(ProductCategory productCategory, HttpServletRequest request) {
        Long currentShopId = (Long) HttpSessionAttributeUtil.getAttribute(request, "currentShopId");
        productCategory.setShopId(currentShopId);
        ModelAndView modelAndView = new ModelAndView();
        if (productCategory==null || productCategory.getProductCategoryId()==null || productCategory.getShopId()==null) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        ProductCategoryTO productCategoryTO = productCategoryService.deleteProductCategory(productCategory);
        int state = productCategoryTO.getState();
        if (state==1) {

            modelAndView.setViewName("redirect:/productCategoryList");
            modelAndView.setStatus(HttpStatus.OK);
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @GetMapping("addCategory")
    public String addCatePage() {
        return "owner/addProductCategory";
    }
}
