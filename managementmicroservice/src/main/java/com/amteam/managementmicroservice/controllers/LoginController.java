package com.amteam.managementmicroservice.controllers;

import com.amteam.managementmicroservice.config.JwtTokenUtil;
import com.amteam.managementmicroservice.entities.LoginRequest;
import com.amteam.managementmicroservice.services.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginService loginService;
    public LoginController(LoginService loginService, JwtTokenUtil jwtTokenUtil) {
        this.loginService = loginService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAsClientOrInstructor(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
    @GetMapping("/whoAmI")
    public  ResponseEntity<?> whoAmI(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        return new ResponseEntity<>(jwtTokenUtil.getTokenSubject(token), HttpStatus.OK);
    }
}
