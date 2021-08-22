package com.adityatiwari.android.movieapp.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adityatiwari.android.movieapp.AppExecutors;
import com.adityatiwari.android.movieapp.Models.MovieModel;
import com.adityatiwari.android.movieapp.Response.MovieSearchResponse;
import com.adityatiwari.android.movieapp.Utils.Credentials;
import com.adityatiwari.android.movieapp.Utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //Making a global runnable request
    private RetreiveMoviesRunnable retreiveMoviesRunnable;





    //This is livedata
    private MutableLiveData<List<MovieModel>> mMovies;



    private static MovieApiClient instance;



    public static MovieApiClient getInstance(){

        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }


    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }


    //1-this method which we are going to call through the classes
    public void searchMovieApi(String query, int pageNumber){

        if(retreiveMoviesRunnable != null){
            retreiveMoviesRunnable = null;
        }

        retreiveMoviesRunnable = new RetreiveMoviesRunnable(query,pageNumber);


        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retreiveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call

                myHandler.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);



    }

    //Retrieving data from rest api by runnable class
    //we have the two types of query: the id and search query
    private class RetreiveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetreiveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMovies(query,pageNumber).execute();

                if(cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        //sending data to live data
                        //postValue: used for background threads
                        //setValue: Not for background threads
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error is " + error);
                    mMovies.postValue(null);
                }







            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }




        }

        //search method or query
        private Call<MovieSearchResponse> getMovies(String query,int pageNumber){
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );

        }

        private void cancelReqeust(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest = true;
        }

    }




}
