package com.tx.api.test.automate.utils;

import com.tx.api.test.automate.security.BasicAuth;
import lombok.*;

import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class TestExecutionContext {
   private String testName;
   private String baseUrl;
   private String resourceUrl;
   private String httpMethod;
   private String contentType;
   private Map<String,String> headers;
   private Map<String,Object> queryParams;
   private String requestBody;
   private Integer statusCode;
   private String responseType;
   private String expectedResponse;
}
