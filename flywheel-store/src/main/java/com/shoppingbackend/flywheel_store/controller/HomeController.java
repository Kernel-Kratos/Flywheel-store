package com.shoppingbackend.flywheel_store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingbackend.flywheel_store.response.ApiResponse;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<ApiResponse> home() {
        return ResponseEntity.ok(new ApiResponse("Welcome to Flywheel Store API! Base Url: /api/v1 ", null));
    }
    @GetMapping("/api/v1")
    public ResponseEntity<ApiResponse> baseEndpoint() {
        return ResponseEntity.ok(new ApiResponse("Start exploring the controller to get endpoints along with appropriate request", null));
    }
}
