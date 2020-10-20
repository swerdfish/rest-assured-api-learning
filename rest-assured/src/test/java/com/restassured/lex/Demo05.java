package com.restassured.lex;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Demo05 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		Headers allHeaders = response.getHeaders();
		for (Header header : allHeaders) {
			System.out.println("Key: "+header.getName()+" Value: "+header.getValue());
		}
	}

}
