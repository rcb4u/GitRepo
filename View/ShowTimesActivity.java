package com.example.rspl_rahul.gitrepo.View;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rspl_rahul.gitrepo.Model.ShowTime;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Utils.ArcConfiguration;
import com.example.rspl_rahul.gitrepo.Utils.CustomDateView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ShowTimesActivity extends AppCompatActivity {
    public static final String TAG_ID = "id";
    private static final String TAG_INDEX = "index";
    final ShowTimesActivity context = this;
    String accessToken_id;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    String api_status;
    ImageView banner;
    Button btn;
    Spinner count;
    int countData = 0;
    int countDataTwo = 0;
    CustomDateView dateList;
    Fragment fr;
    int id;
    String image;
    // public ImageLoader imageLoader;
    JSONObject json_data;
    LinearLayout linearLayout;
    ImageView menu;
    String movie_id;
    String movie_name;
    TextView name;
    String notselectTheartName = "";
    ImageView now_showing;
    String portrait_image;
    ImageView profile;
    String responseString;
    String selectAlldataTime;
    String showDate;
    String showTime;
    ShowTimesActivityAsyncTask ShowTimesActivityAsyncTask;
    TextView textView13;
    TextView textView14;
    TextView textView15;
    TextView textView16;
    String theaterID;
    String theater_id_button;
    String theater_index;
    ImageView theatres;
    String[] timeArray;
    LinearLayout time_list;
    ImageView up_coming;
    String user_keyData;
    String value;
    View view;
    private Handler mUiHandler = new Handler();
    private JSONObject jsonObject;
    private String movieId;
    private String TAG;
    private JSONObject data;
    private String theater_name;
    private String key;
    private ArrayList<ShowTime> showTimeList;
    private JSONArray timeOfMoviesArray;
    private String dayofMonth;
    private String monthofYear;
    private String dateofMonth;
    private String theater_id;
    private List<String> keyvalue;

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getbodyjson() {
        return "{\n" +
                "  \"locationId\": 1,\n" +
                "  \"movieId\": 8228,\n" +
                "  \"source\": \"scopecinemas\",\n" +
                "  \"token\": \"UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O\"\n" +
                "}";
    }

    private void auth() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            InputStream is = this.getResources().getAssets().open("latest.crt");
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
            URL url = new URL("https://api-dev.fyi.lk:8243/movie/1.2/get_showtimes?" +
                    "token=UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O");
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
                    responseString = result.toString();
                    JSONObject readers = new JSONObject(result.toString());
                    Log.e("GetShowTime", String.valueOf(readers));
                    Boolean state = (readers.getBoolean("state"));
                    Log.e("state", String.valueOf(state));
                    data = readers.getJSONObject("data");
                    Log.e("data", String.valueOf(data));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((LinearLayout) findViewById(R.id.mainMenu)).setVisibility(View.VISIBLE);
        ((FrameLayout) findViewById(R.id.fragment_place))
                .addView(getLayoutInflater().inflate(R.layout.show_time_fragment, null));
        Intent intent = getIntent();//data coming from album adapter
        movieId = intent.getStringExtra("movieId");
        Log.e("TAG", String.valueOf(movieId));
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        Typeface fontTwo = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        now_showing = (ImageView) findViewById(R.id.now_showing);
        up_coming = (ImageView) findViewById(R.id.up_coming);
        theatres = (ImageView) findViewById(R.id.theatres);
        profile = (ImageView) findViewById(R.id.profile);
        menu = (ImageView) findViewById(R.id.menu);
        getFragmentManager().addOnBackStackChangedListener(new C03706());
        textView13 = (TextView) findViewById(R.id.textView13);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);
        banner = (ImageView) findViewById(R.id.imageView11);
        btn = (Button) findViewById(R.id.btn);
        name = (TextView) findViewById(R.id.name);
        dateList = (CustomDateView) findViewById(R.id.date_list);
        time_list = (LinearLayout) findViewById(R.id.time_list);
        portrait_image = getIntent().getStringExtra("Banner");
        System.out.println("portrait_image " + this.portrait_image);
        textView13.setTypeface(font);
        textView14.setTypeface(font);
        textView15.setTypeface(font);
        textView16.setTypeface(font);
        name.setTypeface(fontTwo);
        btn.setTypeface(fontTwo);
        ShowTimesActivityAsyncTask = new ShowTimesActivityAsyncTask();
        ShowTimesActivityAsyncTask.execute(new Void[0]);
    }

    class C03706 implements FragmentManager.OnBackStackChangedListener {
        C03706() {
        }

        public void onBackStackChanged() {
            Fragment f = ShowTimesActivity.this.getFragmentManager().findFragmentById(R.id.fragment_place);
            if (f != null) {
                // ShowTimesActivity.this.updateTitleAndDrawer(f);
            }
        }
    }

    class ShowTimesActivityAsyncTask extends AsyncTask<Void, Integer, Void> {
        SimpleArcDialog mDialog;
        boolean running;

        ShowTimesActivityAsyncTask() {
        }

        protected Void doInBackground(Void... params) {
            try {
                auth();
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                ShowTimesActivity.this.mUiHandler.post(new CancelDialog());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
            try {
                json_data = new JSONObject(responseString);
                if ("true".equals(json_data.getString("state"))) {
                    jsonObject = json_data.getJSONObject("data");
                    getkeysfun();
                }
            } catch (Exception e) {
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.running = true;
            this.mDialog = new SimpleArcDialog(ShowTimesActivity.this);
            this.mDialog.setConfiguration(new ArcConfiguration(ShowTimesActivity.this));
            this.mDialog.show();
        }
    }

    public void getkeysfun() throws JSONException {
        JSONArray theaters = data.getJSONArray("theaters");
        Log.e("theaters", String.valueOf(theaters) + "  " + theaters.length());
        // for (int i=0;i<theaters.length();i++) {
        JSONObject theater_Id = theaters.getJSONObject(0);
         theater_id = theater_Id.getString("id");
        JSONObject theater_Name = theaters.getJSONObject(0);
        theater_name = theater_Name.getString("name");
        //here the value getting against the id (theater_id) that is jsonobject
        JSONObject date_times = data.getJSONObject("date_times").getJSONObject(theater_id);
        Log.e("date_times theater", String.valueOf(date_times) + "  " + date_times.length());
        //for (int c = 0; c < date_times.length(); c++) {
        try {
            JSONObject resObject = new JSONObject(String.valueOf(date_times));
            Iterator<String> iterator = resObject.keys();
            keyvalue = new ArrayList<String>();
            dateList.setVisibility(View.VISIBLE);
            dateList.removeAllViews();
            while (iterator.hasNext()) {
                key = iterator.next();//
                Log.e("Date :- ", key);
                keyvalue.add(key);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(key);
                Log.e("Date :- key ", String.valueOf(key));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
                dayofMonth = simpleDateFormat.format(date).toUpperCase();
                System.out.println("DAY " + simpleDateFormat.format(date).toUpperCase());
                simpleDateFormat = new SimpleDateFormat("MMM");
                monthofYear = simpleDateFormat.format(date).toUpperCase();
                System.out.println("MONTH " + simpleDateFormat.format(date).toUpperCase());
                simpleDateFormat = new SimpleDateFormat("dd");
                dateofMonth = simpleDateFormat.format(date).toUpperCase();
                // dateList.removeAllViews();

                CustomDateView customDateView1 = new CustomDateView(this);
                System.out.println("date " + simpleDateFormat.format(date).toUpperCase());
                           /* if(customDateView1.getParent()!= null) {
                                System.out.println("date im here ");
                                customDateView1.removeView(dateList);
                            }*/
                customDateView1.addRow(dayofMonth, dateofMonth, monthofYear, key, java.util.Calendar.getInstance().getTime(), "1");
                CustomDateView customDateView = this.dateList;
                LinearLayout.LayoutParams paramsnew = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //this.addContentView(customDateView1,paramsnew);
                customDateView.addView(customDateView1);
                customDateView.setSelect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        Log.e("Date :- size ", String.valueOf(keyvalue.size()));

                    /*for(int i =0;i<keyvalue.size();i++){
                        if(keyvalue.size()<=0){
                            dateList.removeAllViews();
                        }else{
                        try {
                          //  dateList=new CustomDateView(this);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = formatter.parse(keyvalue.get(i));
                            Log.e("Date :- key ", String.valueOf(keyvalue.get(i)));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
                            dayofMonth = simpleDateFormat.format(date).toUpperCase();
                            System.out.println("DAY " + simpleDateFormat.format(date).toUpperCase());
                            simpleDateFormat = new SimpleDateFormat("MMM");
                            monthofYear = simpleDateFormat.format(date).toUpperCase();
                            System.out.println("MONTH " + simpleDateFormat.format(date).toUpperCase());
                            simpleDateFormat = new SimpleDateFormat("dd");
                            dateofMonth = simpleDateFormat.format(date).toUpperCase();
                           // dateList.removeAllViews();

                            CustomDateView customDateView1=new CustomDateView(this);
                            System.out.println("date " + simpleDateFormat.format(date).toUpperCase());
                           *//* if(customDateView1.getParent()!= null) {
                                System.out.println("date im here ");
                                customDateView1.removeView(dateList);
                            }*//*
                            customDateView1.addRow(dayofMonth, dateofMonth, monthofYear, key, java.util.Calendar.getInstance().getTime(), "1");
                            CustomDateView customDateView=this.dateList;
                            LinearLayout.LayoutParams paramsnew = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            // this.addContentView(customDateView,paramsnew);
                            customDateView.addView(customDateView1,i);
                            customDateView.setSelect();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        }*/
    }

                //}
           // }

/*   for(int c=0;c<val.length();c++) {1
                    try {
                        JSONObject resObject = new JSONObject(String.valueOf(date_times));
                        Iterator<String> iterator = resObject.keys();
                        while (iterator.hasNext()) {
                            key = iterator.next();// this will be your date
                            timeOfMoviesArray = date_times.getJSONArray(key);//this is time against date
                            Log.e("timeOfMoviesArray", key + " " + timeOfMoviesArray);
                            ArrayList<String> showTimes = new ArrayList<>();
                            for (int a = 0; a < timeOfMoviesArray.length(); a++) {
                                showTimes.add(String.valueOf(timeOfMoviesArray.get(a)));
                            }
                            ////Custom Date Function ///////
                            long millis=System.currentTimeMillis();
                            java.sql.Date date=new java.sql.Date(millis);
                            System.out.println(date);
                            CustomeTicketDate customeTicketDate =new CustomeTicketDate(ShowTimesActivity.this);
                            customeTicketDate.addRow("11","11","01",key,date,"5");
                            Log.d("*******", theater_name+"   "+key+" "+date+"  "+timeOfMoviesArray.length());
                            ///////////////
                            showTimeList = new ArrayList<ShowTime>();
                            ShowTime showTime = new ShowTime(theater_name, key, showTimes);
                            // Log.d("*******", theater_name+"   "+key+" "+String.valueOf(showTimes));
                            showTimeList.add(showTime);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
        /*public void getkeysfun() throws JSONException {
            JSONArray theaters = data.getJSONArray("theaters");
            Log.e("theaters", String.valueOf(theaters));
            for (int i=0;i<theaters.length();i++) {

                JSONObject theater_Id=theaters.getJSONObject(i);
                String val =theater_Id.getString("id");

                JSONObject theater_Name=theaters.getJSONObject(i);
                theater_name =theater_Name.getString("name");

                //here the value getting against the id (theater_id) that is jsonobject
                JSONObject date_times = data.getJSONObject("date_times").getJSONObject(val);
                Log.e("date_times", String.valueOf(val.length()));
                   for(int c=0;c<val.length();c++) {
                    try {
                        JSONObject resObject = new JSONObject(String.valueOf(date_times));
                        Iterator<String> iterator = resObject.keys();
                        while (iterator.hasNext()) {
                            key = iterator.next();// this will be your date
                            timeOfMoviesArray = date_times.getJSONArray(key);//this is time against date
                            Log.e("timeOfMoviesArray", key + " " + timeOfMoviesArray);
                            ArrayList<String> showTimes = new ArrayList<>();
                            for (int a = 0; a < timeOfMoviesArray.length(); a++) {
                                showTimes.add(String.valueOf(timeOfMoviesArray.get(a)));
                            }
                            ////Custom Date Function ///////
                            long millis=System.currentTimeMillis();
                            java.sql.Date date=new java.sql.Date(millis);
                            System.out.println(date);
                            CustomeTicketDate customeTicketDate =new CustomeTicketDate(ShowTimesActivity.this);
                            customeTicketDate.addRow("11","11","01",key,date,"5");
                            Log.d("*******", theater_name+"   "+key+" "+date+"  "+timeOfMoviesArray.length());
                            ///////////////
                            showTimeList = new ArrayList<ShowTime>();
                            ShowTime showTime = new ShowTime(theater_name, key, showTimes);
                            // Log.d("*******", theater_name+"   "+key+" "+String.valueOf(showTimes));
                            showTimeList.add(showTime);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            *//*showTimeAdpater=new ShowTimeAdpater(ShowTimesActivity.this,showTimeList);
            Showtimerecy.setAdapter(showTimeAdpater);
            showTimeAdpater.notifyDataSetChanged();*//*

        }*/


        class CancelDialog implements Runnable {
            CancelDialog() {
            }

            public void run() {
                View deleteDialogView = LayoutInflater.from(ShowTimesActivity.this).inflate(R.layout.custom_dialog, null);
                Typeface font = Typeface.createFromAsset(ShowTimesActivity.this.getAssets(), "fonts/Lato-Light.ttf");
                ((TextView) deleteDialogView.findViewById(R.id.textView1)).setTypeface(font);
                ((Button) deleteDialogView.findViewById(R.id.button1)).setTypeface(font);
                final AlertDialog deleteDialog = new AlertDialog.Builder(ShowTimesActivity.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        }


    }
