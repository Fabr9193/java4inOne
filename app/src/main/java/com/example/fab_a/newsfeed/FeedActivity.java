package com.example.fab_a.newsfeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

/**
 * Created by fab_a on 29/01/2017.
 */

public class FeedActivity extends Activity {
    private ItemData[] listData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_rss);

        this.generateDummyData();
        ListView listView = (ListView) this.findViewById(R.id.listRss);
        ItemDataAdapter itemAdapter = new ItemDataAdapter(this,
                R.layout.item_rss, listData);
        listView.setAdapter(itemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    private void generateDummyData() {
        ItemData data = null;
        listData = new ItemData[10];
        for (int i = 0; i < 10; i++) { //please ignore this comment :>
            data = new ItemData();
            data.itemDescription= "May 20, 2013";
            data.itemTitle= "Post " + (i + 1) + " Title: This is the Post Title from RSS Feed";
            listData[i] = data;
        }
    }

}
