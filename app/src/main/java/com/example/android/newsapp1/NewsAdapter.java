package com.example.android.newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if there is an existing list item view(called convertView) that we can reuse,
        //otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        //Find the news at the given position in the list of news.
        News currentNews = getItem(position);
        //Find the TextView with position ID
        TextView positionView = listItemView.findViewById(R.id.position);
        positionView.setText(String.valueOf(position + 1));
        //Find the TextView with ID title
        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentNews.getTitle());
        //Find the TextView with ID name
        TextView nameView = listItemView.findViewById(R.id.name);
        nameView.setText(currentNews.getName());
        //Find the TextView with ID date
        TextView dateView = listItemView.findViewById(R.id.date);
        String customDate = getMonth(currentNews.getDate());
        dateView.setText(customDate);

        TextView timeView = listItemView.findViewById(R.id.time);
        String customTime = getTime(currentNews.getDate());
        timeView.setText(customTime);
        return listItemView;
    }

    private String getTime(String datetime) {
        String formattedTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getDefault());
            Date date = sdf.parse(datetime);
            formattedTime = new SimpleDateFormat("h:mm a").format(date);
        } catch (ParseException e) {
            Log.e("NewsAdapter", "Error parsing date.");
        }
        return formattedTime;
    }

    private static String getMonth(String datetime) {
        String formattedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getDefault());
            Date date = sdf.parse(datetime);
            formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
        } catch (ParseException e) {
            Log.e("NewsAdapter", "Error parsing date.");
        }
        return formattedDate;
    }
}
