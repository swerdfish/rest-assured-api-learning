package com.restassured.lex;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Demo11 {
	
	private final String placeHolderUrlPrefix = "https://jsonplaceholder.typicode.com";

	@Test
	public void test() {
		RestAssured.baseURI = placeHolderUrlPrefix;
		// Get the RequestSpecification of the request that you want to sent to the server
		// The server is specified by the BaseURI that we have specified in the above step
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject();
		// We can add key-Value pairs using put method
		requestParams.put("accountNumber", "443328602688019");
		requestParams.put("accountHolderName", "Martin Luther");
		requestParams.put("ifsc", "EDUB0000501");
		request.body(requestParams.toString());
		Response response = request.post("/posts");
		String respBody = response.getBody().asString();
		Assert.assertTrue("Response body does not contain id 101", respBody.contains("101"));
	}

}
