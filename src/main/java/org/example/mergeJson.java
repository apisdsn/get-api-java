package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class mergeJson {
    public static void main(String[] args) throws IOException, ParseException {
        JSONArray dataProvince = bacaFile("province.json");
        JSONArray dataCity = bacaFile("city.json");
        JSONArray dataDistrict = bacaFile("district.json");
        JSONArray dataSubDistrict = bacaFile("subdistrict.json");

        JSONArray gabungan = gabungkanData(dataProvince, dataCity, dataDistrict, dataSubDistrict);
        sortJSONByProvinceID(gabungan);
        simpanKeFile(gabungan, "result.json");

        System.out.println("Pengolahan data selesai. Hasil disimpan dalam file result.json");
    }
    public static void sortJSONByProvinceID(JSONArray jsonArray) {
        List<JSONObject> jsonList = new ArrayList<>();
        for (Object obj : jsonArray) {
            jsonList.add((JSONObject) obj);
        }

        Collections.sort(jsonList, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String id1 = (String) o1.get("id");
                String id2 = (String) o2.get("id");
                return id1.compareTo(id2);
            }
        });

        jsonArray.clear();
        jsonArray.addAll(jsonList);
    }
    public static JSONArray bacaFile(String namaFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try (FileReader fileReader = new FileReader(namaFile)) {
            return (JSONArray) parser.parse(fileReader);
        }
    }

    public static JSONArray gabungkanData(JSONArray dataProvince, JSONArray dataCity, JSONArray dataDistrict, JSONArray dataSubDistrict) {
        Map<String, JSONObject> provinceMap = new HashMap<>();
        Map<String, JSONObject> cityMap = new HashMap<>();
        Map<String, JSONObject> districtMap = new HashMap<>();

        for (Object dataP : dataProvince) {
            JSONObject province = (JSONObject) dataP;
            String idProvince = province.get("id").toString().substring(0, 2);
            provinceMap.put(idProvince, province);
            province.put("nama", province.get("nama"));
            province.put("id", province.get("id"));
            province.put("city", new JSONArray());
        }

        for (Object dataC : dataCity) {
            JSONObject city = (JSONObject) dataC;
            String idCity = city.get("id").toString().substring(0, 4);
            cityMap.put(idCity, city);
            city.put("nama", city.get("nama"));
            city.put("id", city.get("id"));
            city.put("district", new JSONArray());

            String idProvince = idCity.substring(0, 2);
            JSONObject province = provinceMap.get(idProvince);
            if (province != null) {
                JSONArray cityArray = (JSONArray) province.get("city");
                cityArray.add(city);
            }
        }

        for (Object dataD : dataDistrict) {
            JSONObject district = (JSONObject) dataD;
            String idDistrict = district.get("id").toString().substring(0, 6);
            districtMap.put(idDistrict, district);
            district.put("nama", district.get("nama"));
            district.put("id", district.get("id"));
            district.put("sub_district", new JSONArray());

            String idCity = idDistrict.substring(0, 4);
            JSONObject city = cityMap.get(idCity);
            if (city != null) {
                JSONArray districtArray = (JSONArray) city.get("district");
                districtArray.add(district);
            }
        }

        for (Object dataSD : dataSubDistrict) {
            JSONObject subDistrict = (JSONObject) dataSD;
            String idSubDistrict = subDistrict.get("id").toString().substring(0, 8);

            String idDistrict = idSubDistrict.substring(0, 6);
            JSONObject district = districtMap.get(idDistrict);
            if (district != null) {
                JSONArray subDistrictArray = (JSONArray) district.get("sub_district");
                subDistrictArray.add(subDistrict);
            }
        }

        JSONArray gabungan = new JSONArray();
        for (JSONObject province : provinceMap.values()) {
            gabungan.add(province);
        }

        return gabungan;
    }

    public static void simpanKeFile(JSONArray data, String namaFile) throws IOException {
        try (FileWriter fileWriter = new FileWriter(namaFile)) {
            fileWriter.write(data.toJSONString());
        }
    }
}