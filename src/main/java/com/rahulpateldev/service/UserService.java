package com.rahulpateldev.service;


import com.rahulpateldev.enums.Messages;
import com.rahulpateldev.exception.custom.UserAlreadyExistsException;
import com.rahulpateldev.exception.custom.UserRegistrationException;
import com.rahulpateldev.model.entity.User;
import com.rahulpateldev.model.request.UserRequest;
import com.rahulpateldev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserDetails> userDetails = this.userRepository.findUserByUsername(username);
        return userDetails.orElseThrow(() -> new UsernameNotFoundException(
                Messages.USER_NOT_FOUND.getMessage().formatted(username)));
    }

    public void isUserValid(UserDetails userDetails) {
        new AccountStatusUserDetailsChecker().check(userDetails);
    }

    public User userRegistration(UserRequest request) {
        Optional<UserDetails> userDetails = this.userRepository
                .findUserByUsername(request.getUsername());
        if (userDetails.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User user = User
                .builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(this.encoder.encode(request.getPassword()))
                .age(request.getAge())
                .roles(request.getRoles())
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .build();
        Optional<User> savedUser = Optional.of(this.userRepository.save(user));
        return savedUser.orElseThrow(
                () -> new UserRegistrationException(
                        request.getUsername(), "Please try again!"));
    }

}
