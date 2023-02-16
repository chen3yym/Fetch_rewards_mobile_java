package edu.gatech.seclass.fetch_rewards_mobile.Database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Collections;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.jar.JarInputStream;

public class User {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader("https://fetch-hiring.s3.amazonaws.com/hiring.json"));
            ArrayList<JSONObject> list = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                list.add((JSONObject) array.get(i));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


class MyJSONComparator implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject o1, JSONObject o2) {

    }
}