package com.restassured.mentor;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class ToolsQAVariations_csv {
	
	private static final String urlPrefix = "https://jsonplaceholder.typicode.com";

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = urlPrefix;
	}
	
	@Test
	@FileParameters("src\\test\\resources\\fake-data-csv.csv")
	public void csvSourcePut(int id, String firstName, String lastName, String username, String password) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", id);
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", lastName);
		requestParams.put("username", username);
		requestParams.put("password", password);
		request.body(requestParams.toString());

		Response response = request.put("/posts/"+id);
		Assert.assertTrue(response.statusCode()==200 || response.statusCode()==201);
		Assert.assertTrue(response.getBody().asString().contains(""+id));
		Assert.assertTrue(response.getBody().asString().contains(firstName));
		Assert.assertTrue(response.getBody().asString().contains(lastName));
		Assert.assertTrue(response.getBody().asString().contains(username));
		Assert.assertTrue(response.getBody().asString().contains(password));
	}
	
	@Test
	@FileParameters("src\\test\\resources\\fake-registration-csv.csv")
	public void csvSourcePost(int id, String firstName, String lastName, String username, String password) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", id);
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", lastName);
		requestParams.put("username", username);
		requestParams.put("password", password);
		request.body(requestParams.toString());

		Response response = request.post("/posts");
		Assert.assertTrue(response.statusCode()==200 || response.statusCode()==201);
		// The id field is overwritten by jsonplaceholder
//		Assert.assertTrue(response.getBody().asString().contains(""+id));
		Assert.assertTrue(response.getBody().asString().contains(firstName));
		Assert.assertTrue(response.getBody().asString().contains(lastName));
		Assert.assertTrue(response.getBody().asString().contains(username));
		Assert.assertTrue(response.getBody().asString().contains(password));
	}

}
