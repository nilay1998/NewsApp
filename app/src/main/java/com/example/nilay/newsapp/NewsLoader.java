package com.example.nilay.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class NewsLoader extends AsyncTaskLoader<String>
{
    String s;
    NewsLoader(Context context, String a)
    {
        super(context);
        s=a;
    }

    @Override
    protected void onStartLoading() {
        Log.e("NewsLoader","onStartLoader");
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        Log.e("NewsLoader","loadInBackground");
        URL url= QueryUtils.createUrl(QueryUtils.createStringUrl(s));
        String jsonResponse="";
        try {
            jsonResponse=QueryUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e("NewsLoader", "Problem making the HTTP request.", e);
        }
        return jsonResponse;
    }
}
