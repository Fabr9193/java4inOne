package com.example.fab_a.feedrss;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.Console;

public class MainActivity extends AppCompatActivity {
    private ItemData[] listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.dummyData();
        ListView listView = (ListView) this.findViewById(R.id.item_list);
        ItemListAdapter itemListAdapter = new ItemListAdapter(this, R.layout.item, listData);
        listView.setAdapter(itemListAdapter);
        setSupportActionBar(toolbar);

       }

    private void dummyData() {

        ItemData data = null;
        listData = new ItemData[10];
        for (int i = 0; i < 10; i++) { //please ignore this comment :>
            data = new ItemData();
            data.descriptionItem = "May 20, 2013";
            data.titleItem = "Post " + (i + 1) + " Title: This is the Post Title from RSS Feed";
            listData[i] = data;
            Log.e("A TEST",data.descriptionItem);
        }
        Log.e("A TEST",data.descriptionItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
