package com.lmj.o2o.controller;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.ProductExecuteException;
import com.lmj.o2o.service.AwardSerice;
import com.lmj.o2o.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AwardController
 * Description:
 * date: 2020/3/19 10:36
 *
 * @author MJ
 */
@Controller
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardSerice awardSerice;

    @GetMapping("/getAwardList")
    @ResponseBody
    public Map<String,Object> getAwardListByShopId(HttpServletRequest request) {
        Long currentShopId = (Long)request.getSession().getAttribute("currentShopId");
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(shop.getShopId())) {
           map.put("result",new Result(OperationEnum.NULL_PARAM));
           return map;
        }
        AwardTO awardTo = awardSerice.getAwardListByShopId(shop);
        if (awardTo.getState()==1) {
            map.put("result",new Result(OperationEnum.SUCCESS));
            map.put("awardList",awardTo.getList());
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;
    }


    @PostMapping("/addNewAward")
    public ModelAndView addNewAward(HttpServletRequest request, String validateCode,MultipartFile thumbnail, Award award) {

        ModelAndView modelAndView = new ModelAndView();
        String verifyCode = (String) request.getSession().getAttribute("validateCode");
        if (!verifyCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        Long currentShopId = (Long) request.getSession().getAttribute("currentShopId");
        award.setShopId(currentShopId);
        if (StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
            //存储缩略图
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String thumbnailImgPath = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            award.setAwardImg(thumbnailImgPath);
            Date date = new Date();
            award.setCreateTime(date);
            award.setLastEditTime(date);
            award.setEnableStatus(0);

        //  存储描述图到数据库
        AwardTO awardTO = awardSerice.addNewAward(award);
        if (awardTO.getState()==1) {
            modelAndView.setViewName("redirect:/awards");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @PostMapping("/updateAward")
    public ModelAndView updateAward(ModelAndView modelAndView,MultipartFile thumbnail,String validateCode,Award award,HttpServletRequest request) {
        String verifyCode = (String) request.getSession().getAttribute("validateCode");
        if (!verifyCode.equals(validateCode)) {
            modelAndView.setViewName("validateNotMatch");
            return modelAndView;
        }
        Long currentShopId= (Long)request.getSession().getAttribute("currentShopId");
        Long currentAwardId =(Long) request.getSession().getAttribute("currentAwardId");
        award.setAwardId(currentAwardId);
        award.setShopId(currentShopId);
        if (!StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String thumbnailImgPath = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            award.setAwardImg(thumbnailImgPath);
        }
        Date date = new Date();
        award.setLastEditTime(date);
        AwardTO awardTO = awardSerice.updateAward(award);
        if (awardTO.getState()==1) {
            modelAndView.setViewName("redirect:/awards");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }



    @GetMapping("/getAwardInfo")
    public ModelAndView getAwardInfo(Award award,ModelAndView modelAndView,HttpServletRequest request) {
       Long currentShopId= (Long)request.getSession().getAttribute("currentShopId");
       request.getSession().setAttribute("currentAwardId",award.getAwardId());
       award.setShopId(currentShopId);
        if (StringUtils.isEmpty(award.getAwardId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        AwardTO awardTO = awardSerice.getAwardByAwardId(award);
        if (awardTO.getState()==1) {
            Award awardInfo = awardTO.getAward();
            awardInfo.setAwardImg(Consts.FDFS_SERVER+awardInfo.getAwardImg());
            modelAndView.addObject("award",awardInfo);
            modelAndView.setViewName("owner/awardInfo");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }


    @GetMapping("/editAwardPage")
    public ModelAndView editAward(HttpServletRequest request,ModelAndView modelAndView,Award award) {
        Long currentShopId= (Long)request.getSession().getAttribute("currentShopId");
        if (StringUtils.isEmpty(award.getAwardId())) {
            Long currentAwardId = (Long) request.getSession().getAttribute("currentAwardId");
            award.setAwardId(currentAwardId);
        }else {
            request.getSession().setAttribute("currentAwardId",award.getAwardId());
        }
        award.setShopId(currentShopId);
        AwardTO awardTO = awardSerice.getAwardByAwardId(award);
        if (awardTO.getState()==1) {
            Award awardInfo = awardTO.getAward();
            modelAndView.addObject("award",awardInfo);
            modelAndView.setViewName("owner/editAward");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @GetMapping("/changeStatus")
    public ModelAndView changeStatus(HttpServletRequest request,Award award,ModelAndView modelAndView) {
        Long currentShopId=(Long)request.getSession().getAttribute("currentShopId");
        award.setShopId(currentShopId);
        AwardTO awardTO = awardSerice.updateAward(award);
        if (awardTO.getState()==1) {
            modelAndView.setViewName("redirect:/awards");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
