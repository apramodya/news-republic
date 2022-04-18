package com.pramodya.newsrepublic;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class NewsList extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> newsTitles =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        listView = findViewById(R.id.newsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsTitles);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String titleLink = "";
            JSONObject jsonObject = null;
            JSONObject articleObject = null;
            int numberOfTopNews;
            try {

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}