package com.example.rspl_rahul.gitrepo.View;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rspl_rahul.gitrepo.Adpater.ShowTimeTheaterListAdapter;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.List;

public class GetShowTimeActivity extends AppCompatActivity {
    private Boolean state;
    private String Movie_Id;
    RoundedImageView Movie_ImageView;
    TextView Movie_Title,Movie_genre,Getshow_message,Movie_Date;
    ShowTimeTheaterListAdapter adapter;
    RecyclerView mrecyclerView;
    private GetShowtimes.MovieDate Movie_Date_time;
    private List<GetShowtimes.ShowTime> showtimes;
    private List<GetShowtimes.Experience> experience;
    private Movie_Data movie_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_show_time);
        Movie_ImageView=(RoundedImageView)findViewById(R.id.Movie_ImageView);
        Movie_Title=(TextView)findViewById(R.id.Movie_title);
        Movie_genre=(TextView)findViewById(R.id.Movie_genre);
        Movie_Date=(TextView)findViewById(R.id.Movie_Date);
        Getshow_message=(TextView)findViewById(R.id.Getshow_message);
        mrecyclerView=(RecyclerView) findViewById(R.id.Get_time_Grid_view);
        Movie_Date.setVisibility(View.VISIBLE);
        //Heading.setText("SELECT A SHOWTIME");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                movie_data = (Movie_Data) bundle.getSerializable("Movie_data");
                Movie_Date_time = (GetShowtimes.MovieDate)bundle.getSerializable("Movie_ShowTime");
                experience=(List<GetShowtimes.Experience>)bundle.getSerializable("Experiences");
                showtimes = Movie_Date_time.getShowTimes();

                Movie_Title.setText(movie_data.getMovieName());
                Movie_genre.setText(movie_data.getGenre() + "~" + movie_data.getDuration());
                Movie_Date.setText(Movie_Date_time.getDate());
                Picasso.get()
                        .load(movie_data.getThumbnailImagePath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(Movie_ImageView);
               initrecyclerView();

                }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initrecyclerView() {
        try{
            adapter = new ShowTimeTheaterListAdapter(GetShowTimeActivity.this,experience,showtimes,new TimeSelectClicked());
            mrecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            mrecyclerView.setHasFixedSize(true);
            mrecyclerView.setLayoutManager(new LinearLayoutManager(GetShowTimeActivity.this, LinearLayoutManager.VERTICAL, false));
            mrecyclerView.setAdapter(adapter);
        }catch (NullPointerException e){
        e.printStackTrace();
    }
    }
    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    class TimeSelectClicked implements ShowTimeTheaterListAdapter.ShowTimesAdapterCallback {
        TimeSelectClicked() {
        }
        public void onClickItem(GetShowtimes.ShowTime showTime) {
            Log.e("ShowTimeId", String.valueOf(showTime.getShowTimeId()));
            Intent in = new Intent(GetShowTimeActivity.this,GetSeatPlanActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("ShowTime",showTime);
            bundle.putSerializable("Movie_data",movie_data);
            bundle.putSerializable("Movie_ShowTime", Movie_Date_time);
            bundle.putSerializable("Experiences", (Serializable) experience);
            in.putExtras(bundle);
            startActivity(in);
        }
    }

}
