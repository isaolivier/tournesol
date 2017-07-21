package com.tournesol.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by draluy on 19/07/2017.
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
