package com.lmj.o2o.dao;

import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: PersonInfoDao
 * Description:
 * date: 2020/3/22 11:22
 *
 * @author MJ
 */
@Repository
public interface PersonInfoDao {


    int addPersonInfo(PersonInfo personInfo);

    int updatePersonInfo(PersonInfo personInfo);

    int addUserViaWeb(LocalAuth localAuth);

    int addUserViaWechat(WechatAuth wechatAuth);

    List<PersonInfo> queryAllPersonInfo(PageVO pageVO);

    int countAllRecords();
}
