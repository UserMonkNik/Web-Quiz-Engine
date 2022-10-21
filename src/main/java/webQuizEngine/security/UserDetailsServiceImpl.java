package webQuizEngine.security;

import webQuizEngine.entity.User;
import webQuizEngine.exception.UserNotFoundException;
import webQuizEngine.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findById(username);

        if (optionalUser.isPresent()) {
            return new UserDetailsImpl(optionalUser.get());
        }

        throw new UserNotFoundException(username + " not found, please try another email.");
    }
}
