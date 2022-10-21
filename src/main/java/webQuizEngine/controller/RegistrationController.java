package webQuizEngine.controller;

import webQuizEngine.entity.User;
import webQuizEngine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final UserService userService;
    @Autowired
    public RegistrationController(UserService service) {
        this.userService = service;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteMyAccount(Authentication auth) {
        return userService.deleteMyAccount(auth.getName());
    }
}
