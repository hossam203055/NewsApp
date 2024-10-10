package com.hsm.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private String TAG = MainActivity.class.getSimpleName();
    ListView newsListView;
    ArrayList<News> newsArrayList;
    NewsAdapter newsAdapter;
    final String urlJSON = "http://content.guardianapis.com/search?&show-tags=contributor&q=debates&api-key=test";
    LoaderManager loaderManager;
    int requestCodeForIntent;
    boolean titleTextViewVisibility;
    boolean sectionTextViewVisibility;
    boolean dateTextViewVisibility;
    boolean pillerNameTextViewVisibility;
    boolean authorNameTextViewVisibility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsArrayList = new ArrayList<>();
        newsListView = findViewById(R.id.newsListView);
        loaderManager = getLoaderManager();
        if (isNetworkAvailable()) {
            loaderManager.initLoader(1, null, this);
            titleTextViewVisibility = true;
            sectionTextViewVisibility = true;
            dateTextViewVisibility = true;
            pillerNameTextViewVisibility = true;
            authorNameTextViewVisibility = true;
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArrayList.get(position).getUrlForDetails()));
                    startActivity(webIntent);
                }
            });
        } else {
            TextView messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setText(getResources().getText(R.string.messageNetworkNotAvailable));
            messageTextView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTaskLoader(this, urlJSON, newsArrayList, TAG);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        newsArrayList = data;
        newsAdapter = new NewsAdapter(getApplicationContext(), newsArrayList);
        newsListView.setAdapter(newsAdapter);
        if (newsArrayList.isEmpty()) {
            Log.e(TAG, getResources().getString(R.string.messageDataNotFound));
            TextView messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setText(getResources().getText(R.string.messageDataNotFound));
            messageTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if  (id==R.id.settingsMenu){
            if (isNetworkAvailable()){
                requestCodeForIntent = 20;
                Intent goToSettingsActivityIntent = new Intent(MainActivity.this,SettingsActivity.class);
                Bundle booleansBundle = new Bundle();
                booleansBundle.putBoolean("titleBoolean",titleTextViewVisibility);
                booleansBundle.putBoolean("sectionBoolean",sectionTextViewVisibility);
                booleansBundle.putBoolean("dateBoolean",dateTextViewVisibility);
                booleansBundle.putBoolean("pillerBoolean",pillerNameTextViewVisibility);
                booleansBundle.putBoolean("authorBoolean",authorNameTextViewVisibility);
                goToSettingsActivityIntent.putExtras(booleansBundle);
                startActivityForResult(goToSettingsActivityIntent,requestCodeForIntent);
            } else Toast.makeText(getApplicationContext(),getResources().getText(R.string.messageNetworkNotAvailable),Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeForIntent && resultCode == RESULT_OK) {
            Bundle settingsActivityBundle = data.getExtras();
            titleTextViewVisibility = settingsActivityBundle.getBoolean("titleBoolean");
            sectionTextViewVisibility = settingsActivityBundle.getBoolean("sectionBoolean");
            dateTextViewVisibility = settingsActivityBundle.getBoolean("dateBoolean");
            pillerNameTextViewVisibility = settingsActivityBundle.getBoolean("pillerBoolean");
            authorNameTextViewVisibility = settingsActivityBundle.getBoolean("authorBoolean");
            newsAdapter.setTitleTextViewVisibility(titleTextViewVisibility);
            newsAdapter.setSectionTextViewVisibility(sectionTextViewVisibility);
            newsAdapter.setDateTextViewVisibility(dateTextViewVisibility);
            newsAdapter.setPillerNameTextViewVisibility(pillerNameTextViewVisibility);
            newsAdapter.setAuthorNameTextViewVisibility(authorNameTextViewVisibility);
            newsListView.setAdapter(newsAdapter);
            //I don't know why newsArrayList's items duplicate
        }

    }
}


