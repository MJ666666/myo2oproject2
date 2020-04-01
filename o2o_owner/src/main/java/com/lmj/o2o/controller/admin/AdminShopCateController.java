package com.lmj.o2o.controller.admin;

import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ShopCategoryService;
import com.lmj.o2o.utils.ImageUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminShopCateController
 * Description:
 * date: 2020/3/27 20:53
 *
 * @author MJ
 */
@Controller
@RequestMapping("/adminCates")
public class AdminShopCateController {

    @Autowired
    private ShopCategoryService shopCategoryService;


    @GetMapping("/getAllCates")
    @ResponseBody
    public Map<String,Object> getAllShopCates() {
        Map<String, Object> map = new HashMap<>();
        ShopCategoryTO allShopCategory = shopCategoryService.getAllShopCategory();
        if (allShopCategory.getState() == 1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            map.put("cateList", allShopCategory.getShopCategoryList());
            return map;
        }
        map.put("result", new Result(allShopCategory));
        return map;
    }

    @PostMapping("/addNewShopCate")
    public ModelAndView addNewShopCate(ModelAndView modelAndView, ShopCategory shopCategory, MultipartFile thumbnail) {
        if (StringUtils.isEmpty(shopCategory.getShopCategoryName()) || StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
        String shopCategoryImg = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
        shopCategory.setShopCategoryImg(shopCategoryImg);
        shopCategory.setCreateTime(new Date());
        shopCategory.setLastEditTime(new Date());
        ShopCategoryTO shopCategoryTO = shopCategoryService.addShopCategory(shopCategory);
        if (shopCategoryTO.getState() == 1) {
            modelAndView.setViewName("redirect:/shopCates");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errorCode", shopCategoryTO.getState());
        modelAndView.addObject("errMsg", shopCategoryTO.getStateInfo());
        return modelAndView;
    }


    @PostMapping("/updateShopCate")
    public ModelAndView updateShopCate(ModelAndView modelAndView, ShopCategory shopCategory, MultipartFile thumbnail) {
        if (StringUtils.isEmpty(shopCategory.getShopCategoryId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        if (!StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String shopCategoryImg = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            shopCategory.setShopCategoryImg(shopCategoryImg);
        }
        shopCategory.setLastEditTime(new Date());
        ShopCategoryTO shopCategoryTO = shopCategoryService.modifyShopCategory(shopCategory);
        if (shopCategoryTO.getState() == 1) {
            modelAndView.setViewName("redirect:/shopCates");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errorCode", shopCategoryTO.getState());
        modelAndView.addObject("errMsg", shopCategoryTO.getStateInfo());
        return modelAndView;
    }

    @GetMapping("/editShopCate")
    public ModelAndView editShopCategory(ShopCategory shopCategory,ModelAndView modelAndView){
        if (StringUtils.isEmpty(shopCategory.getShopCategoryId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        modelAndView.addObject("shopCate",shopCategory);
        modelAndView.setViewName("admin/editShopCate");
        return modelAndView;

    }

}
