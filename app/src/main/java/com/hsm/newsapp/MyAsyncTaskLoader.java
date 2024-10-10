package com.hsm.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by HSM on 13/4/2018.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<News>> {
    Context context;
    String urlJSON;
    ArrayList<News> newsArrayList;
    String TAG;
    public MyAsyncTaskLoader(Context context , String urlJSON , ArrayList<News> newsArrayList, String TAG) {
        super(context);
        this.context = context;
        this.urlJSON = urlJSON;
        this.newsArrayList = newsArrayList;
        this.TAG = TAG;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        HttpHandler sh = new HttpHandler();
        String url = urlJSON;
        String jsonString = "";
        try {
            jsonString = sh.makeHttpRequest(createUrl(url));
        } catch (IOException e) {
            return null;
        }

        Log.e(TAG, "Response from url: " + jsonString);
        if (jsonString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                JSONObject responseObject = jsonObj.getJSONObject("response");
                JSONArray resultsArray = responseObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject iObject = resultsArray.getJSONObject(i);
                    JSONArray tagsArray = iObject.getJSONArray("tags");
                    JSONObject tagObject = tagsArray.getJSONObject(0);
                    newsArrayList.add(new News(iObject.getString("webTitle"), iObject.getString("sectionName"), iObject.getString("webPublicationDate"), iObject.getString("pillarName"), iObject.getString("webUrl"),tagObject.getString("firstName"),tagObject.getString("lastName")));
                }
            } catch (final JSONException e) {
                Log.e(TAG, getContext().getResources().getString(R.string.messageErrorConnection) + e.getMessage());
            }

        } else {
            Log.e(TAG, getContext().getResources().getString(R.string.messageCouldntGetJSON));
        }
        return newsArrayList;
    }
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
