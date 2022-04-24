package com.pramodya.newsrepublic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class NewsDetailsActivity extends AppCompatActivity {
    static String TAG = "NewsDetailsActivity";

    TextView detailsViewTitleTextView;
    TextView detailsViewDescriptionTextView;
    ImageView detailsViewImageView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        detailsViewTitleTextView = findViewById(R.id.detailsViewTitleTextView);
        detailsViewDescriptionTextView = findViewById(R.id.detailsViewDescriptionTextView);
        detailsViewImageView = findViewById(R.id.detailsViewImageView);

        News news = (News) getIntent().getSerializableExtra("news");

        detailsViewTitleTextView.setText(news.title);
        detailsViewDescriptionTextView.setText(news.description);
        new DownloadImage().execute(news.image);
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewsDetailsActivity.this);
            mProgressDialog.setTitle("Loading...");
            mProgressDialog.setMessage("Fetching image");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            detailsViewImageView.setImageBitmap(result);
            mProgressDialog.dismiss();
        }
    }
}