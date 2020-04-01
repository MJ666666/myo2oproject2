package com.lmj.o2o.controller.admin;

import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.entity.HeadLine;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.service.HeadLineService;
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
 * ClassName: AdminHeadLineController
 * Description:
 * date: 2020/3/27 15:20
 *
 * @author MJ
 */
@Controller
@RequestMapping("/adminHeadLine")
public class AdminHeadLineController {

    @Autowired
    private HeadLineService headLineService;

    @GetMapping("/getHeadLines")
    @ResponseBody
    public Map<String, Object> getHeadLineList() {
        Map<String,Object> map=new HashMap<>();
        HeadLineTO headLineList = headLineService.getAllHeadLineList();
        if (headLineList.getState()==1) {
            map.put("headLineList",headLineList.getHeadLineList());
            map.put("result",new Result(headLineList));
            return map;
        }
        map.put("result",new Result(headLineList));
        return map;
    }

    @PostMapping("/addNewHeadLine")
    public ModelAndView addHeadLine(ModelAndView modelAndView, HeadLine headLine, MultipartFile thumbnail) {
        if (StringUtils.isEmpty(headLine.getLineName())  || StringUtils.isEmpty(headLine.getLineLink()) || StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
        String lineImg = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
        headLine.setLineImg(lineImg);
        headLine.setEnableStatus(1);
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        HeadLineTO headLineTO = headLineService.addHeadLine(headLine);
        return getModelAndView(modelAndView, headLineTO);
    }

    @GetMapping("/editHeadLine")
    public ModelAndView editHeadLine(ModelAndView modelAndView,HeadLine headLine) {
        modelAndView.setViewName("admin/editHeadLine");
        modelAndView.addObject("headLine", headLine);
        return modelAndView;
    }


    @PostMapping("/updateHeadLine")
    public ModelAndView updateHeadLine(ModelAndView modelAndView, HeadLine headLine, MultipartFile thumbnail) {
        if (StringUtils.isEmpty(headLine.getLineId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        if (!StringUtils.isEmpty(thumbnail.getOriginalFilename())) {
            byte[] thumbnailBytes = ImageUtil.getThumbnailBytes(thumbnail);
            String lineImg = ImageUtil.uploadImage(thumbnail.getName(), thumbnail.getOriginalFilename(), thumbnailBytes);
            headLine.setLineImg(lineImg);
        }
        headLine.setLastEditTime(new Date());
        HeadLineTO headLineTO = headLineService.updateHeadLine(headLine);
        return getModelAndView(modelAndView, headLineTO);
    }


    @GetMapping("/updateHeadLineStatus")
    public ModelAndView updateHeadLineStatus(ModelAndView modelAndView, HeadLine headLine) {
        if (StringUtils.isEmpty(headLine.getLineId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        headLine.setLastEditTime(new Date());
        HeadLineTO headLineTO = headLineService.updateHeadLine(headLine);
        return getModelAndView(modelAndView, headLineTO);
    }


    @GetMapping("/deleteHeadLine")
    public ModelAndView deleteHeadLine(ModelAndView modelAndView, HeadLine headLine) {
        if (StringUtils.isEmpty(headLine.getLineId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        HeadLineTO headLineTO = headLineService.deleteHeadLine(headLine);
        return getModelAndView(modelAndView, headLineTO);

    }
    private ModelAndView getModelAndView(ModelAndView modelAndView, HeadLineTO headLineTO) {
        if (headLineTO.getState() == 1) {
            modelAndView.setViewName("redirect:/headLine");
            return modelAndView;
        }
        modelAndView.addObject("errorCode", headLineTO.getState());
        modelAndView.addObject("errMsg", headLineTO.getStateInfo());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
