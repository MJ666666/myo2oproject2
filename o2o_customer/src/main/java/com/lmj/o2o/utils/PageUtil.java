package com.lmj.o2o.utils;



import com.lmj.o2o.vo.PageVO;

import java.util.Map;

/**
 * ClassName: PageUtil
 * Description: 分页工具类
 * date: 2020/3/10 9:33
 *
 * @author MJ
 */
public class PageUtil {

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/10 9:36
     * @param: 当前页，页面显示条数
     * @return: 起始显示位置
     */
    public static int pageCountConvert(int currentPage,int pageSize) {

        return (currentPage-1)*pageSize;


    }

    public static PageVO convertPage(PageVO pageVO){
        int i = pageCountConvert(pageVO.getCurrentPage(), pageVO.getPageSize());
        pageVO.setStartIndex(i);
        return pageVO;
    };


    public static Map<String,Object> fillParamsIntoMap(Map<String,Object> map,PageVO pageVO,int totalRecords) {
        map.put("totalRecords",totalRecords);
        int mod=totalRecords/pageVO.getPageSize();
        int totalPages = totalRecords%pageVO.getPageSize()==0 ? mod : mod+1;
        map.put("totalPages",totalPages);
        return map;
    }
}
