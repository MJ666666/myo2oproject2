package com.lmj.o2o.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ProductExecuteException
 * Description:
 * date: 2020/3/8 11:49
 *
 * @author MJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductExecuteException extends BaseException {

    private String errMsg;

}
