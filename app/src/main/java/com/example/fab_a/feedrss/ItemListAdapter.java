package com.example.fab_a.feedrss;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by fab_a on 28/01/2017.
 */

class ItemListAdapter extends ArrayAdapter<ItemData> {

    private Activity myContext;
    private ItemData [] datas;

    private static class ViewHolder{
        TextView titleItem;
        TextView descriptionItem;
    }

    public ItemListAdapter(Context context, int resource, ItemData[] objects) {
        super(context, resource);
        myContext = (Activity) context;
        datas = objects;

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item, null);

            viewHolder = new ViewHolder();
            viewHolder.titleItem= (TextView) convertView
                    .findViewById(R.id.title_item);
            viewHolder.descriptionItem = (TextView) convertView
                    .findViewById(R.id.description_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleItem.setText(datas[position].titleItem);
        viewHolder.descriptionItem.setText(datas[position].descriptionItem);
        return convertView;
    }
}
