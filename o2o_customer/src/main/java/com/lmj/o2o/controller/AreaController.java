package com.lmj.o2o.controller;

import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AreaController
 * Description:
 * date: 2020/3/19 16:10
 *
 * @author MJ
 */
@Controller
@RequestMapping("/area")
public class AreaController {


    @Autowired
    private AreaService areaService;

    @GetMapping("/getAreas")
    @ResponseBody
    public Map<String,Object> getAreaList() {
        Map<String, Object> map = new HashMap<>();
        AreaTO areas = areaService.getAreas();
        if (areas.getState()==1) {
            map.put("areaList",areas.getDataList());
            map.put("result",new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result",new Result(OperationEnum.INNER_ERROR));
        return map;
    }
}
