package com.restassured;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Demo2_2 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";
	
	@Test
	public void test() {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = pokeUrlPrefix;
		// Get the RequestSpecification of the request that you want to send to the server.
		// The server is specified by the baseURI that we have specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();
		// Make a request to the server by specifying the method Type and the method URL.
		// This will return the Response from the server. Store the response in a variable.
		Response response = httpRequest.request(Method.GET, "/pokemon/pikachu");
		// print the message body of the response received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => "+ responseBody);
	}

}
