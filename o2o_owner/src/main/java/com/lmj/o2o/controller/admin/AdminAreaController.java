package com.lmj.o2o.controller.admin;

import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.entity.Area;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminAreaController
 * Description:
 * date: 2020/3/27 9:46
 *
 * @author MJ
 */
@Controller
@RequestMapping("/admin")
public class AdminAreaController {


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

    @PostMapping("/addNewArea")
    @ResponseBody
    private ModelAndView addArea(Area area,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(area.getAreaName())|| StringUtils.isEmpty(area.getAreaDesc())|| StringUtils.isEmpty(area.getPriority())) {
         modelAndView.setViewName("nullparam");
         return modelAndView;
        }
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        AreaTO areaTO = areaService.addArea(area);
        if (areaTO.getState() == 1) {
            modelAndView.setViewName("redirect:/adminArea");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;

    }

    @PostMapping("/updateArea")
    private ModelAndView updateArea(Area area,ModelAndView modelAndView) {
        if (StringUtils.isEmpty(area.getAreaId())) {
            modelAndView.setViewName("nullparam");
            return modelAndView;
        }
        area.setLastEditTime(new Date());
        AreaTO areaTO = areaService.modifyArea(area);
        if (areaTO.getState() == 1) {
            modelAndView.setViewName("redirect:/adminArea");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;

    }






    @RequestMapping("/addArea")
    public ModelAndView skipToAddArea(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/addArea");
        return modelAndView;

    }

    @GetMapping("/editArea")
    public ModelAndView skipToEditArea(Area area,ModelAndView modelAndView) {
        modelAndView.addObject("area",area);
        modelAndView.setViewName("admin/editArea");
        return modelAndView;

    }


}
