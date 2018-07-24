package com.example.android.newsapp1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String TAG = "NewsActivity";
    //Get API key
    String apiKey = BuildConfig.THE_GUARDIAN_API_KEY;

    // URL for the news data form  Guardian API
    private static final String URL = "https://content.guardianapis.com/search?show-tags=contributor&api-key=";
    String GUARDIAN_URL = URL + apiKey;
    /**
     * Constant value for the News loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    //Adapter for the list of news items
    private NewsAdapter mAdapter;
    private TextView emptyTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.d(TAG, "OnCreate started");

        //Find listView in the layout
        recyclerView = findViewById(R.id.recycler_news_list);

        //Find empty TextView in the layout
        emptyTextView = findViewById(R.id.empty_list_item);
        // newsListView.setEmptyView(emptyTextView);
        //Get a reference to the ConnectivityManager to check state of network connectivity.
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //Check if device is connected to internet.
        if (networkInfo == null || !networkInfo.isConnected()) {
            emptyTextView.setText(R.string.no_connection);
            return;
        }

        Log.i(TAG, "initLoader");
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this).forceLoad();
    }


    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(TAG, "onCreateLoader");
        return new NewsLoader(NewsActivity.this, GUARDIAN_URL); // NewsLoader(NewsActivity.this, GUARDIAN_URL);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {

        //If there is a valid list of News, then add them to the adapter's data set.
        //This will trigger the listView to update.
        if (data != null && !data.isEmpty()) {
            Log.i(TAG, "onLoadFinish");
            //Create a new adapter that takes an empty list of news as input
            mAdapter = new NewsAdapter(this, data);

            //Set the adapter on the listView to display list items
            recyclerView.setAdapter(mAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.notifyDataSetChanged();

        } else {
            emptyTextView.setText(R.string.emptyList);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mAdapter.ClearAdapter();
    }


}
