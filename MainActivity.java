package com.example.rspl_rahul.gitrepo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rspl_rahul.gitrepo.Adpater.AlbumsAdapter;
import com.example.rspl_rahul.gitrepo.Model.Data;
import com.example.rspl_rahul.gitrepo.Networks.ConnectivityReceiver;
import com.example.rspl_rahul.gitrepo.Rest.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.util.ArrayList;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends Activity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private String test;
    private String jsonResponse;
    private RecyclerView movieList;
    private ArrayList<Data> movielist;
    private boolean state;
    private TextView Invisible_text;
    private boolean IsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IsConnected = ConnectivityReceiver.isConnected();
        if(IsConnected) {
            GetAllMovies();
        }
        movieList=(RecyclerView)findViewById(R.id.movieList);
        Invisible_text=(TextView)findViewById(R.id.Invisible_text);
        movieList.setLayoutManager(new LinearLayoutManager(this));
       /* RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        movieList.setLayoutManager(mLayoutManager);
      movieList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        movieList.setItemAnimator(new DefaultItemAnimator());
*/        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void GetAllMovies(){
        class WaitingforResponse extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Latest Movies ...", "Please Wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if(state){
                    movieList.setAdapter(new AlbumsAdapter(MainActivity.this, movielist));
                }
                else{Invisible_text.setVisibility(View.VISIBLE);
                    Invisible_text.setText("No movies found.");
                    movieList.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No movies found.", Toast.LENGTH_SHORT).show();
                }// swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            protected String doInBackground(Void... params) {
              // apiClient.auth(getApplicationContext());
                    auth();
                //swipeRefreshLayout.setRefreshing(false);
                return null;
            }

        }
        WaitingforResponse WaitingforResponse = new WaitingforResponse();
        WaitingforResponse.execute();

    }
    /*@Override
    public void onRefresh() {
        GetAllMovies();
    }*/
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
        URL url = new URL("https://api-dev.fyi.lk:8243/scope/1.0/get_movies?token=UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Bearer 232d244b-d35f-3918-b82a-c1a9b9e2ec29");
        urlConnection.setSSLSocketFactory(context.getSocketFactory());

        if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder result = new StringBuilder();
                result.append(line);
                JSONObject readers = new JSONObject(result.toString());
                Log.e("GetMovies", String.valueOf(readers));
                state = (readers.getBoolean("state"));
                Log.e("state", String.valueOf(state));
                if (state) {
                    JSONArray data = readers.getJSONArray("data");
                    Log.e("data", String.valueOf(data));

                    movielist = new ArrayList<Data>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String name = jsonObject1.getString("name");
                        String genre = jsonObject1.getString("genre");
                        int movieId = jsonObject1.getInt("movieId");
                        String source = jsonObject1.getString("source");
                        String banner = jsonObject1.getString("banner");
                        String summery = jsonObject1.getString("summery");
                        String thumbnailImagePath = jsonObject1.getString("thumbnailImagePath");
                        String openBookingsOn = jsonObject1.getString("openBookingsOn");
                        String eventType = jsonObject1.getString("eventType");
                        Data data1 = new Data(source, movieId, eventType, name, genre, summery, banner, thumbnailImagePath, openBookingsOn);
                        movielist.add(data1);
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        IsConnected=isConnected;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}




