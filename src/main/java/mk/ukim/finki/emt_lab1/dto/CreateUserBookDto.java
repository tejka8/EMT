package mk.ukim.finki.emt_lab1.dto;

import mk.ukim.finki.emt_lab1.model.domain.UserBook;

public record CreateUserBookDto(Long bookId, String username) {
    public CreateUserBookDto(Long bookId, String username) {
        this.bookId = bookId;
        this.username = username;
    }

    public UserBook toUserBook() {
        return new UserBook(this.bookId, this.username);
    }

    public Long bookId() {
        return this.bookId;
    }

    public String username() {
        return this.username;
    }
}
