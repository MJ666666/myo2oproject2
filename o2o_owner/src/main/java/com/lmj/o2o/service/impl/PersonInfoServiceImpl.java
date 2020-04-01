package com.lmj.o2o.service.impl;

import com.lmj.o2o.dao.PersonInfoDao;
import com.lmj.o2o.dto.PersonInfoTO;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.PersonInfoService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: PersonInfoServiceImpl
 * Description:
 * date: 2020/3/22 15:33
 *
 * @author MJ
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public PersonInfoTO updatePersonInfo(PersonInfo personInfo) {
        personInfo.setLastEditTime(new Date());
        int i = personInfoDao.updatePersonInfo(personInfo);
        if (i==1) {
            return new PersonInfoTO(OperationEnum.SUCCESS);
        }
        return new PersonInfoTO(OperationEnum.INNER_ERROR);
    }

    @Override
    public PersonInfoTO getAllPersonInfo(PageVO pageVO) {
        PersonInfoTO personInfoTO;
        int i = personInfoDao.countAllRecords();
        List<PersonInfo> personInfos = personInfoDao.queryAllPersonInfo(pageVO);
        if (personInfos != null && personInfos.size() != 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("personInfoList", personInfos);
            PageUtil.fillParamsIntoMap(map, pageVO, i);
            personInfoTO = new PersonInfoTO(OperationEnum.SUCCESS);
            personInfoTO.setMap(map);
            return personInfoTO;
        }
        personInfoTO = new PersonInfoTO(OperationEnum.INNER_ERROR);
        return personInfoTO;
    }
}
