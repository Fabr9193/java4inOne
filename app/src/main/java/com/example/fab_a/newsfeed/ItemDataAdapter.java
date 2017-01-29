package com.example.fab_a.newsfeed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fab_a on 29/01/2017.
 */

public class ItemDataAdapter extends ArrayAdapter<ItemData> {

    private Activity myContext;
    private ItemData[] datas;

    public ItemDataAdapter(Context context, int textViewResourceId,
                           ItemData[] objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_rss, null);
        TextView postTitleView = (TextView) rowView
                .findViewById(R.id.titeRss);
        postTitleView.setText(datas[position].itemTitle);

        TextView postDateView = (TextView) rowView
                .findViewById(R.id.descriptionRss);
        postDateView.setText(datas[position].itemDescription);

        return rowView;
    }
}
