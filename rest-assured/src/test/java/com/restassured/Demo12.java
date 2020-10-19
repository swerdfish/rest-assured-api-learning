package com.restassured;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Demo12 {
	
	private final String placeHolderUrlPrefix = "https://jsonplaceholder.typicode.com";
	private final String accountInformationPath = System.getProperty("user.dir")+"\\src\\test\\resources\\account-information.xlsx";

	@Test
	public void test() throws IOException {
		RestAssured.baseURI = placeHolderUrlPrefix;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject();
		FileInputStream fis = new FileInputStream(accountInformationPath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		for (int i=sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
			long accountNumber = (long) sheet.getRow(i).getCell(0).getNumericCellValue();
			String accHolderName = sheet.getRow(i).getCell(1).getStringCellValue();
			requestParams.put("accountNumber", accountNumber);
			requestParams.put("accountHolderName", accHolderName);
			request.body(requestParams.toString());
			Response response = request.post("/posts");
			assertTrue(
					"Entry does not hold account number "+accountNumber,
					response.getBody().asString().contains(""+accountNumber)
			);
			assertTrue(
					"Entry does not hold account holder name "+accHolderName,
					response.getBody().asString().contains(accHolderName)
			);
		}
	}

}
