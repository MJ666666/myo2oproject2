package com.lmj.o2o.service;


import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.entity.HeadLine;

/**
 * ClassName: HeadLineDao
 * Description:
 * date: 2020/3/15 11:35
 *
 * @author MJ
 */
public interface HeadLineService {

    HeadLineTO getHeadLineList();


    HeadLineTO getAllHeadLineList();

    HeadLineTO addHeadLine(HeadLine headLine);

    HeadLineTO updateHeadLine(HeadLine headLine);

    HeadLineTO deleteHeadLine(HeadLine headLine);
}
