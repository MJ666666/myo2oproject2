package com.lmj.o2o.utils;

import java.util.UUID;

/**
 * ClassName: UuidUtils
 * Description:
 * date: 2020/3/22 12:49
 *
 * @author MJ
 */
public class UuidUtils {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");
        return s;
    }
}
