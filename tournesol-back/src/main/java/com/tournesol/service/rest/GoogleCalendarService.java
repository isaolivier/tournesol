package com.tournesol.service.rest;

import com.tournesol.bean.ClientBean;
import com.tournesol.service.rest.entity.RDV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Created by redeyed on 7/23/17.
 */
@Service
public class GoogleCalendarService {
    @Autowired
    RestTemplate restTemplate;

    public Collection<RDV> getRDV (Principal principal){
        String email = (String)((LinkedHashMap) (((OAuth2Authentication) principal).getUserAuthentication().getDetails())).get("email");
        OAuth2AuthenticationDetails authDetails = (OAuth2AuthenticationDetails)((OAuth2Authentication) principal).getDetails();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", String.format("%s %s", authDetails.getTokenType(), authDetails.getTokenValue()));
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<String> request = new HttpEntity<String>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange("https://apidata.googleusercontent.com/caldav/v2/" + email + "/events", HttpMethod.GET, request, String.class);
        System.out.println("#### post response = " + result);

        return Collections.emptyList();
    }
}
