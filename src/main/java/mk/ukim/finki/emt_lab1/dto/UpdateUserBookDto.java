package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.UserBook;

public record UpdateUserBookDto(Long id, Long bookId, String username) {
    public UpdateUserBookDto(Long id, Long bookId, String username) {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
    }

    public static UpdateUserBookDto from(UserBook userbook) {
        return new UpdateUserBookDto(userbook.getId(), userbook.getBookId(), userbook.getUsername());
    }

    public static List<UpdateUserBookDto> from(List<UserBook> userbooks) {
        return (List)userbooks.stream().map(UpdateUserBookDto::from).collect(Collectors.toList());
    }

    public Long id() {
        return this.id;
    }

    public Long bookId() {
        return this.bookId;
    }

    public String username() {
        return this.username;
    }
}
