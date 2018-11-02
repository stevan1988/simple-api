package com.todcode.api.api.v1.controller;

import com.todcode.api.api.v1.dto.request.UserLoginRequest;
import com.todcode.api.api.v1.dto.request.UserRegistrationRequest;
import com.todcode.api.api.v1.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/user")
    public String getUnauthorized() {
        return "";
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return authenticationService.login(userLoginRequest);
    }

    @PostMapping ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return authenticationService.signUp(userRegistrationRequest);
    }
}
