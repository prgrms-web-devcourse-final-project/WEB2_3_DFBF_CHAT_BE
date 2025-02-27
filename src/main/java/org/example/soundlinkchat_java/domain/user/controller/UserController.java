package org.example.soundlinkchat_java.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.example.soundlinkchat_java.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User API", description = "유저 관련 API")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "유저 조회", description = "유저 조회 API")
    public ResponseEntity<?> getUser(/*@AuthenticationPrincipal Long id*/) {
        return ResponseEntity.ok().body(userService.getUserDataWithoutRedis(1L));
    }
}
