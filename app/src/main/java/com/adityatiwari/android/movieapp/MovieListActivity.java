package com.adityatiwari.android.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adityatiwari.android.movieapp.Adapters.MovieRecyclerView;
import com.adityatiwari.android.movieapp.Adapters.OnMovieListener;
import com.adityatiwari.android.movieapp.Models.MovieModel;
import com.adityatiwari.android.movieapp.Request.Servicey;
import com.adityatiwari.android.movieapp.Response.MovieResponse;
import com.adityatiwari.android.movieapp.Response.MovieSearchResponse;
import com.adityatiwari.android.movieapp.Utils.Credentials;
import com.adityatiwari.android.movieapp.Utils.MovieApi;
import com.adityatiwari.android.movieapp.Viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {


    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;



    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup search view
        SetupSearchView();


        recyclerView = findViewById(R.id.recyclerView);




        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        ConfigureRecyclerView();

        //calling the method observeAnyChange
        ObserveAnyChange();




//        //Testing the method
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchMovieApi("Fast",1);
//            }
//        });



    }



    //Observing any change
    private void ObserveAnyChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // for observing any change
                if(movieModels != null){
                    for(MovieModel movieModel : movieModels){
                        //get the data in the log
                        Log.v("Tag","onChanged: "+ movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }

            }
        });

    }





    //5 initializing the recyclerView and adding data to it
    private void ConfigureRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        //RecyclerView pagination
        //Displaying the next pages of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    //here we need to display the next search result on the next page of api

                    movieListViewModel.searchNextPage();


                }


            }
        });



    }

    @Override
    public void onMovieClick(int position) {
//        Toast.makeText(this,"The position: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {
    }


    //get the data from search view and query api
    private void SetupSearchView() {

        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                movieListViewModel.searchMovieApi(
                        query,
                        1
                );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }


//    private void getRetrofitResponse() {
//        MovieApi movieApi = Servicey.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(
//                        Credentials.API_KEY,
//                        "john wick",
//                        1);
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//
//                if(response.code() == 200){
//                    Log.v("Tag","The Response is " + response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for (MovieModel movie: movies){
//                        Log.v("Tag","the release date " + movie.getTitle());
//                    }
//                }
//                else {
//
//
//                    try {
//                        Log.v("Tag","Error Message" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } ;
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//
//
//
//    }
//
//
//
//    private void getRetrofitResponseById(){
//        MovieApi movieApi = Servicey.getMovieApi();
//
//        Call<MovieModel> movieResponseCall = movieApi
//                .getMovie(
//                550,
//                Credentials.API_KEY );
//
//        movieResponseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if(response.code() == 200){
//                    MovieModel movie = response.body();
//                    Log.v("Tag","The response is " + movie.getTitle());
//                }
//                else{
//
//                    try {
//                        Log.v("Tag","Error -" + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//
//
//    }




}