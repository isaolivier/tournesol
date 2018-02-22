package com.tournesol;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2
public class TournesolApiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournesolApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TournesolApiApplication.class, args);
    }
}
