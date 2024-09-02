package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(CsrfToken token) {
        return token;
    }

    @PostMapping("/validate-csrf-token")
    public ResponseEntity<String> isCsrfTokenValid(HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(token!=null){
            return ResponseEntity.ok().body("Valid CSRF token");
        }
        return ResponseEntity.badRequest().body("Invalid CSRF token");
    }


}

