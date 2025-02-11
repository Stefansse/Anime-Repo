//package com.AnimeApp.service;
//
//import com.AnimeApp.model.User;
//import com.AnimeApp.repository.UserRepository;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final EncryptionService encryptionService;
//    private final JWTService jwtService;
//
//    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
//        this.userRepository = userRepository;
//        this.encryptionService = encryptionService;
//        this.jwtService = jwtService;
//    }
//
//    public String loginWithGoogle(String googleEmail) {
//        Optional<User> opUser = userRepository.findByEmailIgnoreCase(googleEmail);
//        if (opUser.isPresent()) {
//            User user = opUser.get();
//            return jwtService.generateJWT(user); // Generate JWT for the authenticated user
//        }
//        return null; // User doesn't exist, you can handle this by registering the user or returning an error
//    }
//
//    public String registerUserWithGoogle(String googleEmail) {
//        Optional<User> opUser = userRepository.findByEmailIgnoreCase(googleEmail);
//        if (opUser.isPresent()) {
//            return jwtService.generateJWT(opUser.get()); // User already exists, just return JWT
//        }
//
//        User user = new User();
//        user.setEmail(googleEmail);
//        user.setUsername(googleEmail.split("@")[0]); // Default username from email prefix
//        // Set other user details (you can update this to use data from Google if needed)
//        user.setPassword(encryptionService.encryptPassword("defaultPassword")); // Set a default password or use a passwordless system
//        user.setConfirmPassword("defaultPassword");
//        user = userRepository.save(user);
//
//        return jwtService.generateJWT(user); // Generate and return JWT for the new user
//    }
//}
