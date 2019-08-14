package com.example.rspl_rahul.gitrepo.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rspl_rahul.gitrepo.Model.GetSeatPlan;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Movie_Data;
import com.example.rspl_rahul.gitrepo.Model.Selection_Response;
import com.example.rspl_rahul.gitrepo.R;
import com.example.rspl_rahul.gitrepo.Rest.ApiClient;
import com.example.rspl_rahul.gitrepo.Rest.ApiInterface;
import com.example.rspl_rahul.gitrepo.Utils.Constants;
import com.example.rspl_rahul.gitrepo.Utils.FormatDate;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketViewActivity extends AppCompatActivity {

    private ImageView Movie_ImageView;
    private Movie_Data movie_data;
    private TextView Movie_title,Movie_Date,Movie_Time,Seats,Movie_Duration;
    private GetShowtimes.MovieDate Movie_Date_time;
    private List<GetShowtimes.Experience> experience;
    private ArrayList<String> SelectedSeatId;
    private GetShowtimes.ShowTime showTime;
    private Boolean State;
    private ArrayList<String> SelectedSeatLabel;
    private int ticket_count_Adult;
    private int ticket_count_child;
    private Button confirm_Booking;
    private TextView toolbar_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        Movie_ImageView = (ImageView) findViewById(R.id.Movie_Image_ticket);
        Movie_title=(TextView)findViewById(R.id.Movie_title);
        Movie_Date=(TextView)findViewById(R.id.Movie_Ticket_Date);
        Movie_Time=(TextView)findViewById(R.id.Movie_Time);
        Seats=(TextView)findViewById(R.id.seats);
        Movie_Duration=(TextView)findViewById(R.id.Movie_duration);
        confirm_Booking = (Button)findViewById(R.id.confirm_booking);
        toolbar_title.setText("CONFIRM BOOKING");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                movie_data = (Movie_Data) bundle.getSerializable("Movie_data");
                Movie_Date_time = (GetShowtimes.MovieDate) bundle.getSerializable("Movie_ShowTime");
                experience = (List<GetShowtimes.Experience>) bundle.getSerializable("Experiences");
                showTime = (GetShowtimes.ShowTime) bundle.getSerializable("ShowTime");
                SelectedSeatId = (ArrayList<String>) bundle.getSerializable("SelectedSeatId");
                SelectedSeatLabel = (ArrayList<String>) bundle.getSerializable("Seat");
                Log.e("SelectedSeatId",SelectedSeatId+" "+SelectedSeatLabel);
                ticket_count_child=bundle.getInt("ticket_count_child");
                ticket_count_Adult=bundle.getInt("ticket_count_Adult");
                Picasso.get()
                .load(movie_data.getThumbnailImagePath())
                .placeholder(R.drawable.bharat)
                .error(R.drawable.bharat)
                .into(Movie_ImageView);
                Movie_title.setText(movie_data.getMovieName());
                Movie_Duration.setText(movie_data.getGenre() + "~" + movie_data.getDuration());
                Seats.setText(SelectedSeatLabel.toString().replace("[","").replace("]",""));
                FormatDate formatDate=new FormatDate(Movie_Date_time.getDate());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(formatDate.getDatewithSuffix());
                stringBuilder.append("  ");
                stringBuilder.append(formatDate.getMonth());
                stringBuilder.append("  ");
                stringBuilder.append(formatDate.getYYYY());
                Movie_Date.setText(stringBuilder.toString());
                stringBuilder.setLength(0);
                stringBuilder.append(FormatDate.getTimewithPM2(showTime.getShowTime()));
                Movie_Time.setText(stringBuilder.toString());
            }
            confirm_Booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeatSelectionResponse();
                }
            });
     }catch (Exception e){
        e.printStackTrace();
        }
    }

    private void SeatSelectionResponse() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        //how to create an json body and pass to api is as follows
        try {
            jsonObject.put("showTimeId", showTime.getShowTimeId());
            jsonObject.put("seatIds",SelectedSeatId);
            jsonObject.put("locationId", "1");
            jsonObject.put("movieId", movie_data.getMovieId());
            jsonObject.put("source", "scopecinemas");//showTime.getCategories().get(0).getCategoryId()
            jsonObject.put("categoryId", showTime.getCategories().get(0).getCategoryId());
            JSONArray subCategories = new JSONArray();
            subCategories.put(getsubCategory(String.valueOf(showTime.getCategories().get(0).getSubCategories().get(0)), String.valueOf(ticket_count_Adult)));//write here seat count for adult
            subCategories.put(getsubCategory(String.valueOf(showTime.getCategories().get(0).getSubCategories().get(1)), String.valueOf(ticket_count_child)));//write here seat count for child
            jsonObject.put("subCategories", subCategories);
            jsonObject.put("token", Constants.TOKEN);
            //Added new Fields from here

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body =
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<Selection_Response> call = apiService.get_Seat_Selection_Response(Constants.AUTHORIZATION, body);
        call.enqueue(new Callback<Selection_Response>() {
            @Override
            public void onResponse(Call<Selection_Response> call, Response<Selection_Response> response) {
                if (response.isSuccessful()) {
                    Selection_Response selection_response = new Selection_Response();
                    if (State = response.body().getState()) {
                        Log.e("Selection Response", "" + selection_response.getData().getBookingIndex());
                    }else{
                        Toast.makeText(TicketViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Selection Response", "" + selection_response.getData());

                    }
                }
            }

            @Override
            public void onFailure(Call<Selection_Response> call, Throwable t) {

            }
        });

    }
    JSONObject  getsubCategory(String subCategoryId, String seatCount){
        JSONObject subCategory = new JSONObject();
        try {
            subCategory.put("subCategoryId", subCategoryId);
            subCategory.put("seatCount", seatCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subCategory ;
    }


    /*{
  "showTimeId": 201,
  "seatIds": [
    12,
    324,
    345
  ],
  "locationId": 1,
  "movieId": 112,
  "source": "scopecinemas",
  "categoryId": 1,
  "subCategories": [
    {
      "subCategoryId": 1,
      "seatCount": 2
    },
    {
      "subCategoryId": 2,
      "seatCount": 3
    }
  ],
  "token": "dsdfdsgdsgfdgfh63464dfchcvh"
}*/
}
