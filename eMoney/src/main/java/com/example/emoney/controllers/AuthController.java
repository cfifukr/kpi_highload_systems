package com.example.emoney.controllers;


import com.example.emoney.dtos.AuthenticationResponseDto;
import com.example.emoney.dtos.RegistrationDto;
import com.example.emoney.dtos.UserReponseDto;
import com.example.emoney.exceptions.ExceptionDto;
import com.example.emoney.models.User;
import com.example.emoney.services.JwtService;
import com.example.emoney.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;


    @GetMapping("/user")
    public ResponseEntity<?>  getUser(@RequestHeader (HttpHeaders.AUTHORIZATION) String authToken){
        String username = jwtService.extractUsername(authToken.substring(7));

        User user = userService.findByUsername(username);

        return ResponseEntity.ok(UserReponseDto.getDto(user));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto){


        User user = userService.registration(registrationDto);

        if(user == null){
            return new ResponseEntity<>(new ExceptionDto(HttpStatus.IM_USED.value(), "User with such username was already created"), HttpStatus.OK);
        }

        String token = jwtService.generateToken(user);

        return new ResponseEntity<>(new AuthenticationResponseDto(token), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginDto){

        User user = userService.login(loginDto.get("username"), loginDto.get("password"));
        if(user == null){
            return ResponseEntity.ok(new ExceptionDto(HttpStatus.NOT_FOUND.value(),
                    String.format("User with username: '%s' and password wasn't found", loginDto.get("username"))));
        }
        String token = jwtService.generateToken(user);


        return new ResponseEntity<>(new AuthenticationResponseDto(token), HttpStatus.OK);

    }
}
