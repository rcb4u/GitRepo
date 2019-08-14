package com.example.rspl_rahul.gitrepo.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rspl_rahul.gitrepo.Adpater.AlbumsAdapter;
import com.example.rspl_rahul.gitrepo.Model.Dth_Model;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.Model.GetMovies;
import com.example.rspl_rahul.gitrepo.MyBroadCastReceiver;
import com.example.rspl_rahul.gitrepo.Networks.ConnectivityReceiver;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Rest.ApiClient;
import com.example.rspl_rahul.gitrepo.Rest.ApiInterface;
import com.example.rspl_rahul.gitrepo.Utils.Constants;
import com.example.rspl_rahul.gitrepo.Utils.GridSpaceItemDecoration;
import com.example.rspl_rahul.gitrepo.Utils.SimpleArcDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rspl_rahul.gitrepo.Utils.GridSpaceItemDecoration.dpToPx;

public class MainActivity extends Activity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private RecyclerView movieList;
    private ArrayList<Movie_Data> movielist;
    private boolean state;
    private TextView Invisible_text;
    private boolean IsConnected;
    private SimpleArcDialog mDialog;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastReceiver=new MyBroadCastReceiver();
        IsConnected = ConnectivityReceiver.isConnected();
        if (IsConnected) {
            LoadResults();
        }
        movieList = (RecyclerView) findViewById(R.id.movieList);
        Invisible_text = (TextView) findViewById(R.id.Invisible_text);
        movieList.setLayoutManager(new LinearLayoutManager(this));
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        IsConnected=isConnected;
    }

    public void LoadResults(){
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Loading Latest Movies...");
        loading.setIndeterminate(false);
        loading.setCancelable(false);//
        loading.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        //how to create an json body and pass to api is as follows
        try {
            jsonObject.put("token",Constants.TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        //////

        Call<GetMovies> call =apiService.getAllMovies(Constants.AUTHORIZATION,body);
        call.enqueue(new Callback<GetMovies>() {
            @Override
            public void onResponse(Call<GetMovies> call, Response<GetMovies> response) {
                loading.dismiss();
                if(response.isSuccessful()){
                if (state = response.body().getState()) {
                    Log.e("Get_Movies",response.body().getData().toString());
                    AlbumsAdapter albumsAdapter=new AlbumsAdapter(
                            MainActivity.this, response.body().getData());
                    movieList.setHasFixedSize(true);
                    movieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                    movieList.addItemDecoration(new GridSpaceItemDecoration(1,dpToPx(4),true));
                    movieList.setAdapter(albumsAdapter);
                }else{
                        Invisible_text.setVisibility(View.VISIBLE);
                        Invisible_text.setText("No movies found.");
                        movieList.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "No movies found.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Invisible_text.setVisibility(View.VISIBLE);
                    Invisible_text.setText(" Error In Loading Movies, Please try After SomeTime. ");
                    movieList.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Error In Loading Movies, Please try After SomeTime.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetMovies> call, Throwable t) {
                loading.dismiss();
                Log.e("Response error", String.valueOf(t.fillInStackTrace()));
            }
        });
    }

    /*public void check_Amount(){
        final ProgressDialog progressDialog1 = ProgressDialog.show(MainActivity.this,
                "Processing Recharge...", "Please Wait...", false, false);
        WindowManager.LayoutParams wmlp =
                progressDialog1.getWindow().getAttributes();
        wmlp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmlp.height = 400;
        wmlp.width = 400;
        progressDialog1.getWindow().setAttributes(wmlp);
        ApiInterface apiService =
                ApiClient.getDth().create(ApiInterface.class);
        Log.e("TAG","114395760");
        Call<Dth_Model> call = apiService.Dth("599db777b31dcc40119ed488f31cdeeb","roffer","114395760","Videocon");
        //Testing Numbers are below
        //post  0712232950
        //post 0714307000
        //pre 0712232050
        call.enqueue(new Callback<Dth_Model>() {
            @Override
            public void onResponse(Call<Dth_Model> call, Response<Dth_Model> response) {
                Log.e("vala1", String.valueOf(response.body()));

                progressDialog1.dismiss();
                if(response.isSuccessful() && response.body()!= null) {
                    Log.e("vala", String.valueOf(response.body()));
                    // SIM_TYPE=response.body().getMessage();
               *//* if(response.body().getSuccess().equals(1)) {

                }else{
                    Toast toast= Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL,100,0);
                    toast.show();

                *//*}

            }

            @Override
            public void onFailure(Call<Dth_Model> call, Throwable t) {
                progressDialog1.dismiss();
                Log.e("TAG",t.toString());
                Toast.makeText(MainActivity.this,t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
   */
/////////////////

}

