package com.tx.api.test.automate.execute;

import com.google.gson.JsonParser;
import com.tx.api.test.automate.client.RestClient;
import com.tx.api.test.automate.exception.ApiAutomationException;
import com.tx.api.test.automate.utils.TestExecutionContext;
import com.tx.api.test.automate.utils.TestExecutionContextManager;
import com.tx.api.test.automate.utils.UserContextManager;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class ExecuteApiTest {

    public static void runTest() {
       TestExecutionContext testExecutionContext = TestExecutionContextManager.getTestExecutionContext();
       RestClient restClient = new RestClient(testExecutionContext.getBaseUrl(), UserContextManager.getUserContext());
       Response response = null;
       switch(testExecutionContext.getHttpMethod()){
           case "GET" :
               response =  restClient.getMethod(testExecutionContext.getResourceUrl(), testExecutionContext.getHeaders(), testExecutionContext.getQueryParams());

               break;
           case "POST" :
               if("MULTIPART".equalsIgnoreCase(testExecutionContext.getContentType())){
                    response=restClient.postMultipartMethod(testExecutionContext.getResourceUrl(), testExecutionContext.getRequestBody(), testExecutionContext.getHeaders());
               }
               else {
                   response = restClient.postMethod(testExecutionContext.getResourceUrl(), testExecutionContext.getRequestBody(),
                           testExecutionContext.getContentType(), testExecutionContext.getHeaders(), testExecutionContext.getQueryParams());
               }
               break;
           case "PUT" :
               response =  restClient.putMethod(testExecutionContext.getResourceUrl(), testExecutionContext.getRequestBody(),
                       testExecutionContext.getContentType(), testExecutionContext.getHeaders(),testExecutionContext.getQueryParams());
               break;
           case "DELETE":
               response=restClient.deleteMethod(testExecutionContext.getResourceUrl());
               break;
           default :
               throw new ApiAutomationException("Unsupported Http Method "+ testExecutionContext.getHttpMethod());

       }

       validateApiResponse(testExecutionContext, response);

   }

    private static void validateApiResponse(TestExecutionContext testExecutionContext, Response response) {
       log.info("validateApiResponse {}",response);

        response.then().assertThat().statusCode(testExecutionContext.getStatusCode());

        String responseStr = response.then().assertThat().extract().asString();

        log.info("validateApiResponse body{}",responseStr);

        switch(testExecutionContext.getResponseType()){
            case "String" :
                assertEquals(responseStr, testExecutionContext.getExpectedResponse());
                break;
            case "JSON" :
                assertEquals(JsonParser.parseString(responseStr), JsonParser.parseString(testExecutionContext.getExpectedResponse()));
                break;
            default :
                break;
        }

    }
}
