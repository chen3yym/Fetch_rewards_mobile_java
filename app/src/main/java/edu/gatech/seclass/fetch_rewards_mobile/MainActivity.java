package edu.gatech.seclass.fetch_rewards_mobile;
import androidx.appcompat.app.AppCompatActivity;
import edu.gatech.seclass.fetch_rewards_mobile.FetchData;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Collections;
import java.util.Comparator;
public class MainActivity extends AppCompatActivity {

    String json = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<JSONObject> list = new ArrayList<>();

        try {
            list = fetchData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            init(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public ArrayList<JSONObject> fetchData() throws IOException {

        ArrayList<JSONObject> list = new ArrayList<>();
        String urlToFetch = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        JSONArray data = null;
        try {

            // run fetchdata class using asynctask, get the execute string data => json
            FetchData fd = new FetchData();
            json = fd.execute(urlToFetch).get();


            // filter null and empty name
            data = (JSONArray) new JSONParser().parse(json);
            for(int i = 0; i < data.size(); i ++) {
                JSONObject ob = (JSONObject) data.get(i);
                if(ob.get("name") != null && ob.get("id") != null && ob.get("listId") != null){
                    if (ob.get("name").equals("")){
                    }else{
                        list.add(ob);
                    }
                }
            }


            // Sort arraylist with two fields in jsonobject
            Collections.sort(list, new Comparator<JSONObject>() {
                private static final String KEY_NAME1= "listId";
                private static final String KEY_NAME2 = "name";
                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String a1 = "";
                    String b1 = "";
                    a1 = a.get(KEY_NAME1).toString();
                    b1 = b.get(KEY_NAME1).toString();
                    String a2 = "";
                    String b2 = "";
                    a2 = a.get(KEY_NAME2).toString();
                    b2 = b.get(KEY_NAME2).toString();
                    if (a1.compareTo(b1) == 0) {
                        return a2.compareTo(b2);
                    } else {
                        return a1.compareTo(b1);
                    }
                }
            });
            return list;
        } catch (Exception e) {
            // error check tools
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            json =  sw.toString();
        }

        return null;

    }

// add table header
    private TableRow PrepareTableHeader(){

        TableRow header = new TableRow(this);
        TextView tvListIdHeader = new TextView(this);
        TextView tvNameHeader = new TextView(this);
        tvListIdHeader.setTypeface(null, Typeface.BOLD);
        TextView tvIdHeader = new TextView(this);
        tvIdHeader.setTypeface(null, Typeface.BOLD);
        tvNameHeader.setText("Name");
        tvNameHeader.setTypeface(null, Typeface.BOLD);
        tvIdHeader.setText("Id");
        tvListIdHeader.setText("ListId");
        header.addView(tvListIdHeader);
        header.addView(tvNameHeader);
        header.addView(tvIdHeader);
        header.setBackgroundColor(Color.parseColor("#f0f0f0"));
        header.setWeightSum(500);
        return header;
    }



// add table view which includes the arraylist data
    public void init(ArrayList<JSONObject> list) throws JSONException {

        ArrayList<JSONObject> temp = new ArrayList<>();
        TableLayout stk = (TableLayout) findViewById(R.id.tableLayoutForUserListingPage);
        TableRow tableHeader = PrepareTableHeader();
        stk.addView(tableHeader);


        for (JSONObject j: list){
            TableRow tr = new TableRow(this);
            TextView tvListId = new TextView(this);
            TextView tvName = new TextView(this);
            TextView tvId = new TextView(this);
            tvListId.setText(j.get("listId").toString());
            tvName.setText(j.get("name").toString());
            tvId.setText(j.get("id").toString());
            tr.addView(tvListId);
            tr.addView(tvName);
            tr.addView(tvId);
            stk.addView(tr);
        }
    }




}

