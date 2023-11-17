package com.example.autentificare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class MainController {

    @GetMapping("/welcome")
    public String welcome(Authentication authentication) {
        return "Welcome " + authentication.getName();
    }

    @GetMapping("/anyOtherEndpont")
    public String anyOtherRequest() {
        return "anyOtherEndpoint";
    }

    @GetMapping("/delete")
    public String deleteResource() {
        return "You are allowed here because you have DELETE_AUTHORITY assigned";
    }

    @GetMapping("/writeOrUpdate")
    public String writeOrUpdate(){
        return "You are allowed here because you have WRITE_AUTHORITY and UPDATE_AUTHORITY assigned";
    }
}
