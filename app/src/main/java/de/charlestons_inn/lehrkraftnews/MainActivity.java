package de.charlestons_inn.lehrkraftnews;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static int ENTRYURLTAG = 1;
    private static String fb6Url = "http://fb6.beuth-hochschule.de";
    private static List<LehrkraftnewsEntry> entries;

    public static List<LehrkraftnewsEntry> getAllEntries() {
        return allEntries;
    }

    public static void setAllEntries(List<LehrkraftnewsEntry> allEntries) {
        MainActivity.allEntries = allEntries;
    }

    private static List<LehrkraftnewsEntry> allEntries;
    private static LehrkraftnewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection Convert2Diamond
        entries = new ArrayList<LehrkraftnewsEntry>();
        allEntries = new ArrayList<LehrkraftnewsEntry>();

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

    public void addSourcesOnSpinner(List sources) {
        sources.add(0, getString(R.string.all_news));

        Spinner spinner = (Spinner) findViewById(R.id.filter_spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sources);

        spinnerAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private List sources;

            private AdapterView.OnItemSelectedListener init(List sources) {
                this.sources = sources;
                return this;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another
            }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                    int position, long id) {
                String source = (String) sources.get(position);
                List filteredEntries = new ArrayList<LehrkraftnewsEntry>();

                if (source.equals(getString(R.string.all_news))) {
                    filteredEntries.addAll(getAllEntries());
                } else {
                    for (LehrkraftnewsEntry entry : getAllEntries()) {
                        if (entry.getSource().equals(source)) {
                            filteredEntries.add(entry);
                        }
                    }
                }

                getEntries().clear();
                getEntries().addAll(filteredEntries);
                getAdapter().notifyDataSetChanged();
            }
        }.init(sources));

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

