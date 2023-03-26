package com.example.movies.httpclient;

import com.example.movies.interfaces.ClientConfigurator;
import com.example.movies.interfaces.HttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient implements HttpClient, ClientConfigurator {

    RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "f81cfd34";
    private static final String URI_API_IMDB = "https://www.omdbapi.com/";

    @Override
    public String getMovieDetail(String title, Integer year) {
        String uri;

        if (year == null){
             uri = prepareServiceUrl("", title);
        }else {
             uri = prepareServiceUrl(title, year);
        }

        ResponseEntity<String> response = executeCall(uri);

        return  response.getBody();
    }

    @Override
    public String getMovieById(String movieId) {
        String uri = prepareServiceUrl(movieId, "");

        ResponseEntity<String> response = executeCall(uri);

        return response.getBody();
    }

    @Override
    public String getMovieByName(String movieName){
        String uri = prepareServiceUrl("", movieName);

        ResponseEntity<String> response = executeCall(uri);

        return response.getBody();
    }

    public String prepareServiceUrl(String title, int year){
        String uri = URI_API_IMDB;

        uri = uri.concat("?t=" + title)
                 .concat("&y=" + year);

        uri = prepareAuthentication(uri);

        return uri;
    }

    public String prepareServiceUrl(String titleId, String movieName){
        String uri = URI_API_IMDB;

        uri = titleId.isEmpty() ?  uri.concat("?t=" + movieName) : uri.concat("?i=" + titleId);
        uri = prepareAuthentication(uri);

        return uri;
    }

    private ResponseEntity<String> executeCall(String uri){
        return restTemplate.exchange(uri,
                HttpMethod.GET,
                HttpEntity.EMPTY
                ,String.class);
    }

    public String prepareAuthentication(String uri){
        return uri.concat("&apikey=" + API_KEY);
    }
}