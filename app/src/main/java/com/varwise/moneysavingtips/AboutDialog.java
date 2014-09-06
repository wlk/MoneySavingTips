package com.varwise.moneysavingtips;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

public class AboutDialog extends Dialog {
    private static Context mContext = null;

    public AboutDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.about);
        TextView tv = (TextView) findViewById(R.id.info_text);
        tv.setText(Html.fromHtml("<h3>Money Saving Tips</h3>Version 1.0<br>by Varwise<br>Copyright 2014<br><b>http://www.varwise.com</b><br><br>"));
        tv.setLinkTextColor(Color.BLUE);
        Linkify.addLinks(tv, Linkify.ALL);
    }
}