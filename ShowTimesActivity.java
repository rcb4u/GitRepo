package com.example.rspl_rahul.gitrepo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rspl_rahul.gitrepo.Adpater.ShowTimeAdpater;
import com.example.rspl_rahul.gitrepo.Model.ShowTime;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ShowTimesActivity extends AppCompatActivity {
    private static final String TAG ="ShowTimesActivity";
    RecyclerView Showtimerecy;
    List<ShowTime>showTimeList;
    ImageView iv;
    Integer movieId;
    JSONArray timeOfMoviesArray;
    private String key;
    private ShowTimeAdpater showTimeAdpater;
    private String theater_name;
    private JSONObject data;
    private HorizontalCalendar horizontalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_times);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, 0);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 15);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.GRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                //*****RC*****this for dot to down ********* //////
                /*.addEvents(new CalendarEventsPredicate() {
                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        int count = rnd.nextInt(6);

                        for (int i = 0; i <= count; i++){
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }

                        return events;
                    }
                })*/
                .build();
        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString();
                Toast.makeText(ShowTimesActivity.this, selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);
            }

        });


        Intent intent = getIntent();//data coming from album adapter
        movieId = intent.getIntExtra("movieId",0);
        Log.e(TAG, String.valueOf(movieId));
        showTimeList = new ArrayList<ShowTime>();
        iv = (ImageView) findViewById(R.id.image);
        Showtimerecy=(RecyclerView)findViewById(R.id.show_time_recycler);
        Showtimerecy.setLayoutManager(new LinearLayoutManager(this));
      /*  mCircle = new ExpandingCircleAnimationDrawable(900);
        iv.setImageDrawable(mCircle);*/
      GetShowtimes();

    }

    private void GetShowtimes() {
        class Getshowtime extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(ShowTimesActivity.this, "Latest Movies ...", "Please Wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                try {
                    getkeysfun();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            protected String doInBackground(Void... params) {
                auth();
                return null;
            }

        }
        Getshowtime WaitingforResponse = new Getshowtime();
        WaitingforResponse.execute();

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
            URL url = new URL("https://api-dev.fyi.lk:8243/scope/1.0/get_showtimes?token=UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O");
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
public void getkeysfun() throws JSONException {

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
                    Log.d("*******", key+" "+String.valueOf(showTimes));
                    ShowTime showTime = new ShowTime(theater_name, key, showTimes);
                    showTimeList.add(showTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    showTimeAdpater=new ShowTimeAdpater(ShowTimesActivity.this,showTimeList);
    Showtimerecy.setAdapter(showTimeAdpater);
    showTimeAdpater.notifyDataSetChanged();

}

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getbodyjson(){
        return "{\n" +
                "  \"movie_date\": \""+getDate()+"\",\n" +
                "  \"movie_id\": \""+movieId+"\"\n" +
                "}";
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        //mCircle.start();
    }
    @Override
    protected void onPause() {
       // mCircle.stop();
        super.onPause();
    }
}
