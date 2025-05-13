package mk.ukim.finki.emt_lab1.dto;

import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.enumerations.Role;

public record UpdateUserDto(String username, String name, String surname, Role role) {
    public UpdateUserDto(String username, String name, String surname, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public static UpdateUserDto from(User user) {
        return new UpdateUserDto(user.getUsername(), user.getName(), user.getSurname(), user.getRole());
    }

    public User toUser() {
        return new User(this.username, this.name, this.surname, this.role.name());
    }

    public String username() {
        return this.username;
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