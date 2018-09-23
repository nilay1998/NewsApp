package com.example.nilay.newsapp;

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
import android.view.View;
import android.widget.AdapterView;
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
    String query;
    NewsAdapter newsAdapter;
    ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressBar = findViewById(R.id.loading_spinner);
        mEmptyStateTextView = findViewById(R.id.empty_view);

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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

        editText=findViewById(R.id.edit_query);
        query=editText.getText().toString();

//        ListView listView=findViewById(R.id.listview);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                News news=newsAdapter.getItem(i);
//                String url=news.murl;
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//            }
//        });
     }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        Log.e("MainActivity","onCreateLoder");
        return new NewsLoader(MainActivity.this,query);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.e("MainActivity","onLoadFinished");
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
        Log.e("MainActivity","onLoadReset");
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

    public void search(View view)
    {
        if(editText.getText().toString().equals(query))
            return;
        query=editText.getText().toString();
        newsAdapter.clear();
        if(checkConnection(cm))
        {
            restartLoader();
        }
        else {
            newsAdapter.clear();
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
        }
    }

    public void restartLoader() {
        mEmptyStateTextView.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(1, null, MainActivity.this);
    }
}
