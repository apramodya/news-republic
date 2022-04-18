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
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("https://gnews.io/api/v4/top-headlines?country=ca&max=10&token=5de21de784eb663d796d65faa3472853");
        adapter.notifyDataSetChanged();
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                Log.i("Response Code: ", Integer.toString(responseCode));
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        result += inputLine;
                    }
                    br.close();
                } else {
                    Log.i("Error: ", urlConnection.getResponseMessage());
                }
                return result;
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
                jsonObject = new JSONObject(s);
                if (!jsonObject.isNull("articles")) {
                    String articleString = jsonObject.getString("articles");
                    JSONArray articleArray = new JSONArray(articleString);
                    numberOfTopNews = 10;
                    if (articleArray.length() < 10) {
                        numberOfTopNews = articleArray.length();
                    }
                    for (int i = 0; i < numberOfTopNews; i++) {
                        articleObject = new JSONObject(articleArray.getString(i));
                        if (!articleObject.isNull("title") && !articleObject.isNull("url")) {
                            String articleTitle = articleObject.getString("title");
                            String articleURL = articleObject.getString("url");
                            String articleId = String.valueOf(i + 1);
                            newsTitles.add(articleTitle);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}