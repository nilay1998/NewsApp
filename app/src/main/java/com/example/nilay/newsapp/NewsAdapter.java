package com.example.nilay.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

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
        final News current=getItem(position);
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        TextView date=convertView.findViewById(R.id.date);
        TextView title=convertView.findViewById(R.id.title);
        TextView author=convertView.findViewById(R.id.author);

        date.setText(current.getmDate());
        title.setText(current.getmTitle());
        author.setText(current.getmAuthor());

        CardView cardView=convertView.findViewById(R.id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=current.getMurl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
