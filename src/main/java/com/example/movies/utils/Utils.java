package com.example.movies.utils;

import com.example.movies.domain.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Utils {

    public static Movie convertResponse(String apiResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(apiResponse);

        return mapResponseFields(node);
    }

    private static Movie mapResponseFields(JsonNode node){
        Movie movie = new Movie();
        movie.setMovieName(node.get("Title").asText());
        movie.setReleaseYear(node.get("Year").asText());
        movie.setMovieId(node.get("imdbID").asText());
        movie.setImdbRating(node.get("imdbRating").asDouble());

        return movie;
    }
}
