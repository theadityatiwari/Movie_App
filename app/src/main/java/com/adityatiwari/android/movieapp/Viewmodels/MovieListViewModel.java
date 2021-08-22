package com.adityatiwari.android.movieapp.Viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adityatiwari.android.movieapp.Models.MovieModel;
import com.adityatiwari.android.movieapp.Repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //this class is for ViewModel

    private MovieRepository movieRepository;
    //this is constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    //getter
    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    //3-Calling the method in view model
    public void searchMovieApi(String query,int pageNumber){

        movieRepository.searchMovieApi(query,pageNumber);

    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }



}
