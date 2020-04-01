package com.lmj.o2o.controller;

import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AwardService;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AwardController
 * Description:
 * date: 2020/3/21 9:53
 *
 * @author MJ
 */
@Controller
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/getAwardList")
    @ResponseBody
    public Map<String,Object> getAwardListByShopId(PageVO awardPage) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(awardPage.getShopId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        AwardTO awardTo = awardService.getAwardListByShopId(awardPage);
        return getStringObjectMap(map, awardTo);
    }

    @GetMapping("/getAward")
    @ResponseBody
    public Map<String,Object> getAwardByAwardId(Award award) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(award.getAwardId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        AwardTO awardTo = awardService.getAwardByAwardId(award);
        return getStringObjectMap(map, awardTo);
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> map, AwardTO awardTo) {
        if (awardTo.getState() == 1) {
            map.put("awardList", awardTo.getDataList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }
}
