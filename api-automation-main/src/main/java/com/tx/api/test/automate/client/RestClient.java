package com.tx.api.test.automate.client;

import com.tx.api.test.automate.utils.FileResolver;
import com.tx.api.test.automate.utils.UserContext;
import com.tx.api.test.automate.utils.UserContextManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

@Slf4j
public class RestClient {

	private final RequestSpecBuilder requestSpecBuilder;

	private final String baseURI;

	private final UserContext userContext;


	public RestClient(String baseURI,UserContext userContext){
		requestSpecBuilder = new RequestSpecBuilder();
		this.userContext = userContext;
		this.baseURI=baseURI;
    }

	private void setRequestContentType(String contentType) {
		switch (contentType) {
		case "JSON":
			log.info("******* Content Type is JSON *******");
			requestSpecBuilder.setContentType(ContentType.JSON);
			break;
		case "MULTIPART":
			log.info("******* Content Type is MULTIPART *******");
			requestSpecBuilder.setContentType(ContentType.MULTIPART);
			break;
		default:
			log.info("******* Content Type {} is passed *******",contentType);
			throw new RuntimeException("Please pass the correct Content Type");
		}
	}

	private String getBasicAuthenticationEncodedCredentials() {
		String valueToEncode = userContext.getBasicAuth().getUsername() + ":" +  userContext.getBasicAuth().getPassword();
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}

	private void addAuthHeader() {
       switch (userContext.getAuthType()){
		   case "credentials":
			   String auth = getBasicAuthenticationEncodedCredentials();
			   if(log.isDebugEnabled())
			       log.debug("Auth token {}", auth);
			   requestSpecBuilder.addHeader("Authorization", auth);
			   break;
	   }
	}

	private RequestSpecification createRequestSpecification() {
		return createRequestSpecification(null,null,null,null,null);
	}

	private RequestSpecification createRequestSpecification(Map<String, String> headersMap) {
		return createRequestSpecification(null,null,headersMap,null,null);
	}
	
	private RequestSpecification createRequestSpecificationWithathParms(Map<String, String> pathParamsMap) {
		return createRequestSpecification(null,null,null,null,pathParamsMap);
	}
	

	private RequestSpecification createRequestSpecification(Map<String, String> headersMap,
			Map<String, Object> queryParams) {
		return createRequestSpecification(null,null,headersMap,queryParams,null);
	}

	private RequestSpecification createRequestSpecification(Object requestBody, String contentType) {
		return createRequestSpecification(requestBody,contentType,null,null,null);
	}

	private RequestSpecification createRequestSpecification(Object requestBody, String contentType, Map<String, String> headersMap) {
		return createRequestSpecification(requestBody,contentType,headersMap,null,null);
	}

	private RequestSpecification createRequestSpecification(Object requestBody, String contentType,
															Map<String, String> headersMap,Map<String, Object> queryParams) {
		return createRequestSpecification(requestBody,contentType,headersMap,queryParams,null);
	}

	private RequestSpecification createRequestSpecification(Object requestBody, String contentType,	Map<String, String> headersMap,
															Map<String, Object> queryParams,Map<String, String> pathParamsMap) {
		requestSpecBuilder.setBaseUri(baseURI);

		if (ObjectUtils.isNotEmpty(contentType)) {
			setRequestContentType(contentType);
		}

		if(userContext.isAuthEnabled()) {
			addAuthHeader();
		}
		if (!CollectionUtils.sizeIsEmpty(queryParams)) {
			requestSpecBuilder.addQueryParams(queryParams);
		}
		if (!CollectionUtils.sizeIsEmpty(headersMap)) {
			requestSpecBuilder.addHeaders(headersMap);
		}
		if (ObjectUtils.isNotEmpty(requestBody)) {
			requestSpecBuilder.setBody(requestBody);
		}
		if (!CollectionUtils.sizeIsEmpty(pathParamsMap )) {
			requestSpecBuilder.addPathParams(pathParamsMap);
		}
		return requestSpecBuilder.build();
	}

	public Response getMethod(String serviceURL ) {
		return getMethod(createRequestSpecification(),serviceURL);
	}

	public Response getMethod(String serviceURL, Map<String, String> headersMap) {
		return getMethod(createRequestSpecification(headersMap),serviceURL);
	}

	public Response getMethod(String serviceURL, Map<String, String> headersMap, Map<String, Object> queryParams) {
		return getMethod(createRequestSpecification(headersMap, queryParams),serviceURL);
	}	
	
	public Response getMethodWithPathParams(String serviceURL, Map<String, String> headersMap, Map<String, String> pathParamsMap) {
		return getMethod(createRequestSpecificationWithathParms(pathParamsMap),serviceURL);
	}

	private Response getMethod(RequestSpecification requestSpecification,String serviceURL){
		if (userContext.isTraceLogEnabled()) {
			return given().spec(requestSpecification).log().all().when()
					.get(serviceURL);
		}
		return given().spec(requestSpecification).when().get(serviceURL);
	}

	public Response postMethod(String serviceURL, Object requestBody, String contentType,
			Map<String, String> headersMap) {
		return postMethod(createRequestSpecification(requestBody, contentType, headersMap),serviceURL);
	}

	public Response postMethod(String serviceURL, String requestBody, String contentType, Map<String, String> headers,
							   Map<String, Object> queryParams) {
		return postMethod(createRequestSpecification(requestBody, contentType, headers,queryParams),serviceURL);
	}

	public Response postMethod(String serviceURL, Object requestBody, String contentType) {
		return postMethod(createRequestSpecification(requestBody, contentType),serviceURL);
	}

	private Response postMethod(RequestSpecification requestSpecification,String serviceURL){
		if (userContext.isTraceLogEnabled()) {
			return given().spec(requestSpecification).log().all().when()
					.post(serviceURL);
		}
		return given().spec(requestSpecification).when().post(serviceURL);
	}

	public Response putMethod(String serviceURL, Object requestBody, String contentType, Map<String, String> headersMap) {
		return putMethod(createRequestSpecification(requestBody, contentType, headersMap),serviceURL);
	}

	public Response putMethod(String serviceURL, Object requestBody, String contentType, Map<String, String> headersMap,
							  Map<String, Object> queryParams) {
		return  putMethod(createRequestSpecification(requestBody, contentType, headersMap,queryParams),serviceURL);
	}

	public Response putMethod(String serviceURL, Object requestBody, String contentType) {
		return putMethod(createRequestSpecification(requestBody, contentType),serviceURL);
	}

	private Response putMethod(RequestSpecification requestSpecification,String serviceURL){
		if (userContext.isTraceLogEnabled()) {
			return given().spec(requestSpecification).log().all().when()
					.put(serviceURL);
		}
		return given().spec(requestSpecification).when().put(serviceURL);
	}

	public Response patchMethod(String serviceURL, Object requestBody, String contentType,
			Map<String, String> headersMap) {
		return patchMethod(createRequestSpecification(requestBody, contentType, headersMap),serviceURL);
	}

	public Response patchMethod(String serviceURL,Object requestBody, String contentType) {
		return patchMethod(createRequestSpecification(requestBody,contentType),serviceURL);
	}

	private Response patchMethod(RequestSpecification requestSpecification,String serviceURL){
		if (userContext.isTraceLogEnabled()) {
			return given().spec(requestSpecification).log().all().when()
					.patch(serviceURL);
		}
		return given().spec(requestSpecification).when().patch(serviceURL);
	}
		
    public Response deleteMethod(String serviceURL) {
		if (userContext.isTraceLogEnabled()) {
				return given().spec(createRequestSpecification()).log().all()
				.when().delete(serviceURL);
			}
		return given().spec(createRequestSpecification()).when().delete(serviceURL);
	}
    public Response postMultipartMethod(String serviceURL,String filePath,Map<String,String> headersMap){
        try {
            File fileToUpload= FileResolver.resolve(filePath);
            RequestSpecification requestSpecification = given()
                    .baseUri(this.baseURI)
                    .headers(headersMap)
                    .multiPart("file",new File(filePath));
            if(userContext.isTraceLogEnabled()){
                return requestSpecification.log().all().when().post(serviceURL);
            }

            return given().spec(requestSpecification).when().post(serviceURL);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to reolve or upload file: "+ filePath , e);
        }


    }
}