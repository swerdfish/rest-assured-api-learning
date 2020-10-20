package com.restassured.mentor;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ToolsQAVariations {
	
	private static final String urlPrefix = "https://jsonplaceholder.typicode.com";
	private final String testResourcesPath = System.getProperty("user.dir")+"\\src\\test\\resources";
	private XSSFWorkbook wb;
	
	private static String getStringValue(XSSFCell cell) {
		return getStringValue(cell, cell.getCellTypeEnum(), false);
	}
	
	private static String getStringValue(XSSFCell cell, CellType cellType, boolean recursed) {
		switch(cellType) {
		case BLANK:
		case STRING:
			return cell.getStringCellValue();
		case BOOLEAN:
			return ""+cell.getBooleanCellValue();
		case ERROR:
			return cell.getErrorCellString();
		case FORMULA:
			if (recursed) return "";
			else return getStringValue(cell, cell.getCachedFormulaResultTypeEnum(), true);
		case NUMERIC:
			return ""+cell.getNumericCellValue();
		case _NONE:
		default:
			return "";
		}
	}

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = urlPrefix;
	}

	@Test
	@Parameters({
		"fake-jsonPlaceholder-data.xlsx", "fake-registration-data_1.xlsx", "fake-registration-data_2.xlsx"
	})
	public void post(String filename) throws IOException {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		JSONObject requestParams = new JSONObject();
		
		FileInputStream fis = new FileInputStream(testResourcesPath+"\\"+filename);
		wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow headerRow = sheet.getRow(sheet.getFirstRowNum());
		
		for (int i=sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
			for (int j=headerRow.getFirstCellNum()+1; j<headerRow.getLastCellNum(); j++) {
				XSSFCell cell = sheet.getRow(i).getCell(j);
				requestParams.put(headerRow.getCell(j).getStringCellValue(), getStringValue(cell));
			}
			request.body(requestParams.toString());
			Response response = request.post("/posts");
			Assert.assertTrue(response.statusCode()==200 || response.statusCode()==201);
			for (int j=headerRow.getFirstCellNum()+1; j<headerRow.getLastCellNum(); j++) {
				XSSFCell cell = sheet.getRow(i).getCell(j);
				Assert.assertTrue(
						"Response does not contain expected "+headerRow.getCell(j).getStringCellValue()+" value: "
								+getStringValue(cell),
						response.getBody().asString().contains(getStringValue(cell))
				);
			}
		}
	}
	
	@Test
	@Parameters({
		"fake-jsonPlaceholder-data.xlsx", "fake-registration-data_1.xlsx", "fake-registration-data_2.xlsx"
	})
	public void put(String filename) throws IOException {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		JSONObject requestParams = new JSONObject();
		
		FileInputStream fis = new FileInputStream(testResourcesPath+"\\"+filename);
		wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow headerRow = sheet.getRow(sheet.getFirstRowNum());
		
		for (int i=sheet.getFirstRowNum()+1; i<=sheet.getLastRowNum(); i++) {
			for (int j=headerRow.getFirstCellNum(); j<headerRow.getLastCellNum(); j++) {
				XSSFCell cell = sheet.getRow(i).getCell(j);
				requestParams.put(headerRow.getCell(j).getStringCellValue(), getStringValue(cell));
			}
			request.body(requestParams.toString());
			Response response = request.put("/posts/"+(int) sheet.getRow(i).getCell(0).getNumericCellValue());
			Assert.assertTrue(response.statusCode()==200);
			for (int j=headerRow.getFirstCellNum()+1; j<headerRow.getLastCellNum(); j++) {
				XSSFCell cell = sheet.getRow(i).getCell(j);
				Assert.assertTrue(
						"Response does not contain expected "+headerRow.getCell(j).getStringCellValue()+" value: "+getStringValue(cell),
						response.getBody().asString().contains(getStringValue(cell))
				);
			}
		}
	}
	
	@After
	public void close() throws IOException {
		wb.close();
	}

}
