package com.rahulpateldev.controller;

import com.rahulpateldev.exception.custom.TokenRefreshException;
import com.rahulpateldev.model.entity.RefreshToken;
import com.rahulpateldev.model.entity.User;
import com.rahulpateldev.model.request.UserRequest;
import com.rahulpateldev.model.response.UserResponse;
import com.rahulpateldev.service.JwtTokenService;
import com.rahulpateldev.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate-user")
    public ResponseEntity<UserResponse> authenticateUser(
            @Valid @RequestBody UserRequest request) {
        User userDbResult = (User) this.userService.loadUserByUsername(request.getUsername());
        this.userService.isUserValid(userDbResult);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        RefreshToken refreshToken = this.jwtTokenService
                .createRefreshToken(userDbResult);

        UserResponse response = UserResponse
                .builder()
                .accessToken(this.jwtTokenService.createToken(userDbResult))
                .refreshToken(refreshToken.getToken())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody UserRequest request) {
        this.userService.userRegistration(request);
        return this.authenticateUser(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody UserRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        UserResponse token = new UserResponse();
        token.setRefreshToken(requestRefreshToken);
        return jwtTokenService.findByToken(requestRefreshToken)
                .map(jwtTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    token.setAccessToken(jwtTokenService.createToken(user));
                    return ResponseEntity.ok(token);
                }).orElseThrow(
                        () -> new TokenRefreshException(requestRefreshToken,
                                "Refresh token is not in database!"));
    }
}
