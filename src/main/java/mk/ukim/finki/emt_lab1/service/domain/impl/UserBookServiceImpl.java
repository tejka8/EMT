package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.domain.UserBook;
import mk.ukim.finki.emt_lab1.repository.BookRepository;
import mk.ukim.finki.emt_lab1.repository.UserBookRepository;
import mk.ukim.finki.emt_lab1.service.domain.UserBookService;
import org.springframework.stereotype.Service;

@Service
public class UserBookServiceImpl implements UserBookService {
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;

    public UserBookServiceImpl(UserBookRepository userBookRepository, BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
    }

    public List<UserBook> findUserBooksByBookId(Long bookId) {
        return this.userBookRepository.findById(bookId).isPresent() ? this.userBookRepository.findUserBooksByBookId(bookId) : null;
    }

    public Optional<UserBook> rentUserBooks(Long bookId, String username) {
        Book book = (Book)this.bookRepository.findById(bookId).orElseThrow(() -> {
            return new RuntimeException("Book not found");
        });
        if (bookId != null && book.getAvaliableCopies() > 0) {
            book.setAvaliableCopies(book.getAvaliableCopies() - 1);
            this.bookRepository.save(book);
            return Optional.of((UserBook)this.userBookRepository.save(new UserBook(bookId, username)));
        } else {
            return Optional.empty();
        }
    }

    public List<UserBook> findAllByBookId(Long bookId) {
        return this.userBookRepository.findById(bookId).isPresent() ? this.userBookRepository.findUserBooksByBookId(bookId) : null;
    }

    public User findMostActiveUser() {
        return this.userBookRepository.findMostActiveUser();
    }

    public Optional<Book> findMostRentedBook() {
        Long bookId = this.userBookRepository.findMostRentedBook();
        return bookId != null ? this.bookRepository.findById(bookId) : Optional.empty();
    }

    public Author findMostRentedAuthor() {
        return null;
    }
}
