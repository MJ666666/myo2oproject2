package com.lmj.o2o.controller.admin;

import com.lmj.o2o.dto.PersonInfoTO;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.PersonInfoService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminPersonInfoController
 * Description:
 * date: 2020/3/28 10:56
 *
 * @author MJ
 */
@Controller
@RequestMapping("/adminUser")
public class AdminPersonInfoController {


    @Autowired
    private PersonInfoService personInfoService;

    @PostMapping("/getAccountList")
    @ResponseBody
    public Map<String,Object> getAllPersonInfo(@RequestBody PageVO pageVO) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(pageVO.getCurrentPage())) {
            pageVO.setStartIndex(0);
            pageVO.setPageSize(5);
        }else {
            PageUtil.convertPage(pageVO);
        }
        PersonInfoTO allPersonInfo = personInfoService.getAllPersonInfo(pageVO);
        if (allPersonInfo.getState() == 1) {
            map = allPersonInfo.getMap();
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(allPersonInfo));
        return map;

    }

    @GetMapping("/changeAccountStatus")
    public ModelAndView changeAccountStatus(PersonInfo personInfo,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(personInfo.getUserId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        PersonInfoTO personInfoTO = personInfoService.updatePersonInfo(personInfo);
        if (personInfoTO.getState() == 1) {
            modelAndView.setViewName("redirect:/accounts");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        modelAndView.addObject("errorCode", personInfoTO.getState());
        modelAndView.addObject("errMsg", personInfoTO.getStateInfo());
        return modelAndView;

    }
}
