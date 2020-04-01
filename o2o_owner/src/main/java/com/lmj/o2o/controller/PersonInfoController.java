package com.lmj.o2o.controller;

import com.lmj.o2o.dto.PersonInfoTO;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PersonInfoController
 * Description:
 * date: 2020/3/22 15:38
 *
 * @author MJ
 */
@Controller
@RequestMapping("/personInfo")
public class PersonInfoController {


    @Autowired
    private PersonInfoService personInfoService;

    @PostMapping("/updatePersonInfo")
    @ResponseBody
    public Map<String,Object> updatePersonInfo(PersonInfo personInfo) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(personInfo.getUserId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        PersonInfoTO personInfoTO = personInfoService.updatePersonInfo(personInfo);
        if (personInfoTO.getState() == 1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.INNER_ERROR));
        return map;

    }
}
