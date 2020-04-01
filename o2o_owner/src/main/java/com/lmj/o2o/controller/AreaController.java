package com.lmj.o2o.controller;

import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.entity.Result;
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
 * date: 2020/3/12 16:54
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
    private Map<String,Object> getAreas() {
        Map<String, Object> map = new HashMap<>();
        AreaTO areaTO = areaService.getAreaLists();
        if (areaTO.getState()==1) {
            map.put("areaList",areaTO.getAreaList());
        }
        map.put("result",new Result(areaTO));
        return map;
    }
}
