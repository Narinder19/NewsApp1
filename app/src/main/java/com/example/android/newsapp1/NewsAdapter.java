package com.example.android.newsapp1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String TAG = "NewsAdapter";

    private List<News> newsList;
    private Context mContext;

    public NewsAdapter(Context mContext, List<News> newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
    }

    //Clear news list
    public void ClearAdapter() {
        final int size = newsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                newsList.remove(i);
            }
        }
        notifyItemRangeRemoved(0, size);
    }

    // This method is called when the adapter is created and is used to initialize ViewHolder.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //This method is called for each ViewHolder to bind it to the adapter. This is where data is passed to
    // ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        final News currentNews = newsList.get(position);
        holder.positionView.setText(String.valueOf(position + 1));
        holder.titleView.setText(currentNews.getTitle());
        holder.nameView.setText(currentNews.getName());
        holder.authorView.setText(mContext.getString(R.string.author_prefix) + " " + currentNews.getAuthor());
        holder.dateView.setText(getDate(currentNews.getDate()));
        holder.timeView.setText(getTime(currentNews.getDate()));
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert the string URL into a URI object
                Uri newsUri = Uri.parse(currentNews.getUrl());
                //Create a new intent to view the URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                //Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });
    }

    //This method returns the size of the newsList that contains the items need to be displayed.
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    //Extract time from publicationDate
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

    //Extract and format date from publicationDate
    private static String getDate(String datetime) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parent_layout;
        TextView titleView;
        TextView nameView;
        TextView authorView;
        TextView dateView;
        TextView timeView;
        TextView positionView;

        //ViewHolder object represent each item in newsList and is used to display item.
        public ViewHolder(View itemView) {
            super(itemView);
            positionView = itemView.findViewById(R.id.position);
            titleView = itemView.findViewById(R.id.title);
            nameView = itemView.findViewById(R.id.name);
            authorView = itemView.findViewById(R.id.author_name);
            dateView = itemView.findViewById(R.id.date);
            timeView = itemView.findViewById(R.id.time);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
