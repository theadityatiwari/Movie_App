package com.adityatiwari.android.movieapp.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adityatiwari.android.movieapp.Models.MovieModel;
import com.adityatiwari.android.movieapp.Request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    //This class is acting as repositories

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;






    public static MovieRepository getInstance(){

            if(instance == null){
                instance = new MovieRepository();
            }
            return instance;

    }

    private MovieRepository(){

        movieApiClient = MovieApiClient.getInstance();


    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    //2-Calling the Method in repository
    public void searchMovieApi(String query,int pageNumber){



        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query,pageNumber);


    }

    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }



}
