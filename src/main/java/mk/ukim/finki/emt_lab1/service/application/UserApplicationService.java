
package mk.ukim.finki.emt_lab1.service.application;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CreateUserDto;
import mk.ukim.finki.emt_lab1.dto.LogInUserDto;
import mk.ukim.finki.emt_lab1.dto.LoginResponseDto;
import mk.ukim.finki.emt_lab1.dto.UpdateUserDto;

public interface UserApplicationService {
    Optional<UpdateUserDto> register(CreateUserDto createUserDto);

    Optional<LoginResponseDto> login(LogInUserDto loginUserDto);

    Optional<UpdateUserDto> findByUsername(String username);

    List<UpdateUserDto> findAll();
}
