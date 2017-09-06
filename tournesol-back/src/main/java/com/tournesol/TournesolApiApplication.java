package com.tournesol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TournesolApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournesolApiApplication.class, args);
        setupProxy(true);
    }

    private static void setupProxy(boolean setProxy) {
        // TODO: Externalize literal constants in application.props
        if (setProxy) {
            String PROXY_HOST = "zscaler-paris.pj.fr";
            String PROXY_HTTP_PORT = "80";
            String PROXY_HTTPS_PORT = PROXY_HTTP_PORT;
            //            String username = "gtouati";
            //            String password = "urgpt9ZM#";

            System.setProperty("http.proxyHost", PROXY_HOST);
            System.setProperty("http.proxyPort", PROXY_HTTP_PORT);
            System.setProperty("https.proxyHost", PROXY_HOST);
            System.setProperty("https.proxyPort", PROXY_HTTPS_PORT);
            //            Authenticator.setDefault(new Authenticator() {
            //                @Override
            //                public PasswordAuthentication getPasswordAuthentication() {
            //                    return new PasswordAuthentication(username, password.toCharArray());
            //                }
            //            });
        } else {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
            //            Authenticator.setDefault(null);
        }

        //-Dhttps.proxySet=true -Dhttp.proxySet=true -Dhttp.proxyHost=zscaler-paris.pj.fr -Dhttp.proxyPort=80 -Dhttp.proxyUser=gtouati -Dhttp.proxyPassword=urgpt9ZM# -Dhttps.proxyHost=zscaler-paris.pj.fr -Dhttps.proxyPort=80 -Dhttps.proxyUser=gtouati -Dhttps.proxyPassword=urgpt9ZM#

    }
}
