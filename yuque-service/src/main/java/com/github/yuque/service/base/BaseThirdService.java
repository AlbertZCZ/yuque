package com.github.yuque.service.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @program: yuque
 * @description: 基础
 * @author: zhangchaozhen
 * @create: 2021-01-07 16:01
 **/
@Slf4j
@Service
public class BaseThirdService {
    @Autowired
    protected RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String app;
    @Value("${yuque.token}")
    private String token;
    /**
     * 基础接口调用类封装
     * @param url
     * @param method
     * @param responseBodyType
     * @param requestBody
     * @param <T>
     * @param <A>
     * @return
     */
    protected <T, A> T exchange(String url, HttpMethod method,
                                ParameterizedTypeReference<T> responseBodyType, A requestBody) {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        processHeaders(headers);
        // 请求体
        headers.setContentType(processMediaType());
        // 发送请求
        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseBodyType);
        return resultEntity.getBody();
    }

    protected void processHeaders(HttpHeaders headers) {
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("User-Agent", app);
        headers.add("X-Auth-Token", token);
    }

    protected MediaType processMediaType() {
        MimeType mimeType = MediaType.parseMediaType("application/json;charset=utf-8");
        return new MediaType(mimeType.getType(), mimeType.getSubtype(),
                StandardCharsets.UTF_8);
    }
}
