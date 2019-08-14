package com.example.rspl_rahul.gitrepo.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridBottomOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridTopOffsetItemDecoration;
import com.example.rspl_rahul.gitrepo.Adpater.DateListAdapter;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Rest.ApiClient;
import com.example.rspl_rahul.gitrepo.Rest.ApiInterface;
import com.example.rspl_rahul.gitrepo.Utils.Constants;
import com.example.rspl_rahul.gitrepo.Utils.GridSpaceItemDecoration;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShowDateActivity extends AppCompatActivity {

    private Boolean state;
    private String Movie_Id;
    RoundedImageView Movie_ImageView;
    TextView Movie_Title,Movie_genre,Getshow_message,Heading;
    private DateListAdapter adapter;
    RecyclerView mrecyclerView;
    private Movie_Data album;
    private TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_show_date);
        Movie_ImageView=(RoundedImageView)findViewById(R.id.Movie_ImageView);
        Movie_Title=(TextView)findViewById(R.id.Movie_title);
        Movie_genre=(TextView)findViewById(R.id.Movie_genre);
        Heading=(TextView)findViewById(R.id.heading);
        Getshow_message=(TextView)findViewById(R.id.Getshow_message);
        mrecyclerView=(RecyclerView) findViewById(R.id.GetdateGrid);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("SELECT A DATE");
        Heading.setText("SELECT DATE");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
             Movie_Id=bundle.getString("movieId");
             album = (Movie_Data) bundle.getSerializable("Movie_data");
            Movie_Title.setText(album.getMovieName());
            Movie_genre.setText(album.getGenre()+" "+album.getDuration());
            Picasso.get()
                    .load(album.getThumbnailImagePath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(Movie_ImageView);
            LoadResults();

        }
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
            jsonObject.put("locationId","1");//
            jsonObject.put("movieId",Movie_Id);
            jsonObject.put("source",album.getSources().get(0).getName());////album.getSources().get(0).getName()//"scopecinemas"
            jsonObject.put("token",Constants.TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());

        Call<GetShowtimes> call =apiService.get_AllShow_Movies(Constants.AUTHORIZATION,body);
        call.enqueue(new Callback<GetShowtimes>() {
            @Override
            public void onResponse(Call<GetShowtimes> call, Response<GetShowtimes> response) {
                loading.dismiss();
                if (state = response.body().getState()) {
                   // Log.e("Get_Movies",response.body().getData().getExperiences().toString());
                    if(album!=null) {
                        adapter = new DateListAdapter(GetShowDateActivity.this, album,response.body().getData().getExperiences() ,response.body().getData().getExperiences().get(0).getMovieDates());
                        mrecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
                        mrecyclerView.setHasFixedSize(true);
                        mrecyclerView.setLayoutManager(new GridLayoutManager(GetShowDateActivity.this, 4));
                        mrecyclerView.addItemDecoration(new GridSpaceItemDecoration(4, dpToPx(16), true));
                        mrecyclerView.addItemDecoration(new GridBottomOffsetItemDecoration(dpToPx(16), 4));
                        mrecyclerView.addItemDecoration(new GridTopOffsetItemDecoration(dpToPx(16), 4));
                        mrecyclerView.setAdapter(adapter);
                        }
                }else{
                    showNoItems();
                }

            }

            @Override
            public void onFailure(Call<GetShowtimes> call, Throwable t) {
            loading.dismiss();
            showNoItems();
            Log.e("Response error", String.valueOf(t.fillInStackTrace()));
            }
        });
    }
    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    private void showNoItems() {
        Getshow_message.setVisibility(View.VISIBLE);
        mrecyclerView.setVisibility(View.GONE);
    }
}
