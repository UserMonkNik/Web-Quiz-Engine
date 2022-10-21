package webQuizEngine.service;

import webQuizEngine.entity.User;
import webQuizEngine.exception.EmailAlreadyExistException;
import webQuizEngine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsById(user.getEmail())) {
            throw new EmailAlreadyExistException(user.getEmail() + " already exists, please try to log in or choose another email.");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteMyAccount(String currentUser) {
        userRepository.deleteById(currentUser);
        return new ResponseEntity<>("Account have been removed", HttpStatus.NO_CONTENT);
    }
}
