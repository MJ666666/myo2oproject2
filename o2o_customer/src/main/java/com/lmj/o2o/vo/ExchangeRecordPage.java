package com.lmj.o2o.vo;

import lombok.Data;

/**
 * ClassName: ExchangeRecoedPage
 * Description:
 * date: 2020/3/21 15:31
 *
 * @author MJ
 */
@Data
public class ExchangeRecordPage {
    private Long shopId;
    private Long userId;
    private Integer usedStatus;
    private int startIndex;
    private int pageSize;
}
