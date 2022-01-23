package com.amteam.managementmicroservice.services;


import com.amteam.managementmicroservice.config.JwtTokenUtil;
import com.amteam.managementmicroservice.controllers.Token;
import com.amteam.managementmicroservice.entities.*;
import com.amteam.managementmicroservice.repositories.AdminRepository;
import com.amteam.managementmicroservice.repositories.ClientRepository;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {
    private final JwtTokenUtil jwtTokenUtil;
    private final ClientRepository clientRepository;
    private final InstructorRepository instructorRepository;
    private final AdminRepository adminRepository;

    public LoginService(ClientRepository clientRepository, InstructorRepository instructorRepository, AdminRepository adminRepository, JwtTokenUtil jwtTokenUtil) {
        this.clientRepository = clientRepository;
        this.instructorRepository = instructorRepository;
        this.adminRepository = adminRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    private Token createJwt(int role, Long loggedUserId){
        TokenSubject tokenSubject = new TokenSubject(role, loggedUserId, (new Date(System.currentTimeMillis())).toString(), JwtTokenUtil.JWT_TOKEN_VALIDITY);
        String token = null;
        try {
            token = jwtTokenUtil.createToken(tokenSubject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Token(token);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        Client client = clientRepository.findByUserName(userName);
        if (client != null) {
            if (!loginRequest.getPassword().equals(client.getPassword()))
                return new ResponseEntity<>(new ErrorMessage("Invalid password!"), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(createJwt(3, client.getId()), HttpStatus.OK);
        }
        Instructor instructor = instructorRepository.findByUserName(userName);
        if (instructor != null) {
            if (!loginRequest.getPassword().equals(instructor.getPassword()))
                return new ResponseEntity<>(new ErrorMessage("Invalid password!"), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(createJwt(2, instructor.getId()), HttpStatus.OK);
        }
        Admin admin = adminRepository.findByUserName(userName);
        if (admin != null) {
            if (!loginRequest.getPassword().equals(admin.getPassword()))
                return new ResponseEntity<>(new ErrorMessage("Invalid password!"), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(createJwt(1, admin.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMessage("No user with this username found!"), HttpStatus.NOT_FOUND);
    }

}
