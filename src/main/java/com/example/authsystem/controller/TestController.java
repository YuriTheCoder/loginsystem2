package com.example.authsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Test Endpoints", description = "Test endpoints for role-based access control")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
    
    @Operation(summary = "Public endpoint", description = "Accessible to everyone")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/public")
    public String publicAccess() {
        return "Public Content - Accessible to everyone!";
    }
    
    @Operation(summary = "User endpoint", description = "Accessible to authenticated users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content - Accessible to users with USER or ADMIN role!";
    }
    
    @Operation(summary = "Admin endpoint", description = "Accessible to admin users only")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Content - Accessible to users with ADMIN role only!";
    }
}