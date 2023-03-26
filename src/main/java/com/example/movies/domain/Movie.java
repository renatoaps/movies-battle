package com.example.movies.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {

    private String movieName;
    private String releaseYear;
    private String movieId;
    private Double imdbRating;
}
