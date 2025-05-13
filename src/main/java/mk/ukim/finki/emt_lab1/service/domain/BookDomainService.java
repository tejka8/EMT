
package mk.ukim.finki.emt_lab1.service.domain;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;

public interface BookDomainService {
    List<Book> findAll();

    Optional<Book> save(Book book);

    Optional<Book> findById(Long id);

    Optional<Book> markAsRented(Long id);

    Optional<Book> update(Long id, Book book);

    void deleteById(Long id);

    void rerefreshMaterializedView();

    List<Book> findAllByCategory(Category category);

    List<Book> getAvaliableCopies();

    List<Book> searchBooksByName(String fragment);
}
