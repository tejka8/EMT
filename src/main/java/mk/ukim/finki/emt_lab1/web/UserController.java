
package mk.ukim.finki.emt_lab1.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import mk.ukim.finki.emt_lab1.dto.CreateUserDto;
import mk.ukim.finki.emt_lab1.dto.LogInUserDto;
import mk.ukim.finki.emt_lab1.dto.LoginResponseDto;
import mk.ukim.finki.emt_lab1.dto.UpdateUserDto;
import mk.ukim.finki.emt_lab1.model.exceptions.InvalidArugemtsException;
import mk.ukim.finki.emt_lab1.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.emt_lab1.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.emt_lab1.service.application.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/user"})
@Tag(
        name = "User API",
        description = "Endpoints for user registration, login, and session management"
)
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(
            summary = "List all users",
            description = "Returns a list of all registered users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users listed successfully"
    )
    @GetMapping({"/all"})
    public ResponseEntity<List<UpdateUserDto>> findAllUsers() {
        List<UpdateUserDto> user = this.userApplicationService.findAll();
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "User registered successfully"
    ), @ApiResponse(
            responseCode = "400",
            description = "Invalid input or passwords do not match"
    )})
    @PostMapping({"/register"})
    public ResponseEntity<UpdateUserDto> register(@RequestBody CreateUserDto createUserDto) {
        try {
            return (ResponseEntity)this.userApplicationService.register(createUserDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (PasswordsDoNotMatchException | InvalidArugemtsException var3) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user and generates a JWT"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "User authenticated successfully"
    ), @ApiResponse(
            responseCode = "404",
            description = "Invalid username or password"
    )})
    @PostMapping({"/login"})
    public ResponseEntity<LoginResponseDto> login(@RequestBody LogInUserDto loginUserDto) {
        try {
            return (ResponseEntity)this.userApplicationService.login(loginUserDto).map(ResponseEntity::ok).orElseThrow(InvalidUserCredentialsException::new);
        } catch (InvalidUserCredentialsException var3) {
            return ResponseEntity.notFound().build();
        }
    }
}
