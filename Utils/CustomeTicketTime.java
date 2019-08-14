package com.example.rspl_rahul.gitrepo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.R;

import java.util.Date;
import java.util.HashMap;

public class CustomeTicketTime extends LinearLayout {
    public static final String TAG_DATE = "date";
    public static final String TAG_PASS_DATE = "real_date";
    public static String data = "";
    RelativeLayout background;
    Context context;
    LayoutInflater mInflater;
    HashMap<String, String> map = new HashMap();
    Date ticketDate;
    public TextView time;
    public TextView value;
    Spannable wordtoSpan;

    public CustomeTicketTime(Context context) {
        super(context);
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    public CustomeTicketTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    public CustomeTicketTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View v = this.mInflater.inflate(R.layout.time_view, this, true);
        Typeface tue_font = Typeface.createFromAsset(this.context.getAssets(), "fonts/AvenirLTStd-Book.ttf");
        this.background = (RelativeLayout) v.findViewById(R.id.date_background);
        this.time = (TextView) v.findViewById(R.id.time);
        this.value = (TextView) v.findViewById(R.id.value);
        this.time.setTypeface(tue_font);
        this.time.setTextColor(Color.parseColor("#ffffff"));
        this.background.setBackgroundColor(Color.parseColor("#388eb1"));
        this.background.getBackground().setAlpha(40);
    }

    public void addRow(String time, String value) {
        this.wordtoSpan = new SpannableString(time);
        this.wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ef689b")), 6, 8, 33);
        this.time.setText(this.wordtoSpan);
        this.value.setText(value);
    }

    public void setUnselect() {
        this.time.setTextColor(Color.parseColor("#ffffff"));
        this.background.setBackgroundColor(Color.parseColor("#388eb1"));
        this.background.getBackground().setAlpha(40);
    }

    public void setSelect(String show_time) {
        wordtoSpan = new SpannableString(show_time);
        this.wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 6, 8, 33);
        this.time.setText(this.wordtoSpan);
        this.background.setBackgroundColor(Color.parseColor("#ad2455"));
        this.map.put("date", this.time.getText().toString());
        this.map.put(TAG_PASS_DATE, this.value.getText().toString());
        Log.d("time >>>>>>> @@", this.value.getText().toString());
        data = this.value.getText().toString();
    }

    public Date getTicketDate() {
        return this.ticketDate;
    }

    public String getDate() {
        return data;
    }
}
