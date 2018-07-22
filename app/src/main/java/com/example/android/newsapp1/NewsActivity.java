package com.example.android.newsapp1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    // URL for the Technology section news data form  Guardian API
    private  static final String GUARDIAN_URL = "https://content.guardianapis.com/search?api-key=090f74d7-7815-4ebf-a3cf-04cfa17db7a9";
    //Adapter for the list of news items
    private NewsAdapter mAdapter;
    private TextView emptyTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Find listView in the layout
        ListView newsListView = findViewById(R.id.news_list);
        //Find empty TextView in the layout
        emptyTextView = findViewById(R.id.empty_list_item);
        newsListView.setEmptyView(emptyTextView);
        //Check if device is connected to internet.
        if(networkInfo == null || !networkInfo.isConnected() ){
            emptyTextView.setText(R.string.no_connection);
            return;
        }
        //Create a new adapter that takes an empty list of news as input
        mAdapter  = new NewsAdapter(this,new ArrayList<News>());

        //Set the adapter on the listView to display list items
        newsListView.setAdapter(mAdapter);
        //Set an item click listener on the ListView, which sends an intent to a web browser
        //to open a website with more information about the selected news item.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Find the current News item that was clicked on
                News currentNews = mAdapter.getItem(position);
                // Convert the string URL into a URI object
                Uri newsUri = Uri.parse(currentNews.getUrl());
                //Create a new intent to view the URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                //Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        getSupportLoaderManager().initLoader(1,null,this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(NewsActivity.this, GUARDIAN_URL); // NewsLoader(NewsActivity.this, GUARDIAN_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        //Clear the adapter of previous news data
        mAdapter.clear();

        //If there is a valid list of News, then add them to the adapter's data set.
        //This will trigger the listView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else
        {
            emptyTextView.setText(R.string.emptyList);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
