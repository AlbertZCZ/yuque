package com.github.yuque.common.config;

import com.github.yuque.common.constant.BizCodeConstant;
import com.github.yuque.common.constant.CharacterConstant;
import com.github.yuque.common.exception.ServerException;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


/**
 * @author zhangchaozhen
 */
@AllArgsConstructor
public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

  private boolean trusted;
  private int maxTotal;
  private int maxPerRoute;

  @Override
  public void customize(RestTemplate restTemplate) {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = poolingHttpClientConnectionManager(
        maxTotal, maxPerRoute);
    RequestConfig requestConfig = requestConfig();
    CloseableHttpClient httpClient = httpClient(poolingHttpClientConnectionManager, requestConfig);
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    requestFactory.setConnectionRequestTimeout(3000);
    requestFactory.setConnectTimeout(5000);
    requestFactory.setReadTimeout(5000);
    restTemplate.setRequestFactory(requestFactory);
    restTemplate.setInterceptors(ImmutableList.of(traceInterceptor()));
  }

  private ClientHttpRequestInterceptor traceInterceptor() {
    return (request, body, execution) -> {
      String traceId = MDC.get(CharacterConstant.REQUEST_ID_HEADER);
      if (StringUtils.isEmpty(traceId)) {
        traceId = Thread.currentThread().getName();
      }
      request.getHeaders().add(CharacterConstant.REQUEST_ID_HEADER, traceId);
      return execution.execute(request, body);
    };
  }

  private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(int maxTotal,
      int maxPerRoute) {
    PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
    result.setMaxTotal(maxTotal);
    result.setDefaultMaxPerRoute(maxPerRoute);
    return result;
  }

  private RequestConfig requestConfig() {
    return RequestConfig.custom()
        .setConnectionRequestTimeout(500)
        .setConnectTimeout(500)
        .setSocketTimeout(1_000)
        .build();
  }

  private CloseableHttpClient httpClient(
      PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
      RequestConfig requestConfig) {
    HttpClientBuilder builder = HttpClientBuilder
        .create()
        .setConnectionManager(poolingHttpClientConnectionManager)
        .setDefaultRequestConfig(requestConfig);
    if (!trusted) {
      try {
        builder.setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
            .loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build(),
            NoopHostnameVerifier.INSTANCE));
      } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
        throw new ServerException(BizCodeConstant.UNKNOWN_ERROR, e);
      }
    }
    return builder.build();
  }
}
