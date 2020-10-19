package com.restassured;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo07 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		int statusCode = response.getStatusCode();
		Assert.assertTrue(statusCode==200);
		String statusLine = response.getStatusLine();
		Assert.assertTrue(
				"Incorrect statusLine returned. \n Expected: \"HTTP/1.1 200 OK\" \n Actual: \""+statusLine+"\"",
				statusLine.equals("HTTP/1.1 200 OK"));
		String contentType = response.getContentType();
		Assert.assertTrue(
				"Incorrect contentType returned. \n Expected: \"application/json; charset=utf-8\" \n Actual: \""+contentType+"\"",
				contentType.toLowerCase().equals("application/json; charset=utf-8"));
		String responseBody = response.getBody().asString();
		Assert.assertTrue(
				"Response body does not contain \"lightning-rod\"",
				responseBody.contains("lightning-rod"));
		// Putting in conversion to lower case to ignore case
		Assert.assertTrue(
				"Response body does not contain \"lightning-rod\"",
				responseBody.toLowerCase().contains("lightning-rod"));
	}

}
