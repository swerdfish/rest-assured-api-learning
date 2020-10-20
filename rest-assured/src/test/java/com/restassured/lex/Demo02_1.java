package com.restassured.lex;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo02_1 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";
	
	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
	}

}
