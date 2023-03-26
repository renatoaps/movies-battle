package com.example.movies.controller;

import com.example.movies.domain.Movie;
import com.example.movies.httpclient.RestClient;
import com.example.movies.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movies")
@AllArgsConstructor
public class MovieController {
    private final RestClient restClient;
    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) throws JsonProcessingException {
        String response = restClient.getMovieById(id);

        if (response.contains("Error")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return  new ResponseEntity<>(Utils.convertResponse(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Movie> getMovieByParams(@RequestParam  String title,
                                                  @RequestParam (required = false) Integer year) throws JsonProcessingException {

        String response = restClient.getMovieDetail(title, year);

        if (response.contains("Error")){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return  new ResponseEntity<>(Utils.convertResponse(response), HttpStatus.OK);
    }
}
