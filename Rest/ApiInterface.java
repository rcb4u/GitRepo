package com.example.rspl_rahul.gitrepo.Rest;

import com.example.rspl_rahul.gitrepo.Model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rspl-rahul on 30/11/17.
 */

public interface ApiInterface {
    @GET("/get_movies")
    Call<List<Data>> getAllMovies(@Query("token") String token);

}
