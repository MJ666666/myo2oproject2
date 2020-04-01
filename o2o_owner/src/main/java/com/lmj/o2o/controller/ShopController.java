package com.lmj.o2o.controller;


import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.enums.UserEnum;
import com.lmj.o2o.service.ShopService;
import com.lmj.o2o.utils.ImageUtil;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ShopController
 * Description:
 * date: 2020/3/7 14:31
 *
 * @author MJ
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/7 15:16
     * @param:传入 userId
     * @return: data map
     */
    @PostMapping("/getShopList")
    @ResponseBody
    public Map<String, Object> getShopList(@RequestBody PageVO pageVO, HttpSession session) {
        //从session获取ownerId
        //request.getSession().getAttribute("userId");
        Long currentUserId = (Long)session.getAttribute("currentUserId");
        pageVO.setOwnerId(currentUserId);
        PageUtil.convertPage(pageVO);
        Map<String, Object> map;
        if (currentUserId==null) {
            map=new HashMap<>();
           map.put("result",new Result(UserEnum.NULL_USER_ID));
            return map;
        }
        Shop shop = new Shop();
        shop.setOwnerId(currentUserId);
        pageVO.setShop(shop);
        ShopTO shopTO = shopService.getShopList(pageVO);
        int state = shopTO.getState();
        if (state > 0) {
            map = shopTO.getMap();
        } else {
            map=new HashMap<>();
            map.put("result",new Result(OperationEnum.NULL_RESULT));
        }
        map.put("result",new Result(shopTO));
        return map;
    }

    @PostMapping("/modifyShopInfo")
    public ModelAndView modifyShopInfo(Shop shop, HttpServletRequest request, MultipartFile thumbnail,ModelAndView modelAndView, String validateCode) {
        String verifyCode = (String) request.getSession().getAttribute("validateCode");
        if (!verifyCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        Long currentShopId = (Long)request.getSession().getAttribute("currentShopId");
        if (thumbnail!=null) {
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String fileName = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            shop.setShopImg(fileName);
        }
        shop.setShopId(currentShopId);
        ShopTO shopTO = shopService.modifyShopInfo(shop);
        if (shopTO.getState()==1) {
            modelAndView.setViewName("redirect:/showShopInfo");
            return modelAndView;
        }else {
            modelAndView.setViewName("error");
            return modelAndView;
        }


    }

    @PostMapping("/addNewShop")
    public ModelAndView addNewShop(Shop shop, HttpSession session,ModelAndView modelAndView, MultipartFile thumbnail) {

        Long currentUserId = (Long)session.getAttribute("currentUserId");
        shop.setOwnerId(currentUserId);
        Map<String,Object> map=new HashMap<>();
        byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
        String path= ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
        shop.setShopImg(path);
        ShopTO shopTO = shopService.addNewShop(shop);
        if (shopTO.getState()==1) {
            modelAndView.setViewName("redirect:/shopList");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }



    @GetMapping("/getShopDetail")
    @ResponseBody
    public Map<String,Object> getShopDetail(HttpServletRequest request) {
        Long currentShopId = (Long)request.getSession().getAttribute("currentShopId");
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        Map<String,Object> map=new HashMap<>();
        ShopTO shopTO = shopService.getShopInfo(shop);
        fillInToMap(map, shopTO);
        return map;

    }
    @GetMapping("/editPage")
    public String editPage() {
        return "owner/editShop";
    }

    @GetMapping("/addShopPage")
    public String addShopPage() {
        return "owner/addShop";
    }


    private void fillInToMap(Map<String, Object> map, ShopTO shopTO) {
        int state = shopTO.getState();
        if (state > 0) {
            map.put("shop", shopTO.getShop());
        }
        map.put("result", new Result(shopTO));
    }
}
