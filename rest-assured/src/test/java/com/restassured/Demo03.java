package com.restassured;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo03 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		String id = response.getSessionId();
		System.out.println("Session ID: "+id);
		String contentType = response.getContentType();
		System.out.println("Content-Type value: "+contentType);
	}

}
