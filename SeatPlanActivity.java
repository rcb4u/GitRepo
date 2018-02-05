package com.example.rspl_rahul.gitrepo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.GenericLoaderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeatPlanActivity extends Activity {

    final ArrayList<String> SeatData = new ArrayList();
    Button StartTimer;
    String adultPrice;
    AlertDialog alertDialog;

    AlertDialog.Builder alertDialogBuilder;
    JSONArray array = new JSONArray();
    JSONArray arraySeat = new JSONArray();
    String auth_token;
    ImageView banner;
    String bannerData;
    String booking_id = "null";
    String childPrice;
    int count = 0;
    TextView countdata;
    TextView coupon;
    EditText couponData;
    TextView dateData;
    String dateMovie;
    Fragment fr;
    JSONObject getJson_data = new JSONObject();
    Settings.Global globalVariable;
    String hit;
    String hitBtn = "no";
    String hold = "2";
    String html_desing;

    IntentFilter intentFilter;
    JSONArray jsArray = new JSONArray();
    JSONObject jsonObject;
    JSONObject json_data;
    String label;

    private Handler mUiHandler = new Handler();
    public TextView main_title;
    ImageView menu;
    String movieID;
    String movie_date;
    String movie_id;
    CheckBox mySwitch = null;
    WebView myWebView;
    String name;
    ImageView now_showing;
    JSONObject object1;
    ImageView profile;
    long remainedSecs;
    String responseString;
    final ArrayList<String> seat = new ArrayList();
    final ArrayList<String> seatData = new ArrayList();
    final ArrayList<String> seatFullData = new ArrayList();
    final ArrayList<String> seatHold = new ArrayList();
    final ArrayList<String> seatID = new ArrayList();
    SeatPlanAsyncTask seatPlanAsyncTask;
    String seat_count;
    String seat_plan;
    String selected;
    String showBookingID;
    TextView showtmeData;
    String source = "4";
    String status;
    TextView textTimer;
    TextView textView17;
    TextView textView18;
    TextView textView19;
    TextView textView20;
    TextView textView21;
    TextView textView23;
    TextView textView30;
    TextView textView32;
    TextView textView33;
    TextView textView34;
    TextView theater;
    String theaterData;
    String theater_id;
    ImageView theatres;
    String time;
    String time_slot;
    String timedata;
    TextView total_price;
    ImageView up_coming;
    String user_keyData;
    View view;
    String x_height;
    String x_value;
    String y_height;
    String y_value;
    private StringBuffer LoadWEb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_plan);


        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.setBackgroundColor(0);
        myWebView.setScrollbarFadingEnabled(false);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        LoadWEb1 = new StringBuffer();
      int i,j=0;
      for(i=1;i<=10;i++){
          for(j=1;j<=10;j++){
              seat_plan = "<button id='"+j+"'  data-seatid='"+j+"' class='btn' style='height:25px;width:25px; padding:5px;margin:5px;" +
                      "font-size: 5px;border:none;" +
                      " border-radius: 5px; " +
                      " background-color:#0b98a7;" +
                      " margin-top:'"+j+"'px; margin-left:'"+j+"'px; position: absolute;\">"+j+"</button>";
              LoadWEb1.append(seat_plan);
          }
          LoadWEb1.append("<br/>");
      }
      myWebView.loadData( LoadWEb1.toString(), "text/html", "utf-8");
    }
    public class WebAppInterface {
        Context mContext;
        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
    /*
        LoadWEb1.append("<html><body>\n" +
                "        <div class=\"container\">\n" +
                "            <button class=\"trigger\">Hall A</button>\n" +
                "        </div><script>\n" +
                "            $('.trigger').on('click',\n" +
                "                    function () {\n" +
                "                        LoopTest();\n" +
                "                    });\n" +
                "            function LoopTest() {\n" +
                "               \n" +
                "                for (i = 1; i <= 100; i++) {\n" +
                "                    $('body').append('<button class=\"trigger\">. ' + i + '</button>')\n" +
                "                }\n" +
                "            }\n" +
                "        </script> </body></html>").toString();*/


    class SeatPlanAsyncTask extends AsyncTask<Void, Integer, Void> {


        SeatPlanAsyncTask() {
        }

        protected Void doInBackground(Void... params) {
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    final class ChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage msg) {
            Log.d(
                    "WebView",
                    String.format("%s %s:%d", msg.message(), msg.lineNumber(), msg.sourceId())
            );
            return true;
        }
    }

}

        /*public class SeatPlanFragment extends Activity {
            final ArrayList<String> SeatData = new ArrayList();
            Button StartTimer;
            String adultPrice;
            AlertDialog alertDialog;

            AlertDialog.Builder alertDialogBuilder;
            JSONArray array = new JSONArray();
            JSONArray arraySeat = new JSONArray();
            String auth_token;
            ImageView banner;
            String bannerData;
            String booking_id = "null";
            String childPrice;
            final SeatPlanFragment context = this;
            int count = 0;
            TextView countdata;
            TextView coupon;
            EditText couponData;
            TextView dateData;
            String dateMovie;
            Fragment fr;
            JSONObject getJson_data = new JSONObject();
            Settings.Global globalVariable;
            String hit;
            String hitBtn = "no";
            String hold = "2";
            String html_desing;
            public ImageLoader imageLoader;
            IntentFilter intentFilter;
            JSONArray jsArray = new JSONArray();
            JSONObject jsonObject;
            JSONObject json_data;
            String label;
            SimpleArcDialog mDialog;
            private Handler mUiHandler = new Handler();
            public TextView main_title;
            ImageView menu;
            String movieID;
            String movie_date;
            String movie_id;
            MyReceiver myReceiver;
            CheckBox mySwitch = null;
            WebView myWebView;
            String name;
            ImageView now_showing;
            JSONObject object1;
            ImageView profile;
            long remainedSecs;
            String responseString;
            final ArrayList<String> seat = new ArrayList();
            final ArrayList<String> seatData = new ArrayList();
            final ArrayList<String> seatFullData = new ArrayList();
            final ArrayList<String> seatHold = new ArrayList();
            final ArrayList<String> seatID = new ArrayList();
            SeatPlanAsyncTask seatPlanAsyncTask;
            String seat_count;
            String seat_plan;
            String selected;
            String showBookingID;
            TextView showtmeData;
            String source = "4";
            String status;
            TextView textTimer;
            TextView textView17;
            TextView textView18;
            TextView textView19;
            TextView textView20;
            TextView textView21;
            TextView textView23;
            TextView textView30;
            TextView textView32;
            TextView textView33;
            TextView textView34;
            TextView theater;
            String theaterData;
            String theater_id;
            ImageView theatres;
            String time;
            String time_slot;
            String timedata;
            TextView total_price;
            ImageView up_coming;
            String user_keyData;
            View view;
            String x_height;
            String x_value;
            String y_height;
            String y_value;

            class C03492 implements View.OnClickListener {
                C03492() {
                }

                public void onClick(View view) {
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.clickOneTest));
                    now_showing.getBackground().setAlpha(25);
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    FragmentManager fm = context.getFragmentManager();
                    fr = fm.findFragmentByTag(SyncStateContract.Constants.NOW_SHOWING_TAG);
                    if (fr == null) {
                        fr = new NowShowingFragment();
                    }
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fr, Constants.NOW_SHOWING_TAG);
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                    now_showing.setEnabled(false);
                    up_coming.setEnabled(true);
                    theatres.setEnabled(true);
                    profile.setEnabled(true);
                    menu.setEnabled(true);
                }
            }

            class C03503 implements View.OnClickListener {
                C03503() {
                }

                public void onClick(View view) {
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.clickOneTest));
                    up_coming.getBackground().setAlpha(45);
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    FragmentManager fm = context.getFragmentManager();
                    fr = fm.findFragmentByTag(SyncStateContract.Constants.UP_COMING_TAG);
                    if (fr == null) {
                        fr = new UpcomingMoviesFragment();
                    }
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fr, SyncStateContract.Constants.UP_COMING_TAG);
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                    now_showing.setEnabled(true);
                    up_coming.setEnabled(false);
                    theatres.setEnabled(true);
                    profile.setEnabled(true);
                    menu.setEnabled(true);
                }
            }

            class C03514 implements View.OnClickListener {
                C03514() {
                }

                public void onClick(View view) {
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.clickOneTest));
                    theatres.getBackground().setAlpha(45);
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    FragmentManager fm = context.getFragmentManager();
                    fr = fm.findFragmentByTag("theater");
                    if (fr == null) {
                        fr = new TheaterFragment();
                    }
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fr, "theater");
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                    now_showing.setEnabled(true);
                    up_coming.setEnabled(true);
                    theatres.setEnabled(false);
                    profile.setEnabled(true);
                    menu.setEnabled(true);
                }
            }

            class C03535 implements View.OnClickListener {
                C03535() {
                }

                public void onClick(View view) {
                    Settings.Global globalVariable = (Settings.Global) getApplicationContext();
                    String user_keyData = globalVariable.getUid();
                    if ("null".equals(user_keyData) || user_keyData == null) {
                        View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.popup_alert, null);
                        TextView textView1 = (TextView) deleteDialogView.findViewById(R.id.mes);
                        textView1.setText("Please login to view your profile");
                        textView1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                        Button btn = (Button) deleteDialogView.findViewById(R.id.btn);
                        btn.setText("OKAY");
                        btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                        final android.support.v7.app.AlertDialog deleteDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
                        deleteDialog.setView(deleteDialogView);
                        deleteDialogView.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                                startActivity(new Intent(this, LoginFragment.class));
                            }
                        });
                        deleteDialog.show();
                        return;
                    }
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.clickOneTest));
                    profile.getBackground().setAlpha(45);
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    Bundle bundle = new Bundle();
                    bundle.putString(AccessToken.USER_ID_KEY, user_keyData);
                    bundle.putString("first_name", globalVariable.getFirstName());
                    bundle.putString("last_name", globalVariable.getLast_name());
                    fr = new MyBookingFragment();
                    fr.setArguments(bundle);
                    FragmentManager fm = context.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fr, Constants.MY_BOOKING_TAG);
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                    now_showing.setEnabled(true);
                    up_coming.setEnabled(true);
                    theatres.setEnabled(true);
                    profile.setEnabled(false);
                    menu.setEnabled(true);
                }
            }

            class C03546 implements View.OnClickListener {
                C03546() {
                }

                public void onClick(View view) {
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.clickOneTest));
                    menu.getBackground().setAlpha(45);
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    Global globalVariable = (Settings.Global) getApplicationContext();
                    String user_keyData = globalVariable.getUid();
                    String first_name = globalVariable.getFirstName();
                    String last_name = globalVariable.getLast_name();
                    Bundle bundle = new Bundle();
                    bundle.putString(AccessToken.USER_ID_KEY, user_keyData);
                    bundle.putString("first_name", first_name);
                    bundle.putString("last_name", last_name);
                    FragmentManager fm = context.getFragmentManager();
                    fr = fm.findFragmentByTag(SyncStateContract.Constants.MENU_TAG);
                    if (fr == null) {
                        fr = new MenuFragment();
                        fr.setArguments(bundle);
                    } else {
                        fr.getArguments().putString(AccessToken.USER_ID_KEY, user_keyData);
                        fr.getArguments().putString("first_name", first_name);
                        fr.getArguments().putString("last_name", last_name);
                    }
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fr, Constants.MENU_TAG);
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                    now_showing.setEnabled(true);
                    up_coming.setEnabled(true);
                    theatres.setEnabled(true);
                    profile.setEnabled(true);
                    menu.setEnabled(false);
                }
            }

            class C03557 implements FragmentManager.OnBackStackChangedListener {
                C03557() {
                }

                public void onBackStackChanged() {
                    Fragment f = getFragmentManager().findFragmentById(R.id.fragment_place);
                    if (f != null) {
                        updateTitleAndDrawer(f);
                    }
                }
            }

            class SeatPlanAsyncTask extends AsyncTask<Void, Integer, Void> {
                SimpleArcDialog mDialog;
                boolean running;

                class C03591 implements Runnable {
                    C03591() {
                    }

                    public void run() {
                        View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
                        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                        ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                        ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                        final AlertDialog deleteDialog = new Builder(this).create();
                        deleteDialog.setView(deleteDialogView);
                        deleteDialogView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });
                    }
                }

                class C03602 implements Runnable {
                    C03602() {
                    }

                    public void run() {
                        myWebView.loadData(html_desing, "text/html", "UTF-8");
                    }
                }

                class C03623 implements Runnable {
                    C03623() {
                    }

                    public void run() {
                        View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
                        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                        ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                        ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                        final AlertDialog deleteDialog = new Builder(this).create();
                        deleteDialog.setView(deleteDialogView);
                        deleteDialogView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        });
                        deleteDialog.show();
                    }
                }

                SeatPlanAsyncTask() {
                }

                protected Void doInBackground(Void... params) {
                    try {
                        Intent intent = getIntent();
                        movieID = intent.getStringExtra("movieID");
                        Resources resource = getResources();
                        String accessToken_id = resource.getString(R.string.Authorization);
                        String online_server_url = resource.getString(R.string.base_url);
                        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("movie_id", movie_id)
                                .addFormDataPart(TheaterFragment.TAG_ID, theater_id)
                                .addFormDataPart("movie_date", movie_date)
                                .addFormDataPart("time_slot", time_slot)
                                .addFormDataPart("req_seat_count", seat_count)
                                .build();
                        System.out.println("movie_id " + movie_id);
                        System.out.println("theater_id " + theater_id);
                        System.out.println("movie_date " + movie_date);
                        System.out.println("time_slot " + time_slot);
                        System.out.println("req_seat_count " + seat_count);
                        Request request = new Request.Builder().url(online_server_url + "buy_tickets/seat_plan")
                                .post(requestBody)
                                .addHeader("Authorization", accessToken_id)
                                .build();
                        System.out.println("requestBody >>> " + requestBody);
                        Response response = ApiRequestClient.getOkHttpClient(this).newCall(request).execute();
                        responseString = response.body().string();
                        response.body().close();
                        Log.e("Fail 1", e.toString());
                        mUiHandler.post(new C03591());
                    }
                    return null;
                }

                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                protected void onPreExecute() {
                    super.onPreExecute();
                    running = true;
                    mDialog = new SimpleArcDialog(this);
                    mDialog.setConfiguration(new ArcConfiguration(this));
                    mDialog.show();
                }

                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    try {
                        json_data = new JSONObject(responseString);
                        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(json_data.getString("status"))) {
                            jsonObject = json_data.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                            JSONArray seat_plan_array = jsonObject.getJSONArray(Constants.SEAT_PLAN_TAG);
                            for (int x = 0; x < seat_plan_array.length(); x++) {
                                JSONObject object = seat_plan_array.getJSONObject(x);
                                seatData.clear();
                                y_value = object.getString("y");
                                x_value = object.getString("x");
                                label = object.getString("label");
                                String id = object.getString("id");
                                object1 = new JSONObject();
                                object1.put("label", label);
                                object1.put("y_value", y_value);
                                object1.put("x_value", x_value);
                                arraySeat.put(object1);
                                seatFullData.add(label);
                                status = object.getString("status");
                                String btn_class = "";
                                if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(status)) {
                                    seat_plan = "  <button id='13787'  data-seatid='A22' class='btn' style='height:25px;width:25px; font-size: 5px;border:none; border-radius: 5px;    background-color:#0b98a7; margin-top:2px;margin-left:26px; position: absolute;'A22'</button>";
                                    seat.add(seat_plan);
                                } else if (AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(status)) {
                                    seat_plan = "  <button id=" + label + "  data-seatid=" + label + "  style=\"height:25px;width:25px; font-size: 5px;border:none; border-radius: 5px;    background-color:#cb5a5e; margin-top:" + y_value + "px;margin-left:" + x_value + "px; position: absolute;\"> " + label + "         </button>";
                                    seat.add(seat_plan);
                                }
                            }
                            JSONArray seat_plan_book = jsonObject.getJSONArray("seat_plan_booked");
                            JSONArray seat_plan_hold = jsonObject.getJSONArray("seat_plan_hold");
                            for (int a = 0; a < seat_plan_hold.length(); a++) {
                                seatHold.add(seat_plan_hold.getString(a));
                            }
                            String seat_plan_html = seat.toString().replaceAll(",", "").toString().replace("[", "").replace("]", "").replace("", "");
                            String seat_plan_hold_js = seat_plan_hold.toString().replace("[", "").replace("]", "").replace("", "");
                            String seat_plan_book_js = seat_plan_book.toString().replace("[", "").replace("]", "").replace("", "");
                            html_desing =
                                    "<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script> " +
                                    "<script type='text/javascript'>function showAndroidToast(toast) { Android.showToast(toast);}</script>" +
                                    "  <script type='text/javascript'>function showBookingID(toast) { Android.showBookingID(toast);}</script> " +
                                    "  <script type='text/javascript'>function alertShow(toast) { Android.alertShow(toast);}</script>  " +
                                    " <script type='text/javascript'>function alertClose(toast) { Android.alertClose(toast);}</script> " +
                                    " <script type='text/javascript'>function maxSeat(toast) { Android.maxSeat(toast);}</script><div class=\"seatPlan\">\n" + seat_plan_html + "</div>  " +
                                    "<script>  " + "var holdString = '" + seat_plan_hold_js + "';" + "var arrayString=holdString.replace(/['\"]+/g, '');"
                                    + "var holdArray = arrayString.split(',');" + " $('.seatPlan').find('button').each(function( index, element ) " +
                                    "{" + "for (var i = 0; i<holdArray.length; i++){" + "var holdSeat=holdArray[i];" + "var holdSeatid=$(this).data('seatid');"
                                    + " if(holdSeatid == holdSeat ) " +
                                    " {" + "$(this).css({'background-color':'#ededed','color':'#000 !important'}).prop('disabled', true);" + "}else{" + "}" + "}"
                                    + "});" + "" + "var bookString = '" + seat_plan_book_js + "';" + "var arrayStringBook=bookString.replace(/['\"]+/g, '');"
                                    + "var bookArray = arrayStringBook.split(',');" + " $('.seatPlan').find('button').each(function( index, element )" +
                                    " {" + "for (var i = 0; i<bookArray.length; i++){" + "var bookSeat=bookArray[i];" + "var bookSeatid=$(this).data('seatid');" +
                                    " if(bookSeatid == bookSeat )  {" + "$(this).css('background-color','#ee0c6e').prop('disabled', true);" + "}" +
                                    "else{" + "}" + "}" + "});" + "var booking_id = '';" + "var seatCount=0;" + "var clickedCount=1;" +
                                    "var seatArray = new Array();" + "$('.btn').on('click',function(e){" + "var Seatid = $(this).data('seatid');"
                                    + "var added=false;"
                                    + "$.map(seatArray, function(elementOfArray, indexInArray)" +
                                    " {" + " if (elementOfArray == Seatid) {" + "   added = true;" + " }" + "});"
                                    + "if (!added) {" + "  seatArray.push(Seatid);" + "showAndroidToast(Seatid);"
                                    + "console.log('not_added'); " + " console.log('seat_array_after_added '+seatArray);" + "}" +
                                    "else{" + "console.log('added'); " + "showAndroidToast(Seatid);"
                                    + " console.log('seat_array_before_delete '+seatArray);" +
                                    " var found = jQuery.inArray(Seatid, seatArray);" + " seatArray.splice(found, 1);"
                                    + " console.log('seat_array_after_delete '+seatArray);"
                                    + "   added = false;" + "$(this).toggleClass('active');"
                                    + "clickedCount=parseInt(clickedCount)-1;" + "console.log('seat selected '+Seatid);"
                                    + "$.ajax({" + "method: 'POST'," + "dataType: 'json'," + "crossDomain: true,"
                                    + "url:'https://www.scopecinemas.com/ajax/select_seat/index'," +
                                    "headers:{" + "'Authorization':'wssko8s4k0ccsks8oc8cc00oc0o4g0sookc880k8'," + "},"
                                    + "data:{" + "'booking_id':booking_id,"
                                    + "'selected' : Seatid," +
                                    "'theater' : '" + theater_id + "'," +
                                    "'movie' : '" + movie_id + "'," +
                                    "'movie_date' : '" + movie_date + "',"
                                    + "'status' : '" + hold + "',"
                                    + "'time_slot' : '" + time_slot + "',"
                                    + "'source' : '4'" + "}" + "}).done(function(data){" + "});" + "return false;" + "}"
                                    + "seatCount='" + seat_count + "';" + "console.log('go_down'); "
                                    + "if(Number(seatCount) < Number(clickedCount)){" + "console.log('seat_plan_exceed');"
                                    + " var found = jQuery.inArray(Seatid, seatArray);" + " seatArray.splice(found, 1);"
                                    + " console.log('seat_array_before_message '+seatArray);"
                                    + "showAndroidToast(Seatid);"
                                    + "maxSeat('show');"
                                    + "return false;" + "}"
                                    + "$(this).toggleClass('active');"
                                    + "clickedCount=parseInt(clickedCount)+1;"
                                    + "alertShow('show');"
                                    + "$.ajax({" + "method: 'POST'," + "dataType: 'json',"
                                    + "crossDomain: true," + "url:'https://www.scopecinemas.com/ajax/select_seat/index',"
                                    + "headers:{" + "'Authorization':'wssko8s4k0ccsks8oc8cc00oc0o4g0sookc880k8'," + "},"
                                    + "data:{" + "'booking_id':booking_id,"
                                    + "'selected' : Seatid," + "'theater' : '"
                                    + theater_id + "'," +
                                    "'movie' : '" + movie_id + "',"
                                    + "'movie_date' : '" + movie_date + "',"
                                    + "'status' : '" + hold + "',"
                                    + "'time_slot' : '" + time_slot + "',"
                                    + "'source' : '4'" + "}" + "}).done(function(data){"
                                    + "if (data.status){" + "alertClose('show');"
                                    + "booking_id = data.data.booking_id;"
                                    + "showBookingID(data.data.booking_id);"
                                    + "var movie_id   = data.data.movie_id;"
                                    + "var theater_id = data.data.theater_id;"
                                    + "var time_slot  = data.data.time_slot;"
                                    + "var unavailable_seats  = data.unavailable_seats;"
                                    + "" + "for (var i in unavailable_seats){"
                                    + "unavailable_seats[i].label;"
                                    + "unavailable_seats[i].booking_id;"
                                    + "var hold_id = unavailable_seats[i].label ;"
                                    + "var status = unavailable_seats[i].status ;" + ""
                                    + " $('.seatPlan').find('button').each(function( index, element ) {"
                                    + "  if ($(this).attr('id') == hold_id ) {"
                                    + "if(status == 1){" + "  $(this).css({"
                                    + " 'background-color':'#ee0c6e'" + "  });" + "}else{"
                                    + "  $(this).css({" + " 'background-color':'#ededed'" + "  });"
                                    + "}" + "" + "  }" + "   });" + "}" + "}" +
                                    "else{" + "var unavailable_seats  = data.unavailable_seats;"
                                    + "for (var i in unavailable_seats){" + "unavailable_seats[i].label;"
                                    + "unavailable_seats[i].booking_id;" + "var hold_id = 'unavailable_seats[i].label' ;"
                                    + "" + "" + " $('.seatPlan').find('button').each(function( index, element ) " +
                                    "{" + "" + "  if ($(this).attr('id')== hold_id )" +
                                    " {" + "  $(this).css({" + " 'background-color':'red'" + "  });" +
                                    "" + "  }" + "   });" + "}" + "}" + "}).error(function(data){"
                                    + "alertClose('show');" + "console.log(JSON.stringify(data));"
                                    + "var hold_id= 'A5' ;" + "" + "" + " $('.seatPlan').find('button').each(function( index, element )" +
                                    " {" + "" + "    if ($(this).attr('id')== hold_id ) {" + "  $(this).css({" + " 'background-color':'black'" + "  });"
                                    + "" + "  }" + "   });" + "" + "})" + "}); </script> ";
                            mUiHandler.post(new C03602());
                        }
                        JSONArray price_object = jsonObject.getJSONArray("price_object");
                        for (int n = 0; n < price_object.length(); n++) {
                            JSONObject jsonObject1 = price_object.getJSONObject(n);
                            if (jsonObject1.getString("category_name").equals("Adult")) {
                                adultPrice = jsonObject1.getString("price");
                            } else {
                                childPrice = jsonObject1.getString("price");
                            }
                            double AdultPrice = Double.parseDouble(adultPrice) * Double.parseDouble(seat_count);
                            total_price = (TextView) findViewById(R.id.total_price);
                            System.out.println("double : " + String.format("%.2f", new Object[]{Double.valueOf(AdultPrice)}));
                            Spannable spannableString = new SpannableString("TOTAL Rs. " + String.format("%.2f", new Object[]{Double.valueOf(AdultPrice)}));
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#52719b")), 0, 5, 33);
                            total_price.setText(spannableString);
                            System.out.println("adultPrice " + adultPrice);
                            System.out.println("childPrice " + childPrice);
                        }
                    } catch (Exception e) {
                        mUiHandler.post(new C03623());
                        e.printStackTrace();
                    }
                    mDialog.cancel();
                }
            }

            public class WebAppInterface {
                Context mContext;

                WebAppInterface(Context c) {
                    mContext = c;
                }

                @JavascriptInterface
                public void showToast(String toast) throws JSONException {
                    selected = toast;
                    if (seatID.contains(toast)) {
                        seatID.remove(toast);
                    } else {
                        count++;
                        seatID.add(toast);
                        System.out.println("seatID >>>" + seatID);
                    }
                    if ("no".equals(hitBtn)) {
                        startTimer(300000, 1000);
                    }
                }

                @JavascriptInterface
                public void showBookingID(String toast) throws JSONException {
                    booking_id = toast;
                    globalVariable.setBookingID(toast);
                    System.out.println("globalVariable >>>>><<<< " + globalVariable.getBookingID());
                }

                @JavascriptInterface
                public void alertShow(String toast) throws JSONException {
                    mDialog = new SimpleArcDialog(this);
                    mDialog.setConfiguration(new ArcConfiguration(this));
                    mDialog.setCancelable(false);
                    mDialog.show();
                }

                @JavascriptInterface
                public void alertClose(String toast) throws JSONException {
                    mDialog.cancel();
                }

                @JavascriptInterface
                public void maxSeat(String toast) throws JSONException {
                    View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
                    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                    TextView textView1 = (TextView) deleteDialogView.findViewById(R.id.textView1);
                    textView1.setText("Sorry you can only select " + seat_count + " seats");
                    textView1.setTypeface(font);
                    ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
                    deleteDialog.setView(deleteDialogView);
                    deleteDialogView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            deleteDialog.dismiss();
                        }
                    });
                    deleteDialog.show();
                }
            }

            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash);
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
                ((Global) getApplicationContext()).seatPlanFragment = this;
                ((LinearLayout) findViewById(R.id.mainMenu)).setVisibility(0);
                ((FrameLayout) findViewById(R.id.fragment_place)).addView(getLayoutInflater().inflate(R.layout.seat_plan_fragment, null));
                ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
                final ScrollView scrollView = sv;
                sv.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(33);
                    }
                });
                Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                Typeface heavy = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
                Typeface heavyTwo = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf");
                globalVariable = (Global) getApplicationContext();
                Intent intent = getIntent();
                movie_id = intent.getStringExtra("movie_id");
                theater_id = intent.getStringExtra(TheaterFragment.TAG_ID);
                movie_date = intent.getStringExtra("movie_date");
                time_slot = intent.getStringExtra("time_slot");
                auth_token = intent.getStringExtra("auth_token");
                bannerData = intent.getStringExtra("banner");
                theaterData = intent.getStringExtra("theater");
                timedata = intent.getStringExtra("timedata");
                name = intent.getStringExtra("name");
                dateMovie = intent.getStringExtra("movie_date");
                seat_count = intent.getStringExtra(MyBookingFragment.SEAT_COUNT);
                textTimer = (TextView) findViewById(R.id.textView25);
                textTimer.setTypeface(normal);
                now_showing = (ImageView) findViewById(R.id.now_showing);
                up_coming = (ImageView) findViewById(R.id.up_coming);
                theatres = (ImageView) findViewById(R.id.theatres);
                profile = (ImageView) findViewById(R.id.profile);
                menu = (ImageView) findViewById(R.id.menu);
                now_showing.setOnClickListener(new C03492());
                up_coming.setOnClickListener(new C03503());
                theatres.setOnClickListener(new C03514());
                profile.setOnClickListener(new C03535());
                menu.setOnClickListener(new C03546());
                getFragmentManager().addOnBackStackChangedListener(new C03557());

                myWebView = (WebView) findViewById(R.id.webView);
                myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                myWebView.getSettings().setUseWideViewPort(true);
                myWebView.getSettings().setSupportZoom(true);
                myWebView.getSettings().setBuiltInZoomControls(true);
                myWebView.getSettings().setDisplayZoomControls(false);
                myWebView.getSettings().setBuiltInZoomControls(true);
                myWebView.setBackgroundColor(0);
                myWebView.setScrollbarFadingEnabled(false);
                myWebView.getSettings().setUseWideViewPort(true);
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setDomStorageEnabled(true);
                myWebView.getSettings().setAppCacheEnabled(true);
                myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
                myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
                myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");


                main_title = (TextView) findViewById(R.id.main_title);
                TextView textView26 = (TextView) findViewById(R.id.textView26);
                TextView textView27 = (TextView) findViewById(R.id.textView27);
                TextView textView28 = (TextView) findViewById(R.id.textView28);
                TextView textView31 = (TextView) findViewById(R.id.textView31);
                TextView textView29 = (TextView) findViewById(R.id.textView29);
                TextView seatLabel = (TextView) findViewById(R.id.seatLabel);
                ((TextView) findViewById(R.id.subseatLabel)).setTypeface(normal);
                TextView catFull = (TextView) findViewById(R.id.catFull);
                ((TextView) findViewById(R.id.catHalf)).setTypeface(heavy);
                catFull.setTypeface(heavy);
                TextView catTwo = (TextView) findViewById(R.id.catTwo);
                TextView catThree = (TextView) findViewById(R.id.catThree);
                TextView catfour = (TextView) findViewById(R.id.catFour);
                ((TextView) findViewById(R.id.catOne)).setTypeface(normal);
                catTwo.setTypeface(normal);
                catThree.setTypeface(normal);
                catfour.setTypeface(normal);
                myReceiver = new MyReceiver();
                intentFilter = new IntentFilter("ss");
                main_title.setTypeface(normal);
                textView26.setTypeface(normal);
                textView27.setTypeface(normal);
                textView28.setTypeface(normal);
                textView31.setTypeface(normal);
                textView29.setTypeface(normal);
                seatLabel.setTypeface(normal);
                textView32 = (TextView) findViewById(R.id.textView32);
                try {
                    String _24HourTime = timedata;
                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                    Date _24HourDt = _24HourSDF.parse(_24HourTime);
                    System.out.println(_24HourDt);
                    System.out.println(_12HourSDF.format(_24HourDt));
                    textView32.setText(_12HourSDF.format(_24HourDt).toLowerCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView33 = (TextView) findViewById(R.id.textView33);
                textView34 = (TextView) findViewById(R.id.textView34);
                textView30 = (TextView) findViewById(R.id.textView30);
                TextView countData = (TextView) findViewById(R.id.countData);
                final TextView adultCount = (TextView) findViewById(R.id.adultCount);
                TextView halfCount = (TextView) findViewById(R.id.halfCount);
                textView33.setText(name);
                textView34.setText(theaterData);
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateMovie);
                    String dateString = new SimpleDateFormat("dd").format(date);
                    System.out.println(dateString);
                    String datecomplety = new SimpleDateFormat("MMMM yyy").format(date);
                    System.out.println(datecomplety);
                    if (date.getDate() > 10 && date.getDate() < 14) {
                        dateString = dateString + "th, ";
                    } else if (dateString.endsWith(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                        dateString = dateString + "st, ";
                    } else if (dateString.endsWith("2")) {
                        dateString = dateString + "nd, ";
                    } else if (dateString.endsWith("3")) {
                        dateString = dateString + "rd, ";
                    } else {
                        dateString = dateString + "th, ";
                    }
                    System.out.println("Check " + dateString);
                    textView30.setText(dateString + "" + datecomplety);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                countData.setText("TOTAL TICKETS - " + seat_count);
                adultCount.setText(seat_count);
                textView33.setTypeface(normal);
                textView34.setTypeface(normal);
                textView32.setTypeface(normal);
                textView30.setTypeface(normal);
                countData.setTypeface(heavy);
                ImageView fullPlus = (ImageView) findViewById(R.id.fullPlus);
                ImageView halfMinus = (ImageView) findViewById(R.id.halfMinus);
                ImageView halfPlus = (ImageView) findViewById(R.id.halfPlus);
                final TextView textView = halfCount;
                final Typeface typeface = heavyTwo;
                ((ImageView) findViewById(R.id.fullMinus)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (!AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(adultCount.getText().toString())) {
                            adultCount.setText(String.valueOf(Integer.parseInt(adultCount.getText().toString()) - 1));
                            textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString()) + 1));
                            double total = (Double.parseDouble(adultPrice) * Double.parseDouble(adultCount.getText().toString())) + (Double.parseDouble(childPrice) * Double.parseDouble(textView.getText().toString()));
                            Spannable wordtoSpan = new SpannableString("TOTAL Rs. " + String.format("%.2f", new Object[]{Double.valueOf(total)}));
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#52719b")), 0, 5, 33);
                            total_price.setText(wordtoSpan);
                            total_price.setTypeface(typeface);
                        }
                    }
                });
                textView = halfCount;
                typeface = heavyTwo;
                fullPlus.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(textView.getText().toString())) {
                            adultCount.setText(String.valueOf(Integer.parseInt(adultCount.getText().toString()) + 1));
                            textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString()) - 1));
                            double total = (Double.parseDouble(adultPrice) * Double.parseDouble(adultCount.getText().toString())) + (Double.parseDouble(childPrice) * Double.parseDouble(textView.getText().toString()));
                            Spannable wordtoSpan = new SpannableString("TOTAL Rs. " + String.format("%.2f", new Object[]{Double.valueOf(total)}));
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#52719b")), 0, 5, 33);
                            total_price.setText(wordtoSpan);
                            total_price.setTypeface(typeface);
                        }
                    }
                });
                textView = halfCount;
                typeface = heavyTwo;
                halfMinus.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(textView.getText().toString())) {
                            textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString()) - 1));
                            adultCount.setText(String.valueOf(Integer.parseInt(adultCount.getText().toString()) + 1));
                            double total = (Double.parseDouble(adultPrice) * Double.parseDouble(adultCount.getText().toString())) + (Double.parseDouble(childPrice) * Double.parseDouble(textView.getText().toString()));
                            Spannable wordtoSpan = new SpannableString("TOTAL Rs. " + String.format("%.2f", new Object[]{Double.valueOf(total)}));
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#52719b")), 0, 5, 33);
                            total_price.setText(wordtoSpan);
                            total_price.setTypeface(typeface);
                        }
                    }
                });
                textView = halfCount;
                typeface = heavyTwo;
                halfPlus.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(adultCount.getText().toString())) {
                            textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString()) + 1));
                            adultCount.setText(String.valueOf(Integer.parseInt(adultCount.getText().toString()) - 1));
                            double total = (Double.parseDouble(adultPrice) * Double.parseDouble(adultCount.getText().toString())) + (Double.parseDouble(childPrice) * Double.parseDouble(textView.getText().toString()));
                            Spannable wordtoSpan = new SpannableString("TOTAL Rs. " + String.format("%.2f", new Object[]{Double.valueOf(total)}));
                            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#52719b")), 0, 5, 33);
                            total_price.setText(wordtoSpan);
                            total_price.setTypeface(typeface);
                        }
                    }
                });
                seatPlanAsyncTask = new SeatPlanAsyncTask();
                seatPlanAsyncTask.execute(new Void[0]);
                Button btn = (Button) findViewById(R.id.btn);
                btn.setTypeface(heavy);
                textView = halfCount;
                btn.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (seatID.size() == 0) {
                            View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.popup_alert, null);
                            TextView textView1 = (TextView) deleteDialogView.findViewById(R.id.mes);
                            textView1.setText("Please select the seats");
                            textView1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                            Button btn = (Button) deleteDialogView.findViewById(R.id.btn);
                            btn.setText("OKAY");
                            btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                            final android.support.v7.app.AlertDialog deleteDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
                            deleteDialog.setView(deleteDialogView);
                            deleteDialogView.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    deleteDialog.dismiss();
                                }
                            });
                            deleteDialog.show();
                            return;
                        }
                        if (seatID.size() < Integer.parseInt(seat_count)) {
                            int seatTotal = Integer.parseInt(seat_count) - seatID.size();
                            deleteDialogView = LayoutInflater.from(this).inflate(R.layout.popup_alert, null);
                            textView1 = (TextView) deleteDialogView.findViewById(R.id.mes);
                            textView1.setText("Select " + seatTotal + " more seats to proceed further");
                            textView1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                            btn = (Button) deleteDialogView.findViewById(R.id.btn);
                            btn.setText("OKAY");
                            btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                            deleteDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
                            deleteDialog.setView(deleteDialogView);
                            deleteDialogView.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    deleteDialog.dismiss();
                                }
                            });
                            deleteDialog.show();
                            return;
                        }
                        try {
                            String timein12Format = new SimpleDateFormat("K:mm a").format(new SimpleDateFormat("H:mm").parse(timedata));
                            Intent intent = new Intent(this, SummeryFragment.class);
                            intent.putExtra("movie_date", movie_date);
                            intent.putExtra("theater", theater_id);
                            intent.putExtra("theater_name", textView34.getText().toString());
                            intent.putExtra(MyBookingFragment.TIME, timein12Format);
                            intent.putExtra("name", movie_id);
                            intent.putExtra("date", textView30.getText().toString());
                            intent.putExtra("banner", bannerData);
                            intent.putExtra(MyBookingFragment.SEAT_COUNT, seat_count);
                            intent.putExtra("seatID", seatID);
                            intent.putExtra("adultCount", adultCount.getText().toString());
                            intent.putExtra("halfCount", textView.getText().toString());
                            intent.putExtra("totalPrice", total_price.getText().toString());
                            intent.putExtra("booking_id", booking_id);
                            intent.putExtra("adultPrice", adultPrice);
                            intent.putExtra("childPrice", childPrice);
                            startActivity(intent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            public void startTimer(long finish, long tick) {
                hitBtn = "yes";
                CountDownTimer t = new CountDownTimer(finish, tick) {

                    class C03441 implements Runnable {
                        C03441() {
                        }

                        public void run() {
                            textTimer.setText("You have " + (remainedSecs / 60) + ":" + (remainedSecs % 60) + " to complete the booking");
                        }
                    }

                    class C03452 implements Runnable {
                        C03452() {
                        }

                        public void run() {
                            textTimer.setText("00:00");
                        }
                    }

                    public void onTick(long millisUntilFinished) {
                        remainedSecs = millisUntilFinished / 1000;
                        mUiHandler.post(new C03441());
                    }

                    public void onFinish() {
                        sendBroadcast(new Intent("ss").putExtra("movieID", movie_id).putExtra(MyBookingFragment.BANNER, bannerData));
                        try {
                            Log.e("app", "Activity name:" + getPackageManager().getActivityInfo(getComponentName(), 0).name);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (getWindow().getDecorView().isShown()) {
                            mUiHandler.post(new C03452());
                            cancel();
                        }
                    }
                }.start();
            }

            private void updateTitleAndDrawer(Fragment fragment) {
                String fragClassName = fragment.getClass().getName();
                System.out.println("fragClassName " + fragClassName);
                if (fragClassName.equals(NowShowingFragment.class.getName())) {
                    now_showing.setBackgroundColor(Color.parseColor("#687788"));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(UpcomingMoviesFragment.class.getName())) {
                    up_coming.setBackgroundColor(Color.parseColor("#687788"));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(TheaterFragment.class.getName())) {
                    theatres.setBackgroundColor(Color.parseColor("#687788"));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(MyBookingFragment.class.getName())) {
                    profile.setBackgroundColor(Color.parseColor("#687788"));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(MenuFragment.class.getName())) {
                    menu.setBackgroundColor(Color.parseColor("#687788"));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(LoginFragment.class.getName())) {
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                } else if (fragClassName.equals(HomeFragment.class.getName())) {
                    menu.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    now_showing.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    profile.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    up_coming.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                    theatres.setBackgroundColor(ContextCompat.getColor(this, R.color.def));
                }
            }

            public void onBackPressed() {
                if (seatID.size() == 0) {
                    super.onBackPressed();
                    return;
                }
                System.out.println("booking_id " + booking_id);
                try {
                    Resources resource = getResources();
                    Request request = new Request.Builder().url(resource.getString(R.string.base_url) + "buy_tickets/cancel_booking?booking_id=" + booking_id).addHeader("Authorization", resource.getString(R.string.Authorization)).build();
                    Log.d(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, request.toString());
                    Response response = ApiRequestClient.getOkHttpClient(this).newCall(request).execute();
                    String responseString = response.body().string();
                    response.body().close();
                    System.out.println(responseString);
                    json_data = new JSONObject(responseString);
                    if (json_data.getString("status").equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                        super.onBackPressed();
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.popup_alert, null);
                                TextView textView1 = (TextView) deleteDialogView.findViewById(R.id.mes);
                                textView1.setText("There was an error while connecting to server. Please check your connection settings.");
                                textView1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                                Button btn = (Button) deleteDialogView.findViewById(R.id.btn);
                                btn.setText("OKAY");
                                btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf"));
                                final android.support.v7.app.AlertDialog deleteDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
                                deleteDialog.setView(deleteDialogView);
                                deleteDialogView.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
                                    public void onClick(View v) {
                                        deleteDialog.dismiss();
                                    }
                                });
                                deleteDialog.show();
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                    mUiHandler.post(new Runnable() {
                        public void run() {
                            View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
                            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                            ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                            ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                            final AlertDialog deleteDialog = new Builder(this).create();
                            deleteDialog.setView(deleteDialogView);
                            deleteDialogView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    deleteDialog.dismiss();
                                }
                            });
                            deleteDialog.show();
                        }
                    });
                }
            }

            protected void onResume() {
                super.onResume();
                registerReceiver(myReceiver, intentFilter);
            }

            protected void onPause() {
                super.onPause();
                unregisterReceiver(myReceiver);
            }
        }*/