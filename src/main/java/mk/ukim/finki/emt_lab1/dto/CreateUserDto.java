package mk.ukim.finki.emt_lab1.dto;

import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.enumerations.Role;

public record CreateUserDto(String username, String password, String repeatPassword, String name, String surname, Role role) {
    public CreateUserDto(String username, String password, String repeatPassword, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User toUser() {
        return new User(this.username, this.password, this.name, this.surname, this.role);
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public String repeatPassword() {
        return this.repeatPassword;
    }

    public String name() {
        return this.name;
    }

    public String surname() {
        return this.surname;
    }

    public Role role() {
        return this.role;
    }
}