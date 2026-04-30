package com.example.identity_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean isAuthenticated = authenticationService.authenticate(request);
        // builder pattern giúp code gọn hơn, dễ đọc hơn, tránh lỗi khi có nhiều field
        return ApiResponse.<AuthenticationResponse>builder() // Start building ApiResponse
                .result( // Set the result field
                        AuthenticationResponse.builder() // Start building AuthenticationResponse
                                .authenticated(isAuthenticated) // Set authenticated field
                                .build()) // Finish AuthenticationResponse
                .build(); // Finish ApiResponse
    }
}