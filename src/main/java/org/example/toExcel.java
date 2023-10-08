package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
//    public static void jsonToExcel(JSONObject data) {

public class toExcel {
    public static void jsonToExcel() {
        try {
            // Membaca data dari file JSON
            FileReader myResult = new FileReader("ResultsJSON.json");
            JSONParser parser = new JSONParser();
            JSONArray resultJson = (JSONArray) parser.parse(myResult);

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Bebas");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("region_name");
            header.createCell(1).setCellValue("location");
            int rowNum = 1;
            int colNum = 0;
            for (Object item : resultJson) {
                Row bodyRow = sheet.createRow(rowNum++);
                Cell nameCell = bodyRow.createCell(colNum++);
                Cell idCell = bodyRow.createCell(colNum++);
                nameCell.setCellValue(((JSONObject)item).get("nama").toString());
                idCell.setCellValue(((JSONObject)item).get("id").toString());
                colNum = 0;
            }

            FileOutputStream outputStream = new FileOutputStream("ResultsJSON.xlsx");
            wb.write(outputStream);
            wb.close();
            System.out.println("--- Excel file generated ---");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
