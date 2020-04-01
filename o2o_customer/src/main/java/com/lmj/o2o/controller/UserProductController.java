package com.lmj.o2o.controller;

import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserProductController
 * Description:
 * date: 2020/3/20 14:45
 *
 * @author MJ
 */
@Controller
@RequestMapping("/userProduct")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;

    @GetMapping("/getRecordsInShop")
    @ResponseBody
    public Map<String, Object> getRecordsByShopId(UserProductMap userProductMap) {
        Map<String, Object> map = new HashMap<>();
        return getStringObjectMap( map, userProductMap);
    }
    @GetMapping("/getAllRecords")
    @ResponseBody
    public Map<String, Object> getAllRecords(UserProductMap userProductMap) {
        Map<String, Object> map = new HashMap<>();
        return getStringObjectMap( map, userProductMap);
    }

    private Map<String, Object> getStringObjectMap( Map<String, Object> map, UserProductMap userProductMap)  {
        UserProductTO recordTO = userProductService.getRecords(userProductMap);
        if (recordTO.getState() == 1) {
            map.put("records", recordTO.getDataList());
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }


}
