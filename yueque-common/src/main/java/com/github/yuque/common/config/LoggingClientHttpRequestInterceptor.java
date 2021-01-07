package com.github.yuque.common.config;

import com.github.yuque.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangmiao
 */
@Slf4j
@Component
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    long begin = 0L;
    long end = 0L;
    ClientHttpResponse response = null;
    StringBuilder inputStringBuilder = new StringBuilder();
    String requestBody = new String(body, StandardCharsets.UTF_8);
    begin = System.currentTimeMillis();
    try {
      response = execution.execute(request, body);
      end = System.currentTimeMillis();
      if (response.getBody() != null) {
        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
          String line = bufferedReader.readLine();
          while (line != null) {
            inputStringBuilder.append(line);
            line = bufferedReader.readLine();
          }
        }
      } else {
        log.warn(JsonUtil.obj2String(response));
      }
      log.info(
          "invoke third success, duration:{}ms,  url:{}, request:{}, responseCode:{}, responseBody:{}",
          (end - begin), request.getURI(), requestBody, response.getStatusCode(),
          inputStringBuilder.toString());
    } catch (Exception e) {
      log.info("Exception");
      log.error(
          "invoke third fail, , duration:{}ms,  url:{}, request:{}", (end - begin),
          request.getURI(), requestBody);
    } finally {
      if (end == 0) {
        end = System.currentTimeMillis();
      }
    }
    return response;
  }
}
