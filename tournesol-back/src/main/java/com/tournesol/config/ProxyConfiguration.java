package com.tournesol.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "proxy")
@Profile("proxy")
public class ProxyConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyConfiguration.class);

    private String host;

    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @PostConstruct
    private void setupProxy() {
        LOGGER.debug("########################################");
        LOGGER.debug("## ENABLING PROXY " + host + ":" + port + " ##");
        LOGGER.debug("########################################");
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port);
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", port);
    }
}
