package com.hsm.newsapp;

/**
 * Created by HSM on 8/4/2018.
 */

public class News {
    private String title;
    private String section;
    private String date;
    private String pillerName;
    private String urlForDetails;
    private String authorFirstName;
    private String autherLastName;
    private String autherFullName;

    public News(String title, String section, String date, String pillerName, String urlForDetails, String authorFirstName, String autherLastName) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.pillerName = pillerName;
        this.urlForDetails = urlForDetails;
        this.authorFirstName = authorFirstName;
        this.autherLastName = autherLastName;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUrlForDetails() {
        return urlForDetails;
    }

    public String getPillerName() {
        return pillerName;
    }

    public String getAutherFullName() {
        return authorFirstName + " " + autherLastName;
    }
}
