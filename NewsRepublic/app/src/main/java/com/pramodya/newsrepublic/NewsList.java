package com.pramodya.newsrepublic;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    static String TAG = "NewsList";
    static ArrayList<News> newsList = new ArrayList<>();
    static ArrayList<String> newsTitleList = new ArrayList<>();
    ArrayAdapter newsArrayAdapter;
    ListView listView;
    String category;
    public void onNewsTitleSelect(News news) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("news", news);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        listView = findViewById(R.id.newsList);
        newsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newsTitleList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                onNewsTitleSelect(news);
            }
        });
        listView.setAdapter(newsArrayAdapter);
        newsList = new ArrayList<>();
        newsTitleList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("title");
        if (category.equals("all")) {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("https://gnews.io/api/v4/top-headlines?token=ded9ce1073a96a6926d7946c63313c26");
        } else if (category.equals("saved")) {
        } else {
            DownloadTask downloadTask = new DownloadTask();
            String _url = "https://gnews.io/api/v4/top-headlines?q=new&max=10&topic=" + category + "&token=ded9ce1073a96a6926d7946c63313c26";
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
                            News news = new News(title, description, content, image);
                            newsList.add(news);
                            newsTitleList.add(title);
                        }
                    }
                    newsArrayAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}