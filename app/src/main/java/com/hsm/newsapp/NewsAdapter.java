package com.hsm.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HSM on 8/4/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private Context context;
    private ArrayList<News> newsArrayList;
    private TextView titleTextView;
    private TextView sectionTextView;
    private TextView dateTextView;
    private TextView pillerNameTextView;
    private TextView authorNameTextView;
    private boolean titleTextViewVisibility;
    private boolean sectionTextViewVisibility;
    private boolean dateTextViewVisibility;
    private boolean pillerNameTextViewVisibility;
    private boolean authorNameTextViewVisibility;

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> objects) {
        super(context, R.layout.item, objects);
        this.context = context;
        this.newsArrayList = objects;
        this.titleTextViewVisibility = true;
        this.sectionTextViewVisibility = true;
        this.dateTextViewVisibility = true;
        this.pillerNameTextViewVisibility = true;
        this.authorNameTextViewVisibility = true;
    }

    public void setTitleTextViewVisibility(boolean titleTextViewVisibility) {
        this.titleTextViewVisibility = titleTextViewVisibility;
    }

    public void setSectionTextViewVisibility(boolean sectionTextViewVisibility) {
        this.sectionTextViewVisibility = sectionTextViewVisibility;
    }

    public void setDateTextViewVisibility(boolean dateTextViewVisibility) {
        this.dateTextViewVisibility = dateTextViewVisibility;
    }

    public void setPillerNameTextViewVisibility(boolean pillerNameTextViewVisibility) {
        this.pillerNameTextViewVisibility = pillerNameTextViewVisibility;
    }

    public void setAuthorNameTextViewVisibility(boolean authorNameTextViewVisibility) {
        this.authorNameTextViewVisibility = authorNameTextViewVisibility;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Nullable
    @Override
    public News getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        titleTextView = view.findViewById(R.id.titleItemTextView);
        sectionTextView = view.findViewById(R.id.sectionItemTextView);
        dateTextView = view.findViewById(R.id.dateItemTextView);
        pillerNameTextView = view.findViewById(R.id.pillerNameItemTextView);
        authorNameTextView = view.findViewById(R.id.authorNameItemTextView);

        titleTextView.setText(newsArrayList.get(position).getTitle());
        sectionTextView.setText(newsArrayList.get(position).getSection());
        dateTextView.setText(newsArrayList.get(position).getDate());
        pillerNameTextView.setText(newsArrayList.get(position).getPillerName());
        authorNameTextView.setText(newsArrayList.get(position).getAutherFullName());

        titleTextView.setVisibility(titleTextViewVisibility?View.VISIBLE:View.GONE);
        sectionTextView.setVisibility(sectionTextViewVisibility?View.VISIBLE:View.GONE);
        dateTextView.setVisibility(dateTextViewVisibility?View.VISIBLE:View.GONE);
        pillerNameTextView.setVisibility(pillerNameTextViewVisibility?View.VISIBLE:View.GONE);
        authorNameTextView.setVisibility(authorNameTextViewVisibility?View.VISIBLE:View.GONE);
        return view;
    }


}
