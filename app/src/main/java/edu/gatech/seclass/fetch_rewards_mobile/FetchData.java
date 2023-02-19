package edu.gatech.seclass.fetch_rewards_mobile;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
public class FetchData extends AsyncTask<String, String, String>
{
    String json = "";

    protected void onPreExecute() {

    }


    protected String doInBackground (String... params) {

        ArrayList<JSONObject> list = new ArrayList<>();
        String urlToFetch = params[0];
        JSONArray data = null;
        InputStream iStream = null;
        JSONObject jsonObject;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(urlToFetch);

            urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream())
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
//            urlConnection.setDoInput(true);
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            json = sb.toString();
            return json;


//          data = (JSONArray) new JSONTokener(IOUtils.toString(new URL(urlToFetch).openStream(), Charset.forName("UTF-8"))).nextValue();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            json =  sw.toString();
        } finally {
            urlConnection.disconnect();
        }

        return json;

    }

}
