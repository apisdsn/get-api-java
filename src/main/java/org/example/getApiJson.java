package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class getApiJson {
    public static void main(String[] args) throws Exception{
        JSONArray provinces = getApiData("http://192.168.20.223:8979/location-api/province", "province.json");
        JSONArray cities = getApiDataWithId(provinces, "id", "http://192.168.20.223:8979/location-api/city?province_id=", "city.json");
        JSONArray districts = getApiDataWithId(cities, "id", "http://192.168.20.223:8979/location-api/district?city_id=", "district.json");
        getApiDataWithId(districts, "id", "http://192.168.20.223:8979/location-api/sub-district?district_id=", "sub_district.json");
    }

    public static JSONArray getApiData(String apiUrl, String fileName) throws Exception {
        JSONArray resArray = new JSONArray();

        URL urlApi = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) urlApi.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() == 200) {
            JSONParser parser = new JSONParser();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                JSONObject data = (JSONObject) parser.parse(content.toString());

                JSONArray resdata = (JSONArray) data.get("data");
                for (Object item : resdata) {
                    JSONObject resItem = (JSONObject) item;
                    resArray.add(resItem);
                }
            }

            con.disconnect();
        }

        makeFile(fileName, resArray);
        return resArray;
    }


    public static JSONArray getApiDataWithId(JSONArray data, String idKey, String apiUrl, String fileName) throws Exception {
        JSONArray resArray = new JSONArray();

        for (Object item : data) {
            String id = ((JSONObject) item).get(idKey).toString();
            URL urlApi = new URL(apiUrl + id);
            HttpURLConnection con = (HttpURLConnection) urlApi.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                JSONParser parser = new JSONParser();

                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    JSONObject responseData = (JSONObject) parser.parse(content.toString());

                    JSONArray resDataArray = (JSONArray) responseData.get("data");
                    for (Object resItem : resDataArray) {
                        JSONObject resJsonObject = (JSONObject) resItem;
                        resArray.add(resJsonObject);
                    }
                }

                con.disconnect();
            }
        }

        makeFile(fileName, resArray);
        return resArray;
    }
    public static void makeFile(String fileName, JSONArray resArray) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(resArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
