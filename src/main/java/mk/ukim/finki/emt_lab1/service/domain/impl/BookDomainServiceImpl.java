
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
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    public Optional<Book> save(Book bookDto) {
        if (bookDto.getAuthor() != null && bookDto.getCategory() != null) {
            Optional<Author> authorOptional = this.authorService.findById(bookDto.getAuthor().getId());
            if (authorOptional.isPresent()) {
                Book book = new Book(bookDto.getName(), Category.valueOf(bookDto.getCategory().toString()), (Author)authorOptional.get(), bookDto.getAvaliableCopies());
                return Optional.of((Book)this.bookRepository.save(book));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<Book> update(Long id, Book book) {
        return this.bookRepository.findById(id).map((existingBook) -> {
            if (book.getName() != null) {
                existingBook.setName(book.getName());
            }

            if (book.getAvaliableCopies() != null) {
                existingBook.setAvaliableCopies(book.getAvaliableCopies());
            }

            if (book.getCategory() != null) {
                existingBook.setCategory(Category.valueOf(book.getCategory().toString()));
            }

            if (book.getAuthor() != null && this.authorService.findById(book.getAuthor().getId()).isPresent()) {
                existingBook.setAuthor((Author)this.authorService.findById(book.getAuthor().getId()).get());
            }

            return (Book)this.bookRepository.save(existingBook);
        });
    }

    public Optional<Book> markAsRented(Long id) {
        Optional<Book> book1 = this.bookRepository.findById(id);
        if (book1.isPresent() && ((Book)book1.get()).getAvaliableCopies() > 0) {
            Book rented = (Book)book1.get();
            rented.setAvaliableCopies(rented.getAvaliableCopies() - 1);
            return Optional.of((Book)this.bookRepository.save(rented));
        } else {
            throw new RuntimeException("Book not available for rent");
        }
    }

    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
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
