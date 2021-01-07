package com.github.yuque.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangchaozhen
 */
@Order(1)
@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplate(@Value("${rest-template.trusted}") boolean trusted,
      @Value("${rest-template.max-thread}") int maxTotal,
      @Value("${rest-template.max-per-route}") int maxPerRoute) {
    RestTemplateBuilder builder = restTemplateBuilder(trusted, maxTotal, maxPerRoute);
    RestTemplate restTemplate = builder.build();
    //先获取到converter列表
    List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
    for (HttpMessageConverter<?> converter : converters) {
      //因为我们只想要jsonConverter支持对text/html的解析
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        try {
          //先将原先支持的MediaType列表拷出
          List<MediaType> mediaTypeList = new ArrayList<>(converter.getSupportedMediaTypes());
          //加入对text/html的支持
          mediaTypeList.add(MediaType.TEXT_HTML);
          mediaTypeList.add(MediaType.TEXT_PLAIN);
          mediaTypeList.add(MediaType.parseMediaType("text/plain;charset=utf-8"));
          //将已经加入了text/html的MediaType支持列表设置为其支持的媒体类型列表
          ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    restTemplate.setRequestFactory(
        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
    return restTemplate;
  }

  private CustomRestTemplateCustomizer customRestTemplateCustomizer(boolean trusted, int maxTotal,
      int maxPerRoute) {
    return new CustomRestTemplateCustomizer(trusted, maxTotal, maxPerRoute);
  }

  private RestTemplateBuilder restTemplateBuilder(boolean trusted, int maxTotal, int maxPerRoute) {
    return new RestTemplateBuilder(customRestTemplateCustomizer(trusted, maxTotal, maxPerRoute));
  }

}
