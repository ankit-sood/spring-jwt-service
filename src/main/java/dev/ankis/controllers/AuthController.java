package dev.ankis.controllers;

import dev.ankis.entities.User;
import dev.ankis.requests.LoginUserRequest;
import dev.ankis.requests.RegisterUserRequest;
import dev.ankis.responses.LoginResponse;
import dev.ankis.services.AuthenticationService;
import dev.ankis.services.CacheDataService;
import dev.ankis.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;
    private final CacheDataService cacheDataService;

//    @PostMapping("/sign-up")
//    public ResponseEntity<User> register(@RequestBody RegisterUserRequest inputRequest){
//        User registeredUser = authenticationService.signup(inputRequest);
//        return ResponseEntity.ok(registeredUser);
//    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody RegisterUserRequest inputRequest){
        return authenticationService.signup(inputRequest);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> register(@RequestBody LoginUserRequest inputRequest){
        User authenticatedUser = authenticationService.authenticate(inputRequest);
        String jwt = jwtService.generateToken(authenticatedUser);
        cacheDataService.saveData("token-"+inputRequest.getEmail(), jwt);
        return ResponseEntity.ok(LoginResponse.builder()
                .token(jwt)
                .expiresIn(jwtService.getExpirationTime())
                .build());
    }
}
