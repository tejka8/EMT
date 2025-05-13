package mk.ukim.finki.emt_lab1.dto;

public record LogInUserDto(String username, String password) {
    public LogInUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }
}
