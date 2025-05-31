package com.AnimeApp.web.controller;

import com.AnimeApp.model.dto.LoginBody;
import com.AnimeApp.model.dto.LoginResponse;
import com.AnimeApp.model.dto.RegistrationBody;
import com.AnimeApp.model.exceptions.AuthorAlreadyExistsException;
import com.AnimeApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (AuthorAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);

        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

//    @GetMapping("/login/google")
//    public String loginWithGoogle(String googleEmail) {
//        // Call the service to log in or register the user with Google email
//        String jwtToken = userService.loginWithGoogle(googleEmail);
//        if (jwtToken != null) {
//            return jwtToken; // Send the JWT back to the client
//        }
//        return "Error logging in with Google.";
//    }
}
