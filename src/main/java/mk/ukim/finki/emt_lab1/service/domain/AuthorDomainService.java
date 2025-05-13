package mk.ukim.finki.emt_lab1.service.domain;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Author;

public interface AuthorDomainService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<Author> save(Author author);

    Optional<Author> update(Long id, Author author);

    void deleteById(Long id);

    void refreshMaterializedView();
}
