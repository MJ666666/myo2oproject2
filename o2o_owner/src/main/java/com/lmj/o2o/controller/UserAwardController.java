package com.lmj.o2o.controller;

import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserAwardService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.utils.UuidUtils;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserAwardController
 * Description:
 * date: 2020/3/21 13:43
 *
 * @author MJ
 */
@Controller
@RequestMapping("/userAward")
public class UserAwardController {

    @Autowired
    private UserAwardService userAwardService;

    @PostMapping("/exchangeAward")
    @ResponseBody
    public Map<String,Object> exchangeAward(UserAwardMap userAwardMap) {
        System.out.println(userAwardMap);
        Map<String, Object> map = new HashMap<>();
        userAwardMap.setCreateTime(new Date());
        UserAwardTO userAwardTO = userAwardService.addNewUserAwardRecord(userAwardMap);
        return getStringObjectMap(map, userAwardTO);
    }

    @GetMapping("/updateRecord")
    @ResponseBody
    public Map<String,Object> updateRecord(UserAwardMap userAwardMap,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userAwardMap.getUserAwardId()) || StringUtils.isEmpty(userAwardMap.getAwardCode())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
         userAwardMap.setUsedStatus(1);
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        shopAuthMap.setShopId(userAwardMap.getShopId());
        Long currentUserId = (Long)session.getAttribute("currentUserId");
        shopAuthMap.setEmployeeId(currentUserId);
        UserAwardTO userAwardTO = userAwardService.updateUserAwardRecord(userAwardMap,shopAuthMap);
        if (userAwardTO.getState() == OperationEnum.UNGRANTED_OPERATION.getState()) {
            map.put("result", new Result(OperationEnum.UNGRANTED_OPERATION));
            return map;
        }
        return getStringObjectMap(map, userAwardTO);
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> map, UserAwardTO userAwardTO) {
        if (userAwardTO.getState() == 1) {
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", OperationEnum.INNER_ERROR);
        return map;
    }


    @PostMapping("/getCustomerAwardRecords")
    @ResponseBody
    public Map<String,Object> getAllCustomerAwardRecords(@RequestBody PageVO pageVO,HttpSession session) {
        Map<String,Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(pageVO.getCurrentPage())|| StringUtils.isEmpty(pageVO.getPageSize())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        PageUtil.convertPage(pageVO);
        Long currentShopId = (Long) session.getAttribute("currentShopId");
        pageVO.setShopId(currentShopId);
        UserAwardTO awardRecordsTo = userAwardService.getCustomersAwardRecords(pageVO);
        if (awardRecordsTo.getState() == 1) {
            map=awardRecordsTo.getMap();
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }



    /**
     * Description: 给二维码设置时效
     * @author: MJ
     * @date: 2020/3/22 17:33
     * @param:
     * @return:
     */
    @GetMapping("/getAwardCode")
    @ResponseBody
    public Map<String,Object> getRandomCodeToExchange() {
        Map<String, Object> map = new HashMap<>();
        String randomCode = userAwardService.getRandomCode();
        map.put("awardCode",randomCode);
        return map;
    }

    @GetMapping("/getAllAwardRecords")
    @ResponseBody
    public Map<String,Object> getAllAwardRecords(PageVO userAwardMapPage) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userAwardMapPage.getUserId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        if(StringUtils.isEmpty(userAwardMapPage.getCurrentPage()) || userAwardMapPage.getCurrentPage()==0){
            userAwardMapPage.setTotals(5);
        }else {
            PageUtil.convertPageForWechatPage(userAwardMapPage);
        }
        UserAwardTO historyAwardRecords = userAwardService.getHistoryAwardRecords(userAwardMapPage);
        if (historyAwardRecords.getState() == 1) {
            map.put("historyRecords", historyAwardRecords.getList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;

    }
}
