package com.github.yuque.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * json工具类
 * @author zhangchaozhen
 */
@Slf4j
public class JsonUtil {

  private static ObjectMapper objectMapper = new ObjectMapper();
  private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

  static {
    objectMapper.setSerializationInclusion(Include.ALWAYS);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * 对象转Json格式字符串
   * @param obj 对象
   * @return Json格式字符串
   */
  public static <T> String obj2String(T obj) {
    if (obj == null) {
      return null;
    }
    try {
      return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.warn("Parse Object to String error : {}", e.getMessage());
      return null;
    }
  }

  /**
   * 对象转Json格式字符串(格式化的Json字符串)
   * @param obj 对象
   * @return 美化的Json格式字符串
   */
  public static <T> String obj2StringPretty(T obj) {
    if (obj == null) {
      return null;
    }
    try {
      return obj instanceof String ? (String) obj
          : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.warn("Parse Object to String error : {}", e.getMessage());
      return null;
    }
  }

  /**
   * 字符串转换为自定义对象
   * @param str 要转换的字符串
   * @param clazz 自定义对象的class对象
   * @return 自定义对象
   */
  public static <T> T string2Obj(String str, Class<T> clazz) {
    if (StringUtils.isEmpty(str) || clazz == null) {
      return null;
    }
    try {
      return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
    } catch (Exception e) {
      log.warn("Parse String to Object error : {}", e.getMessage());
      return null;
    }
  }

  public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
    if (StringUtils.isEmpty(str) || typeReference == null) {
      return null;
    }
    try {
      return (T) (typeReference.getType().equals(String.class) ? str
          : objectMapper.readValue(str, typeReference));
    } catch (IOException e) {
      log.warn("Parse String to Object error", e);
      return null;
    }
  }

  public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
    JavaType javaType = objectMapper.getTypeFactory()
        .constructParametricType(collectionClazz, elementClazzes);
    try {
      return objectMapper.readValue(str, javaType);
    } catch (IOException e) {
      log.warn("Parse String to Object error : {}" + e.getMessage());
      return null;
    }
  }

}
