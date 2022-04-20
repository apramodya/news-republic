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
    String category;
    static ArrayList<String> newsTitles = new ArrayList<>();
    static ArrayList<News> newsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_list);
        listView = findViewById(R.id.newsList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsTitles);
        listView.setAdapter(adapter);
        newsTitles = new ArrayList<>();
        newsList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("title");

        if (category.equals("all")) {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("https://gnews.io/api/v4/top-headlines?token=5de21de784eb663d796d65faa3472853");
        } else if (category.equals("saved")) {

        } else {
            DownloadTask downloadTask = new DownloadTask();
            String _url = "https://gnews.io/api/v4/top-headlines?q=new&max=10&topic=" + category + "&token=5de21de784eb663d796d65faa3472853";
            Log.i("URL", "onCreate: " + _url);
            downloadTask.execute(_url);
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
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
                        result.append(inputLine);
                    }
                    br.close();
                } else {
                    Log.i("Error: ", urlConnection.getResponseMessage());
                }
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jsonObject = null;
            JSONObject articleObject = null;
            int numberOfTopNews;

            try {
                jsonObject = new JSONObject(s);
                if (!jsonObject.isNull("articles")) {
                    String articleString = jsonObject.getString("articles");
                    JSONArray articleArray = new JSONArray(articleString);

                    numberOfTopNews = Math.min(articleArray.length(), 10);

                    for (int i = 0; i < numberOfTopNews; i++) {
                        articleObject = new JSONObject(articleArray.getString(i));
                        if (!articleObject.isNull("title") && !articleObject.isNull("url")) {
                            String title = articleObject.getString("title");
                            String description = articleObject.getString("description");
                            String content = articleObject.getString("content");
                            String image = articleObject.getString("image");

                            newsTitles.add(title);

                            News news = new News(title, description, content, image);
                            newsList.add(news);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}