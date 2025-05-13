
package mk.ukim.finki.emt_lab1.service.application;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CreateBookDto;
import mk.ukim.finki.emt_lab1.dto.UpdateBookDto;
import mk.ukim.finki.emt_lab1.model.domain.Book;

public interface BookApplicationService {
    List<Book> findAll();

    Optional<CreateBookDto> save(CreateBookDto createBookDto);

    Optional<Book> findById(Long id);

    Optional<Book> markAsRented(Long id);

    Optional<UpdateBookDto> update(Long id, UpdateBookDto updateBookDto);

    void deleteById(Long id);

    List<UpdateBookDto> getAvaliableBooks();
}
