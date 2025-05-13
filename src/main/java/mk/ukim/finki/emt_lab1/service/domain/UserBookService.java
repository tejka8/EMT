package mk.ukim.finki.emt_lab1.service.domain;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.domain.UserBook;

public interface UserBookService {
    List<UserBook> findUserBooksByBookId(Long bookId);

    Optional<UserBook> rentUserBooks(Long bookId, String username);

    List<UserBook> findAllByBookId(Long bookId);

    User findMostActiveUser();

    Optional<Book> findMostRentedBook();

    Author findMostRentedAuthor();
}
