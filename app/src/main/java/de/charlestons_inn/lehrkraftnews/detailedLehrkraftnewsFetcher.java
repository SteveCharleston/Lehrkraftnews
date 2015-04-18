package de.charlestons_inn.lehrkraftnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by steven on 17.04.15.
 */
public class detailedLehrkraftnewsFetcher
    extends AsyncTask<String, String, String> {
        private MainActivity mainActivity;

        public detailedLehrkraftnewsFetcher(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        protected String doInBackground(String... url) {
            Document doc = new Document(url[0]);

            try {
                doc = Jsoup.connect(url[0]).get();
            } catch (IOException e) {
                String TAG = "Lehrkraftnews";
                Log.d(TAG, "oh noez!");
            }

            Elements detailedEntry = doc
                    .getElementById("singleMessage")
                    .getAllElements();

            Element newsMessage = detailedEntry.get(13);
            HtmlToPlainText htmlToPlainText = new HtmlToPlainText();

            return htmlToPlainText.getPlainText(newsMessage);

        }

        @Override
        protected void onPostExecute(String newsMessage) {
            super.onPostExecute(newsMessage);

            Intent intent = new Intent(mainActivity, DetailedLehrkraftnewsActivity.class);
            intent.putExtra("newsMessage", newsMessage);
            mainActivity.startActivity(intent);
        }
    }
