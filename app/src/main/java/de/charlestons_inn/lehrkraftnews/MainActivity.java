package de.charlestons_inn.lehrkraftnews;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static String TAG = "Lehrkraftnews";
    private static String lehrkraftnewsUrl =
            "http://fb6.beuth-hochschule.de/lehrkraftnews/message/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new LehrkraftnewsFetcher().execute(lehrkraftnewsUrl);

        List<LehrkraftnewsEntry> entries = getDummyData();

        ListView lehrkraftnewsEntries
                = (ListView) findViewById(R.id.lehrkraftnews_entries);

        LehrkraftnewsAdapter adapter = new LehrkraftnewsAdapter(this, entries);
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
        ArrayList<LehrkraftnewsEntry> entries
                = new ArrayList<LehrkraftnewsEntry>();

        LehrkraftnewsEntry first = new LehrkraftnewsEntry();
        first.setValidityDate("01.01.2015");
        first.setSource("B. Wayne");
        first.setMessage("To the Batcave");
        entries.add(first);

        LehrkraftnewsEntry second = new LehrkraftnewsEntry();
        second.setValidityDate("02.01.2015");
        second.setSource("B. Banner");
        second.setMessage("You wouldn't like me when I'm angry");
        entries.add(second);

        LehrkraftnewsEntry third = new LehrkraftnewsEntry();
        third.setValidityDate("03.01.2015");
        third.setSource("P. Parker");
        third.setMessage("My spider senses are Tingeling");
        entries.add(third);

        return entries;
    }

    private class LehrkraftnewsFetcher
            extends AsyncTask<String, Integer, Document> {
        //public static String TAG = MainActivity.TAG;

        protected Document doInBackground(String... url) {
            Document doc = new Document(url[0]);

            try {
                doc = Jsoup.connect(url[0]).get();
            } catch (IOException e) {
                //e.printStackTrace();
                Log.d(TAG, "oh noez!");
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);
            //TextView text = (TextView) findViewById(R.id.main_text);
            //text.setText(doc.toString());
        }
    }
}

