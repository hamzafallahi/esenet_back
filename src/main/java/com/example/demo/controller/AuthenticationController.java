package com.example.demo.controller;




import com.example.demo.entity.Members;
import com.example.demo.service.impl.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody Members request) throws MessagingException, IOException {
        service.register(request);

        return ResponseEntity.ok("Verification email sent.");
    }










}
