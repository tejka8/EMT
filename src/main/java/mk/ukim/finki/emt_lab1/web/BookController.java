package mk.ukim.finki.emt_lab1.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.dto.BooksPerAuthorDto;
import mk.ukim.finki.emt_lab1.dto.CreateBookDto;
import mk.ukim.finki.emt_lab1.dto.UpdateBookDto;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.domain.UserBook;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;
import mk.ukim.finki.emt_lab1.repository.views.BooksPerAuthorViewRepository;
import mk.ukim.finki.emt_lab1.service.application.BookApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.BookDomainService;
import mk.ukim.finki.emt_lab1.service.domain.UserBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"http://localhost:3000"}
)
@RestController
@RequestMapping({"/api/books"})
@Tag(
        name = "Book API",
        description = "Endpoints for managing books"
)
public class BookController {
    private final BookApplicationService bookApplicationService;
    private final BookDomainService bookDomainService1;
    private final UserBookService userBookService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public BookController(BookApplicationService bookApplicationService, BookDomainService bookDomainService1, UserBookService userBookService, BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.bookApplicationService = bookApplicationService;
        this.bookDomainService1 = bookDomainService1;
        this.userBookService = userBookService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieves a list of all available books."
    )
    @GetMapping
    public List<Book> findAll() {
        return this.bookApplicationService.findAll();
    }

    @Operation(
            summary = "Get books by ID",
            description = "Finds a book by its ID."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return bookApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a new book",
            description = "Creates a new book based on the given CreateBookDto"
    )
    @PostMapping({"/add"})
    public ResponseEntity<CreateBookDto> save(@RequestBody CreateBookDto bookDto) {
       return bookApplicationService
               .save(bookDto)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update an existing book",
            description = "Updates a book by ID using UpdateBookDto."
    )
    @PutMapping({"/edit/{id}"})
    public ResponseEntity<UpdateBookDto> update(@PathVariable Long id, @RequestBody UpdateBookDto bookDto) {
        return bookApplicationService
                .update(id,bookDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its ID."
    )
    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
       bookApplicationService.deleteById(id) ;
       return ResponseEntity.noContent().build();
    }

    @PutMapping({"/rent/{id}"})
    public void markBookAsRented(@PathVariable Long id) {
       return;
    }

    @PostMapping({"/save_userbook/{bookId}"})
    public ResponseEntity<UserBook> saveUserBook(@PathVariable Long bookId, String username) {
        return (ResponseEntity)this.userBookService.rentUserBooks(bookId, username).map((b) -> {
            return ResponseEntity.ok().body(b);
        }).orElseGet(() -> {
            return ResponseEntity.badRequest().build();
        });
    }

    @GetMapping({"/findby/{bookId}"})
    public List<UserBook> findUserBooksByBookId(@PathVariable Long bookId) {
        return this.userBookService.findUserBooksByBookId(bookId);
    }

    @Operation(
            summary = "Get the most rented book",
            description = "Returns the most frequently rented book."
    )
    @GetMapping({"/most-rented"})
    Optional<Book> findMostRentedBook() {
        return this.userBookService.findMostRentedBook();
    }

    @Operation(
            summary = "Get number of books per author",
            description = "Materialized view, refresh scheduled."
    )
    @GetMapping({"/by-author"})
    public List<BooksPerAuthorDto> getBooksPerAuthor() {
        return this.booksPerAuthorViewRepository.findAll().stream().map((view) -> {
            return new BooksPerAuthorDto(view.getAuthorId(), view.getNumBooks());
        }).toList();
    }

    @GetMapping({"/by-category"})
    public ResponseEntity<List<Book>> getBooksByCategory(@RequestParam Category category) {
        List<Book> books = this.bookDomainService1.findAllByCategory(category);
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @Operation(
            summary = "Get books by availability",
            description = "Retrieves a list of books that are currently available for borrowing."
    )
    @GetMapping({"/available"})
    public ResponseEntity<List<UpdateBookDto>> getAvailableBooks() {
        List<UpdateBookDto> books = this.bookApplicationService.getAvaliableBooks();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @GetMapping({"/search"})
    public List<Map<String, Object>> searchBooks(@RequestParam("q") String query) {
        return (List)this.bookDomainService1.searchBooksByName(query).stream().map((book) -> {
            Map<String, Object> map = new HashMap();
            map.put("id", book.getId());
            map.put("title", book.getName());
            map.put("author", book.getAuthor().getName());
            return map;
        }).collect(Collectors.toList());
    }
}
