package com.example.nilay.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>
{
    NewsAdapter(Context context, ArrayList<News> news)
    {
        super(context,0,news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        News current=getItem(position);
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        TextView date=convertView.findViewById(R.id.date);
        TextView title=convertView.findViewById(R.id.title);
        TextView author=convertView.findViewById(R.id.author);

        date.setText(current.getmDate());
        title.setText(current.getmTitle());
        author.setText(current.getmAuthor());

        return convertView;
    }
}
