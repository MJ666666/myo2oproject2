package com.lmj.o2o.service;

import com.lmj.o2o.dto.PersonInfoTO;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: PersonInfoService
 * Description:
 * date: 2020/3/22 15:31
 *
 * @author MJ
 */
public interface PersonInfoService {


    PersonInfoTO updatePersonInfo(PersonInfo personInfo);

    PersonInfoTO getAllPersonInfo(PageVO pageVO);
}
