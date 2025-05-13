package mk.ukim.finki.emt_lab1.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    LIBRARIAN;

    private Role() {
    }

    public String getAuthority() {
        return this.name();
    }
}
