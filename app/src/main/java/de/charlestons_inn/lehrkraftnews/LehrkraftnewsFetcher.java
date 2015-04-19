package de.charlestons_inn.lehrkraftnews;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
* Created by steven on 17.04.15.
*/
class LehrkraftnewsFetcher
        extends AsyncTask<String, Integer, List<LehrkraftnewsEntry>> {

    private MainActivity mainActivity;

    public LehrkraftnewsFetcher(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    protected List<LehrkraftnewsEntry> doInBackground(String... url) {
        @SuppressWarnings("Convert2Diamond") ArrayList<LehrkraftnewsEntry> newsEntries
                = new ArrayList<LehrkraftnewsEntry>();

        Document doc = new Document(url[0]);

        try {
            doc = Jsoup.connect(url[0]).get();
        } catch (IOException e) {
            //e.printStackTrace();
            String TAG = "Lehrkraftnews";
            Log.d(TAG, "oh noez!");
        }

        Elements pageEntries = doc.select("tr");

        // throw away first entry
        pageEntries.remove(0);

        for (Element pageEntry : pageEntries) {
            String validity = pageEntry.getElementsByClass("date_column")
                    .get(0)
                    .text();

            String source = pageEntry.getElementsByClass("person_column")
                    .get(0)
                    .text();

            String message = pageEntry.getElementsByClass("person_column")
                    .get(0)
                    .nextElementSibling()
                    .text();

            String entryUrl = pageEntry.select("a").attr("href");

            LehrkraftnewsEntry newsEntry = new LehrkraftnewsEntry();
            newsEntry.setValidityDate(validity);
            newsEntry.setSource(source);
            newsEntry.setMessage(message);
            newsEntry.setUrl(entryUrl);

            newsEntries.add(newsEntry);
        }

        return newsEntries;
    }

    @Override
    protected void onPostExecute(List<LehrkraftnewsEntry> newsEntries) {
        super.onPostExecute(newsEntries);

        SortedSet sources = new TreeSet<String>(); // retains alphebtical order

        for (LehrkraftnewsEntry entry : newsEntries) {
            sources.add(entry.getSource());
        }

        ArrayList sourcesArray = new ArrayList<String>(sources);
        mainActivity.addSourcesOnSpinner(sourcesArray);

        mainActivity.getEntries().addAll(newsEntries);
        mainActivity.getAllEntries().addAll(newsEntries);
        mainActivity.getAdapter().notifyDataSetChanged();
        //TextView text = (TextView) findViewById(R.id.main_text);
        //text.setText(doc.toString());
    }
}
