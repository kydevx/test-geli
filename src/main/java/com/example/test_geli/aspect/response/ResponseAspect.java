package com.example.test_geli.aspect.response;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.test_geli.constants.Code;
import org.springframework.beans.factory.annotation.Value;

@Aspect
@Component
public class ResponseAspect {
  @Value("${api}")
  private String api;

  @Around("@annotation(response)")
  public Object resultWrapper(ProceedingJoinPoint joinPoint, Response response) throws Throwable {
    ResponseEntity<?> responseEntity = (ResponseEntity<?>) joinPoint.proceed();
    Map<String, Object> map = new HashMap<>();
    Code code = Code.success;
    switch (String.format("%s", responseEntity.getStatusCode().value())) {
      case "400":
        code = Code.error;
        break;
      case "401":
        code = Code.unauthorization;
        break;
      case "403":
        code = Code.forbideen;
        break;
      case "500":
        code = Code.internal;
        break;
      default:
        break;
    }
    map.put(responseEntity.getBody() instanceof String ? "message" : "result",
        responseEntity.getBody() instanceof Page ? resultPage(responseEntity.getBody(), response.url())
            : responseEntity.getBody());
    map.put("status", responseEntity.getStatusCode().value() <= 201 ? "Successfully" : "Failed");
    map.put("code", code.getCode());
    return new ResponseEntity<>(map, HttpStatus.valueOf(responseEntity.getStatusCode().value()));
  }

  public Object resultPage(Object values, String url) {
    Page<?> data = (Page<?>) values;
    Map<String, Object> map = new HashMap<>();
    int page = data.getNumber() + 1;
    map.put("page", page);
    map.put("size", data.getSize());
    map.put("count", data.getTotalElements());
    map.put("total", data.getTotalPages());
    map.put("data", data.getContent());
    map.put("previous",
        page > 1
            ? String.format("%s%s?page=%s&size=%s", api, url,
                data.getTotalPages() <= data.getNumber() ? data.getTotalPages() - 1 : page - 2, data.getSize())
            : null);
    map.put("next",
        data.getNumber() + 1 < data.getTotalPages()
            ? String.format("%s%s?page=%s&size=%s", api, url, page, data.getSize())
            : null);
    return map;
  }
}