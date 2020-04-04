package com.lmj.test.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * ClassName: AuthFilter
 * Description:
 * date: 2020/4/2 10:24
 *
 * @author MJ
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");

        if (token==null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", "401");
            responseData.put("message", "非法请求");
            responseData.put("cause", "Token is empty");
            try{
                ObjectMapper objectMapper = new ObjectMapper();

                byte[] data = objectMapper.writeValueAsBytes(responseData);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("content-Type","application/json;charset=utf-8");
                return response.writeWith(Mono.just(buffer));

            }  catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
