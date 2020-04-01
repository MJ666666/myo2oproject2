package com.lmj.o2o.dao;


import com.lmj.o2o.entity.HeadLine;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: HeadLineDao
 * Description:
 * date: 2020/3/15 11:28
 *
 * @author MJ
 */
@Repository
public interface HeadLineDao {

    List<HeadLine> queryHeadLines();

    int addHeadLine(HeadLine headLine);

    int updateHeadLine(HeadLine headLine);

    int deleteHeadLine(HeadLine headLine);


    List<HeadLine> queryAllHeadLines();
}
