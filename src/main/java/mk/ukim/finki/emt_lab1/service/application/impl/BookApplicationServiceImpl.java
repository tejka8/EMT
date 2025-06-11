package mk.ukim.finki.emt_lab1.service.application.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CreateBookDto;
import mk.ukim.finki.emt_lab1.dto.UpdateBookDto;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;
import mk.ukim.finki.emt_lab1.service.application.BookApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.AuthorDomainService;
import mk.ukim.finki.emt_lab1.service.domain.BookDomainService;
import org.springframework.stereotype.Service;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    private final BookDomainService bookDomainService;
    private final AuthorDomainService authorDomainService;

    public BookApplicationServiceImpl(BookDomainService bookDomainService, AuthorDomainService authorDomainService) {
        this.bookDomainService = bookDomainService;
        this.authorDomainService = authorDomainService;
    }

    public List<Book> findAll() {
        return bookDomainService.findAll();
    }


    public Optional<Book> findById(Long id) {
        return bookDomainService.findById(id);
    }

    public Optional<CreateBookDto> save(CreateBookDto createBookDto) {
        Author author = authorDomainService.findById(createBookDto.author()).orElseThrow(()->new RuntimeException("Author not found"));

        return bookDomainService
                .save(createBookDto.toBook(author,Category.valueOf(createBookDto.category())))
                .map(CreateBookDto::from);
    }


    public Optional<UpdateBookDto> update(Long id, UpdateBookDto updateBookDto) {
        Author author = authorDomainService.findById(updateBookDto.author()).orElseThrow(()->new RuntimeException("Author not found"));
        Category category= Category.valueOf(updateBookDto.category());

        Book book = bookDomainService.findById(id).orElseThrow(()->new RuntimeException("Book not found"));

        return bookDomainService
                .update(id,updateBookDto.toBook(book,author,category))
                .map(UpdateBookDto::from);
    }
    public Optional<Book> markAsRented(Long id) {
        return null;
    }


    public void deleteById(Long id) {
       bookDomainService.deleteById(id);
    }

    public List<UpdateBookDto> getAvaliableBooks() {
        return UpdateBookDto.from(this.bookDomainService.getAvaliableCopies());
    }
}
