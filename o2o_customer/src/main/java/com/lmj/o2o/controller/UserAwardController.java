package com.lmj.o2o.controller;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserAwardService;
import com.lmj.o2o.utils.HttpRequestUtils;
import com.lmj.o2o.utils.UuidUtils;
import com.lmj.o2o.vo.ExchangeRecordPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserAwardController
 * Description:
 * date: 2020/3/20 15:10
 *
 * @author MJ
 */
@Controller
@RequestMapping("/userAward")
public class UserAwardController {


    @Autowired
    private UserAwardService userAwardService;

    @GetMapping("/getAllRecords")
    @ResponseBody
    public Map<String,Object> getAllExchangeRecords(HttpServletRequest request) {
        ExchangeRecordPage exchangeRecordPage = new ExchangeRecordPage();
        return getStringObjectMap(request, exchangeRecordPage);
    }
    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/20 15:18
     * @param: shopId 或者shopId和usedStatus
     * @return:
     */
    @GetMapping("/getRecordsByShopID")
    @ResponseBody
    public Map<String,Object> getRecordsByShopID(HttpServletRequest request, ExchangeRecordPage recordsPage) {
        return getStringObjectMap(request, recordsPage);
    }

    private Map<String, Object> getStringObjectMap(HttpServletRequest request, ExchangeRecordPage recordsPage) {
        Map<String, Object> map = new HashMap<>();
        //Long currentUserId = (Long) request.getSession().getAttribute("currentUserId");
       // userAwardMap.setUserId(currentUserId);
        UserAwardTO exchangeTO = userAwardService.getExchangeRecords(recordsPage);
        if (exchangeTO.getState() == 1) {
            map.put("exchangeRecords", exchangeTO.getDataList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }


    @PostMapping("/exchangeReward")
    @ResponseBody
    public Map<String,Object> exchangeAward(@RequestBody UserAwardMap userAwardMap,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userAwardMap.getAwardId()) || StringUtils.isEmpty(userAwardMap.getShopId())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        //Long currentUserId = (Long)request.getSession().getAttribute("currentUserId");
        //userAwardMap.setUserId(currentUserId);
        userAwardMap.setUsedStatus(0);
        userAwardMap.setCreateTime(new Date());
        map = userAwardService.postToAddRecord(userAwardMap);
        return map;
    }




    /**
     * Description: 用于兑奖
     * @author: MJ
     * @date: 2020/3/22 17:39
     * @param:
     * @return:
     */
    @GetMapping("/updateRecord")
    public void updateAwardUsedStatus(String userAwardId, String awardCode, HttpServletResponse response,HttpSession session) throws IOException {
        if (StringUtils.isEmpty(userAwardId) || StringUtils.isEmpty(awardCode)) {
                response.getOutputStream().write("param is null".getBytes());
        }
        System.out.println(userAwardId);
        System.out.println(awardCode);
        String storeCode = (String)session.getAttribute("awardCode");
        System.out.println(storeCode);
        if (awardCode.equals(storeCode)) {
            StringBuilder sb = new StringBuilder();
            sb.append(Consts.WECHAT_AWARD_QRCODE_EXCHANGE).append("?userAwardId="+userAwardId).append("&usedStatus=1");
            String respJson = HttpRequestUtils.sendGetRequest(sb.toString());
            response.getOutputStream().write(respJson.getBytes());
        }else {
            response.getOutputStream().write("code is invalid".getBytes());
        }

    }
}
