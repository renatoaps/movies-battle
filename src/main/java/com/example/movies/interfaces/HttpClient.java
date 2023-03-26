package com.example.movies.interfaces;

public interface HttpClient {
    String getMovieDetail(String title, Integer year);
    String getMovieById(String movieId);

    String getMovieByName(String movieName);
}
