package com.kbertv.edgeapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class EdgeApiController {

    @GetMapping("/")
    public String index(Principal principal) {
        return principal.getName();
    }


}
