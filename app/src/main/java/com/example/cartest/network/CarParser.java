package com.example.cartest.network;

import android.util.Log;

import com.example.cartest.model.Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarParser {
    public static List<Car> fromJson(String json) {
        List<Car> cars = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject garage = root.getJSONObject("garageInfo");
            JSONArray array = garage.getJSONArray("carsDetails");

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONObject carObject = object.getJSONObject("car");

                int id = carObject.getInt("id");
                String brand = carObject.getString("brand");
                String model = carObject.getString("model");
                int year = carObject.getInt("fabricationYear");

                Car car = new Car(id, brand, model, year);
                cars.add(car);
            }
            return cars;
        } catch (JSONException ex) {
            Log.e("JSONParse", "Error when parsing the json file");
        }
        return new ArrayList<>();
    }
}
