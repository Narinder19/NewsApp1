package com.example.android.newsapp1;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    private static final String TAG = "NewsLoader";

    public NewsLoader(Context context, String Url) {

        super(context);
        mUrl = Url;
        Log.i(TAG, "newloader");
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onstartloading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> result = QueryUtils.fetchNewsData(mUrl);
        Log.i(TAG, "loadinBackground after fetch news data.");
        return result;
    }
}
