package com.lmj.o2o.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * ClassName: ElasticSearchService
 * Description:
 * date: 2020/4/4 12:18
 *
 * @author MJ
 */
@FeignClient(value = "es-service")
public interface ElasticSearchService {



}
