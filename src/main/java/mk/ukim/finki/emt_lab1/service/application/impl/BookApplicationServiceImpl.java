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
        return this.bookDomainService.findAll();
    }

    public Optional<CreateBookDto> save(CreateBookDto createBookDto) {
        if (createBookDto.author() != null && createBookDto.category() != null) {
            Optional<Author> authorOptional = this.authorDomainService.findById(createBookDto.author());
            if (authorOptional.isPresent()) {
                Category category = Category.valueOf(createBookDto.category().toUpperCase());
                Book book = createBookDto.toBook((Author)authorOptional.get(), category);
                return this.bookDomainService.save(book).map(CreateBookDto::from);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<Book> findById(Long id) {
        return this.bookDomainService.findById(id);
    }

    public Optional<Book> markAsRented(Long id) {
        return this.bookDomainService.findById(id).filter((book) -> {
            return book.getAvaliableCopies() > 0;
        }).flatMap((book) -> {
            book.setAvaliableCopies(book.getAvaliableCopies() - 1);
            return this.bookDomainService.save(book);
        });
    }

    public Optional<UpdateBookDto> update(Long id, UpdateBookDto updateBookDto) {
        Optional<Book> existingBook = this.bookDomainService.findById(id);
        if (existingBook.isEmpty()) {
            return Optional.empty();
        } else {
            Optional<Author> authorOptional = this.authorDomainService.findById(updateBookDto.author());
            if (authorOptional.isEmpty()) {
                return Optional.empty();
            } else {
                Category newCategory = Category.valueOf(updateBookDto.category().toUpperCase());
                Book bookToUpdate = (Book)existingBook.get();
                bookToUpdate.setName(updateBookDto.name());
                bookToUpdate.setAvaliableCopies(updateBookDto.avaliableCopies());
                bookToUpdate.setAuthor((Author)authorOptional.get());
                bookToUpdate.setCategory(newCategory);
                return this.bookDomainService.update(id, bookToUpdate).map(UpdateBookDto::from);
            }
        }
    }

    public void deleteById(Long id) {
        this.bookDomainService.deleteById(id);
    }

    public List<UpdateBookDto> getAvaliableBooks() {
        return UpdateBookDto.from(this.bookDomainService.getAvaliableCopies());
    }
}
