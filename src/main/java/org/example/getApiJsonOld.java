package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class getApiJsonOld {
    public static void main(String[] args) throws Exception{

        JSONObject resultsJson = new JSONObject();
        JSONArray resArray = new JSONArray();


        URL urlApi = new URL("http://192.168.20.223:8979/location-api/province");
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


                in.close();
            }
            con.disconnect();
        }
        try {
            FileWriter myWriter = new FileWriter("province.json");
            myWriter.write(resArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file province.json");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        resultsJson.put("data", resArray);
        getApiCity(resultsJson);
    }
    public static void getApiCity(JSONObject city) throws Exception {


        JSONObject resultsJson = new JSONObject();
        JSONArray resArray = new JSONArray();

        JSONArray datas = (JSONArray) city.get("data");
        for (Object item : datas) {
            URL urlApi = new URL("http://192.168.20.223:8979/location-api/city?province_id="+((JSONObject)item).get("id"));
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
                for (Object items : resdata) {
                    JSONObject resItem = ((JSONObject) items);
                    resArray.add(resItem);
                }

                in.close();
            }
            con.disconnect();
        }
        try {
            FileWriter myWriter = new FileWriter("city.json");
            myWriter.write(resArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file city.json");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        resultsJson.put("data", resArray);
        getApiDistrict(resultsJson);
    }
    public static void getApiDistrict(JSONObject district) throws Exception {


        JSONObject resultsJson = new JSONObject();
        JSONArray resArray = new JSONArray();

        JSONArray datas = (JSONArray) district.get("data");
        for (Object item : datas) {
            URL urlApi = new URL("http://192.168.20.223:8979/location-api/district?city_id=" + ((JSONObject) item).get("id"));
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
                for (Object items : resdata) {
                    JSONObject resItem = ((JSONObject) items);
                    resArray.add(resItem);
                }

                in.close();
            }
            con.disconnect();
        }
        try {
            FileWriter myWriter = new FileWriter("district.json");
            myWriter.write(resArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file district.json");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        resultsJson.put("data", resArray);
        getApiSubDistrict(resultsJson);
    }
    public static void getApiSubDistrict(JSONObject subDistrict) throws Exception {


        JSONObject resultsJson = new JSONObject();
        JSONArray resArray = new JSONArray();

        JSONArray datas = (JSONArray) subDistrict.get("data");
        for (Object item : datas) {
            URL urlApi = new URL("http://192.168.20.223:8979/location-api/sub-district?district_id=" + ((JSONObject) item).get("id"));
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
                for (Object items : resdata) {
                    JSONObject resItem = ((JSONObject) items);
                    resArray.add(resItem);
                }

                in.close();
            }
            con.disconnect();
        }
        try {
            FileWriter myWriter = new FileWriter("sub_district.json");
            myWriter.write(resArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file sub_district.json");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        resultsJson.put("data", resArray);

    }
}

