package com.restassured.lex;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo04 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: "+statusCode);
		String statusLine = response.getStatusLine();
		System.out.println("Status Line: "+statusLine);
	}

}
