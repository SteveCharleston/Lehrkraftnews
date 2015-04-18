package de.charlestons_inn.lehrkraftnews;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static int ENTRYURLTAG = 1;
    private static String fb6Url = "http://fb6.beuth-hochschule.de";
    private static List<LehrkraftnewsEntry> entries;
    private static LehrkraftnewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection Convert2Diamond
        entries = new ArrayList<LehrkraftnewsEntry>();

        String lehrkraftnewsUrl = "http://fb6.beuth-hochschule.de/lehrkraftnews/message/";
        new LehrkraftnewsFetcher(this).execute(lehrkraftnewsUrl);

        ListView lehrkraftnewsEntries
                = (ListView) findViewById(R.id.lehrkraftnews_entries);
        adapter = new LehrkraftnewsAdapter(this, entries);
        lehrkraftnewsEntries.setAdapter(adapter);
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

    public List<LehrkraftnewsEntry> getDummyData() {
        //noinspection Convert2Diamond
        ArrayList<LehrkraftnewsEntry> newsEntries
                = new ArrayList<LehrkraftnewsEntry>();

        LehrkraftnewsEntry first = new LehrkraftnewsEntry();
        first.setValidityDate("01.01.2015");
        first.setSource("B. Wayne");
        first.setMessage("To the Batcave");
        newsEntries.add(first);

        LehrkraftnewsEntry second = new LehrkraftnewsEntry();
        second.setValidityDate("02.01.2015");
        second.setSource("B. Banner");
        second.setMessage("You wouldn't like me when I'm angry");
        newsEntries.add(second);

        LehrkraftnewsEntry third = new LehrkraftnewsEntry();
        third.setValidityDate("03.01.2015");
        third.setSource("P. Parker");
        third.setMessage("My spider senses are Tingeling");
        newsEntries.add(third);

        return newsEntries;
    }

    public static List<LehrkraftnewsEntry> getEntries() {
        return entries;
    }

    public static void setEntries(List<LehrkraftnewsEntry> entries) {
        MainActivity.entries = entries;
    }

    public void detailedMessage(View v) {
        //Log.d("Lehrkraftnews", v.getTag(R.id.URLENTRY).toString());
        String messageUrl = fb6Url + v.getTag(R.id.URLENTRY).toString();
        new detailedLehrkraftnewsFetcher(this).execute(messageUrl);
    }

    public static LehrkraftnewsAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(LehrkraftnewsAdapter adapter) {
        MainActivity.adapter = adapter;
    }

}

