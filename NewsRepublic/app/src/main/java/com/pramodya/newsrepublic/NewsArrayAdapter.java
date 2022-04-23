package com.pramodya.newsrepublic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter<News> {
    private static String TAG = "NewsArrayAdapter";
    private Context context;
    int resource;

    public NewsArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView titleTextView = convertView.findViewWithTag(R.id.titleTextView);
        TextView descriptionTextView = convertView.findViewWithTag(R.id.descriptionTextView);

        titleTextView.setText("title");
        descriptionTextView.setText("description");

        return convertView;
    }
}