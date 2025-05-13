
package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.List;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.enumerations.Role;
import mk.ukim.finki.emt_lab1.model.exceptions.InvalidArugemtsException;
import mk.ukim.finki.emt_lab1.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.emt_lab1.model.exceptions.UserNotFoundException;
import mk.ukim.finki.emt_lab1.repository.UserRepository;
import mk.ukim.finki.emt_lab1.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails)this.userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException(username);
        });
    }

    public User findByUsername(String username) {
        return (User)this.userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException(username);
        });
    }

    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            if (!password.equals(repeatPassword)) {
                throw new IllegalArgumentException("Passwords do not match.");
            } else if (this.userRepository.findByUsername(username).isPresent()) {
                throw new IllegalArgumentException("Username already exists: " + username);
            } else {
                User user = new User(username, this.passwordEncoder.encode(password), name, surname, userRole);
                return (User)this.userRepository.save(user);
            }
        } else {
            throw new IllegalArgumentException("Invalid username or password.");
        }
    }

    public User login(String username, String password) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = (User)this.userRepository.findByUsername(username).orElseThrow(() -> {
                return new UsernameNotFoundException(username);
            });
            if (!this.passwordEncoder.matches(password, user.getPassword())) {
                throw new InvalidUserCredentialsException();
            } else {
                return user;
            }
        } else {
            throw new InvalidArugemtsException();
        }
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
