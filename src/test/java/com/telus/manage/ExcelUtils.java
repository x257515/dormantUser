package com.telus.manage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static String sheetPath = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\TestData.xlsx";
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	public static FileInputStream file = null;
	private static XSSFRow row;
	private static XSSFCell cell;

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {

			file = new FileInputStream(sheetPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			workbook = new XSSFWorkbook(file);

			sheet = workbook.getSheet(sheetName);
			// workbook = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sheet = workbook.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}

	public static ArrayList<Object[]> getDataFromExcel(){
			
			ArrayList<Object[]> myData = new ArrayList<Object[]>();
			try {
				
					file = new FileInputStream(sheetPath);
					workbook = new XSSFWorkbook(file);
					sheet = workbook.getSheetAt(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			for(int rowNum=2; rowNum<= getRowCount("ReadExcel"); rowNum++) {
				String userName = getCellData("ReadExcel", "Username", rowNum);
				String password = getCellData("ReadExcel", "Password", rowNum);
				
				Object ob[] = {userName,password};
				myData.add(ob);
			}
			
			return myData;
		}

	public static int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	public static String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			row = sheet.getRow(rowNum - 1);
			cell = row.getCell(col_Num);
			return cell.getStringCellValue();
		}

		catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}
}
