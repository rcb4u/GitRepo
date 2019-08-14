package com.example.rspl_rahul.gitrepo.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Utils.ArcConfiguration;
import com.example.rspl_rahul.gitrepo.Utils.SimpleArcDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

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
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
        final ScrollView scrollView = sv;
        sv.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(10);
            }
        });
        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        Typeface heavy = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        Typeface heavyTwo = Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Book.ttf");

        movie_id = "107";
        theater_id = "8";
        movie_date = "2019-01-03";
        time_slot = "10:30:00";
        //auth_token = intent.getStringExtra("auth_token");
        bannerData = "http:\\/\\/dev2.appslanka.com\\/scope\\/images\\/movie\\/Robin-Hood-1800x1200.jpg";
        theaterData = "Liberty Screen 02";
        timedata = "10:30:00";
        name = "Robin Hood";
        dateMovie = "2019-01-03";
        seat_count = "1";
        //timer code written
        textTimer = (TextView) findViewById(R.id.textView25);
        textTimer.setTypeface(normal);
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {

                long seconds=(millisUntilFinished/1000)%60;

                long minutes=((millisUntilFinished-seconds)/1000)/60;
                textTimer.setText("seconds remaining: "+minutes +":"+seconds);
            }
            public void onFinish() {
                textTimer.setText("done!");
            }
        }.start();


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
            } else if (dateString.endsWith("1")) {
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

        seatPlanAsyncTask = new SeatPlanAsyncTask();
        seatPlanAsyncTask.execute(new Void[0]);
    }

    class SeatPlanAsyncTask extends AsyncTask<Void, Integer, Void> {
        SimpleArcDialog mDialog;
        boolean running;

        class C03591 implements Runnable {
            C03591() {
            }

            public void run() {
                View deleteDialogView = LayoutInflater.from(SeatPlanActivity.this).inflate(R.layout.custom_dialog, null);
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                final AlertDialog deleteDialog = new AlertDialog.Builder(SeatPlanActivity.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
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
                View deleteDialogView = LayoutInflater.from(SeatPlanActivity.this).inflate(R.layout.custom_dialog, null);
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
                ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                final AlertDialog deleteDialog = new AlertDialog.Builder(SeatPlanActivity.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
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
                try {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
                    InputStream is = SeatPlanActivity.this.getResources().getAssets().open("latest.crt");
                    InputStream caInput = new BufferedInputStream(is);
                    Certificate ca;
                    try {
                        ca = cf.generateCertificate(caInput);
                        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                    } finally {
                        caInput.close();
                    }
                    // Create a KeyStore containing our trusted CAs
                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);
                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);
                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLSv1.2");
                    context.init(null, tmf.getTrustManagers(), null);
                    // Tell the URLConnection to use a SocketFactory from our SSLContext
                    SocketFactory sf = SSLSocketFactory.getDefault();
                    SSLSocket socket = (SSLSocket) sf.createSocket("api-dev.fyi.lk", 9443);
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    SSLSession s = socket.getSession();
                    socket.close();
                    HostnameVerifier allHostsValid = new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    };
                    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                    // At this point SSLSocket performed certificate verificaiton and
                    // we have performed hostname verification, so it is safe to proceed.
                    URL url = new URL("https://api-dev.fyi.lk:8243/scope/1.0/get_seats_plan?token=UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O");
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setRequestProperty("Authorization", "Bearer 232d244b-d35f-3918-b82a-c1a9b9e2ec29");
                    urlConnection.setSSLSocketFactory(context.getSocketFactory());
                    urlConnection.setRequestMethod("POST");
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(getbodyjson().getBytes());
                    os.flush();

                    if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            StringBuilder result = new StringBuilder();
                            result.append(line);
                            Log.w("responseString" +
                                    "", "" + result.toString());
                            responseString = result.toString();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
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
            mDialog = new SimpleArcDialog(SeatPlanActivity.this);
            mDialog.setConfiguration(new ArcConfiguration(SeatPlanActivity.this));
            mDialog.show();
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                json_data = new JSONObject(responseString);
                if ("true".equals(json_data.getString("status"))) {
                    jsonObject = json_data.getJSONObject("data");
                    JSONArray seat_plan_array = jsonObject.getJSONArray("seat_plan");
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
                        if ("1".equals(status)) {
                            seat_plan = "  <button id=" + label + "  data-seatid=" + label + "  class=\"btn\" style=\"height:25px;width:25px; font-size: 5px;border:none; border-radius: 5px;    background-color:#0b98a7; margin-top:" + y_value + "px;margin-left:" + x_value + "px; position: absolute;\"  > " + label + "</button>";
                            seat.add(seat_plan);
                        } else if ("0".equals(status)) {
                            seat_plan = "  <button id=" + label + "  data-seatid=" + label + " style=\"height:25px;width:25px; font-size: 5px;border:none; border-radius: 5px;    background-color:#cb5a5e; margin-top:" + y_value + "px;margin-left:" + x_value + "px; position: absolute;\"> " + label + "</button>";
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
                            "<style>button:disabled{color:#000000;}.active{background-color:#2ac853!important;}</style>" +
                            "<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>" +
                            "<script type='text/javascript'>function showAndroidToast(toast){ Android.showToast(toast);}</script>" +
                            "<script type='text/javascript'>function showBookingID(toast) { Android.showBookingID(toast);}</script>" +
                            "<script type='text/javascript'>function alertShow(toast) { Android.alertShow(toast);}</script>" +
                            "<script type='text/javascript'>function alertClose(toast) { Android.alertClose(toast);}</script>" +
                            "<script type='text/javascript'>function maxSeat(toast) { Android.maxSeat(toast);}</script>" +
                            "<div class=\"seatPlan\">\n" + seat_plan_html + "</div>"
                            + "<script>" + "var holdString = '" + seat_plan_hold_js + "';" +
                                    "var arrayString=holdString.replace(/['\"]+/g, '');" +
                                    "var holdArray = arrayString.split(',');" +
                                    " $('.seatPlan').find('button').each(function( index, element ) " +
                                    "{" + "for (var i = 0; i<holdArray.length; i++)" +
                                    "{" + "var holdSeat=holdArray[i];" +
                                    "var holdSeatid=$(this).data('seatid');" +
                                    " if(holdSeatid == holdSeat ) " +
                                    " {" + "$(this).css({'background-color':'#ededed','color':'#000 !important'}).prop('disabled', true);" + "}" +
                                    "else{" + "}" + "}" + "});" +
                                    "" + "var bookString = '" + seat_plan_book_js + "';" +
                                    "var arrayStringBook=bookString.replace(/['\"]+/g, '');" +
                                    "var bookArray = arrayStringBook.split(',');" +

                                    " $('.seatPlan').find('button').each(function( index, element ) " +
                                    "{" + "for (var i = 0; i<bookArray.length; i++)" +
                                    "{" + "var bookSeat=bookArray[i];" + "var bookSeatid=$(this).data('seatid');" +
                                    " if(bookSeatid == bookSeat ) " +
                                    " {" + "$(this).css('background-color','#ee0c6e').prop('disabled', true);" + "}" +
                                    "else{" + "}" + "}" + "});" +

                                    "var booking_id = '';" + "var seatCount=0;" + "var clickedCount=1;" +
                                    "var seatArray = new Array();" + "$('.btn').on('click',function(e)" +
                                    "{" + "var Seatid = $(this).data('seatid');" +
                                    "var added=false;" +"var added=false;" +
                                    "$.map(seatArray, function(elementOfArray, indexInArray) {" +
                                    " if (elementOfArray == Seatid) {" + "   added = true;" + " }" + "});" +
                                    "if (!added) {" + "  seatArray.push(Seatid);" + "showAndroidToast(Seatid);" +
                                    "console.log('not_added'); " +
                                    " console.log('seat_array_after_added '+seatArray);" + "}" +
                                    "else{" + "console.log('added'); " + "showAndroidToast(Seatid);"
                                    + " console.log('seat_array_before_delete '+seatArray);" +
                                    " var found = jQuery.inArray(Seatid, seatArray);" +
                                    " seatArray.splice(found, 1);" +
                                    " console.log('seat_array_after_delete '+seatArray);" +
                                    "added = false;" + "$(this).toggleClass('active');" +
                                    "clickedCount=parseInt(clickedCount)-1;" +
                                    "console.log('seat selected '+Seatid);" +

                                    "$.ajax({" + "method: 'POST'," + "dataType: 'json'," +
                                    "crossDomain: true," +
                                    "url:'https://www.scopecinemas.com/ajax/select_seat/index'," +
                                    "headers:{" + "'Authorization':'wssko8s4k0ccsks8oc8cc00oc0o4g0sookc880k8'," + "},"
                                    + "data:{" + "'booking_id':booking_id," + "'selected' : Seatid," + "'theater' :" +
                                    " '" + theater_id + "'," + "'movie' : '" + movie_id + "'," +
                                    "'movie_date' : '" + movie_date + "'," + "'status' : '" + hold + "'," +
                                    "'time_slot' : '" + time_slot + "'," + "'source' : '4'" + "}" +
                                    "}).done(function(data){" + "});" + "return false;" + "}" +
                                    "seatCount='" + seat_count + "';" + "console.log('go_down'); " +
                                    "if(Number(seatCount) < Number(clickedCount)){" +
                                    "console.log('seat_plan_exceed');" +
                                    " var found = jQuery.inArray(Seatid, seatArray);" +
                                    " seatArray.splice(found, 1);" +
                                    " console.log('seat_array_before_message '+seatArray);" +
                                    "showAndroidToast(Seatid);" +
                                    "maxSeat('show');" + "return false;" + "}" +
                                    "$(this).toggleClass('active');" +
                                    "clickedCount=parseInt(clickedCount)+1;" +
                                    "alertShow('show');" + "$.ajax({" +
                                    "method: 'POST'," + "dataType: 'json'," +
                                    "crossDomain: true," +
                                    "url:'https://www.scopecinemas.com/ajax/select_seat/index'," +
                                    "headers:{" + "'Authorization':'wssko8s4k0ccsks8oc8cc00oc0o4g0sookc880k8'," + "}," +
                                    "data:{" + "'booking_id':booking_id," + "'selected' : Seatid," +
                                    "'theater' : '" + theater_id + "'," +
                                     "'movie' : '" + movie_id + "'," +
                                    "'movie_date' : '" + movie_date + "'," +
                                    "'status' : '" + hold + "'," +
                                    "'time_slot' : '" + time_slot + "'," +
                                     "'source' : '4'" + "}" + "}).done(function(data){" +
                                    "if (data.status){" + "alertClose('show');" +
                                    "booking_id = data.data.booking_id;" + "showBookingID(data.data.booking_id);" +
                                    "var movie_id   = data.data.movie_id;" + "var theater_id = data.data.theater_id;" +
                                    "var time_slot  = data.data.time_slot;" + "var unavailable_seats  = data.unavailable_seats;" +
                                    "" + "for (var i in unavailable_seats){" +
                                    "unavailable_seats[i].label;" + "unavailable_seats[i].booking_id;" +
                                    "var hold_id = unavailable_seats[i].label ;" +
                                    "var status = unavailable_seats[i].status ;" + "" +
                                    " $('.seatPlan').find('button').each(function( index, element ) {" +
                                    "  if ($(this).attr('id') == hold_id ) {" + "if(status == 1){" +
                                    "  $(this).css({" + " 'background-color':'#ee0c6e'" + "  });" + "}else{" +
                                    "  $(this).css({" + " 'background-color':'#ededed'" + "  });" + "}" +
                                    "" + "  }" + "   });" + "}" + "}" +
                                    "else{" + "var unavailable_seats  = data.unavailable_seats;" +
                                    "for (var i in unavailable_seats){" + "unavailable_seats[i].label;" +
                                    "unavailable_seats[i].booking_id;" + "var hold_id = 'unavailable_seats[i].label' ;" +
                                    "" + "" + " $('.seatPlan').find('button').each(function( index, element ) {" +
                                    "" + "  if ($(this).attr('id')== hold_id ) {" + "  $(this).css({" +
                                    " 'background-color':'red'" + "  });" + "" + "  }" + "   });" + "}" +
                                    "}" + "}).error(function(data){" + "alertClose('show');" +
                                    "console.log(JSON.stringify(data));" + "var hold_id= 'A5' ;" + "" + "" +
                                    " $('.seatPlan').find('button').each(function( index, element ) {" + "" +
                                    "if ($(this).attr('id')== hold_id ) {" + "$(this).css({" +
                                    " 'background-color':'black'" + "  });" + "" + "}" + "   });" + "" + "})" + "}); </script>";
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

        }
        /*@JavascriptInterface
        public void showBookingID(String toast) throws JSONException {
            booking_id = toast;
            //globalVariable.setBookingID(toast);
            //System.out.println("globalVariable >>>>><<<< " + globalVariable.getBookingID());
        }

        @JavascriptInterface
        public void alertShow(String toast) throws JSONException {
            SeatPlanActivity.mDialog = new SimpleArcDialog(SeatPlanActivity.this);
            SeatPlanActivity.mDialog.setConfiguration(new ArcConfiguration(SeatPlanActivity.this));
            SeatPlanActivity.mDialog.setCancelable(false);
            SeatPlanActivity.mDialog.show();
        }

        @JavascriptInterface
        public void alertClose(String toast) throws JSONException {
            SeatPlanActivity.mDialog.cancel();
        }

        @JavascriptInterface
        public void maxSeat(String toast) throws JSONException {
            View deleteDialogView = LayoutInflater.from(SeatPlanActivity.this).inflate(R.layout.custom_dialog, null);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
            TextView textView1 = (TextView) deleteDialogView.findViewById(R.id.textView1);
            textView1.setText("Sorry you can only select " + SeatPlanActivity.seat_count + " seats");
            textView1.setTypeface(font);
            ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
            final AlertDialog deleteDialog = new AlertDialog.Builder(SeatPlanActivity.this).create();
            deleteDialog.setView(deleteDialogView);
            deleteDialogView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
            deleteDialog.show();
        }*/
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getbodyjson() {
        return "{\n" +
                "  \"movie_date\": \"2019-01-03\",\n" +
                "  \"theater_id\": \"8\",\n" +
                "  \"movie_id\": \"107\",\n" +
                "  \"time_slot\": \"10:30:00\"\n" +
                "}";
    }
}

