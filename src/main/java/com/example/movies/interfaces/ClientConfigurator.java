package com.example.movies.interfaces;

public interface ClientConfigurator {
    String prepareServiceUrl(String title, int year);
    String prepareServiceUrl(String titleId, String movieName);
    String prepareAuthentication(String uri);
}
