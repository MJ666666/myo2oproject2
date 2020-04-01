package com.lmj.o2o.controller;

import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: HeadLineController
 * Description:
 * date: 2020/3/18 15:17
 *
 * @author MJ
 */
@Controller
@RequestMapping("/headLine")
public class HeadLineController {
    @Autowired
    private HeadLineService headLineService;

    @GetMapping("/getList")
    @ResponseBody
    public Map<String,Object> getHeadLineList() {
        Map<String,Object> map=new HashMap<>();
        HeadLineTO headLineTO = headLineService.getHeadLinesList();
        if (headLineTO.getState()==1) {
            map.put("headLineList",headLineTO.getDataMap());
            map.put("result",new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(OperationEnum.NULL_RESULT));
        return map;


    }


}
