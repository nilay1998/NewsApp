package com.example.nilay.newsapp;

public class News
{
    private String mTitle;
    private String mAuthor;
    private String mDate;
    String murl;

    News(String title,String author,String url, String date)
    {
        mTitle=title;
        mAuthor=author;
        murl=url;
        mDate=date;
    }

    public String getmDate() {
        return mDate;
    }

    public String getMurl() {
        return murl;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }
}
