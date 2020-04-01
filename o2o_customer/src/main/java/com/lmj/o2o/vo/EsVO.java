package com.lmj.o2o.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: EsVO
 * Description: vo for ElasticSearch
 * date: 2020/3/10 9:06
 *
 * @author MJ
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsVO {

    private String index;
    private String type;

    private String field;
    private String[] fields;
    private String keyword;

    private int currentPage;

    private int pageSize;
}
