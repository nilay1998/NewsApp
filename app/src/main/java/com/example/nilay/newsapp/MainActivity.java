package com.example.nilay.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{
    LoaderManager loaderManager;
    private View circleProgressBar;
    private TextView mEmptyStateTextView;
    ListView newsListView;
    EditText editText;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressBar = findViewById(R.id.loading_spinner);
        mEmptyStateTextView = findViewById(R.id.empty_view);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(checkConnection(cm))
        {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null,this);
        }
        else {
            circleProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
        }

        newsListView=findViewById(R.id.listview);
        newsAdapter=new NewsAdapter(this,new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);
        newsListView.setEmptyView(mEmptyStateTextView);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        newsAdapter.clear();
        ArrayList<News> load=QueryUtils.parseJson(s);
        circleProgressBar.setVisibility(View.GONE);
        if(load !=null && !load.isEmpty())
            newsAdapter.addAll(load);
        else {
            newsAdapter.clear();
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("NO NEWS FOUND");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        newsAdapter.clear();
    }

    public boolean checkConnection(ConnectivityManager connectivityManager) {
        // Status of internet connection
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;

        }
    }
}
