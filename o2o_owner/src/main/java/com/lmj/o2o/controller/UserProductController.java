package com.lmj.o2o.controller;

import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserProductService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserProductController
 * Description:
 * date: 2020/3/20 17:13
 *
 * @author MJ
 */
@Controller
@RequestMapping("/useProduct")
public class UserProductController {


    @Autowired
    private UserProductService userProductService;

    @PostMapping("/getRecords")
    @ResponseBody
    public Map<String,Object> getConsumeRecords(@RequestBody PageVO pageVO,HttpSession session) {
        Map<String,Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(pageVO.getCurrentPage())|| StringUtils.isEmpty(pageVO.getPageSize())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        PageUtil.convertPage(pageVO);
        Long currentShopId = (Long) session.getAttribute("currentShopId");
        pageVO.setShopId(currentShopId);
        UserProductTO consumeRecordsTo = userProductService.getConsumeRecords(pageVO);
        if (consumeRecordsTo.getState() == 1) {
            map=consumeRecordsTo.getMap();
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }


    @GetMapping("/getStatistic")
    @ResponseBody
    public Map<String,Object> getConsumerRecordsByShopId(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Long currentShopId = (Long)session.getAttribute("currentShopId");
        if (currentShopId == null) {
            map.put("result", new Result(OperationEnum.INNER_ERROR));
            return map;
        }
        Shop shop = new Shop();
        shop.setShopId(currentShopId);
        UserProductTO userProductTO = userProductService.statisticTheRecords(shop);
        if (userProductTO.getState() == 1) {
            map.put("countList", userProductTO.getCountList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;

    }


    /**
     * Description: 给小程序扫码用
     * @author: MJ
     * @date: 2020/3/24 11:00
     * @param:
     * @return:
     */
    @GetMapping("/addRecord")
    @ResponseBody
    public Map<String,Object> addRecordAndUpdatePoint(UserProductMap userProductMap,HttpSession session) {
        System.out.println(userProductMap);
        Map<String,Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(userProductMap.getShopId()) || StringUtils.isEmpty(userProductMap.getUserId())|| StringUtils.isEmpty(userProductMap.getProductId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        Long currentUserId = (Long)session.getAttribute("currentUserId");
        userProductMap.setCreateTime(new Date());
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        shopAuthMap.setShopId(userProductMap.getShopId());
        shopAuthMap.setEmployeeId(currentUserId);
        UserProductTO userProductTO = userProductService.buyTheProduct(userProductMap,shopAuthMap);
        if (userProductTO.getState()==1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        if (userProductTO.getState()==OperationEnum.UNGRANTED_OPERATION.getState()) {
            map.put("result", new Result(OperationEnum.UNGRANTED_OPERATION));
            return map;
        }
        map.put("result", new Result(OperationEnum.INNER_ERROR));
        return map;

    }

}
