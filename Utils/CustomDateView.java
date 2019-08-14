package com.example.rspl_rahul.gitrepo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.R;

import java.util.Date;
import java.util.HashMap;

public class CustomDateView extends LinearLayout {
    public static final String TAG_DATE = "date";
    public static String data = "";
    public static String realtTime = "";
    LinearLayout background;
    Context context;
    public TextView count;
    TextView date;
    LayoutInflater mInflater;
    HashMap<String, String> map = new HashMap();
    TextView month;
    public TextView real_date;
    Date ticketDate;
    TextView time;

    public CustomDateView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    public CustomDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    public CustomDateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View v = mInflater.inflate(R.layout.date_view, this, true);
      /*  this.background = (LinearLayout) v.findViewById(R.id.date_background);
        this.time = (TextView) v.findViewById(R.id.time);
        this.time.setTextColor(Color.parseColor("#ffffff"));
        this.date = (TextView) v.findViewById(R.id.date);
        this.date.setTextColor(Color.parseColor("#ffffff"));
        this.month = (TextView) v.findViewById(R.id.month);
        this.month.setTextColor(Color.parseColor("#ffffff"));
        this.background.setBackgroundColor(Color.parseColor("#576578"));
        this.real_date = (TextView) v.findViewById(R.id.real_date);
        this.count = (TextView) v.findViewById(R.id.count);*/
    }

    public void addRow(String time, String date, String month,
                       String real_date, Date ticketDate, String count) {

        this.ticketDate = ticketDate;
        this.time.setText(time);
        this.month.setText(month);
        this.real_date.setText(real_date);
        this.date.setText(date);
        this.count.setText(count);
    }

    public void setUnselect() {
        time.setTextColor(Color.parseColor("#ffffff"));
        date.setTextColor(Color.parseColor("#ffffff"));
        month.setTextColor(Color.parseColor("#ffffff"));
        background.setBackgroundColor(Color.parseColor("#5b6578"));
    }

    public void setSelect() {
        time.setTextColor(Color.parseColor("#ffffff"));
        date.setTextColor(Color.parseColor("#ffffff"));
        month.setTextColor(Color.parseColor("#ffffff"));
        background.setBackgroundColor(Color.parseColor("#ae3865"));
        map.put("date", real_date.getText().toString());
        data = real_date.getText().toString();
        realtTime = time.getText().toString();
        System.out.println("time " + data);
    }

    public Date getTicketDate() {
        return ticketDate;
    }
}
