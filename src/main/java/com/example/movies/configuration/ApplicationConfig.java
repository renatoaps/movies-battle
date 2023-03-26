package com.example.movies.configuration;

import com.example.movies.httpclient.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestClient prepareHttpClient(){
        return new RestClient();
    }
}
