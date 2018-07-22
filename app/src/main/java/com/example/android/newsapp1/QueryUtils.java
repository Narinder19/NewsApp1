package com.example.android.newsapp1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestURL) {
        //Create URL object
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request." + e.toString());
        }
        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {
        //If JSON string is empty or null, then return early
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        //Create an empty ArrayList to add news items
        List<News> news = new ArrayList<>();

        //Try to parse the JSON response string. If there's a problem with  JSON format
        //, a JSONException exception object will be thrown.
        //Catch the exception so the app doesn't crash, and print the error message.
        try {
            //Create a JSONObject from the JSON response string
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject responseObject = jsonObject.getJSONObject("response");
            JSONArray newsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String name = currentNews.getString("sectionName");
                String url = currentNews.getString("webUrl");
                String publishDate = currentNews.getString("webPublicationDate");

                //Create a new News item add it to News array.
                News newsItem = new News(title, name, publishDate, url);
                news.add(newsItem);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON data.");
        }
        //return the list of news items
        return news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); //time in milliseconds
            urlConnection.setConnectTimeout(15000); // time in milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results." + e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the InputStream into a String which contains the JSON response form the server
     *
     * @param inputStream
     * @return JSON response
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * @param requestURL API url string
     * @return new URL object from the given string URL
     */
    private static URL createUrl(String requestURL) {
        URL url = null;
        try {
            url = new URL(requestURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL" + e.toString());
        }
        return url;
    }
}
