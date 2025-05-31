package com.AnimeApp.service;

import com.AnimeApp.model.User;
import com.AnimeApp.model.dto.LoginBody;
import com.AnimeApp.model.dto.RegistrationBody;
import com.AnimeApp.model.exceptions.AuthorAlreadyExistsException;
import com.AnimeApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final EncryptionService encryptionService;

    private   JWTService jwtService;

    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }



    public User registerUser(RegistrationBody registrationBody) throws AuthorAlreadyExistsException {
        if (userRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
        || userRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new AuthorAlreadyExistsException();
        }
        User user = new User();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setDateOfBirth(registrationBody.getDateOfBirth());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setConfirmPassword(registrationBody.getConfirmPassword());
        return userRepository.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<User> opUser = userRepository.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
