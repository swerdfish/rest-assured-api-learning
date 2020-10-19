package com.restassured;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Demo09 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";

	@Test
	public void test() {
		Response response = RestAssured.get(pokeUrlPrefix+"pokemon/pikachu");
		JsonPath jsonPathEvaluator = response.jsonPath();
		String lae = jsonPathEvaluator.getString("location_area_encounters");
		System.out.println("Location area encounters: "+lae);
		Assert.assertTrue(lae.equals("https://pokeapi.co/api/v2/pokemon/25/encounters"));
	}

}
