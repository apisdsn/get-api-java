package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class getApiWriteToExcel {
    public static void main(String[] args) throws Exception {
        int[] ids = {
                7301, 7302, 7303, 7304, 7305, 7306, 7307, 7308, 7309, 7310, 7311, 7312,
                7313, 7314, 7315, 7316, 7317, 7318, 7322, 7324, 7326, 7371, 7372, 7373,
                7401, 7402, 7403, 7404, 7405, 7406, 7407, 7408, 7409, 7410, 7411, 7412,
                7413, 7414, 7415, 7471, 7472, 7501, 7502, 7503, 7504, 7505, 7571, 7601,
                7602, 7603, 7604, 7605, 7606, 8101, 8102, 8103, 8104, 8105, 8106, 8107,
                8108, 8109, 8171, 8172, 8201, 8202, 8203, 8204, 8205, 8206, 8207, 8208,
                8271, 8272, 9103, 9106, 9110, 9111, 9115, 9119, 9120, 9171, 9202, 9203,
                9206, 9207, 9208, 9211, 9212, 9301, 9302, 9303, 9304, 9401, 9402, 9403,
                9404, 9405, 9406, 9407, 9408, 9501, 9502, 9503, 9504, 9505, 9506, 9507,
                9508, 9601, 9602, 9603, 9604, 9605, 9671
        };

        JSONObject resultsJson = new JSONObject();
        JSONArray resArray = new JSONArray();

        for (int id : ids) {
            URL urlApi = new URL("http://192.168.20.223:8979/location-api/district?city_id="+id);
            HttpURLConnection con = (HttpURLConnection) urlApi.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {

                JSONParser parser = new JSONParser();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                JSONObject data = (JSONObject) parser.parse(content.toString());


                JSONArray resdata = (JSONArray) data.get("data");
                for (Object item : resdata) {
                    JSONObject resItem = ((JSONObject) item);
                    resArray.add(resItem);
                    System.out.println(resItem);
                }

                in.close();
            }
            con.disconnect();
        }

        resultsJson.put("data", resArray);
        toExcel(resultsJson);
    }

    public static void toExcel(JSONObject data) {
        try {
            JSONArray datas = (JSONArray) data.get("data");

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Bebas");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("ID");

            int rowNum = 1;
            int colNum = 0;
            for (Object item : datas) {
                Row bodyRow = sheet.createRow(rowNum++);
                Cell nameCell = bodyRow.createCell(colNum++);
                Cell idCell = bodyRow.createCell(colNum++);
                nameCell.setCellValue(((JSONObject)item).get("nama").toString());
                idCell.setCellValue(((JSONObject)item).get("id").toString());
                colNum = 0;
            }

            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\EM-AthfalNajah\\Documents\\ResultsJSON.xlsx");
            wb.write(outputStream);
            wb.close();
            System.out.println("--- Excel file generated ---");

        }
        catch (Exception ex) {

        }
    }
}
