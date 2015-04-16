package de.charlestons_inn.lehrkraftnews;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by steven on 16.04.15.
 */
public class LehrkraftnewsAdapter extends ArrayAdapter<LehrkraftnewsEntry> {

    private Context context;
    private List<LehrkraftnewsEntry> entries;

    public LehrkraftnewsAdapter(Context context, List<LehrkraftnewsEntry> entries) {
        // call super class to define layout and list items
        super(context, R.layout.list_item_lehrkraftnews, entries);

        this.context = context;
        this.entries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        LayoutInflater inflater
                = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View entry = inflater.inflate(
                        R.layout.list_item_lehrkraftnews,
                        parent,
                        false);

        TextView validity = (TextView) entry.findViewById(R.id.validity_date);
        TextView source = (TextView) entry.findViewById(R.id.source);
        TextView message = (TextView) entry.findViewById(R.id.message);

        validity.setText(entries.get(position).getValidityDate());
        source.setText(entries.get(position).getSource());
        message.setText(entries.get(position).getMessage());

        return entry;
    }
}
