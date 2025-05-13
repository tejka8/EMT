
package mk.ukim.finki.emt_lab1.dto;

public record LoginResponseDto(String token) {
    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String token() {
        return this.token;
    }
}
