package com.pramodya.newsrepublic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCategorySelect(View view) {
        Button button = (Button) view;
        Intent intent = new Intent(this, NewsList.class);
        intent.putExtra("title", button.getText().toString());
        startActivity(intent);
    }

}