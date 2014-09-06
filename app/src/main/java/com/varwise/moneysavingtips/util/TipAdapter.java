package com.varwise.moneysavingtips.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.varwise.moneysavingtips.R;
import com.varwise.moneysavingtips.Tip;

public class TipAdapter extends ArrayAdapter<Tip> {

    public TipAdapter(Context context, int resource, Tip[] objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        Tip t = getItem(position);
        TextView tt = (TextView) v.findViewById(R.id.title);

        if (tt != null) {
            tt.setText(t.getId()+1 + ". " + t.getName());
        }

        return v;
    }

    public String getTipText(int position) {
        return getItem(position).getText();
    }

    public String getTipName(int position) {
        return getItem(position).getName();
    }
}
