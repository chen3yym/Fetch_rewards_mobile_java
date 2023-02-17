package edu.gatech.seclass.fetch_rewards_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileReader;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        ArrayList<JSONObject> users = (ArrayList<JSONObject>) fetchData();
        init(users);

    }



    public ArrayList<JSONObject> fetchData() {
        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader("https://fetch-hiring.s3.amazonaws.com/hiring.json"));


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonLineItem = (JSONObject) array.get(i);
                if (jsonLineItem.get("name") != null && jsonLineItem.get("listId") != null){
                    list.add((JSONObject) array.get(i));
                }
            }



//            list.sort((JSONObject o1, JSONObject o2) -> {
//                try {
//                    return o1.getInt("listId") - o2.getInt("listId");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return 0;
//            });

//            for (JSONObject obj : list) {
//                System.out.println(((JSONObject) obj.get("attributes")));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }


    private TableRow PrepareTableHeader(){
        TableRow header = new TableRow(this);
        TextView tvListIdHeader = new TextView(this);
        TextView tvNameHeader = new TextView(this);
        tvListIdHeader.setTypeface(null, Typeface.BOLD);
        TextView tvCheckboxHeader = new TextView(this);
        TextView tvIdHeader = new TextView(this);
        tvIdHeader.setTypeface(null, Typeface.BOLD);
        tvCheckboxHeader.setText("");
        tvNameHeader.setText("Name");
        tvNameHeader.setTypeface(null, Typeface.BOLD);
        tvIdHeader.setText("Id");
        tvListIdHeader.setText("ListId");
        header.addView(tvCheckboxHeader);
        header.addView(tvListIdHeader);
        header.addView(tvNameHeader);
        header.addView(tvIdHeader);
        header.setBackgroundColor(Color.parseColor("#f0f0f0"));
        header.setWeightSum(500);
        return header;
    }



    public void init( ArrayList<JSONObject> list) {
        TableLayout stk = (TableLayout) findViewById(R.id.tableLayoutForUserListingPage);
        TableRow tableHeader = PrepareTableHeader();
        stk.addView(tableHeader);
        for (JSONObject j : list){
            TableRow tr = new TableRow(this);
            TextView tvListId = new TextView(this);
            TextView tvName = new TextView(this);
            TextView tvId = new TextView(this);
            tvListId.setText((Integer) j.get("listId"));
            tvName.setText((Integer) j.get("name"));
            tvId.setText((Integer) j.get("id"));
            tr.addView(tvListId);
            tr.addView(tvName);
            tr.addView(tvId);
            stk.addView(tr);

        }
    }




}