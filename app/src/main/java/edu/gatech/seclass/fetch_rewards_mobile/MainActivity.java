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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;

import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
//            ArrayList<JSONObject> temp = new ArrayList<>();
//
//            JSONObject json = new JSONObject();
//            json.put("fromZIPCode","123456");
//
//            JSONObject json1 = new JSONObject();
//            JSONObject fromZIPCode = json1.put("fromZIPCode", "123456");
//            temp.add(json1);
//            temp.add(json);

            init(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public ArrayList<JSONObject> fetchData() throws IOException {
//
        ArrayList<JSONObject> list = new ArrayList<>();
//        String urlToFetch = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        JSONArray data = null;
//        InputStream iStream = null;
//        JSONObject jsonObject;
//        HttpURLConnection urlConnection = null;
        try {
//
//            URL url = new URL(urlToFetch);
//            urlConnection =(HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
////            urlConnection.setDoInput(true);
//            urlConnection.setConnectTimeout(100000000);
//            urlConnection.setReadTimeout(100000000);
//            urlConnection.connect();
//            iStream = urlConnection.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//            StringBuilder sb = new StringBuilder();
////
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            br.close();
            FetchData fd = new FetchData();
            json = fd.execute("https://fetch-hiring.s3.amazonaws.com/hiring.json").get();

            data = (JSONArray) new JSONParser().parse(json);
            for(int i = 0; i < data.size(); i ++) {
                JSONObject ob = (JSONObject) data.get(i);
                if(ob.get("name") != null && ob.get("id") != null && ob.get("listId") != null){
                list.add(ob);}
            }
            return list;


//            data = (JSONArray) new JSONTokener(IOUtils.toString(new URL(urlToFetch).openStream(), Charset.forName("UTF-8"))).nextValue();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            json =  sw.toString();
        }

        return null;

    }




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



    public void init(ArrayList<JSONObject> list) throws JSONException {

        ArrayList<JSONObject> temp = new ArrayList<>();
        TableLayout stk = (TableLayout) findViewById(R.id.tableLayoutForUserListingPage);
        TableRow tableHeader = PrepareTableHeader();
        stk.addView(tableHeader);
//        JSONObject json = new JSONObject();
//        json.put("fromZIPCode","123456");
//
//        JSONObject json1 = new JSONObject();
//        json1.put("fromZIPCode", "123456");
//        temp.add(json1);


        for (JSONObject j: list){
            TableRow tr = new TableRow(this);
            TextView tvListId = new TextView(this);
            TextView tvName = new TextView(this);
            TextView tvId = new TextView(this);
            tvListId.setText(j.get("listId").toString());
            tvName.setText(j.get("name").toString());
//            tvName.setText(list.get(0).get("id").toString());
            tvId.setText(j.get("id").toString());
            tr.addView(tvListId);
            tr.addView(tvName);
            tr.addView(tvId);
            stk.addView(tr);
        }
    }




}

