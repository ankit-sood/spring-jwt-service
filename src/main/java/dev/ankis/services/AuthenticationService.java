package dev.ankis.services;

import dev.ankis.entities.User;
import dev.ankis.repositories.UserRepository;
import dev.ankis.requests.LoginUserRequest;
import dev.ankis.requests.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CacheDataService cacheDataService;

    public User signup(RegisterUserRequest inputRequest){
        User user = new User()
                .setFullName(inputRequest.getFullName())
                .setEmail(inputRequest.getEmail())
                .setPassword(passwordEncoder.encode(inputRequest.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserRequest inputRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        inputRequest.getEmail(),
                        inputRequest.getPassword()
                )
        );
        return userRepository.findByEmail(inputRequest.getEmail()).orElseThrow();
    }
}
