package de.charlestons_inn.lehrkraftnews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DetailedLehrkraftnewsActivity extends ActionBarActivity {
    private static String subscriptionPrefs
            = "de.charlestons-inn.lehrkraftnews.subscriptionPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        setContentView(R.layout.activity_detailed_lehrkraftnews);

        TextView dispDetailedLehrkraftnews =
                (TextView) findViewById(R.id.detailed_lehrkraftnews);

        Button subscribeButton = (Button) findViewById(R.id.subscribe);

        Intent intent = getIntent();
        String newsMessage = intent.getStringExtra("newsMessage");
        String newsSource = intent.getStringExtra("newsSource");

        SharedPreferences sharedPrefs = context.getSharedPreferences
                (subscriptionPrefs, context.MODE_PRIVATE);
        boolean profSubscribed = sharedPrefs.getBoolean(newsSource, false);
        if (profSubscribed) {
            subscribeButton.setText(R.string.unsubscribe);
        } else {
            subscribeButton.setText(R.string.subscribe);
        }

        subscribeButton.setTag(R.id.SUBSCRIBEPROF, newsSource);

        dispDetailedLehrkraftnews.setText(newsMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_lehrkraftnews, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String res = getResources().getResourceName(id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            id = item.getItemId();
            //finish();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggleSubscription(View v) {
        Context context = v.getContext();
        Button b = (Button) v;

        String source = (String) v.getTag(R.id.SUBSCRIBEPROF);

        SharedPreferences sharedPrefs = context.getSharedPreferences
                (subscriptionPrefs, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        boolean profSubscribed = sharedPrefs.getBoolean(source, false);
        editor.putBoolean(source, !profSubscribed);
        editor.commit();

        if (profSubscribed) {
            b.setText(R.string.subscribe);
        } else {
            b.setText(R.string.unsubscribe);
        }
    }
}
