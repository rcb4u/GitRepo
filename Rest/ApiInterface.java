package com.example.rspl_rahul.gitrepo.Rest;

import com.example.rspl_rahul.gitrepo.Model.Dth_Model;
import com.example.rspl_rahul.gitrepo.Model.GetMovies;
import com.example.rspl_rahul.gitrepo.Model.GetSeatPlan;
import com.example.rspl_rahul.gitrepo.Model.GetShowtimes;
import com.example.rspl_rahul.gitrepo.Model.Selection_Response;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by rspl-rahul on 30/11/17.
 */

public interface ApiInterface {
    @POST("/movie/1.2/get_movies")
    Call<GetMovies> getAllMovies(@Header("authorization") String auth, @Body RequestBody body);

    @POST("/movie/1.2/get_showtimes")
    Call<GetShowtimes> get_AllShow_Movies(@Header("authorization") String auth, @Body RequestBody body);

    @POST("/movie/1.2/get_seats_plan")
    Call<GetSeatPlan> get_Seat_Plan(@Header("authorization") String auth, @Body RequestBody body);


    @POST("/movie/1.2/seat_booking")
    Call<Selection_Response> get_Seat_Selection_Response(@Header("authorization") String auth, @Body RequestBody body);

    @POST("/api/Dthinfo.php")
    Call<Dth_Model>Dth(@Query("apikey") String apikey,
                       @Query("offer") String offer,
                       @Query("tel") String mobile, @Query("operator") String operator);
}
