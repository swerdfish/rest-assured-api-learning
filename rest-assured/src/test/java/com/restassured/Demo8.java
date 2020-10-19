package com.restassured;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Demo8 {
	
	private final String pokeUrlPrefix = "https://pokeapi.co/api/v2/";
	private final String pokemonGen1Path = System.getProperty("user.dir")+"\\src\\test\\resources\\pokemon-gen1.xlsx";

	@Test
	public void test() throws IOException {
		FileInputStream fis = new FileInputStream(pokemonGen1Path);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		// the first row is the header row
		for (int i=sheet.getFirstRowNum()+1; i<=sheet.getLastRowNum(); i++) {
			String pokemonName = sheet.getRow(i).getCell(0).getStringCellValue().toLowerCase();
			Response response = RestAssured.get(pokeUrlPrefix+"pokemon/"+pokemonName);
			Assert.assertTrue(
					"Response for "+pokemonName+" does not contain "+pokemonName,
					response.getBody().asString().toLowerCase().contains(pokemonName));
		}
		wb.close();
	}
	
	

}
