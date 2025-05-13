
package mk.ukim.finki.emt_lab1.service.application.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CreateUserDto;
import mk.ukim.finki.emt_lab1.dto.LogInUserDto;
import mk.ukim.finki.emt_lab1.dto.LoginResponseDto;
import mk.ukim.finki.emt_lab1.dto.UpdateUserDto;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.security.JwtHelper;
import mk.ukim.finki.emt_lab1.service.application.UserApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    public Optional<UpdateUserDto> register(CreateUserDto createUserDto) {
        User user = this.userService.register(createUserDto.username(), createUserDto.password(), createUserDto.repeatPassword(), createUserDto.name(), createUserDto.surname(), createUserDto.role());
        return Optional.of(UpdateUserDto.from(user));
    }

    public Optional<LoginResponseDto> login(LogInUserDto loginUserDto) {
        User user = this.userService.login(loginUserDto.username(), loginUserDto.password());
        String token = this.jwtHelper.generateToken(user);
        return Optional.of(new LoginResponseDto(token));
    }

    public Optional<UpdateUserDto> findByUsername(String username) {
        return Optional.of(UpdateUserDto.from(this.userService.findByUsername(username)));
    }

    public List<UpdateUserDto> findAll() {
        return this.userService.findAll().stream().map(UpdateUserDto::from).toList();
    }
}
