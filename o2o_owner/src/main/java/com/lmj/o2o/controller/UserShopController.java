package com.lmj.o2o.controller;

import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.entity.Result;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserShopService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserShopController
 * Description:
 * date: 2020/3/24 17:57
 *
 * @author MJ
 */
@Controller
@RequestMapping("/userShop")
public class UserShopController {

    @Autowired
    private UserShopService userShopService;


    @PostMapping("/getAllUsers")
    @ResponseBody
    public Map<String,Object> getAllCustomers(HttpSession session,@RequestBody PageVO pageVO) {
        Map<String,Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(pageVO.getCurrentPage())|| StringUtils.isEmpty(pageVO.getPageSize())) {
            map.put("result", new Result(OperationEnum.NULL_PARAM));
            return map;
        }
        PageUtil.convertPage(pageVO);
        Long currentShopId = (Long) session.getAttribute("currentShopId");
        pageVO.setShopId(currentShopId);
        UserShopTO userShopTO = userShopService.getAllCustomers(pageVO);
        if (userShopTO.getState() == 1) {
            map=userShopTO.getMap();
            map.put("result", new Result(OperationEnum.SUCCESS));
            return map;
        }
        map.put("result", new Result(OperationEnum.NULL_RESULT));
        return map;
    }
}
