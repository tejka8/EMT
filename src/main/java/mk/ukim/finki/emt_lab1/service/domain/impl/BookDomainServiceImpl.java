
package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;
import mk.ukim.finki.emt_lab1.repository.BookRepository;
import mk.ukim.finki.emt_lab1.repository.views.BooksPerAuthorViewRepository;
import mk.ukim.finki.emt_lab1.service.domain.AuthorDomainService;
import mk.ukim.finki.emt_lab1.service.domain.BookDomainService;
import org.springframework.stereotype.Service;

@Service
public class BookDomainServiceImpl implements BookDomainService {
    private final BookRepository bookRepository;
    private final AuthorDomainService authorService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public BookDomainServiceImpl(BookRepository bookRepository, AuthorDomainService authorDomainService, BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorDomainService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {

        return bookRepository.findById(id);
    }

    public Optional<Book> save(Book bookDto) {
        return Optional.of(bookRepository.save(bookDto));
    }

    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id).map((existing)->{
           existing.setAvaliableCopies(book.getAvaliableCopies());
           existing.setName(book.getName());
           existing.setAuthor(book.getAuthor());
           existing.setCategory(book.getCategory());

           return bookRepository.save(existing);
        });
    }

    public Optional<Book> markAsRented(Long id) {
       return null;
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void rerefreshMaterializedView() {
        this.booksPerAuthorViewRepository.refreshMaterializedView();
    }

    public List<Book> findAllByCategory(Category category) {
        return this.bookRepository.findAllByCategory(category);
    }

    public List<Book> getAvaliableCopies() {
        return this.bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    public List<Book> searchBooksByName(String name) {
        return this.bookRepository.findAllByNameContainingIgnoreCase(name);
    }
}
