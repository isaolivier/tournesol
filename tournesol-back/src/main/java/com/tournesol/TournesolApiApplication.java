package com.tournesol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TournesolApiApplication {

    private static final Logger  LOGGER        = LoggerFactory.getLogger(TournesolApiApplication.class);

    private static final Boolean PROXY_ENABLED = false;

    public static void main(String[] args) {
        SpringApplication.run(TournesolApiApplication.class, args);
        setupProxy();
    }

    private static void setupProxy() {
        if (PROXY_ENABLED) {
            LOGGER.debug("####################");
            LOGGER.debug("## ENABLING PROXY ##");
            LOGGER.debug("####################");
            String PROXY_HOST = "zscaler-paris.pj.fr";
            String PROXY_HTTP_PORT = "80";
            String PROXY_HTTPS_PORT = PROXY_HTTP_PORT;

            System.setProperty("http.proxyHost", PROXY_HOST);
            System.setProperty("http.proxyPort", PROXY_HTTP_PORT);
            System.setProperty("https.proxyHost", PROXY_HOST);
            System.setProperty("https.proxyPort", PROXY_HTTPS_PORT);
        } else {
            LOGGER.debug("#####################");
            LOGGER.debug("## DISABLING PROXY ##");
            LOGGER.debug("#####################");
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        }
    }
}
