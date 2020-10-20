package com.restassured.lex;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo06 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		String contentType = response.header("Content-Type");
		System.out.println("Content-Type value"+contentType);
		String serverType = response.header("Server");
		System.out.println("Server value: "+serverType);
		String date = response.header("Date");
		System.out.println("Date value: "+date);
		String contentEnco = response.header("Content-Encoding");
		System.out.println("Content-Encoding: "+contentEnco);
		String transferEnco = response.header("Transfer-Encoding");
		System.out.println("Transfer-Encoding value: "+transferEnco);
	}

}
