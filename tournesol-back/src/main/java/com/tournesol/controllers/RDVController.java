package com.tournesol.controllers;

import com.tournesol.bean.ClientBean;
import com.tournesol.bean.EntrepriseBean;
import com.tournesol.bean.RDVBean;
import com.tournesol.service.rest.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by redeyed on 7/23/17.
 */
@RestController
public class RDVController {
    @Autowired
    GoogleCalendarService calendarService;


    @GetMapping("/rdv")
    public Collection<RDVBean> greeting(Principal principal) {
        return calendarService.getRDV(principal).stream()
                .map(rdv -> {
                    //TODO: faire un mapping propre des proprietes du rdv vers un rdvbean
                    RDVBean rdvBean = new RDVBean();
                    rdvBean.setDebut(rdv.getDebut());
                    rdvBean.setFin(rdv.getFin());
                    return rdvBean;
                })
                .collect(Collectors.toSet());
    }
}
