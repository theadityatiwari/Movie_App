package com.adityatiwari.android.movieapp.Utils;

import android.graphics.Movie;

import com.adityatiwari.android.movieapp.Models.MovieModel;
import com.adityatiwari.android.movieapp.Response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //Search for movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //Search by movie id
    //https://api.themoviedb.org/3/movie/550?api_key=9dbe6c0d6f777f91a976430b96e06e5a
    //Remember 550 is a code for movie fight club
    @GET("3/movie/{movie_id}")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );


}
